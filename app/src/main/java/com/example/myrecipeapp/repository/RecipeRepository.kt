package com.example.myrecipeapp.repository

import com.example.myrecipeapp.model.Recipe
import com.example.myrecipeapp.model.Recipes
import com.example.myrecipeapp.view.state.ViewState

interface RecipeRepository {

    suspend fun getRecipes(apiKey: String): ViewState<Recipes>?
    suspend fun getRecipe(recipeId: Int, apiKey: String): ViewState<Recipe>?

}