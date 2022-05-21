package com.example.myrecipeapp.repository

import com.example.myrecipeapp.view.state.ViewState

abstract class BaseRepository {

    companion object {
        const val GENERAL_ERROR_CODE = 499
        private const val UNAUTHORIZED = "Unauthorized"
        private const val NOT_FOUND = "Not found"
        private const val SOMETHING_WENT_WRONG = "Something went wrong"

        fun <T : Any> handleSuccess(data: T): ViewState<T> {
            return ViewState.Success(data)
        }

        fun <T : Any> handleException(code: Int): ViewState<T> {
            val exception = getErrorMessage(code)
            return ViewState.Error(Exception(exception))
        }

        private fun getErrorMessage(httpCode: Int): String {
            return when (httpCode) {
                401 -> UNAUTHORIZED
                404 -> NOT_FOUND
                else -> SOMETHING_WENT_WRONG
            }
        }
    }
}