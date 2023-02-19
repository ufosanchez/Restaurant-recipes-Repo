package com.project.v1.testing.recipesproject1.models

data class Step(
    val number: Long = 0L,
    val step: String = "",
    val ingredients: List<Ingredient>? = null,
    val equipment: List<Equipment>? = null,
    val length: Length? = null,
):java.io.Serializable
