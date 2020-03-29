package com.bravoborja.citiboxdemo.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.bravoborja.citiboxdemo.databinding.ItemPostBinding
import com.bravoborja.citiboxdemo.domain.model.PostModel

class PostsAdapter : ListAdapter<PostModel, PostsAdapter.PostViewHolder>(DIFF_CALLBACK) {

    var onClickPost: ((PostModel) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(
        ItemPostBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostModel>() {
            override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel): Boolean =
                oldItem == newItem
        }
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostModel) {
            binding.postTitle.text = post.title
            binding.imageView.load(post.imageUrl)
            binding.root.setOnClickListener {
                onClickPost?.invoke(post)
            }
        }
    }
}
