package com.alifbee.kidsdemo.ui.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alifbee.kidsdemo.data.model.AppItem
import com.alifbee.kidsdemo.databinding.ItemMoreAppBinding

class MoreAppsAdapter(
    private val onAppClick: (AppItem) -> Unit
) : ListAdapter<AppItem, MoreAppsAdapter.AppViewHolder>(DIFF) {

    inner class AppViewHolder(
        private val binding: ItemMoreAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AppItem) {
            binding.appName.text = item.name
            binding.appDescription.text = item.description
            binding.appLink.setOnClickListener { onAppClick(item) }
            binding.root.setOnClickListener { onAppClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppViewHolder {
        val binding = ItemMoreAppBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return AppViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AppViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<AppItem>() {
            override fun areItemsTheSame(oldItem: AppItem, newItem: AppItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AppItem, newItem: AppItem) =
                oldItem == newItem
        }
    }
}
