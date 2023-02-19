package com.project.v1.testing.recipesproject1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.v1.testing.recipesproject1.databinding.FavRecipeRowLayoutBinding
import com.project.v1.testing.recipesproject1.listeners.IFavoriteRecipeClickListener
import com.project.v1.testing.recipesproject1.models.UserRecipe

class FavoriteRecipesAdapter(val context: Context, val iFavoriteRecipeClickListener: IFavoriteRecipeClickListener):RecyclerView.Adapter<FavoriteRecipesAdapter.FavoriteRecipesViewHolder>() {

    var favoriteRecipesList:List<UserRecipe> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipesViewHolder {

        return FavoriteRecipesViewHolder(FavRecipeRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: FavoriteRecipesViewHolder, position: Int) {

        val currentFavRecipe = favoriteRecipesList[position]

        holder.bind(currentFavRecipe, iFavoriteRecipeClickListener)

    }

    override fun getItemCount(): Int {
        return favoriteRecipesList.size
    }

    inner class FavoriteRecipesViewHolder(val binding:FavRecipeRowLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(userRecipe: UserRecipe, iFavoriteRecipeClickListener: IFavoriteRecipeClickListener){

            binding.tvFavDispRecipeName.text = userRecipe.name

            binding.tvFavDispCuisine.text = userRecipe.cuisine

            itemView.setOnClickListener {
                iFavoriteRecipeClickListener.onFavoriteRecipeClicked(userRecipe)
            }

        }


    }

}