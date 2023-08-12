package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.PrimaryYellow

@Composable
fun Profile(context: Context, navController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("MyPreffs", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "John")
    val lastName = sharedPreferences.getString("lastName", "Doe")
    val email = sharedPreferences.getString("email", "example@gmail.com")

    Column(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
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
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "",
                modifier = Modifier.size(70.dp)
                )
        }

        Text(text = "Personal information",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 50.dp)
        )

            Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 40.dp)) {

                OutlinedTextField(
                    value = firstName!!,
                    onValueChange = {
                    },
                    label = { Text(text = "First Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                OutlinedTextField(
                    value = lastName!!,
                    onValueChange = {},
                    label = { Text(text = "Last Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                )

                OutlinedTextField(
                    value = email!!, onValueChange = {},
                    label = { Text(text = "Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 50.dp)
                )
                Column(modifier = Modifier.fillMaxSize().padding(vertical = 30.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                    Button(onClick = {
                        sharedPreferences.edit().clear().apply()
                        navController.navigate(Onboarding.route)
                    },
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = PrimaryYellow),

                        modifier = Modifier
                            .fillMaxWidth()
                            .background(color = PrimaryYellow)
                    ) {
                        Text(text = "Logout")
                    }
                }


            }

        }
    }

    

