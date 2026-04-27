package com.dong.multirun.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.CloneAppActivity
import com.dong.multirun.R
import com.dong.multirun.adapter.InstalledAppAdapter
import com.dong.multirun.clone.CloneEngine
import com.dong.multirun.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class InstalledAppFragment : Fragment() {

    private val TAG = "InstalledAppFragment"

    private lateinit var adapter: InstalledAppAdapter
    private lateinit var rvApps: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnInstall: Button
    private lateinit var tvEmpty: TextView

    private var showSystemApps = false

    companion object {
        private const val ARG_SYSTEM = "show_system"

        fun newInstance(showSystem: Boolean) = InstalledAppFragment().apply {
            arguments = Bundle().apply { putBoolean(ARG_SYSTEM, showSystem) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSystemApps = arguments?.getBoolean(ARG_SYSTEM, false) ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_app_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rvApps       = view.findViewById(R.id.rvApps)
        progressBar  = view.findViewById(R.id.progressBar)
        btnInstall   = view.findViewById(R.id.btnInstall)
        tvEmpty      = view.findViewById(R.id.tvEmpty)

        adapter = InstalledAppAdapter { selected ->
            if (selected.isNotEmpty()) {
                btnInstall.visibility = View.VISIBLE
                btnInstall.text = "Install to SandBox (${selected.size})"
            } else {
                btnInstall.visibility = View.GONE
            }
        }

        rvApps.layoutManager = LinearLayoutManager(requireContext())
        rvApps.adapter = adapter

        // FIX: wire button to the real install flow
        btnInstall.setOnClickListener { installSelected() }

        loadApps()
    }

    // -------------------------------------------------------------------------
    // MultiRunApplication loading
    // -------------------------------------------------------------------------

    private fun loadApps() {
        progressBar.visibility = View.VISIBLE
        lifecycleScope.launch {
            val apps = withContext(Dispatchers.IO) { queryInstalledApps() }
            progressBar.visibility = View.GONE
            if (apps.isEmpty()) {
                tvEmpty.visibility = View.VISIBLE
            } else {
                tvEmpty.visibility = View.GONE
                adapter.setApps(apps)
            }
        }
    }

    private fun queryInstalledApps(): List<AppInfo> {
        val pm = requireContext().packageManager
        val myPkg = requireContext().packageName

        val allApps = pm.getInstalledApplications(0)
        Log.d(TAG, "Total installed packages: ${allApps.size}")

        return allApps
            .filter { it.packageName != myPkg }
            .map { appInfo ->
                AppInfo(
                    packageName = appInfo.packageName,
                    appName     = pm.getApplicationLabel(appInfo).toString(),
                    icon        = try {
                        pm.getApplicationIcon(appInfo)
                    } catch (e: Exception) {
                        Log.w(TAG, "Icon failed for ${appInfo.packageName}: ${e.message}")
                        requireContext().getDrawable(android.R.drawable.sym_def_app_icon)!!
                    }
                )
            }
            .sortedBy { it.appName }
    }

    // -------------------------------------------------------------------------
    // FIX #3: installSelected — was a stub with only a Toast and a comment.
    //         Now actually drives the clone engine.
    // -------------------------------------------------------------------------

    private fun installSelected() {
        val selected = adapter.getSelected()
        if (selected.isEmpty()) return

        // Disable button to prevent double-taps
        btnInstall.isEnabled = false
        btnInstall.text = "Installing…"

        lifecycleScope.launch {
            var successCount = 0
            var failCount    = 0

            withContext(Dispatchers.IO) {
                selected.forEach { app ->
                    val userId = CloneEngine.getNextUserIdForPackage(app.packageName)
                    Log.i(TAG, "Clone install userId=$userId pkg=${app.packageName}")
                    try {
                        val ok = CloneEngine.install(requireContext(), app, userId)
                        if (ok) {
                            successCount++
                            Log.i(TAG, "Clone install succeeded userId=$userId pkg=${app.packageName}")
                        } else {
                            failCount++
                            Log.e(TAG, "Clone install returned false userId=$userId pkg=${app.packageName}")
                        }
                    } catch (e: Exception) {
                        failCount++
                        Log.e(TAG, "Clone install threw exception pkg=${app.packageName}", e)
                    }
                }
            }

            // Re-enable button
            btnInstall.isEnabled = true
//            adapter.clearSelection()
            btnInstall.visibility = View.GONE

            // Report result to parent activity
            val activity = activity as? CloneAppActivity
            if (successCount > 0) {
                Toast.makeText(
                    requireContext(),
                    "$successCount app(s) cloned successfully" +
                            if (failCount > 0) ", $failCount failed" else "",
                    Toast.LENGTH_LONG
                ).show()
                activity?.onCloneInstallComplete(successCount, failCount)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Clone failed for all selected apps. Check logs.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}