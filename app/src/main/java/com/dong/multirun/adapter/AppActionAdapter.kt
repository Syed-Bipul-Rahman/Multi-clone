package com.dong.multirun.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dong.multirun.R
import com.dong.multirun.model.AppAction

class AppActionAdapter(
    private val actions: List<AppAction>,
    private val onAction: (AppAction) -> Unit
) : RecyclerView.Adapter<AppActionAdapter.ViewHolder>() {

    override fun getItemCount() = actions.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_action_button, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = actions[position]
        holder.ivIcon.setImageResource(action.iconRes)
        holder.tvLabel.text = action.label
        holder.itemView.setOnClickListener { onAction(action) }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivIcon: ImageView = view.findViewById(R.id.ivIcon)
        val tvLabel: TextView = view.findViewById(R.id.tvLabel)
    }
}
