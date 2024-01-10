package com.project.v1.testing.recipesproject1.models

data class ExtendedIngredient(
    val id: Long,
    val aisle: String = "",
    val image: String = "",
    val consistency: String = "",
    val name: String = "",
    val nameClean: String = "",
    val original: String = "",
    val originalName: String = "",
    val amount: Double = 0.0,
    val unit: String = "",
    val meta: List<String> = listOf(""),
    val measures: Measures? = null,
):java.io.Serializable
