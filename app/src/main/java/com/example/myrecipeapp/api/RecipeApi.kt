package com.example.myrecipeapp.api

import com.example.myrecipeapp.model.Recipe
import com.example.myrecipeapp.model.Recipes
import com.example.myrecipeapp.util.Constants.Companion.RECIPE_NUMBER
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("random")
    suspend fun getRecipes(
        @Query("number") number: Int = RECIPE_NUMBER,
        @Query("apiKey") apiKey: String
    ): Response<Recipes>

    @GET("{id}/information")
    suspend fun getRecipe(
        @Path("id") recipeId: Int,
        @Query("apiKey") apiKey: String
    ): Response<Recipe>
}