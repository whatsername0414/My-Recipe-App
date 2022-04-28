package com.example.myrecipeapp.repository

import android.util.Log
import com.example.myrecipeapp.api.RecipeApi
import com.example.myrecipeapp.model.Recipe
import com.example.myrecipeapp.model.Recipes
import com.example.myrecipeapp.view.state.ViewState
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi
) : RecipeRepository, BaseRepository() {
    override suspend fun getRecipes(apiKey: String): ViewState<Recipes>? {
        var data: ViewState<Recipes>? = null
        try {
            val result = api.getRecipes(apiKey = apiKey)
            if (result.isSuccessful) {
                result.body()?.let {
                    data = handleSuccess(it)
                }
            } else {
                Log.e("CharacterRepositoryImpl", "Error: ${result.message()}")
                return handleException(result.code())
            }
        } catch (e: Exception) {
            Log.e("CharacterRepositoryImpl", "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }

    override suspend fun getRecipe(recipeId: Int, apiKey: String): ViewState<Recipe>? {
        var data: ViewState<Recipe>? = null
        try {
            val result = api.getRecipe(recipeId, apiKey)
            if (result.isSuccessful) {
                result.body()?.let {
                    data = handleSuccess(it)
                }
            } else {
                Log.e("CharacterRepositoryImpl", "Error: ${result.message()}")
                return handleException(result.code())
            }
        } catch (e: Exception) {
            Log.e("CharacterRepositoryImpl", "Error: ${e.message}")
            return handleException(GENERAL_ERROR_CODE)
        }
        return data
    }
}