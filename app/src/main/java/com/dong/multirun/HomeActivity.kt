package com.dong.multirun

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.adapter.AppGridAdapter
import com.dong.multirun.clone.CloneEngine
import com.dong.multirun.fragment.AppActionsBottomSheet
import com.dong.multirun.model.AppInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivity"
    private lateinit var rvApps: RecyclerView
    private lateinit var adapter: AppGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        rvApps = findViewById(R.id.rvApps)

        adapter = AppGridAdapter(
            onAddClick = { openCloneApp() },
            onAppClick = { app -> launchApp(app) },
            onAppLongClick = { app, _ -> showAppActions(app) }
        )

        rvApps.layoutManager = GridLayoutManager(this, 3)
        rvApps.adapter = adapter

        findViewById<android.widget.ImageButton>(R.id.btnMenu).setOnClickListener { view ->
            val popup = PopupMenu(this, view, Gravity.END)
            popup.menu.add(0, 1, 0, getString(R.string.clone_apps))
            popup.menu.add(0, 2, 1, getString(R.string.setting))
            popup.setOnMenuItemClickListener { item: MenuItem ->
                when (item.itemId) {
                    1 -> openCloneApp()
                    2 -> startActivity(Intent(this, SettingActivity::class.java))
                }
                true
            }
            popup.show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadSandboxApps()
    }

    private fun loadSandboxApps() {
        lifecycleScope.launch {
            val apps = withContext(Dispatchers.IO) {
                try {
                    val entries = CloneEngine.listAllSandboxApps()
                    Log.d(TAG, "Sandbox entries: $entries")
                    val pm = packageManager
                    entries.mapNotNull { (userId, pkg) ->
                        try {
                            val info = pm.getApplicationInfo(pkg, 0)
                            val name = pm.getApplicationLabel(info).toString()
                            AppInfo(
                                packageName = pkg,
                                appName = name,
                                icon = pm.getApplicationIcon(info),
                                spaceIndex = userId
                            )
                        } catch (e: Exception) {
                            Log.w(TAG, "Could not load info for $pkg userId=$userId", e)
                            null
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "loadSandboxApps failed", e)
                    emptyList()
                }
            }
            adapter.setApps(apps)
        }
    }

    private fun openCloneApp() {
        startActivity(Intent(this, CloneAppActivity::class.java))
    }

    private fun launchApp(app: AppInfo) {
        Log.i(TAG, "Launching cloned app userId=${app.spaceIndex} pkg=${app.packageName}")
        CloneEngine.launch(this, app.packageName, app.spaceIndex)
    }

    private fun showAppActions(app: AppInfo) {
        val sheet = AppActionsBottomSheet.newInstance(app)
        sheet.onUninstall = { removed ->
            adapter.removeApp(removed)
        }
        sheet.show(supportFragmentManager, "actions")
    }

    companion object {
        private const val REQUEST_CLONE = 1001
    }
}
