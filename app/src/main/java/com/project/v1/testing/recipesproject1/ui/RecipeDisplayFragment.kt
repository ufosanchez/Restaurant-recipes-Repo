package com.project.v1.testing.recipesproject1.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.adapters.RecipeAdapter
import com.project.v1.testing.recipesproject1.databinding.FragmentRecipeDisplayBinding
import com.project.v1.testing.recipesproject1.listeners.IRecipeClickListener
import com.project.v1.testing.recipesproject1.models.Recipe
import com.project.v1.testing.recipesproject1.repository.RecipeRepository
import com.project.v1.testing.recipesproject1.utilitypack.MyConstants.Companion.API_KEY
import com.project.v1.testing.recipesproject1.viewmodels.RandomRecipeViewModel
import com.project.v1.testing.recipesproject1.viewmodels.RecipeViewModelFactory
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDisplayFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDisplayFragment : Fragment(), IRecipeClickListener {

    private val TAG = this@RecipeDisplayFragment.toString()

    private var _binding:FragmentRecipeDisplayBinding? = null

    val binding:FragmentRecipeDisplayBinding
    get() = _binding!!

    lateinit var myView: View

    lateinit var recipeAdapter: RecipeAdapter

    lateinit var recipeRepository: RecipeRepository

    lateinit var recipeViewModel:RandomRecipeViewModel

    lateinit var progressBarDialog: Dialog


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentRecipeDisplayBinding.inflate(layoutInflater, container, false)

        //val view = binding.root

        myView = binding.root

        recipeRepository = RecipeRepository()

        val recipeViewModelFactory = RecipeViewModelFactory(recipeRepository)

        recipeViewModel = ViewModelProvider(this, recipeViewModelFactory).get(RandomRecipeViewModel::class.java)

        recipeAdapter = RecipeAdapter(requireActivity(), this)

        progressBarDialog = AlertDialog.Builder(this.requireContext())
            .setView(R.layout.progress_bar_layout).create()


        recipeViewModel.randomRecipeList.observe(viewLifecycleOwner, Observer {

            if (it !=null){
                Log.d(TAG, "Recipe List updated using LiveData $it")
                recipeAdapter.recipeList = it
                progressBarDialog.dismiss()
            }else{
                Log.d(TAG, "Data received is Null")
            }
        })

        binding.recyclerDisplRecipes.adapter = recipeAdapter

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSearchRecipe.setOnClickListener {

            progressBarDialog.show()

            getCustomRecipePayload()

        }

        progressBarDialog.show()
        randomRecipePayload()
    }


    // TODO: Get Random Recipes from the API
    private fun randomRecipePayload(){

        viewLifecycleOwner.lifecycleScope.launch {

            recipeViewModel.getRandomRecipesFromRepository(API_KEY, 5)
        }

    }

    private fun getCustomRecipePayload(){

        viewLifecycleOwner.lifecycleScope.launch {

            val getRecipeTxt = binding.edtSearchRecipe.text.toString()

            if (getRecipeTxt.isEmpty() || getRecipeTxt == ""){
                binding.edtSearchRecipe.error = "Please enter a recipe"
                return@launch
            }else{
                recipeViewModel.getRandomCustomRecipesFromRepository(API_KEY, getRecipeTxt, 3)
            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRecipeClicked(recipe: Recipe) {

        val action:NavDirections = RecipeDisplayFragmentDirections.actionRecipeDisplayFragmentToRecipeDetailsFragment(recipe)

        myView.findNavController().navigate(action)


    }

}