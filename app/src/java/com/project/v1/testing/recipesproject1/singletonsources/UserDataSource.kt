package com.project.v1.testing.recipesproject1.singletonsources


import com.google.firebase.auth.FirebaseUser

class UserDataSource private constructor() {

    private var _user:FirebaseUser? = null

    val user:FirebaseUser?
        get() = _user


    fun setCurrentUser(fbUser:FirebaseUser?){

        _user = fbUser

    }

    companion object {
        @Volatile
        private lateinit var instance: UserDataSource

        fun getInstance(): UserDataSource {
            synchronized(this) {
                if (!::instance.isInitialized) {
                    instance = UserDataSource()
                }
                return instance
            }
        }
    }
}