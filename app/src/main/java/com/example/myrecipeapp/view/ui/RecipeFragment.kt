package com.example.myrecipeapp.view.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.myrecipeapp.BuildConfig
import com.example.myrecipeapp.R
import com.example.myrecipeapp.databinding.FragmentRecipeBinding
import com.example.myrecipeapp.model.Recipe
import com.example.myrecipeapp.view.adapter.IngredientAdapter
import com.example.myrecipeapp.view.state.ViewState
import com.example.myrecipeapp.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeFragment : Fragment() {

    private lateinit var binding: FragmentRecipeBinding
    private val viewModel by viewModels<MainViewModel>()
    private val adapter by lazy { IngredientAdapter() }
    private val dialog by lazy { LoadingDialog(requireActivity()) }
    private val args by navArgs<RecipeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.ingredientsRv.adapter = adapter
        viewModel.getRecipe(args.id, BuildConfig.API_KEY)
        observeRecipe()

        binding.networkError.btnRetry.setOnClickListener {
            viewModel.getRecipe(args.id, BuildConfig.API_KEY)
        }

    }

    private fun observeRecipe() {
        viewModel.recipe.observe(viewLifecycleOwner) { data ->
            when (data) {
                is ViewState.Loading -> {
                    dialog.show()
                }
                is ViewState.Success -> {
                    bindData(data.result)
                    dialog.dismiss()
                }
                is ViewState.Error -> {
                    binding.nestedScrollView.visibility = View.GONE
                    dialog.dismiss()
                    binding.networkError.root.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun bindData(recipe: Recipe) {
        adapter.submitList(recipe.extendedIngredients)
        val ingredientSize = recipe.extendedIngredients.size
        Glide
            .with(requireContext())
            .load(recipe.image)
            .placeholder(R.drawable.ic_logo_gray)
            .into(binding.recipeImg)
        binding.recipeName.text = recipe.title
        binding.likes.text = if (recipe.aggregateLikes > 1)
            getString(R.string.likes, recipe.aggregateLikes) else getString(R.string.like)
        binding.serving.text = getString(R.string.serving, recipe.servings)
        binding.price.text = getString(R.string.price, recipe.pricePerServing)
        binding.time.text = getString(R.string.time, recipe.readyInMinutes)
        binding.description.text = HtmlCompat.fromHtml(
            recipe.summary,
            HtmlCompat.FROM_HTML_MODE_LEGACY
        ).toString()
        binding.ingredients.text = if (ingredientSize > 1)
            getString(R.string.ingredients, ingredientSize) else getString(R.string.ingredient)
    }

}