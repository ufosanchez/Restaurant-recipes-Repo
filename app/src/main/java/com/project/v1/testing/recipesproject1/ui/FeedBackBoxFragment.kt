package com.project.v1.testing.recipesproject1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.FragmentFeedBackBoxBinding
import com.project.v1.testing.recipesproject1.models.FeedbackDB
import com.project.v1.testing.recipesproject1.repository.FeedbackRepository

class FeedBackBoxFragment : Fragment(R.layout.fragment_feed_back_box) {
    val TAG:String = "HELLO-FRAGMENT"

    private var _binding: FragmentFeedBackBoxBinding? = null

    private val binding get() = _binding!!

    private val feedbackRepository = FeedbackRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() FeedBackBoxFragment executing")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() FeedBackBoxFragment executing")
        _binding = FragmentFeedBackBoxBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() FeedBackBoxFragment executing")

        binding.btnFeedback.setOnClickListener {

            val description = binding.edtDescription.text.trim().toString()
            var nameUser = binding.edtUserFeed.text.trim().toString()

            if(description.isEmpty()){
                binding.tvError.setText("Error: Please enter Description")
                binding.edtDescription.setError("Error: Please enter Name")
                return@setOnClickListener
            }

            if(nameUser.isEmpty()){
                nameUser = "N/A"
            }

            binding.edtDescription.setText("")
            binding.edtUserFeed.setText("")
            binding.tvError.setText("")

            //save in the feedback collection of the database
            feedbackRepository.addFeedbacktoDB(FeedbackDB(name = nameUser, description = description))
            //save in the feedback collection of the database

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}