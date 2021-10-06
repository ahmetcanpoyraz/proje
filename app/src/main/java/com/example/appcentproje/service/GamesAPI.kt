package com.example.appcentproje.service

import com.example.appcentproje.model.GamesModel
import retrofit2.Call
import retrofit2.http.GET

interface GamesAPI {
    @GET("games?key=85a898c97b504b868c144aa5f22df29d&page_size=50")
    fun getData(): Call<GamesModel>
}