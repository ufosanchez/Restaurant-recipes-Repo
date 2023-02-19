package com.project.v1.testing.recipesproject1.repository

import com.project.v1.testing.recipesproject1.api.RetrofitInstanceSpoontacular
import com.project.v1.testing.recipesproject1.models.Recipe
import retrofit2.http.Query

class RecipeRepository {

    suspend fun getRandomRecipes(apiKey: String, numberOfRecipes: Int): List<Recipe> {

        val iRecipeApi = RetrofitInstanceSpoontacular.retrofitRecipeService

        val recipesObj = iRecipeApi.getRandomRecipes(apiKey, numberOfRecipes)

        return recipesObj.recipes

    }

    suspend fun getRandomCustomizedRecipes(apiKey: String, tags:String, numberOfRecipes:Int):List<Recipe>{

        val iRecipeApi = RetrofitInstanceSpoontacular.retrofitRecipeService

        val recipesObj = iRecipeApi.getRandomCustomRecipes(apiKey, tags, numberOfRecipes)

        return recipesObj.recipes

    }

//    suspend fun getUserChoiceRecipes(apiKey: String, query: Query, numberOfRecipes: Int){
//
//        val iRecipeApi = RetrofitInstanceSpoontacular.retrofitRecipeService
//
//
//
//    }


}