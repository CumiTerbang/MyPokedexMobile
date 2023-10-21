package com.cumiterbang.mypokedexmobile.ui.catch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CatchViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is catch Fragment"
    }
    val text: LiveData<String> = _text
}