package com.project.v1.testing.recipesproject1.api

import com.project.v1.testing.recipesproject1.models.Recipes
import retrofit2.http.GET
import retrofit2.http.Query

interface ISpoontacularResponse {

    @GET("recipes/random")
    suspend fun getRandomRecipes(
        @Query("apiKey") apiKey:String,
        @Query("number") number:Int
    ): Recipes

    @GET("recipes/random")
    suspend fun getRandomCustomRecipes(
        @Query("apiKey") apiKey: String,
        @Query("tags") tags:String,
        @Query("number") number: Int
    ):Recipes

//    @GET(/recipes/{id}/information)
//    suspend fun getRecipeDetails(
//
//    )

//    @GET("/recipes/complexSearch")
//    suspend fun getUserSelectedRecipes(
//        @Query("apiKey") apiKey: String,
//        @Query("query")  query:String,
//        @Query("number") number: Int
//    ):Recipes

}