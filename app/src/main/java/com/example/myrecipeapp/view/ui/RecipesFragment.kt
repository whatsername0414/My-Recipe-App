package com.example.myrecipeapp.view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myrecipeapp.BuildConfig
import com.example.myrecipeapp.databinding.FragmentRecipesBinding
import com.example.myrecipeapp.view.adapter.RecipeAdapter
import com.example.myrecipeapp.view.state.ViewState
import com.example.myrecipeapp.view.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var binding: FragmentRecipesBinding
    private val adapter by lazy { RecipeAdapter() }
    private val dialog by lazy { LoadingDialog(requireActivity()) }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recipeRv.adapter = adapter
        viewModel.getRecipes(BuildConfig.API_KEY)

        observeRecipeLiveData()

        adapter.onRecipeClicked = {
            findNavController().navigate(
                RecipesFragmentDirections.actionRecipesFragmentToRecipeFragment(it)
            )
        }

        binding.networkError.btnRetry.setOnClickListener {
            viewModel.getRecipes(BuildConfig.API_KEY)
        }

    }

    private fun observeRecipeLiveData() {
        viewModel.recipes.observe(viewLifecycleOwner) { data ->
            when (data) {
                is ViewState.Loading -> {
                    dialog.show()
                }
                is ViewState.Success -> {
                    val recipes = data.result.recipes
                    adapter.submitList(recipes)
                    dialog.dismiss()
                }
                is ViewState.Error -> {
                    binding.recipeRv.visibility = View.GONE
                    dialog.dismiss()
                    binding.networkError.root.visibility = View.VISIBLE
                }
            }
        }
    }

}