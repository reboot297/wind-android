package com.reboot297.wind.android

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {
    val data = MutableLiveData<List<Item>>()

    fun load(uri: Uri) {
        val text = readTextFromUri(uri = uri)
        val result = parseJSON(text)
        data.postValue(result)
    }

    @Throws(IOException::class)
    private fun readTextFromUri(
        uri: Uri
    ): String {
        var json: String? = null
        getApplication<App>().contentResolver.openInputStream(uri)?.use { inputStream ->
            json = inputStream.bufferedReader().use { it.readText() }
        }
        return json.orEmpty()
    }

    private fun parseJSON(source: String): List<Item> {
        val itemType = object : TypeToken<List<Item>>() {}.type
        return Gson().fromJson(source, itemType)
    }

}