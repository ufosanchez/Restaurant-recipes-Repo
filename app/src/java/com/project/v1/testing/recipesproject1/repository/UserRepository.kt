package com.project.v1.testing.recipesproject1.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.v1.testing.recipesproject1.models.User
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.COLLECTIONNAME
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.FIELDUSERFIRSTNAME
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.FIELDUSERLASTNAME
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.FIELDUSERADDRESS
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.FIELDUSEREMAIL
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.FIELDUSERID





class UserRepository() {

    private val TAG = this@UserRepository.toString()

    private val db = Firebase.firestore

    private val _isUserAdded = MutableLiveData<Boolean>(false)

//    private val sharedPreferences = context.getSharedPreferences("USER_PREFERENCES", Context.MODE_PRIVATE)
//
//    private val editor = sharedPreferences.edit()

    val isUserAdded: LiveData<Boolean>
        get() = _isUserAdded

    var isUserFound = false



    fun addUserToDatabase(user:User){

        val dataMap:MutableMap<String, Any> = mutableMapOf()

        dataMap[FIELDUSERID] = user.uid

        dataMap[FIELDUSERFIRSTNAME] = user.firstName

        dataMap[FIELDUSERLASTNAME] = user.lastName

        dataMap[FIELDUSERADDRESS] = user.address

        dataMap[FIELDUSEREMAIL] = user.email

        db.collection(COLLECTIONNAME).document(user.uid).set(dataMap).addOnSuccessListener {

            Log.d(TAG, "User added to the Collection $COLLECTIONNAME---User Details $user")


            _isUserAdded.value = true

        }
            .addOnFailureListener {

                Log.d(TAG, "Error adding user to the Collection")

            }

    }

    fun checkIfUserExists(user:FirebaseUser?):Boolean{
        try {

            db.collection(COLLECTIONNAME)
                .whereEqualTo("uid", user?.uid)
                .addSnapshotListener { querySnapshot, error ->

                    if(error != null){
                        Log.d(TAG, "Error in Snapshot listener")
                        isUserFound = false
                        return@addSnapshotListener
                    }

                    if (querySnapshot != null){
                        Log.d(TAG, "Received Document from the collection")

                        for (documentChange in querySnapshot.documentChanges){
                            Log.d(TAG, "Document Found")
                            isUserFound = true
                           // return@addSnapshotListener
                        }

                    }

                }


        }catch (e:Exception){
            Log.d(TAG, "Error performing search operation ${e.message}")
            isUserFound = false
        }

        return isUserFound
    }


    fun resetBoolValue(){
        _isUserAdded.value = false
    }



}