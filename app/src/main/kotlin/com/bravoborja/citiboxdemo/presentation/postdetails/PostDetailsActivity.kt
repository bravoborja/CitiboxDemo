package com.bravoborja.citiboxdemo.presentation.postdetails

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.databinding.ActivityPostDetailsBinding
import com.bravoborja.citiboxdemo.domain.model.PostModel
import com.bravoborja.citiboxdemo.presentation.common.BaseActivity
import com.bravoborja.citiboxdemo.presentation.postdetails.adapter.CommentsAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PostDetailsActivity : BaseActivity<PostDetailsViewModel, ActivityPostDetailsBinding>() {

    companion object {
        const val EXTRA_POST = "EXTRA_POST"
    }

    private val adapter: CommentsAdapter by lazy { CommentsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        val post = intent.extras?.getParcelable<PostModel>(EXTRA_POST)
        viewBinding.commentsRecyclerView.layoutManager =
            LinearLayoutManager(this@PostDetailsActivity)
        viewBinding.commentsRecyclerView.adapter = adapter
        initPost(post)
    }

    private fun initPost(post: PostModel?) {
        viewBinding.postDescription.text = post?.body
        viewModel.authorLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    Log.d("PostDetailsActivity", "" + "loading author")
                }
                is State.Success -> {
                    viewBinding.postAuthor.text = state.data?.name
                    getComments(post?.id)
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.commentsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    Log.d("PostDetailsActivity", "" + "loading comments")
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
        if (viewModel.authorLiveData.value !is State.Success) {
            getAuthor(post?.userId)
        }
    }

    private fun getAuthor(userId: Long?) = viewModel.getAuthor(userId)

    private fun getComments(postId: Long?) = viewModel.getComments(postId)

    override fun createViewBinding(): ActivityPostDetailsBinding =
        ActivityPostDetailsBinding.inflate(layoutInflater)

    override fun createViewModel(): PostDetailsViewModel = viewModelOf(viewModelProvider)
}
