package com.project.v1.testing.recipesproject1.adapters

//Photo by <a href="https://unsplash.com/@briewilly?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Chad Montano</a> on <a href="https://unsplash.com/s/photos/Food?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">Unsplash</a>


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.RecipeItemRowLayoutBinding
import com.project.v1.testing.recipesproject1.listeners.IRecipeClickListener
import com.project.v1.testing.recipesproject1.models.Recipe
import com.squareup.picasso.Picasso

class RecipeAdapter(val context: Context, val iRecipeClickListener: IRecipeClickListener):RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    var recipeList:List<Recipe> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

//    init {
//        recipeList = listOf()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {

        return RecipeViewHolder(RecipeItemRowLayoutBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {

        val getRecipe = recipeList[position]

        holder.bind(getRecipe, iRecipeClickListener)
    }

    override fun getItemCount(): Int {

        return recipeList.size
    }

    inner class RecipeViewHolder(val binding: RecipeItemRowLayoutBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(recipe:Recipe, iRecipeClickListener: IRecipeClickListener){

            binding.tvRecipeTitle.text = recipe.title
            binding.tvRecipeTitle.isSelected = true

            binding.tvRecipeLikes.text = recipe.aggregateLikes.toString()

            val servingsText = "Serves ${recipe.servings}"
            binding.tvRecipeServing.text = servingsText

            val timeTakenTxt = "${recipe.readyInMinutes} Mins"
            binding.tvRecipeTimeTaken.text = timeTakenTxt

            if (recipe.image.isEmpty() || recipe.image == ""){
                binding.imgRecipeImg.setImageResource(R.drawable.default_food_img)
            }else{
                Picasso.get().load(recipe.image).into(binding.imgRecipeImg)
            }

            itemView.setOnClickListener {
                iRecipeClickListener.onRecipeClicked(recipe)
            }

        }

    }

}