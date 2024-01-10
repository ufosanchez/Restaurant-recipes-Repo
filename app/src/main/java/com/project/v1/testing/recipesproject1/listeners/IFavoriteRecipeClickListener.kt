package com.project.v1.testing.recipesproject1.listeners

import com.project.v1.testing.recipesproject1.models.UserRecipe

interface IFavoriteRecipeClickListener {

    fun onFavoriteRecipeClicked(userRecipe: UserRecipe)

}