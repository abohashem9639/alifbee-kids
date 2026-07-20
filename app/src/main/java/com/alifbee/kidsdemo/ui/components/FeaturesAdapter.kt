package com.alifbee.kidsdemo.ui.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alifbee.kidsdemo.data.model.Feature
import com.alifbee.kidsdemo.databinding.ItemFeatureBinding

class FeaturesAdapter : ListAdapter<Feature, FeaturesAdapter.FeatureViewHolder>(DIFF) {

    inner class FeatureViewHolder(
        private val binding: ItemFeatureBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(feature: Feature) {
            binding.featureIcon.setImageResource(feature.iconRes)
            binding.featureLabel.text = feature.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureViewHolder {
        val binding = ItemFeatureBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FeatureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeatureViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Feature>() {
            override fun areItemsTheSame(oldItem: Feature, newItem: Feature) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Feature, newItem: Feature) =
                oldItem == newItem
        }
    }
}
