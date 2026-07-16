package com.alifbee.kidsdemo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alifbee.kidsdemo.data.model.Category
import com.alifbee.kidsdemo.data.repository.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CategoryRepository
) : ViewModel() {

    val categories: List<Category> = repository.getCategories()

    private val _musicOn = MutableLiveData(true)
    val musicOn: LiveData<Boolean> = _musicOn

    fun toggleMusic() {
        _musicOn.value = !(_musicOn.value ?: true)
    }
}
