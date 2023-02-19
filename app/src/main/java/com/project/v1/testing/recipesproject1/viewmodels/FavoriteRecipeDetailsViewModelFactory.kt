package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras

class FavoriteRecipeDetailsViewModelFactory(private var name:String, private var cuisine:String, private var ingredients:String, private var instructions:String):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(FavoriteRecipeDetailsViewModel::class.java)){
            return FavoriteRecipeDetailsViewModel(name, cuisine, ingredients, instructions) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel")

    }

}