package com.example.videocallingapp.login

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(): ViewModel(){
    private val loginState=MutableStateFlow<SignUpState>(SignUpState.Normal)
    val signUpState=loginState.asStateFlow()
    fun signUp(username:String,password:String){
        val auth=Firebase.auth
        loginState.value=SignUpState.Loading
        auth.createUserWithEmailAndPassword(username,password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null) {
                        loginState.value=SignUpState.Success
                    }else{
                        loginState.value=SignUpState.Error
                    }
                } else {
                    loginState.value=SignUpState.Error
                }
            }
    }
}

sealed class SignUpState{
    object Normal:SignUpState()
    object Loading:SignUpState()
    object Success:SignUpState()
    object Error :SignUpState()
}