package com.project.v1.testing.recipesproject1.viewmodels

import android.content.Context
import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.v1.testing.recipesproject1.models.User
import com.project.v1.testing.recipesproject1.repository.UserRepository
import com.project.v1.testing.recipesproject1.singletonsources.UserDataSource
import kotlinx.coroutines.launch

class SignupFragmentViewModel(val userRepository: UserRepository):ViewModel() {

    private val TAG = this@SignupFragmentViewModel.toString()

    // TODO: Reference to our FirebaseAuth. Firebase Auths are created as Singletons
    private lateinit var mAuth: FirebaseAuth


    suspend fun createUserAccount(email:String, password:String){

        // TODO: Get the FirebaseAuth Instance
        mAuth = FirebaseAuth.getInstance()

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {  task ->

            if (task.isSuccessful){

                Log.d(TAG, "User account successfully created. Add this user to the user's collection")

                val createdUser = task.result.user

                Log.d(TAG, "User id received from the newly created user account = ${createdUser!!.uid}")

                val newUser = User(uid = createdUser.uid, email = createdUser.email!!)

                UserDataSource.getInstance().setCurrentUser(createdUser)



                askRepositoryToAddUserToDB(newUser)


            }

        }

    }

    private fun askRepositoryToAddUserToDB(newUser:User){

        userRepository.addUserToDatabase(newUser)

    }

}