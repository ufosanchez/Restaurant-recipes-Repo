package com.project.v1.testing.recipesproject1.ui

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.adapters.FavoriteRecipesAdapter
import com.project.v1.testing.recipesproject1.databinding.FragmentFavoriteRecipesBinding
import com.project.v1.testing.recipesproject1.listeners.IFavoriteRecipeClickListener
import com.project.v1.testing.recipesproject1.models.UserRecipe
import com.project.v1.testing.recipesproject1.repository.UserRecipeRepository


/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteRecipesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteRecipesFragment : Fragment(), IFavoriteRecipeClickListener {

    private val TAG = this@FavoriteRecipesFragment.toString()

    private var _binding:FragmentFavoriteRecipesBinding? = null

    private val binding:FragmentFavoriteRecipesBinding
     get() = _binding!!

    lateinit var myView: View

    lateinit var userRecipeRepository: UserRecipeRepository

    lateinit var favoriteRecipesAdapter: FavoriteRecipesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favorite_recipes, container, false)

        _binding = FragmentFavoriteRecipesBinding.inflate(layoutInflater, container, false)

        myView = binding.root

        userRecipeRepository = UserRecipeRepository(this.requireActivity())

        favoriteRecipesAdapter = FavoriteRecipesAdapter(this.requireActivity(), this)

        userRecipeRepository.favRecipesList.observe(viewLifecycleOwner, Observer {
            if (it!=null){
                Log.d(TAG, "Sub-collection data retrieved from Firebase ${it}")
                favoriteRecipesAdapter.favoriteRecipesList = it
            }

        })
        binding.recyclerFavRecipes.adapter = favoriteRecipesAdapter

        return myView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userRecipeRepository.getAllFavoriteRecipes()

    }

    override fun onFavoriteRecipeClicked(userRecipe: UserRecipe) {
        Log.d(TAG, "Click even received on Favorite recipe $userRecipe")

        val action:NavDirections = FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToFavoriteRecipeDetailsFragment(userRecipe)

        myView.findNavController().navigate(action)


    }


}