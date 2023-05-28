package com.sample.networking.repository

import com.sample.networking.domain.Results
import retrofit2.Response

interface Repository {
    suspend fun getSimpsonCharacterList(): Response<Results>
    suspend fun getWireCharacterList(): Response<Results>
}