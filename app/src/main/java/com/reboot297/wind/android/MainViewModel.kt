package com.reboot297.wind.android

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    val data = listOf(Item("title1", "http://url1.com"), Item("title2", "https://url.com"))
}