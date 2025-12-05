package com.tech.myapplication.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _image = MutableLiveData<Any?>()
    val image: LiveData<Any?> = _image

    private val _location = MutableLiveData<String>()
    val location: LiveData<String> = _location

    fun setImage(image: Any?) {
        _image.value = image
    }

    fun setLocation(location: String) {
        _location.postValue(location)
    }
}