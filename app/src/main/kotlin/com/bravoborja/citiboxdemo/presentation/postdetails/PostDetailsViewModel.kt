package com.bravoborja.citiboxdemo.presentation.postdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.domain.model.CommentModel
import com.bravoborja.citiboxdemo.domain.model.UserModel
import com.bravoborja.citiboxdemo.domain.usecase.GetAuthorUseCase
import com.bravoborja.citiboxdemo.domain.usecase.GetCommentsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
class PostDetailsViewModel @Inject constructor(
    private val getAuthorUseCase: GetAuthorUseCase,
    private val getCommentsUseCase: GetCommentsUseCase
) : ViewModel() {

    private val _commentsLiveData = MutableLiveData<State<List<CommentModel>>>()
    private val _authorLiveData = MutableLiveData<State<UserModel?>>()

    val commentsLiveData: LiveData<State<List<CommentModel>>>
        get() = _commentsLiveData
    val authorLiveData: LiveData<State<UserModel?>>
        get() = _authorLiveData

    fun getAuthor(userId: Long?) {
        viewModelScope.launch {
            userId?.let {
                getAuthorUseCase.getAuthor(it).collect { state ->
                    _authorLiveData.value = state
                }
            }
        }
    }

    fun getComments(postId: Long?) {
        viewModelScope.launch {
            postId?.let {
                getCommentsUseCase.getComments(it).collect { state ->
                    _commentsLiveData.value = state
                }
            }
        }
    }

}
