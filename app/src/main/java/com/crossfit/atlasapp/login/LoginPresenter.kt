package com.crossfit.atlasapp.login

interface LoginPresenter {
    fun onCreate()
    fun onDestroy()
    fun checkForAuthenticatedUser()
    fun validateLogin(email: String, password: String)
    fun registerNewUser(email: String, password: String)
    //fun onEventMainThread(event: LoginEvent)
}
