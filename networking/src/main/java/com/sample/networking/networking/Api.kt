package com.sample.networking.networking

import com.sample.networking.domain.Results
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("2e90d629-463c-4db5-85c8-368f2ed331a8")
    suspend fun getSimpsonCharacters(): Response<Results>

    @GET("a2b234ce-4e48-425d-b3da-43d5a64112c2")
    suspend fun getWireCharacters(): Response<Results>
}