package com.project.v1.testing.recipesproject1.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.FragmentWelcomeBinding
import com.project.v1.testing.recipesproject1.repository.UserRepository
import com.project.v1.testing.recipesproject1.singletonsources.UserDataSource
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment() {

    private val TAG = this@WelcomeFragment.toString()

    private var _binding:FragmentWelcomeBinding? = null

    val binding:FragmentWelcomeBinding
     get() = _binding!!


    lateinit var myView: View

    lateinit var userDataSource: UserDataSource

    lateinit var sharedPreferences: SharedPreferences

    /*
        FireBase variables
     */
    lateinit var signInLauncher: ActivityResultLauncher<Intent>

    var user: FirebaseUser? = null

    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = this.requireContext().getSharedPreferences("USER_PREFERENCE_DATA", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_welcome, container, false)

        _binding = FragmentWelcomeBinding.inflate(layoutInflater, container, false)

        myView = binding.root

        userDataSource = UserDataSource.getInstance()

        userRepository = UserRepository()
        signInLauncher = registerForActivityResult(
            FirebaseAuthUIActivityResultContract()
        ) { res ->
            this.onSignInResult(res)
        }

        userRepository.isUserAdded.observe(viewLifecycleOwner, Observer {

            if (it){
                with(sharedPreferences.edit()) {
                    this.putString("USER_DOC_ID", user?.uid)
                    this.putString("USER_DOC_EMAIL", user?.email!!)
                    this.commit()
                }

                navigateToRecipeFragment()
                userRepository.resetBoolValue()
                //parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }

        })

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val act = this.requireActivity() as MainActivity

        val navView = act.navView

        val menu = navView.menu
        if (sharedPreferences.contains("USER_DOC_ID")){

            if (sharedPreferences.getString("USER_DOC_ID", "") != "" && sharedPreferences.getString("USER_DOC_EMAIL", "")!= ""){
                binding.btnWelcomeSignUp.isEnabled = false
                binding.btnLogin.isEnabled = false
                //binding.btnCreateRecipes.isEnabled = true
                menu.findItem(R.id.createRecipeFragment).isEnabled = true
                menu.findItem(R.id.signUpFragment).isEnabled = false
                menu.findItem(R.id.favoriteRecipesFragment).isEnabled = true
                if (menu.findItem(100) == null){
                    menu.add(0, 100, 0, "Logged in:${sharedPreferences.getString("USER_DOC_EMAIL", "")}")
                }

            }else{
                binding.btnWelcomeSignUp.isEnabled = true
                binding.btnLogin.isEnabled = true
               // binding.btnViewRecipes.isEnabled = false
               // binding.btnCreateRecipes.isEnabled = false
                binding.btnLogout.isEnabled = false
                menu.findItem(R.id.createRecipeFragment).isEnabled = false
                menu.findItem(R.id.signUpFragment).isEnabled = true
                menu.findItem(R.id.favoriteRecipesFragment).isEnabled = false
                if(menu.findItem(100) != null){
                    menu.removeItem(100)
                }
            }


        }else{
            binding.btnWelcomeSignUp.isEnabled = true
            binding.btnLogin.isEnabled = true
           // binding.btnViewRecipes.isEnabled = false
           // binding.btnCreateRecipes.isEnabled = false
            binding.btnLogout.isEnabled = false
            menu.findItem(R.id.createRecipeFragment).isEnabled = false
            menu.findItem(R.id.signUpFragment).isEnabled = true
            menu.findItem(R.id.favoriteRecipesFragment).isEnabled = false
            if(menu.findItem(100) != null){
                menu.removeItem(100)
            }
        }




        binding.btnViewRecipes.setOnClickListener {

            val action:NavDirections = WelcomeFragmentDirections.actionWelcomeFragmentToRecipeDisplayFragment()

            myView.findNavController().navigate(action)

        }

        binding.btnRestaurantsScreen.setOnClickListener {
            val action:NavDirections = WelcomeFragmentDirections.actionWelcomeFragmentToRestaurantsFragment()

            myView.findNavController().navigate(action)
        }

        // TODO: Take the User to the Signup Fragment 
        binding.btnWelcomeSignUp.setOnClickListener {

            val action:NavDirections = WelcomeFragmentDirections.actionWelcomeFragmentToSignUpFragment()

            myView.findNavController().navigate(action)

        }

        // TODO: Handle the user sign-in Logic
        binding.btnLogin.setOnClickListener {
            
        // TODO: Code to Redirect user to the Sign-in Page

        signIn()

        }

        binding.btnLogout.setOnClickListener {
            if (sharedPreferences.contains("USER_DOC_ID")){
                FirebaseAuth.getInstance().signOut()
                userDataSource.setCurrentUser(null)
                with(sharedPreferences.edit()) {
                    this.clear()
                    apply()
                }
                navigateToRecipeFragment()
               //parentFragmentManager.beginTransaction().detach(this).attach(this).commit()

            }
        }

//        binding.btnCreateRecipes.setOnClickListener {
//
//            val action:NavDirections = WelcomeFragmentDirections.actionWelcomeFragmentToCreateRecipeFragment()
//
//            myView.findNavController().navigate(action)
//        }

    }

    private fun signIn(){
        //providerList()

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()


        signInLauncher.launch(signInIntent)

    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult){

        val response = result.idpResponse

        if (result.resultCode == AppCompatActivity.RESULT_OK){

            user = FirebaseAuth.getInstance().currentUser

            Log.d(TAG, "USER ID = ${user?.uid}")

            Log.d(TAG, "USER: ${user?.email}")

            if (response != null) {
                if (response.providerType == GoogleAuthProvider.PROVIDER_ID){
                 Log.d(TAG,"Check if this user is already present in Firebase")

                    val isUserPresent = userRepository.checkIfUserExists(user)
                    
                    if (isUserPresent){
                        Log.d(TAG, "User is already present in the Database, do not add to the collection")
                        userRepository.isUserFound = false
                        navigateToRecipeFragment()
                    }else{
                        // TODO: Add the User to the Users collection
                        val user = com.project.v1.testing.recipesproject1.models.User(uid = user?.uid!!, email = user?.email!!)

                        userRepository.addUserToDatabase(user)

                    }
                    
                }else{
                    with(sharedPreferences.edit()) {
                        this.putString("USER_DOC_ID", user?.uid)
                        this.putString("USER_DOC_EMAIL", user?.email!!)
                        this.commit()
                    }

                    navigateToRecipeFragment()

//                    val fragManager = this@WelcomeFragment.parentFragmentManager
//
//                    fragManager.beginTransaction().detach(this).attach(this).commit()

//                    val act = this.requireActivity() as MainActivity
//
//                    val fragmentTransaction = act.supportFragmentManager.beginTransaction()
//
//                    fragmentTransaction.setReorderingAllowed(true)
//                    fragmentTransaction.detach(this).attach(this)

                    //parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
                }
            }
        }else{
            Log.d(TAG, "Error logging in ${response?.error?.errorCode}")

        }

    }

    private fun navigateToRecipeFragment(){
        val action:NavDirections = WelcomeFragmentDirections.actionWelcomeFragmentToRecipeDisplayFragment()

        myView.findNavController().navigate(action)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}