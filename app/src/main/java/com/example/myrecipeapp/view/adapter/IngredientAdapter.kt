package com.example.myrecipeapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myrecipeapp.R
import com.example.myrecipeapp.databinding.ItemIngredientBinding
import com.example.myrecipeapp.model.ExtendedIngredient
import com.example.myrecipeapp.util.Util.generateImageUrl
import kotlin.math.ceil
import kotlin.math.floor

class IngredientDiffUtil: DiffUtil.ItemCallback<ExtendedIngredient>() {
    override fun areItemsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: ExtendedIngredient,
        newItem: ExtendedIngredient
    ): Boolean {
        return oldItem.id == oldItem.id
    }
}
class IngredientAdapter : ListAdapter<ExtendedIngredient, IngredientsViewHolder>(IngredientDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientsViewHolder {
        val binding: ItemIngredientBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_ingredient,
            parent,
            false
        )
        return IngredientsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientsViewHolder, position: Int) {
        val ingredient = getItem(position)
        holder.binding.apply {
            this.ingredient = ingredient
            val amountValue = if ((ingredient.amount % 1) == 0.0)
                root.context.getString(
                R.string.amount, ingredient.amount.toInt(), ingredient.measures.us.unitShort) else
                root.context.getString(
                R.string.amount_float, ingredient.amount, ingredient.measures.us.unitShort)
            amount.text = amountValue
            ingredientTv.text = ingredient.nameClean?.replaceFirstChar { it.uppercase() }
            Glide
                .with(holder.itemView.context)
                .load(generateImageUrl(ingredient.image))
                .placeholder(R.drawable.ic_logo_gray)
                .into(ingredientImage)
        }
    }
}

class IngredientsViewHolder(val binding: ItemIngredientBinding): RecyclerView.ViewHolder(binding.root)