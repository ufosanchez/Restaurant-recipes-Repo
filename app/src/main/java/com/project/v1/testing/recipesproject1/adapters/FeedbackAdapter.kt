package com.project.v1.testing.recipesproject1.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.project.v1.testing.recipesproject1.databinding.ItemFeedbackBinding
import com.project.v1.testing.recipesproject1.models.Feedback
import com.squareup.picasso.Picasso

class FeedbackAdapter(private val context : Context, private var feedbackList: ArrayList<Feedback>) : RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    class FeedbackViewHolder(var binding : ItemFeedbackBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(currentFeedback: Feedback){

            binding.tvNameUserFeedback.setText(currentFeedback.name)
            binding.tvDescriptionFeedback.setText(currentFeedback.description)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        return FeedbackViewHolder(ItemFeedbackBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        holder.bind(feedbackList.get(position))
    }

    override fun getItemCount(): Int {
        return feedbackList.size
    }
}