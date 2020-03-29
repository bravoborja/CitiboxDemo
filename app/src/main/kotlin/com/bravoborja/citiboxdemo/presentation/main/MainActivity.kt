package com.bravoborja.citiboxdemo.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bravoborja.citiboxdemo.common.State
import com.bravoborja.citiboxdemo.databinding.ActivityMainBinding
import com.bravoborja.citiboxdemo.presentation.common.BaseActivity
import com.bravoborja.citiboxdemo.presentation.common.NetworkHelper
import com.bravoborja.citiboxdemo.presentation.main.adapter.PostsAdapter
import com.bravoborja.citiboxdemo.presentation.postdetails.PostDetailsActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {

    private val adapter: PostsAdapter by lazy { PostsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.postsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        viewBinding.postsRecyclerView.adapter = adapter
        (viewBinding.postsRecyclerView.adapter as PostsAdapter).onClickPost = {
            val intent = Intent(this, PostDetailsActivity::class.java)
            intent.putExtra(PostDetailsActivity.EXTRA_POST, it as Parcelable)
            startActivity(intent)
        }
        initPosts()
    }

    private fun initPosts() {
        viewModel.postsLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> showLoading(true)
                is State.Success -> {
                    if (state.data.isNotEmpty()) {
                        adapter.submitList(state.data.toMutableList())
                        showLoading(false)
                    }
                }
                is State.Error -> {
                    Toast.makeText(applicationContext, state.message, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        })
        viewBinding.swipeRefreshLayout.setOnRefreshListener { getPosts() }
        if (viewModel.postsLiveData.value !is State.Success) {
            getPosts()
        }
        NetworkHelper.getNetworkLiveData(this).observe(this, Observer { isConnected ->
            if (isConnected && viewModel.postsLiveData.value is State.Error || adapter.itemCount == 0) {
                getPosts()
            }
        })
    }

    private fun getPosts() = viewModel.getPosts()

    private fun showLoading(isLoading: Boolean) {
        viewBinding.swipeRefreshLayout.isRefreshing = isLoading
    }

    override fun createViewBinding(): ActivityMainBinding =
        ActivityMainBinding.inflate(layoutInflater)

    override fun createViewModel(): MainViewModel = viewModelOf(viewModelProvider)
}
