package com.example.drugstore.presentation

import android.app.Dialog
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.drugstore.R
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

open class BaseActivity @Inject constructor() : AppCompatActivity() {
    private var doubleClickToExit = false
    private lateinit var mProgressDialog: Dialog

    fun hideSystemBars() {
        val windowInsetsController =
            ViewCompat.getWindowInsetsController(window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    fun showProgressDialog(text: String) {
        this.mProgressDialog = Dialog(this)
        mProgressDialog.setContentView(R.layout.dialog_progress)
        mProgressDialog.findViewById<TextView>(R.id.tvProgress).text = text
        mProgressDialog.show()
    }

    fun hideProgressDialog() {
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit() {
        if (doubleClickToExit) {
            onBackPressed()
        } else {
            doubleClickToExit = true
            Toast.makeText(this, R.string.please_click_back_again_to_exit, Toast.LENGTH_SHORT)
                .show()
            Executors.newSingleThreadScheduledExecutor().schedule({
                doubleClickToExit = false
            }, 2, TimeUnit.SECONDS)
        }
    }

    fun showErrorSnackBar(message: String) {
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        snackBar.view.setBackgroundColor(ContextCompat.getColor(this, R.color.snackBar_error_color))
        snackBar.show()
    }
}