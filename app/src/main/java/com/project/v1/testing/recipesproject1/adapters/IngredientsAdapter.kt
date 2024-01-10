package com.project.v1.testing.recipesproject1.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.v1.testing.recipesproject1.R
import com.project.v1.testing.recipesproject1.databinding.IngredientItemLayoutBinding
import com.project.v1.testing.recipesproject1.models.ExtendedIngredient
import com.squareup.picasso.Picasso

class IngredientsAdapter(val context: Context, var ingredientsList:List<ExtendedIngredient>):RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {

        return IngredientsViewHolder(IngredientItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false))

    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {

        val extendedIngredient = ingredientsList[position]

        holder.bind(extendedIngredient)

    }

    override fun getItemCount(): Int {

        return ingredientsList.size

    }

    inner class IngredientsViewHolder(val binding:IngredientItemLayoutBinding):RecyclerView.ViewHolder(binding.root){


        fun bind(extendedIngredient: ExtendedIngredient){

            binding.tvIngredientName.text = extendedIngredient.name


            binding.tvIngredientUnit.text = extendedIngredient.original

            if (extendedIngredient.image.isEmpty() || extendedIngredient.image == ""){
                binding.imgIngredientImg.setImageResource(R.drawable.default_veggies)
            }else{
                Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/${extendedIngredient.image}").into(binding.imgIngredientImg)
            }

        }

    }


}