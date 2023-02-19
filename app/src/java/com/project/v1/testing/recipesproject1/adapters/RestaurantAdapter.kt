package com.project.v1.testing.recipesproject1.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.project.v1.testing.recipesproject1.databinding.ItemRestaurantBinding
import com.project.v1.testing.recipesproject1.models.data
import com.project.v1.testing.recipesproject1.ui.RestaurantsFragmentDirections
import com.squareup.picasso.Picasso


class RestaurantAdapter(private val context : Context, private var restaurantList: ArrayList<data>) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(var binding: ItemRestaurantBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(currentRestaurant : data){
            //associate individual view and data
            Picasso.get().load(currentRestaurant.photo!!.images.original.url).into(binding.imgRestaurant);
            binding.tvNameRestaurant.setText(currentRestaurant.name)
            binding.tvLocationRestaurant.setText(currentRestaurant.location_string)
            binding.tvAddressRestaurant.setText(currentRestaurant.address)
            binding.tvPriceRestaurant.setText(currentRestaurant.price_level)

            itemView.setOnClickListener {
                Log.d("RestaurantViewHolder", "bind: ${currentRestaurant.name}")
                val action = RestaurantsFragmentDirections.actionRestaurantsFragmentToLocationActivity(
                    currentRestaurant.name, currentRestaurant.latitude, currentRestaurant.longitude,
                    currentRestaurant.location_string, photo = currentRestaurant.photo!!.images.original.url,
                    currentRestaurant.phone, currentRestaurant.address, currentRestaurant.website, currentRestaurant.price_level)
                findNavController(this.itemView).navigate(action)
            }


        }
    }
    //create the appearance or view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        //return RestaurantViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(context), parent, false))
        return RestaurantViewHolder(ItemRestaurantBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    //bind the date with view
    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurantList.get(position))
    }

    //identifies number of item
    override fun getItemCount(): Int {
        return restaurantList.size
    }
}