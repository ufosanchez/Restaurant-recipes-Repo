package com.project.v1.testing.recipesproject1.api

import com.project.v1.testing.recipesproject1.models.Restaurant
import retrofit2.http.GET
import retrofit2.http.Headers

interface IAPIRestaurantResponse {

    //@GET("restaurants/list?location_id=293919&restaurant_tagcategory=10591&restaurant_tagcategory_standalone=10591&currency=USD&lunit=km&limit=30&open_now=false&lang=en_US")
    //Toronto Restaurants
    @Headers("X-RapidAPI-Key: " + "366bd066f0msh3035901d05b4a61p171c16jsn17878ef796c5")
    @GET("restaurants/list?location_id=155019&restaurant_tagcategory=10591&restaurant_tagcategory_standalone=10591&currency=USD&lunit=km&limit=30&open_now=false&lang=en_US")
    suspend fun getAllRestaurant(): Restaurant
}