package com.dong.multirun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.R
import com.dong.multirun.model.AppInfo

class InstalledAppAdapter(
    private val onSelectionChanged: (List<AppInfo>) -> Unit
) : RecyclerView.Adapter<InstalledAppAdapter.ViewHolder>() {

    private val apps = mutableListOf<AppInfo>()
    private val selected = mutableSetOf<String>()

    fun setApps(list: List<AppInfo>) {
        apps.clear()
        apps.addAll(list)
        notifyDataSetChanged()
    }

    fun getSelected(): List<AppInfo> = apps.filter { it.packageName in selected }

    override fun getItemCount() = apps.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_installed_app, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val app = apps[position]
        holder.ivIcon.setImageDrawable(app.icon)
        holder.tvName.text = app.appName
        holder.tvPackage.text = app.packageName
        holder.cbSelect.isChecked = app.packageName in selected

        holder.itemView.setOnClickListener {
            if (app.packageName in selected) {
                selected.remove(app.packageName)
            } else {
                selected.add(app.packageName)
            }
            holder.cbSelect.isChecked = app.packageName in selected
            onSelectionChanged(getSelected())
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cbSelect: CheckBox = view.findViewById(R.id.cbSelect)
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvPackage: TextView = view.findViewById(R.id.tvPackage)
    }
}
