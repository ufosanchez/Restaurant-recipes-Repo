package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.v1.testing.recipesproject1.models.Recipe
import com.project.v1.testing.recipesproject1.repository.RecipeRepository
import kotlinx.coroutines.launch

class RandomRecipeViewModel(val myRecipeRepository: RecipeRepository):ViewModel() {


    private val _randomRecipeList:MutableLiveData<List<Recipe>> = MutableLiveData()

    val randomRecipeList:LiveData<List<Recipe>>
       get() = _randomRecipeList


    fun getRandomRecipesFromRepository(apiKey:String, number:Int){

        viewModelScope.launch {

           _randomRecipeList.value =  myRecipeRepository.getRandomRecipes(apiKey, number)

        }

    }

    fun getRandomCustomRecipesFromRepository(apiKey: String, tags:String, number: Int){

        viewModelScope.launch {
            _randomRecipeList.value = myRecipeRepository.getRandomCustomizedRecipes(apiKey, tags, number)
        }

    }

    fun getRecipeSelectedByUser(apiKey: String, query:String, number: Int){

    }

}