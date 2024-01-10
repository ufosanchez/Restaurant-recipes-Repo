package com.project.v1.testing.recipesproject1.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.project.v1.testing.recipesproject1.models.FeedbackDB
import java.lang.Exception

class FeedbackRepository {

    private val TAG = this.toString()
    private val db = Firebase.firestore

    private val COLLECTION_NAME = "feedback"
    private val FEILD_NAME = "name"
    private val FEILD_DESCRIPTION = "description"

    var allFeedback : MutableLiveData<List<FeedbackDB>> = MutableLiveData<List<FeedbackDB>>()

    fun addFeedbacktoDB(newFeedback : FeedbackDB){
        try{
            val data: MutableMap<String, Any> = HashMap()

            data[FEILD_NAME] = newFeedback.name;
            data[FEILD_DESCRIPTION] = newFeedback.description;

            db.collection(COLLECTION_NAME).add(data).addOnSuccessListener { docRef ->
                Log.d(TAG, "addFeedbacktoDB: Document added with ID ${docRef.id}")
            }.addOnFailureListener{
                Log.e(TAG, "addFeedbacktoDB: ${it}", )
            }

        }catch(ex: Exception){
            Log.e(TAG, "addFeedbacktoDB: ${ex.toString()}")
        }
    }

    fun getAllFeedback(){

        try{
            db.collection(COLLECTION_NAME).addSnapshotListener(EventListener { snapshot, error ->
                if (error != null){
                    Log.e(TAG, "getAllFeedback: Listening to collection documents FAILED ${error}", )
                    return@EventListener
                }

                if (snapshot != null){
                    Log.e(TAG, "getAllFeedback: ${snapshot.size()} Received the documents from collection ${snapshot}", )

                    val feedbackArrayList : MutableList<FeedbackDB> = ArrayList<FeedbackDB>()

                    for(documentChange in snapshot.documentChanges){

                        val currentFeedback : FeedbackDB = documentChange.document.toObject(FeedbackDB::class.java)
                        currentFeedback.id = documentChange.document.id

                        when(documentChange.type){
                            DocumentChange.Type.ADDED -> { feedbackArrayList.add(currentFeedback)}
                            else -> {}
                        }
                    }

                    Log.e(TAG, "getAllFeedback: ${feedbackArrayList.toString()}", )
                    allFeedback.postValue(feedbackArrayList)

                }else{
                    Log.e(TAG, "getAllFeedback: No Documents received from collection", )
                }

            })


        }catch(ex: Exception){
            Log.e(TAG, "getAllFeedback: ${ex}", )
        }
    }
}