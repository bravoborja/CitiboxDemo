package com.bravoborja.citiboxdemo.presentation.postdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bravoborja.citiboxdemo.data.local.model.CommentEntity
import com.bravoborja.citiboxdemo.databinding.ItemCommentBinding

class CommentsAdapter :
    ListAdapter<CommentEntity, CommentsAdapter.CommentViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentViewHolder(
        ItemCommentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CommentEntity>() {
            override fun areItemsTheSame(oldItem: CommentEntity, newItem: CommentEntity): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: CommentEntity,
                newItem: CommentEntity
            ): Boolean =
                oldItem == newItem
        }
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: CommentEntity) {
            binding.commentTitle.text = comment.name
            binding.commentAuthorEmail.text = comment.email
            binding.commentDescription.text = comment.body
        }
    }
}
