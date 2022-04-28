package com.example.myrecipeapp.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myrecipeapp.model.Recipe
import com.example.myrecipeapp.model.Recipes
import com.example.myrecipeapp.repository.RecipeRepository
import com.example.myrecipeapp.view.state.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {
    private val _recipes by lazy { MutableLiveData<ViewState<Recipes>>() }
    val recipes: LiveData<ViewState<Recipes>>
        get() = _recipes

    private val _recipe by lazy { MutableLiveData<ViewState<Recipe>>() }
    val recipe: LiveData<ViewState<Recipe>>
        get() = _recipe

    fun getRecipes(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipes.postValue(ViewState.Loading)
            val response = repository.getRecipes(apiKey)
            response?.let { data ->
                when (data) {
                    is ViewState.Success -> {
                        _recipes.postValue(data)
                    }
                    is ViewState.Error -> {
                        _recipes.postValue(data)
                    }
                    else -> {
                        _recipes.postValue(data)
                    }
                }
            }
        }
    }

    fun getRecipe(recipeId: Int, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _recipe.postValue(ViewState.Loading)
            val response = repository.getRecipe(recipeId, apiKey)
            response?.let { data ->
                when (data) {
                    is ViewState.Success -> {
                        _recipe.postValue(data)
                    }
                    is ViewState.Error -> {
                        _recipe.postValue(data)
                    }
                    else -> {
                        _recipe.postValue(data)
                    }
                }
            }
        }
    }
}