package com.project.v1.testing.recipesproject1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.project.v1.testing.recipesproject1.repository.UserRepository

class SignUpModelViewModelFactory(private val userRepository: UserRepository):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

        if (modelClass.isAssignableFrom(SignupFragmentViewModel::class.java)){
            return SignupFragmentViewModel(userRepository) as T
        }

        throw java.lang.IllegalArgumentException("Unknown ViewModel")

    }

}