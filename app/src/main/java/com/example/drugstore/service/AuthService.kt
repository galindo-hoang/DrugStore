package com.example.drugstore.service

import android.util.Log
import com.example.drugstore.data.models.Address
import com.example.drugstore.data.models.User
import com.example.drugstore.data.repository.AuthRepo
import com.example.drugstore.data.repository.UserRepo
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.utils.Result
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val context: BaseActivity,
    private val userRepo: UserRepo,
    private val authRepo: AuthRepo
) {
    suspend fun connectUserByGoogle(user: FirebaseUser): Boolean {
        delay(3000);
         Log.d("suspend", "in suspend")

        val userCheck = userRepo.fetchUserByID(user.uid)

        val token = FirebaseMessaging.getInstance().token.await().toString()
        Log.e("=======",token)
        if (userCheck == null) {
            userRepo.connectUser(
                User(
                    UserID = user.uid,
                    UserName = user.email.toString(),
                    Token = token
                )
            )
            return true
        }else userRepo.updateUser(user.uid, hashMapOf("token" to token))
        return false
    }

    suspend fun connectUserByPhone(user: FirebaseUser): Boolean {
        val userCheck = userRepo.fetchUserByID(user.uid)

        val token = FirebaseMessaging.getInstance().token.await().toString()
        if (userCheck == null) {
            userRepo.connectUser(
                User(
                    UserID = user.uid,
                    UserName = user.phoneNumber.toString(),
                    PhoneNumber = user.phoneNumber.toString(),
                    Token = token
                )
            )
            return true
        }else userRepo.updateUser(user.uid, hashMapOf("token" to token))
        return false
    }

    suspend fun signOut() {
        authRepo.getCurrentUserId()?.let { userRepo.updateUser(it,hashMapOf("token" to "")) }
        if (isGoogleSignIn()) {
            authRepo.signOutGoogle()
        }
        authRepo.signOut()
    }

    private fun isGoogleSignIn() =
        GoogleSignIn.getLastSignedInAccount(context) != null

    fun setLanguageCode(code: String) {
        authRepo.setLanguageCode(code)
    }

    fun getGoogleSignInIntent() = authRepo.getGoogleSignInIntent()

    fun getPhoneAuthOptionsBuilder(
        phoneNumber: String,
        resendToken: PhoneAuthProvider.ForceResendingToken?
    ): PhoneAuthOptions.Builder {
        val builder = PhoneAuthOptions.newBuilder(authRepo.getFirebaseAuth())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        if (resendToken != null) {
            builder.setForceResendingToken(resendToken)
        }
        return builder
    }

    fun isAuth() = authRepo.getCurrentUserId() != null

    suspend fun findUserByID() = authRepo.getCurrentUserId()?.let { userRepo.fetchUserByID(it) }

    suspend fun updateUser(dataUser: HashMap<String, Any>) =
        authRepo.getCurrentUserId()?.let { userRepo.updateUser(it, dataUser) }

    suspend fun addAddress(address: Address) =
        authRepo.getCurrentUserId()?.let { userRepo.addAddress(address, it) }

    suspend fun getUserHaveToken(): List<User> {
        return when(val result = userRepo.fetchAllUser()){
            is Result.Success -> {
                val listUser: MutableList<User> = mutableListOf()
                for(i in result.data!!){
                    if(i.Token != "" && i.UserID != authRepo.getCurrentUserId()) listUser.add(i)
                }
                listUser
            }
            else -> mutableListOf()
        }
    }
}