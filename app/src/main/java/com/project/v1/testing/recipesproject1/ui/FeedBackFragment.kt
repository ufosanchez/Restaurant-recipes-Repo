package com.project.v1.testing.recipesproject1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.adapters.FeedbackAdapter
import com.project.v1.testing.recipesproject1.databinding.FragmentFeedBackBinding
import com.project.v1.testing.recipesproject1.models.Feedback
import com.project.v1.testing.recipesproject1.repository.FeedbackRepository

class FeedBackFragment : Fragment(R.layout.fragment_feed_back) {

    val TAG:String = "HELLO-FRAGMENT"

    private var _binding: FragmentFeedBackBinding? = null
    private val binding get() = _binding!!

    lateinit var feedArrayList: ArrayList<Feedback>
    private val feedbackRepository = FeedbackRepository()

    var feedbackAdapter: FeedbackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() FeedBackFragment executing")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() FeedBackFragment executing")
        _binding = FragmentFeedBackBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() FeedBackFragment executing")

        feedArrayList = ArrayList()

    }

    override fun onStart() {
        super.onStart()
        feedArrayList.clear()
        feedbackAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

        feedbackRepository.getAllFeedback()

        feedbackRepository.allFeedback.observe(this, Observer { feedbackList ->

            if(feedbackList != null){
                for (feedback in feedbackList){
                    feedArrayList.add(Feedback(name = feedback.name, description = feedback.description))
                    Log.e(TAG, "onResume: $feedback", )
                    feedbackAdapter?.notifyDataSetChanged()
                }
            }
        })

        feedbackAdapter = this@FeedBackFragment.context?.let { FeedbackAdapter(it,feedArrayList) }
        binding.rvFeedback.layoutManager = LinearLayoutManager(context)
        binding.rvFeedback.adapter = feedbackAdapter
    }

    override fun onPause() {
        super.onPause()
        feedArrayList.clear()
        feedbackAdapter?.notifyDataSetChanged()
    }

    override fun onStop() {
        super.onStop()
        feedArrayList.clear()
        feedbackAdapter?.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}