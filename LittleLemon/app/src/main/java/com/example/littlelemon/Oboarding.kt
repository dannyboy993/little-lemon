package com.example.littlelemon

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.PrimaryYellow


@Composable
fun Onboarding (context: Context, navHostController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("MyPreffs", Context.MODE_PRIVATE)

    val firstName = remember {
        mutableStateOf("")
    }
    val lastName = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

        ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "",
                modifier = Modifier.size(250.dp)
                )
        }
        Row(
            modifier = Modifier
                .height(100.dp)
                .background(color = Color(0xff495e57))
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = "Lets get to know you",
                fontSize = 24.sp,
                color = Color.White
            )
        }

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "Personal info",
                modifier = Modifier.padding(vertical = 50.dp)
            )
            
            OutlinedTextField(
                value = firstName.value,
                onValueChange = {
                    firstName.value = it
                },
                label = { Text(text = "First Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )

            OutlinedTextField(
                value = lastName.value,
                onValueChange = {
                    lastName.value = it
                },
                label = { Text(text = "Last Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            )

            OutlinedTextField(
                value = email.value,
                onValueChange = {
                    email.value = it
                },
                label = { Text(text = "Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 50.dp)
            )

            Button(onClick = {
                 if (firstName.value.isNotBlank() && lastName.value.isNotBlank() && email.value.isNotBlank()) {
                     sharedPreferences.edit().putString("firstName", firstName.value)
                         .putString("lastName", lastName.value)
                         .putString("email", email.value)
                         .putBoolean("userRegistered", true).apply()

                     Toast.makeText(context, "Registration succesful", Toast.LENGTH_SHORT).show()
                     navHostController.navigate(Home.route)
                 }
                else {
                    Toast.makeText(context, "Registration unsuccesful, please enter all data", Toast.LENGTH_SHORT).show()
                 }

            },
                colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryYellow),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = PrimaryYellow)
            ) {
                Text(text = "Register")
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {

}