package com.alifbee.kidsdemo.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alifbee.kidsdemo.data.model.Service
import com.alifbee.kidsdemo.databinding.ItemServiceBinding

class ServicesAdapter : ListAdapter<Service, ServicesAdapter.ServiceViewHolder>(DIFF) {

    inner class ServiceViewHolder(
        private val binding: ItemServiceBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            binding.serviceIcon.setImageResource(service.iconRes)
            binding.serviceLabel.text = service.name
            binding.serviceCard.setCardBackgroundColor(
                ContextCompat.getColor(binding.root.context, service.backgroundColorRes)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = ItemServiceBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ServiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Service>() {
            override fun areItemsTheSame(oldItem: Service, newItem: Service) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Service, newItem: Service) =
                oldItem == newItem
        }
    }
}
