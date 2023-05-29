package com.sample.simpsonsviewer.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.networking.domain.Results
import com.sample.networking.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()
    val characterList = MutableLiveData<Results>()
    val loading = MutableLiveData<Boolean>()

    fun getSimpsonCharacterList() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getSimpsonCharacterList()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    characterList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }
}