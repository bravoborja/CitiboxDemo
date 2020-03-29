package com.bravoborja.citiboxdemo.presentation.postdetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.databinding.ActivityPostDetailsBinding
import com.bravoborja.citiboxdemo.presentation.common.BaseActivity
import com.bravoborja.citiboxdemo.presentation.postdetails.adapter.CommentsAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PostDetailsActivity : BaseActivity<PostDetailsViewModel, ActivityPostDetailsBinding>() {

    companion object {
        const val EXTRA_POST_ID = "EXTRA_POST_ID"
        const val EXTRA_USER_ID = "EXTRA_USER_ID"
    }

    private val adapter: CommentsAdapter by lazy { CommentsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val postId = intent.extras?.getLong(EXTRA_POST_ID) ?: 0
        val userId = intent.extras?.getLong(EXTRA_USER_ID) ?: 0
        viewBinding.commentsRecyclerView.layoutManager =
            LinearLayoutManager(this@PostDetailsActivity)
        viewBinding.commentsRecyclerView.adapter = adapter
        initPost(postId, userId)
    }

    private fun initPost(postId: Long, userId: Long) {
        viewModel.commentsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    Log.d("PostDetailsActivity", "" + "loading")
                }
                is State.Success -> {
                    viewBinding.commentsLayout.visibility = View.VISIBLE
                    viewBinding.postNumberOfComments.text =
                        state.data.toMutableList().size.toString()
                    adapter.submitList(state.data.toMutableList())
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
        if (viewModel.commentsLiveData.value !is State.Success) {
            getComments(postId)
        }
    }

    private fun getComments(postId: Long) = viewModel.getComments(postId)

    override fun createViewBinding(): ActivityPostDetailsBinding =
        ActivityPostDetailsBinding.inflate(layoutInflater)

    override fun createViewModel(): PostDetailsViewModel = viewModelOf(viewModelProvider)
}
