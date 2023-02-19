package com.project.v1.testing.recipesproject1.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Recipes(val recipes:List<Recipe>):java.io.Serializable
