package com.bravoborja.citiboxdemo.presentation.postdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.CommentModel
import com.bravoborja.citiboxdemo.domain.usecase.GetAuthorUseCase
import com.bravoborja.citiboxdemo.domain.usecase.GetCommentsUseCase
import com.bravoborja.citiboxdemo.domain.usecase.GetPostUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PostDetailsViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase,
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    private val _commentsLiveData = MutableLiveData<State<List<CommentModel>>>()

    val commentsLiveData: LiveData<State<List<CommentModel>>>
        get() = _commentsLiveData

    fun getComments(postId: Long) {
        viewModelScope.launch {
            getCommentsUseCase.getComments(postId).collect {
                _commentsLiveData.value = it
            }
        }
    }

}
