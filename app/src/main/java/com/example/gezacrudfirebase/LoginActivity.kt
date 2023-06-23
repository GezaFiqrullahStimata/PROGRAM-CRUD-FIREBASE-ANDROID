package com.example.gezacrudfirebase

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    lateinit var progress : ProgressBar
    lateinit var btnLogin : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        progress = findViewById(R.id.progress)
        btnLogin = findViewById(R.id.login)

        progress.visibility = View.GONE
        btnLogin.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
        if(auth!!.currentUser == null){
        } else {
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onClick(p0: View?) {
// Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())
// Create and launch sign-in intent
        startActivityForResult(

            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data:
    Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "Login Berhasil",
                    Toast.LENGTH_SHORT).show()
                intent = Intent(applicationContext,
                    MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Dibatalkan",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}