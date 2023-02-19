package com.project.v1.testing.recipesproject1.ui

import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.adapters.IngredientsAdapter
import com.project.v1.testing.recipesproject1.databinding.FragmentRecipeDetailsBinding
import com.project.v1.testing.recipesproject1.models.Recipe
import com.project.v1.testing.recipesproject1.viewmodels.RecipeDetailsViewModel
import com.project.v1.testing.recipesproject1.viewmodels.RecipeDetailsViewModelFactory
import com.squareup.picasso.Picasso
import java.time.LocalDate


/**
 * A simple [Fragment] subclass.
 * Use the [RecipeDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RecipeDetailsFragment : Fragment() {

    private val TAG = this@RecipeDetailsFragment.toString()

    private var _binding:FragmentRecipeDetailsBinding? = null

    private val binding:FragmentRecipeDetailsBinding
    get() = _binding!!

    lateinit var recipeDetailsViewModel: RecipeDetailsViewModel

    lateinit var ingredientsAdapter: IngredientsAdapter

    lateinit var recipe: Recipe

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_recipe_details, container, false)

        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)

        val view = binding.root

        recipe = RecipeDetailsFragmentArgs.fromBundle(requireArguments()).recipe

        Log.d(TAG, "Recipe Data received from RecipeDisplayFragment $recipe")

        val recipeDetailsViewModelFactory = RecipeDetailsViewModelFactory(recipe.extendedIngredients!!, recipe.title, recipe.image, recipe.summary, recipe.instructions)

        recipeDetailsViewModel = ViewModelProvider(this, recipeDetailsViewModelFactory).get(RecipeDetailsViewModel::class.java)


        ingredientsAdapter = IngredientsAdapter(requireActivity(), recipeDetailsViewModel.ingredientsList)

//        val recipeData = RecipeDetailsFragmentArgs.fromBundle(requireArguments()).recipe
//
//        Log.d(TAG, "Recipe Data received from RecipeDisplayFragment $recipeData")

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerDetailsIngredients.adapter = ingredientsAdapter

        updateScreen()


    }

    private fun updateScreen(){
        binding.tvDetailsRecipeName.text = recipeDetailsViewModel.recipeTitle

        if (recipeDetailsViewModel.recipeImage.isEmpty() || recipeDetailsViewModel.recipeImage == ""){

            binding.imgDetailsRecipeImg.setImageResource(R.drawable.default_food_img)

        }else{
            Picasso.get().load(recipeDetailsViewModel.recipeImage).into(binding.imgDetailsRecipeImg)
        }

        val summary = HtmlCompat.fromHtml(recipeDetailsViewModel.recipeSummary, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

        binding.tvDetailsRecipeDescription.text = summary

        val instructions = HtmlCompat.fromHtml(recipeDetailsViewModel.recipeInstructionsToDisplay, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

        binding.tvDetailsInstructions.text = instructions
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}