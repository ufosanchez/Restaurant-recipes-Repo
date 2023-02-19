package com.project.v1.testing.recipesproject1.models

import java.util.*

data class FeedbackDB(var id : String = UUID.randomUUID().toString(), var name : String = "", var description : String = "") {
}