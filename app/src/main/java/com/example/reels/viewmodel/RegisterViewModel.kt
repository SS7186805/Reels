package com.example.reels.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.reels.model.RegisterUser

class RegisterViewModel : ViewModel() {

    var registerUser = ObservableField<RegisterUser>()
}