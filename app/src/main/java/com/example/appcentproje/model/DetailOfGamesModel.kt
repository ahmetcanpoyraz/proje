package com.example.appcentproje.model

import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.util.*

data class DetailOfGamesModel(
    @SerializedName("id")
    var id: Number,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("released")
    val released: String,
    @SerializedName("metacritic")
    val metacritic: Int,
    @SerializedName("background_image")
    val background_image: String,
    @SerializedName("rating")
    val rating: Number
)
