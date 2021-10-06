package com.example.appcentproje.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.appcentproje.model.Results

class HomeViewModel : ViewModel() {
    val results = MutableLiveData<ArrayList<Results>>()
    val resultsError = MutableLiveData<Boolean>()

}