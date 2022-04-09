package com.example.drugstore.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.drugstore.R
import com.example.drugstore.databinding.ActivitySignInBinding
import com.example.drugstore.data.firebase.FirebaseClass
import com.example.drugstore.presentation.BaseActivity
import com.example.drugstore.presentation.home.HomeActivity
import com.example.drugstore.service.SUserRepo
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*

class SignInActivity : BaseActivity() {
    private lateinit var launcherGoogleSignIn: ActivityResultLauncher<Intent>
    private lateinit var binding: ActivitySignInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.hideSystemBars()
        setUpToolBar()

        setUpLauncherGoogleSignIn()

        binding.btnGoogle.setOnClickListener {
            signIn()
        }
        binding.btnMobile.setOnClickListener {
            startActivity(Intent(this, InputPhoneActivity::class.java))
        }
    }

    private fun setUpToolBar() {
        setSupportActionBar(binding.tb)
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = ""
        }
        binding.tb.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setUpLauncherGoogleSignIn() {
        launcherGoogleSignIn = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Log.w("---", "Google sign in failed", e)
                }
            }else{
                Log.w("---",task.exception.toString())
            }
        }
    }

    private fun signIn() {
        if(FirebaseClass.getInstance().getGoogleSignInClient() == null){
            FirebaseClass.getInstance().setGoogleSignIn(this,resources.getString(R.string.client_id))
        }
        launcherGoogleSignIn.launch(FirebaseClass.getInstance().getGoogleSignInClient()!!.signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        FirebaseClass.getFirebaseAuth().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    SUserRepo().connectUserByGoogle(task.result.user!!)
                    startActivity(Intent(this@SignInActivity, HomeActivity::class.java))
                    finish()
                } else {
                    Log.w("---", "signInWithCredential:failure", task.exception)
                }
            }
    }
}