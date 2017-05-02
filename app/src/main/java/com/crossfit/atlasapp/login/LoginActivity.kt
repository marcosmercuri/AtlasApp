package com.crossfit.atlasapp.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crossfit.atlasapp.MainActivity
import com.crossfit.atlasapp.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() , LoginView {

    private val mAuth = FirebaseAuth.getInstance()
    private lateinit var mCallbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val currentUser = mAuth.currentUser
        if (currentUser!=null) {
            navigateToMainScreen()
            return
        }

        mCallbackManager = CallbackManager.Factory.create()

        login_button.setReadPermissions("email")
        login_button.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                findViewById(R.id.activity_login).snack(getString(R.string.login_cancelled))
            }

            override fun onError(e: FacebookException) {
                somethingWentWrongOnLogin(e.localizedMessage)
            }
        })

        FirebaseAuth.AuthStateListener { firebaseAuth ->
            firebaseAuth.currentUser.apply { navigateToMainScreen() } ?: somethingWentWrongOnLogin()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, { task: Task<AuthResult> ->
                    if (task.isSuccessful) navigateToMainScreen() else somethingWentWrongOnLogin()
                })
    }

    override fun navigateToMainScreen() {
        val intent = Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
    }

    private fun somethingWentWrongOnLogin(exceptionMessage: String = "") {
        val error = String.format(getString(R.string.login_error_message), exceptionMessage)
        findViewById(R.id.activity_login).snack(error)
    }

    override fun enableInputs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disableInputs() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideProgress() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleSignIn() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun handleSignUp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoginError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newUserSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun newUserError(error: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
