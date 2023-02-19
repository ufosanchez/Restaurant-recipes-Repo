package com.project.v1.testing.recipesproject1.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.project.v1.testing.recipesproject1.models.UserRecipe
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.COLLECTIONNAME
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.RECIPECOLLECTIONNAME
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.INGREDIENTS
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.CUISINETYPE
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.INSTRUCTIONS
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.RECIPENAME





class UserRecipeRepository(val context: Context) {

    private val TAG = this@UserRecipeRepository.toString()

    private val sharedPreferences = context.getSharedPreferences("USER_PREFERENCE_DATA", Context.MODE_PRIVATE)

    private var loggedInUserDocId = ""

    private val db = Firebase.firestore

    private var _isUserRecipeAdded = MutableLiveData<Boolean>()


    val isUserRecipeAdded:LiveData<Boolean>
    get() = _isUserRecipeAdded

    private var _isUserRecipeDeleted = MutableLiveData<Boolean>()

    val isUserRecipeDeleted:LiveData<Boolean>
    get() = _isUserRecipeDeleted

    private var _isUserRecipeUpdated = MutableLiveData<Boolean>()

    val isUserRecipeUpdated:LiveData<Boolean>
    get() = _isUserRecipeUpdated


    private var _favRecipesList = MutableLiveData<List<UserRecipe>>()

    val favRecipesList:LiveData<List<UserRecipe>>
    get() = _favRecipesList

    val userRecipeList = mutableListOf<UserRecipe>()


    init {
        if (sharedPreferences.contains("USER_DOC_ID")){
            loggedInUserDocId = sharedPreferences.getString("USER_DOC_ID", "").toString()
        }
    }

    fun addRecipeToDB(userRecipe: UserRecipe){

        try {
            val data = mutableMapOf<String, Any>()

            data[RECIPENAME] = userRecipe.name

            data[CUISINETYPE] = userRecipe.cuisine

            data[INSTRUCTIONS] = userRecipe.instructions

            data[INGREDIENTS] = userRecipe.ingredients

            data["recId"] = userRecipe.recId

            if (loggedInUserDocId.isNotEmpty()){
                db.collection(COLLECTIONNAME)
                    .document(loggedInUserDocId)
                    .collection(RECIPECOLLECTIONNAME)
                    .document(userRecipe.recId)
                    .set(data)
                    .addOnSuccessListener {
                        Log.d(TAG, "Recipe Added to the Recipes sub-collection with doc id ${userRecipe.recId}")
                        _isUserRecipeAdded.value = true
                    }
                    .addOnFailureListener {
                        Log.d(TAG, "Recipe Could not be added to the sub-collection")
                    }
            }else{

                Log.d(TAG, "No Logged in user found")

            }
        }catch (e:Exception){
            Log.d(TAG, "Error performing add recipe operation ${e.message}")
        }
    }

    fun getAllFavoriteRecipes(){
        try {
            if (loggedInUserDocId.isNotEmpty()){

                db.collection(COLLECTIONNAME)
                    .document(loggedInUserDocId)
                    .collection(RECIPECOLLECTIONNAME)
                    .addSnapshotListener { querySnapshot, error ->

                        if (error != null){
                            Log.d(TAG, "Error fetching records from the Sub-collection")
                            return@addSnapshotListener
                        }

                        if (querySnapshot != null){
                            Log.d(TAG, "${querySnapshot.size()} documents retrieved from the sub-collection")

                            userRecipeList.clear()

                            for (documentChange in querySnapshot.documentChanges){

                                val currentRecipe = documentChange.document.toObject(UserRecipe::class.java)

                                val currentRecipeId = documentChange.document.id

                                when(documentChange.type){

                                    DocumentChange.Type.ADDED -> {
                                        userRecipeList.add(currentRecipe)
                                    }

                                    DocumentChange.Type.MODIFIED -> {

                                    }

                                    DocumentChange.Type.REMOVED ->{

                                        userRecipeList.remove(currentRecipe)
                                    }

                                }


                            }

                            _favRecipesList.value = userRecipeList
                        }

                    }

            }else{
                Log.d(TAG, "User not found in Shared Preferences")
            }
        }catch (e:Exception){
            Log.d(TAG, "Error performing fetch operation ${e.message}")
        }
    }

    fun deleteFavoriteRecipeFromDatabase(userRecipe: UserRecipe){
        try {

            Log.d(TAG, "ID to delete ${userRecipe.recId}")
            db.collection(COLLECTIONNAME)
                .document(loggedInUserDocId)
                .collection(RECIPECOLLECTIONNAME)
                .document(userRecipe.recId)
                .delete()
                .addOnSuccessListener {
                    Log.d(TAG, "${userRecipe.name} deleted from the Database")
                    _isUserRecipeDeleted.value = true
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error performing delete operation ${it.message}")
                }


        }catch (e:Exception){
            Log.d(TAG, "Error Deleting recipe from the Database ${e.message}")
        }
    }

    fun resetUserRecipeDeletedValue(){
        _isUserRecipeDeleted.value = false
    }

    fun updateSelectedFavoriteRecipe(userRecipe: UserRecipe, data:MutableMap<String, Any>){
        try {

            db.collection(COLLECTIONNAME)
                .document(loggedInUserDocId)
                .collection(RECIPECOLLECTIONNAME)
                .document(userRecipe.recId)
                .update(data)
                .addOnSuccessListener {
                    Log.d(TAG, "User Recipe successfully updated")
                    _isUserRecipeUpdated.value = true
                }
                .addOnFailureListener {
                    Log.d(TAG, "Error performing update operation ${it.message}")
                }


        }catch (e:Exception){
            Log.d(TAG, "Error updating the recipe")
        }
    }

    fun resetRecipeUpdatedBoolValue(){
        _isUserRecipeUpdated.value = false
    }


    fun resetIsRecipeAddedValue(){
        _isUserRecipeAdded.value = false
    }

}