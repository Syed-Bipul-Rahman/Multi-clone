package com.dong.multirun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.R
import com.dong.multirun.model.AppInfo

class AppGridAdapter(
    private val items: MutableList<AppInfo> = mutableListOf(),
    private val onAddClick: () -> Unit,
    private val onAppClick: (AppInfo) -> Unit,
    private val onAppLongClick: (AppInfo, View) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_APP = 0
        private const val TYPE_ADD = 1
    }

    override fun getItemViewType(position: Int) =
        if (position < items.size) TYPE_APP else TYPE_ADD

    override fun getItemCount() = items.size + 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_APP) {
            AppViewHolder(inflater.inflate(R.layout.item_app_grid, parent, false))
        } else {
            AddViewHolder(inflater.inflate(R.layout.item_app_add, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AppViewHolder -> holder.bind(items[position])
            is AddViewHolder -> holder.itemView.setOnClickListener { onAddClick() }
        }
    }

    fun setApps(apps: List<AppInfo>) {
        items.clear()
        items.addAll(apps)
        notifyDataSetChanged()
    }

    fun addApp(app: AppInfo) {
        items.add(app)
        notifyItemInserted(items.size - 1)
    }

    fun removeApp(app: AppInfo) {
        val idx = items.indexOf(app)
        if (idx >= 0) {
            items.removeAt(idx)
            notifyItemRemoved(idx)
        }
    }

    inner class AppViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val ivIcon: ImageView = view.findViewById(R.id.ivAppIcon)
        private val tvName: TextView = view.findViewById(R.id.tvAppName)
        private val tvSpace: TextView = view.findViewById(R.id.tvSpaceIdx)

        fun bind(app: AppInfo) {
            ivIcon.setImageDrawable(app.icon)
            tvName.text = app.customName
            tvSpace.text = app.spaceIndex.toString()

            itemView.setOnClickListener { onAppClick(app) }
            itemView.setOnLongClickListener {
                onAppLongClick(app, itemView)
                true
            }
        }
    }

    inner class AddViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
