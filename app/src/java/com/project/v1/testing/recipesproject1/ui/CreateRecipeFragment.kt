package com.project.v1.testing.recipesproject1.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.FragmentCreateRecipeBinding
import com.project.v1.testing.recipesproject1.models.UserRecipe
import com.project.v1.testing.recipesproject1.repository.UserRecipeRepository
import java.util.UUID

/**
 * A simple [Fragment] subclass.
 * Use the [CreateRecipeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateRecipeFragment : Fragment() {

    private var _binding:FragmentCreateRecipeBinding? = null

    private val binding:FragmentCreateRecipeBinding
    get() = _binding!!

    private lateinit var myView:View

    private lateinit var userRecipeRepository: UserRecipeRepository

    private lateinit var progressBarDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_create_recipe, container, false)

        _binding = FragmentCreateRecipeBinding.inflate(layoutInflater, container, false)

        myView = binding.root

        progressBarDialog = AlertDialog.Builder(this.requireContext())
            .setView(R.layout.progress_bar_layout).create()

        userRecipeRepository = UserRecipeRepository(this.requireActivity())

        userRecipeRepository.isUserRecipeAdded.observe(viewLifecycleOwner, Observer {

            if (it){
                progressBarDialog.dismiss()

                val action:NavDirections = CreateRecipeFragmentDirections.actionCreateRecipeFragmentToFavoriteRecipesFragment()

                myView.findNavController().navigate(action)

                userRecipeRepository.resetIsRecipeAddedValue()

            }


        })

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveRecipe.setOnClickListener {

            progressBarDialog.show()
            addRecipe()

        }


    }

    private fun addRecipe(){

        val userRecipe = UserRecipe()

        userRecipe.recId = UUID.randomUUID().toString()

        userRecipe.name = binding.edtCreateRecipeName.text.toString()

        userRecipe.cuisine = binding.edtCreateRecipeCuisineType.text.toString()

        userRecipe.ingredients = binding.edtRecipeIngredients.text.toString()

        userRecipe.instructions = binding.edtRecipeInstructions.text.toString()

        userRecipeRepository.addRecipeToDB(userRecipe)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}