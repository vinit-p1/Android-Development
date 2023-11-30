package com.example.miniproject1

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.example.miniproject1.ui.theme.MiniProject1Theme

class LoginActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: ViewModel by viewModels()

        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContent {
            MiniProject1Theme (vm= viewModel){
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Login(auth)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(auth: FirebaseAuth, modifier: Modifier = Modifier) {
    var inputTextLogin by remember { mutableStateOf("") }
    var inputTextPassword by remember { mutableStateOf("") }
    val context = LocalContext.current
    var loginButtonEnabled by remember { mutableStateOf(false) }
    var registerButtonEnabled by remember { mutableStateOf(false) }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "Welcome to Shopping Manager")},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    )




    {
        innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            Text(
                text = "Please Login or Register",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            TextField(
                value = inputTextLogin,
                onValueChange = {
                    inputTextLogin = it
                    loginButtonEnabled = it.isNotBlank() && inputTextPassword.isNotBlank()
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Email Id")}
            )
            Spacer(modifier = Modifier.requiredHeight(10.dp))
            TextField(
                value = inputTextPassword,
                onValueChange = {
                    inputTextPassword = it
                    loginButtonEnabled = inputTextLogin.isNotBlank() && it.isNotBlank()
                    registerButtonEnabled = inputTextLogin.isNotBlank() && it.isNotBlank()
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                label = { Text(text = "Password")}
            )
            Spacer(modifier = Modifier.requiredHeight(10.dp))

            Button(
                onClick = {
                    auth.signInWithEmailAndPassword(
                        inputTextLogin,
                        inputTextPassword
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                context,
                                "Login failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                enabled = loginButtonEnabled,
                modifier = Modifier
                    .requiredWidth(300.dp)
                    .requiredHeight(50.dp)
            ){
                Text(
                    text = "Log in"
                )
            }
            Spacer(modifier = Modifier.requiredHeight(10.dp))

            Button(
                onClick = {
                    auth.createUserWithEmailAndPassword(
                        inputTextLogin,
                        inputTextPassword
                    ).addOnCompleteListener{
                        if(it.isSuccessful){
                            Toast.makeText(
                                context,
                                "User registered successfully.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                context,
                                "Registration failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                enabled = registerButtonEnabled,
                modifier = Modifier
                    .requiredWidth(300.dp)
                    .requiredHeight(50.dp)
            ){
                Text(
                    text = "Register"
                )
            }

        }

    }


}
