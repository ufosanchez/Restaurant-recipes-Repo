package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.ViewModel
import com.project.v1.testing.recipesproject1.models.ExtendedIngredient

class RecipeDetailsViewModel(val ingredientsList:List<ExtendedIngredient>, var recipeTitle:String, var recipeImage:String,
     var recipeSummary:String, var recipeInstructionsToDisplay:String
     ):ViewModel() {





}