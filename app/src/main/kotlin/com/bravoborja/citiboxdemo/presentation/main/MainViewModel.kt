package com.bravoborja.citiboxdemo.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.domain.usecase.GetPostsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(private val getPostsUseCase: GetPostsUseCase) :
    ViewModel() {

    private val _postsLiveData = MutableLiveData<State<List<PostEntity>>>()

    val postsLiveData: LiveData<State<List<PostEntity>>>
        get() = _postsLiveData

    fun getPosts() {
        viewModelScope.launch {
            getPostsUseCase.getPosts().collect {
                _postsLiveData.value = it
            }
        }
    }

}
