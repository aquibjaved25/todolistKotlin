package com.retrofit.model

data class TodoDataModel(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)