package com.example.myrecipeapp.view.ui

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.bumptech.glide.Glide
import com.example.myrecipeapp.R
import com.example.myrecipeapp.databinding.LoadingDialogLayoutBinding

class LoadingDialog(val app: Activity) {

    private var binding: LoadingDialogLayoutBinding
    private var dialog: AlertDialog

    init {
        val dialogBuilder = AlertDialog.Builder(app)
        val layoutInflater = app.layoutInflater
        binding = LoadingDialogLayoutBinding.inflate(layoutInflater)
        dialogBuilder.setView(binding.root)
        dialogBuilder.setCancelable(false)
        dialog = dialogBuilder.create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    fun show() {
        Glide
            .with(binding.root)
            .load(R.drawable.loading)
            .into(binding.spinner)
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}