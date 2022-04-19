package com.example.drugstore.presentation.user

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.auth.SplashActivity
import com.example.drugstore.service.AuthService
import com.example.drugstore.utils.Result
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfileVM @Inject constructor(
    private val authService: AuthService,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    fun signOut(context: BaseActivity) {
        viewModelScope.launch {
            authService.signOut()
            withContext(Dispatchers.Main) {
                context.startActivity(Intent(context, SplashActivity::class.java))
            }
        }
    }

    fun getUserByID() = liveData(Dispatchers.IO){
        emit(firebaseAuth.currentUser?.let { authService.findUserByID(it) })
    }

    fun updateUser(dataUser: HashMap<String,Any>,context: Context) {
        viewModelScope.launch {
            when(val result =
                firebaseAuth.currentUser?.uid?.let { authService.updateUser(it,dataUser) }){
                is Result.Success -> Toast.makeText(context,result.data,Toast.LENGTH_SHORT).show()
                else -> if (result != null) {
                    Toast.makeText(context,result.data,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}