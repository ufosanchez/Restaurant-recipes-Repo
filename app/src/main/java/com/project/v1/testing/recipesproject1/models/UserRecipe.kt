package com.project.v1.testing.recipesproject1.models

import java.util.*

data class UserRecipe(var recId:String = UUID.randomUUID().toString(), var cuisine:String = "", var ingredients:String = "", var instructions:String = "", var name:String = ""):java.io.Serializable
