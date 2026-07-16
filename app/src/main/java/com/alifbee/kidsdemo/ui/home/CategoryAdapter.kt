package com.alifbee.kidsdemo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.databinding.ItemCategoryBinding

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(DIFF) {

    inner class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.categoryImage.setImageResource(category.imageRes)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.imageRes == newItem.imageRes

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }
}
