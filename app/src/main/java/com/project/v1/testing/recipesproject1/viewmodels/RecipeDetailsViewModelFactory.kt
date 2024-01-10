package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.project.v1.testing.recipesproject1.models.ExtendedIngredient

class RecipeDetailsViewModelFactory(private val ingredientsList:List<ExtendedIngredient>, private var recipeTitle:String, private var recipeImage:String, private var recipeSummary:String, private var recipeInstructionsToDisplay:String):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(RecipeDetailsViewModel::class.java)){
            return RecipeDetailsViewModel(ingredientsList, recipeTitle, recipeImage, recipeSummary, recipeInstructionsToDisplay) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel")

    }


}