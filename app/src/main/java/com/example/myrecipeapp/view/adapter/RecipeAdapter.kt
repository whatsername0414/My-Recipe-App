package com.example.myrecipeapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.R
import com.example.myrecipeapp.databinding.ItemRecipeBinding
import com.example.myrecipeapp.model.Recipe

class RecipeDiffUtil: DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(
        oldItem: Recipe,
        newItem: Recipe
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Recipe,
        newItem: Recipe
    ): Boolean {
        return oldItem.id == oldItem.id
    }
}

class RecipeAdapter : ListAdapter<Recipe, RecipeViewHolder>(RecipeDiffUtil()) {

    var onRecipeClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding: ItemRecipeBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_recipe,
            parent,
            false
        )
        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = getItem(position)
        holder.binding.recipe = recipe

        Glide
            .with(holder.itemView.context)
            .load(recipe.image)
            .placeholder(R.drawable.ic_logo_gray)
            .into(holder.binding.recipeImg)

        val stringBuilder = StringBuilder()
        recipe.dishTypes.forEach {
            stringBuilder.append("$it . ")
        }
        holder.binding.dishTypes.text = stringBuilder

        holder.binding.root.setOnClickListener {
            onRecipeClicked?.invoke(recipe.id)
        }

    }
}

class RecipeViewHolder(val binding: ItemRecipeBinding): RecyclerView.ViewHolder(binding.root)