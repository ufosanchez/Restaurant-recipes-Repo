package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.project.v1.testing.recipesproject1.repository.RecipeRepository

class RecipeViewModelFactory(private val myRecipeRepository: RecipeRepository):ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(RandomRecipeViewModel::class.java)){
            return RandomRecipeViewModel(myRecipeRepository) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel")

    }



}