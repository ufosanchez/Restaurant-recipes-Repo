package com.project.v1.testing.recipesproject1.models

data class Equipment(
    val id: Long = 0L,
    val name: String = "",
    val localizedName: String = "",
    val image: String = "",
    val temperature: Temperature? = null,
):java.io.Serializable
