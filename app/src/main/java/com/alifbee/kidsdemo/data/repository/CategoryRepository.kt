package com.alifbee.kidsdemo.data.repository

import com.alifbee.kidsdemo.R
import com.alifbee.kidsdemo.data.model.Category
import javax.inject.Inject

class CategoryRepository @Inject constructor() {

    fun getCategories(): List<Category> = listOf(
        Category(R.drawable.img_games),
        Category(R.drawable.img_videos),
        Category(R.drawable.img_songs)
    )
}
