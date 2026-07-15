package com.alifbee.kidsdemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.data.repository.CategoryRepository

class HomeViewModel : ViewModel() {

    private val repository = CategoryRepository()

    val categories: List<Category> = repository.getCategories()

    private val _currentIndex = MutableLiveData(0)
    val currentIndex: LiveData<Int> = _currentIndex

    private val _musicOn = MutableLiveData(true)
    val musicOn: LiveData<Boolean> = _musicOn

    fun next() {
        val index = _currentIndex.value ?: 0
        if (index < categories.lastIndex) _currentIndex.value = index + 1
    }

    fun previous() {
        val index = _currentIndex.value ?: 0
        if (index > 0) _currentIndex.value = index - 1
    }

    fun toggleMusic() {
        _musicOn.value = !(_musicOn.value ?: true)
    }
}
