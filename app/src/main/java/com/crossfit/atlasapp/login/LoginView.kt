package com.crossfit.atlasapp.login

interface LoginView {
    fun enableInputs()
    fun disableInputs()
    fun showProgress()
    fun hideProgress()

    fun handleSignIn()
    fun handleSignUp()

    fun navigateToMainScreen()
    fun showLoginError(error: String)

    fun newUserSuccess()
    fun newUserError(error: String)
}
