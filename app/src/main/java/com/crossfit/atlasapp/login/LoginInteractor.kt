package com.crossfit.atlasapp.login

interface LoginInteractor {
    fun checkSession(): Boolean
    fun doSignIn(email: String, password: String)
    fun doSignUp(email: String, password: String)
}
