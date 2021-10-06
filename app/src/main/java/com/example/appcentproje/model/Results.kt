package com.example.appcentproje.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("background_image")
    val background_image: String,
    @SerializedName("rating")
    val rating: Number,
    @SerializedName("released")
    val released: String
)
