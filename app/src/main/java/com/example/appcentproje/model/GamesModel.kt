package com.example.appcentproje.model

import com.google.gson.annotations.SerializedName

data class GamesModel(

    @SerializedName("count")
    val count: Int,
    @SerializedName("results")
    var result: List<Results>

)
