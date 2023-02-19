package com.project.v1.testing.recipesproject1.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.FragmentSignUpBinding
import com.project.v1.testing.recipesproject1.repository.UserRepository
import com.project.v1.testing.recipesproject1.singletonsources.UserDataSource
import com.project.v1.testing.recipesproject1.viewmodels.SignUpModelViewModelFactory
import com.project.v1.testing.recipesproject1.viewmodels.SignupFragmentViewModel
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {

    private val TAG = this@SignUpFragment.toString()

    private var _binding:FragmentSignUpBinding? = null

    private val binding:FragmentSignUpBinding
    get() = _binding!!

    private lateinit var myView:View

    private lateinit var progressBarDialog: Dialog

    lateinit var sharedPreferences: SharedPreferences

    var userRepository: UserRepository = UserRepository()

    lateinit var signupFragmentViewModel: SignupFragmentViewModel

    lateinit var userDataSource: UserDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signUpModelViewModelFactory = SignUpModelViewModelFactory(userRepository)

        signupFragmentViewModel = ViewModelProvider(this, signUpModelViewModelFactory).get(SignupFragmentViewModel::class.java)

        userDataSource = UserDataSource.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_sign_up, container, false)

        _binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        myView = binding.root

        progressBarDialog = AlertDialog.Builder(this.requireContext())
            .setView(R.layout.progress_bar_layout).create()

        sharedPreferences = this.requireContext().getSharedPreferences("USER_PREFERENCE_DATA", Context.MODE_PRIVATE)


        userRepository.isUserAdded.observe(viewLifecycleOwner, Observer {
            if (it){

                progressBarDialog.dismiss()

                val fbUser = userDataSource.user

                if (fbUser != null){
                    with(sharedPreferences.edit()) {

                        this.putString("USER_DOC_ID", fbUser.uid)
                        this.putString("USER_DOC_EMAIL", fbUser.email)
                        this.commit()
                    }
//                    val getAct = this.requireActivity() as MainActivity
//
//                    val menu = getAct.navView.menu

                    val action:NavDirections = SignUpFragmentDirections.actionSignUpFragmentToRecipeDisplayFragment()

                    myView.findNavController().navigate(action)

                    userRepository.resetBoolValue()
                }else{
                    Log.d(TAG, "Firebase User not found")
                    Toast.makeText(this.requireActivity(), "Firebase User not found", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignUp.setOnClickListener {

            if (validateUserData()) {
                Log.d(TAG, "Add the user to the Firebase Database")

                progressBarDialog.show()

                viewLifecycleOwner.lifecycleScope.launch {

                    signupFragmentViewModel.createUserAccount(binding.edtEmailAddressInput.text.toString(), binding.edtPasswordInput.text.toString())

                }
            }else {
                  Log.d(TAG, "User cannot be validated")
                  return@setOnClickListener
              }

        }


    }

    private fun validateUserData():Boolean{

        if (binding.edtEmailAddressInput.text.isEmpty() || binding.edtEmailAddressInput.text.toString() == ""){

            binding.edtEmailAddressInput.error = "Email Address cannot be empty"
            return false
        }else if (binding.edtPasswordInput.text.isEmpty() || binding.edtPasswordInput.text.toString() == "" || binding.edtPasswordInput.text.toString().length < 7){

            binding.edtPasswordInput.error = "Password cannot be empty and should be at least 7 chars"
            return  false

        }else return validateUserEmail()



    }

    private fun validateUserEmail():Boolean{

        return if (binding.edtEmailAddressInput.text.toString().indexOf("@") == -1 || binding.edtEmailAddressInput.text.toString()[0].toString() == "."){
            binding.edtEmailAddressInput.error = "Please enter a valid email"
            false
        }else{
            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}