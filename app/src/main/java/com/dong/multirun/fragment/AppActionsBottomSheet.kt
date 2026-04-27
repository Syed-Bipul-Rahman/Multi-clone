package com.dong.multirun.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.R
import com.dong.multirun.adapter.AppActionAdapter
import com.dong.multirun.clone.CloneEngine
import com.dong.multirun.model.AppAction
import com.dong.multirun.model.AppInfo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppActionsBottomSheet : BottomSheetDialogFragment() {

    private var app: AppInfo? = null
    var onUninstall: ((AppInfo) -> Unit)? = null

    companion object {
        private const val ARG_PKG = "pkg"

        fun newInstance(app: AppInfo) = AppActionsBottomSheet().apply {
            this.app = app
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.bottom_sheet_app_actions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val currentApp = app ?: return dismiss()

        view.findViewById<ImageView>(R.id.ivAppIcon).setImageDrawable(currentApp.icon)
        view.findViewById<TextView>(R.id.tvAppName).text = currentApp.customName
        view.findViewById<TextView>(R.id.tvSpaceLabel).text = "Space ${currentApp.spaceIndex}"

        val actions = buildActions()
        val rv = view.findViewById<RecyclerView>(R.id.rvActions)
        rv.layoutManager = GridLayoutManager(requireContext(), 4)
        rv.adapter = AppActionAdapter(actions) { action ->
            handleAction(action, currentApp)
        }
    }

    private fun buildActions() = listOf(
        AppAction("open",    "Open",          R.drawable.ic_add),
        AppAction("clone",   "Clone Next",    R.drawable.ic_add),
        AppAction("rename",  "Rename",        R.drawable.ic_settings),
        AppAction("icon",    "Icon Fake",     R.drawable.ic_settings),
        AppAction("stop",    "Force Stop",    R.drawable.ic_settings),
        AppAction("clear",   "Clear Storage", R.drawable.ic_settings),
        AppAction("share",   "Share",         R.drawable.ic_arrow_right),
        AppAction("uninstall","Uninstall",    R.drawable.ic_arrow_right)
    )

    private fun handleAction(action: AppAction, app: AppInfo) {
        when (action.id) {
            "open" -> {
                CloneEngine.launch(requireContext(), app.packageName, app.spaceIndex)
                dismiss()
            }
            "uninstall" -> {
                lifecycleScope.launch(Dispatchers.IO) {
                    val ok = CloneEngine.uninstall(requireContext(), app.packageName, app.spaceIndex)
                    Log.i("AppActionsBottomSheet", "uninstall userId=${app.spaceIndex} ${app.packageName} ok=$ok")
                }
                onUninstall?.invoke(app)
                dismiss()
            }
            "stop" -> {
                Toast.makeText(requireContext(), "Force stopped ${app.customName}", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            "clear" -> {
                Toast.makeText(requireContext(), "Cleared storage for ${app.customName}", Toast.LENGTH_SHORT).show()
                dismiss()
            }
            else -> {
                Toast.makeText(requireContext(), "${action.label}: ${app.customName}", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }
    }
}
