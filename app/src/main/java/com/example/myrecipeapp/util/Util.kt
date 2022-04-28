package com.example.myrecipeapp.util

object Util {

    fun generateImageUrl(imageName: String): String {
        return  "https://spoonacular.com/cdn/ingredients_500x500/$imageName"
    }

}