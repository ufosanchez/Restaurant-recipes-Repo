package com.project.v1.testing.recipesproject1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.FragmentFavoriteRecipeDetailsBinding
import com.project.v1.testing.recipesproject1.models.UserRecipe
import com.project.v1.testing.recipesproject1.models.data
import com.project.v1.testing.recipesproject1.repository.UserRecipeRepository
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants
import com.project.v1.testing.recipesproject1.viewmodels.FavoriteRecipeDetailsViewModel
import com.project.v1.testing.recipesproject1.viewmodels.FavoriteRecipeDetailsViewModelFactory
import java.util.stream.Stream


/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteRecipeDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteRecipeDetailsFragment : Fragment() {


    private var _binding:FragmentFavoriteRecipeDetailsBinding? = null

    private val binding:FragmentFavoriteRecipeDetailsBinding
    get() = _binding!!

    lateinit var myView: View

    lateinit var userRecipe: UserRecipe

    lateinit var favoriteRecipeDetailsViewModel: FavoriteRecipeDetailsViewModel

    lateinit var userRecipeRepository: UserRecipeRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favorite_recipe_details, container, false)
        _binding = FragmentFavoriteRecipeDetailsBinding.inflate(layoutInflater, container, false)

        myView = binding.root

        userRecipe = FavoriteRecipeDetailsFragmentArgs.fromBundle(requireArguments()).userRecipe

        val favoriteRecipeDetailsViewModelFactory = FavoriteRecipeDetailsViewModelFactory(userRecipe.name, userRecipe.cuisine,
        userRecipe.ingredients, userRecipe.instructions)

        favoriteRecipeDetailsViewModel = ViewModelProvider(this, favoriteRecipeDetailsViewModelFactory).get(FavoriteRecipeDetailsViewModel::class.java)

        userRecipeRepository = UserRecipeRepository(this.requireActivity())

        userRecipeRepository.isUserRecipeDeleted.observe(viewLifecycleOwner, Observer {

            if (it){
                val action:NavDirections = FavoriteRecipeDetailsFragmentDirections.actionFavoriteRecipeDetailsFragmentToFavoriteRecipesFragment()

                myView.findNavController().navigate(action)

                userRecipeRepository.resetUserRecipeDeletedValue()

            }


        })

        userRecipeRepository.isUserRecipeUpdated.observe(viewLifecycleOwner, Observer {

            if (it){
                val action:NavDirections = FavoriteRecipeDetailsFragmentDirections.actionFavoriteRecipeDetailsFragmentToFavoriteRecipesFragment()

                myView.findNavController().navigate(action)

                userRecipeRepository.resetRecipeUpdatedBoolValue()
            }


        })

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnUpdateRecipe.setOnClickListener {
            updateRecipe()
        }

        binding.btnDeleteRecipe.setOnClickListener {
            deleteRecipe()
        }

        updateScreen()

    }

    private fun updateScreen(){
        binding.tvFavRecipeName.text = favoriteRecipeDetailsViewModel.name

        binding.tvFavRecipeCuisine.text = favoriteRecipeDetailsViewModel.cuisine

        binding.tvFavRecipeInstructions.text = favoriteRecipeDetailsViewModel.instructions

        binding.tvFavRecipeIngredients.text = favoriteRecipeDetailsViewModel.ingredients

    }

    private fun deleteRecipe(){
        val confirmDialog = AlertDialog.Builder(this.requireActivity())
            .setTitle("Confirm Operation")
            .setMessage("Do you want to delete this Recipe")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yes"){dialog, which ->
                // TODO: Delete Recipe from the DB
                userRecipeRepository.deleteFavoriteRecipeFromDatabase(userRecipe)

            }
            .setNegativeButton("No", null)
            .create()
        confirmDialog.show()

    }

    private fun updateRecipe(){

        val inflater = requireActivity().layoutInflater

        val dialogView = inflater.inflate(R.layout.dialog_edit_recipe_layout, null)

        val confirmUpdateDialog = AlertDialog.Builder(this.requireActivity())
            .setTitle("Confirm Operation")
            .setMessage("Do you want to update this Recipe")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setView(dialogView)
            .setPositiveButton("Yes"){dialog, which ->

                val edtRecipeName = dialogView.findViewById<EditText>(R.id.edt_dialog_recipe_name)

                val edtRecipeCuisine = dialogView.findViewById<EditText>(R.id.edt_dialog_recipe_cuisine)

                val edtRecipeIngredients = dialogView.findViewById<EditText>(R.id.edt_dialog_recipe_ingredients)

                val edtRecipeInstructions = dialogView.findViewById<EditText>(R.id.edt_dialog_recipe_instructions)

                val newRecipeName = edtRecipeName.text.toString()

                val newRecipeCuisine = edtRecipeCuisine.text.toString()

                val newRecipeIngredients = edtRecipeIngredients.text.toString()

                val newRecipeInstructions = edtRecipeInstructions.text.toString()

                val dataMap:MutableMap<String, Any> = mutableMapOf()

                if (!newRecipeName.isEmpty()) dataMap[MyConstants.RECIPENAME] = newRecipeName
                    else dataMap[MyConstants.RECIPENAME] = userRecipe.name

                if (!newRecipeCuisine.isEmpty()) dataMap[MyConstants.CUISINETYPE] = newRecipeCuisine
                   else dataMap[MyConstants.CUISINETYPE] = userRecipe.cuisine

                if (!newRecipeIngredients.isEmpty()) dataMap[MyConstants.INGREDIENTS] = newRecipeIngredients
                  else dataMap[MyConstants.INGREDIENTS] = userRecipe.ingredients

                if (!newRecipeInstructions.isEmpty()) dataMap[MyConstants.INSTRUCTIONS] = newRecipeInstructions
                  else dataMap[MyConstants.INSTRUCTIONS] = userRecipe.instructions

                userRecipeRepository.updateSelectedFavoriteRecipe(userRecipe, dataMap)

            }
            .setNegativeButton("No", null)
            .create()
        confirmUpdateDialog.show()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}