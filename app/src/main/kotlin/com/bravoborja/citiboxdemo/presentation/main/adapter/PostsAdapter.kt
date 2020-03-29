package com.bravoborja.citiboxdemo.presentation.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bravoborja.citiboxdemo.data.local.model.PostEntity
import com.bravoborja.citiboxdemo.databinding.ItemPostBinding

class PostsAdapter : ListAdapter<PostEntity, PostsAdapter.PostViewHolder>(DIFF_CALLBACK) {

    var onClickPost: ((PostEntity) -> Unit)? = null

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
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostEntity>() {
            override fun areItemsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PostEntity, newItem: PostEntity): Boolean =
                oldItem == newItem
        }
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostEntity) {
            binding.postTitle.text = post.title
            binding.root.setOnClickListener {
                onClickPost?.invoke(post)
            }
        }
    }
}
