package com.sample.networking.repository

import com.sample.networking.domain.Results
import com.sample.networking.networking.Api
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: Api) : Repository {

    override suspend fun getSimpsonCharacterList(): Response<Results> {
        return api.getSimpsonCharacters()
    }
    override suspend fun getWireCharacterList(): Response<Results> {
        return api.getWireCharacters()
    }
}
