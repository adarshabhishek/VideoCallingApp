package com.example.videocallingapp.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController


@Composable
fun SignUpScreen(navController: NavController){
    val userName= remember {
        mutableStateOf("")
    }
    val passWord= remember{
        mutableStateOf("")
    }
    val passWordConfirm= remember{
        mutableStateOf("")
    }
    var loading= remember {
        mutableStateOf(false)
    }
    val viewModel: SignUpViewModel= hiltViewModel()
    Column(modifier= Modifier.fillMaxSize().padding(26.dp),verticalArrangement = Arrangement.Center,
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ){
        Text(text = "Sign Up")
        OutlinedTextField(
            modifier=Modifier.fillMaxWidth(),
            value=userName.value,
            onValueChange = {userName.value=it},
            label = {Text(text = "Username")})
        OutlinedTextField(
            modifier=Modifier.fillMaxWidth(),
            value=passWord.value,
            onValueChange = {passWord.value=it},
            label = {Text(text = "Password")})
        OutlinedTextField(
            modifier=Modifier.fillMaxWidth(),
            value=passWordConfirm.value,
            onValueChange = {passWordConfirm.value=it},
            label = {Text(text = "Confirm Password")})
        if (loading.value){
            CircularProgressIndicator()
        }else {
            Button(
                onClick = {
                    viewModel.signUp(userName.value, passWord.value)

                }, modifier = Modifier.fillMaxWidth(),
                enabled = userName.value.isNotEmpty() && passWord.value.isNotEmpty() && passWordConfirm.value.isNotEmpty() && passWord.value == passWordConfirm.value
            ) {
                Text(text = "Sign Up")
            }
        }
        val state=viewModel.signUpState.collectAsState()
        LaunchedEffect(state.value) {
            if (state.value is SignUpState.Normal) {
                loading.value=false
            }
            else if (state.value is SignUpState.Loading) {
                loading.value=true
            }
            else if (state.value is SignUpState.Success) {
                navController.navigate("home"){
                    popUpTo("login"){
                        inclusive=true
                    }
                }
            }
            else if (state.value is SignUpState.Error) {loading.value=false}
        }
    }
}
@Preview
@Composable
fun SignUpScreenPreview(){
    SignUpScreen(rememberNavController())
}