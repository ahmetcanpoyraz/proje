package com.example.appcentproje.service

import com.example.appcentproje.model.DetailOfGamesModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailOfGamesAPI {
    @GET()
    fun getData(@Url b:String): Call<DetailOfGamesModel>
}