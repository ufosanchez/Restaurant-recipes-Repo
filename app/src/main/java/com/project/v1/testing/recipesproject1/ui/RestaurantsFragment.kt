package com.project.v1.testing.recipesproject1.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.adapters.RestaurantAdapter
import com.project.v1.testing.recipesproject1.api.IAPIRestaurantResponse
import com.project.v1.testing.recipesproject1.api.RetrofitInstanceRestaurant
import com.project.v1.testing.recipesproject1.databinding.FragmentRestaurantsBinding
import com.project.v1.testing.recipesproject1.models.Restaurant
import com.project.v1.testing.recipesproject1.models.data
import kotlinx.coroutines.launch

class RestaurantsFragment : Fragment(R.layout.fragment_restaurants) {
    val TAG:String = "HELLO-FRAGMENT"

    private var _binding: FragmentRestaurantsBinding? = null

    private val binding get() = _binding!!

    //adapter
    var restaurantAdapter: RestaurantAdapter? = null
    //adapter

    // fragments have their own lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() RestaurantsFragment executing")
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView() RestaurantsFragment executing")
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated() RestaurantsFragment executing")

        var restaurantAPI: IAPIRestaurantResponse = RetrofitInstanceRestaurant.retrofitService
        viewLifecycleOwner.lifecycleScope.launch {

            val response: Restaurant = restaurantAPI.getAllRestaurant()

            val restaurantList:List<data> = response.data

            var restaurantListFull : ArrayList<data> = ArrayList()

            for ((i, currRestaurant) in restaurantList.withIndex()) {
                if(currRestaurant.location_id != "155019"){
                    Log.d(TAG, "${currRestaurant.toString()}")
                    restaurantListFull.add(currRestaurant)
                }
            }
            Log.d(TAG, "Total number of restaurantListFull: ${restaurantListFull.size}")
            Log.d(TAG, "Info about the restaurantListFull: ${restaurantListFull.toString()}")

            // instance adapter
            restaurantAdapter = this@RestaurantsFragment.context?.let { RestaurantAdapter(it, restaurantListFull) }
            binding.rvRestaurants.layoutManager = LinearLayoutManager(context)
            binding.rvRestaurants.adapter = restaurantAdapter
            // instance adapter

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}