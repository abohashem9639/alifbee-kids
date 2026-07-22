package com.alifbee.kidsdemo.ui.screens.home.ui.viewmodel

import com.alifbee.kidsdemo.data.repository.CategoryRepository
import com.alifbee.kidsdemo.ui.common.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>(
    initialState = HomeContract.State()
) {

    init {
        setState { copy(categories = categoryRepository.getCategories()) }
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            HomeContract.Event.OnMusicClicked -> setEffect { HomeContract.Effect.NavigateToHomescreen }
            HomeContract.Event.OnMoreByUsClicked -> setEffect { HomeContract.Effect.NavigateToParentsLock }
        }
    }
}
