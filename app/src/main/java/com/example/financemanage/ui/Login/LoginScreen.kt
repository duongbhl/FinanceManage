package com.example.financemanage.ui.Login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.ui.theme.button_color
import com.example.financemanage.ui.theme.text_field_color
import com.example.lunchtray.R
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(userViewModel: UserViewModel,loginButton:()->Unit,changepassButton:()->Unit,signinButton:()->Unit){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(0) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black
    ) {paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues = paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(60.dp))
            Image(
                modifier = Modifier.width(71.dp).height(70.dp),
                painter = painterResource(R.drawable.image_3),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                modifier = Modifier
                    .width(343.dp)
                    .height(32.dp),
                text="Đăng nhập tài khoản của bạn",
                style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 32.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFD9D9D9),

                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .width(343.dp)
                    .height(24.dp),
                text = "HÃY ĐIỀN THÔNG TIN CỦA BẠN",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFD9D9D9),
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Email",
                modifier = Modifier.align(Alignment.Start).padding(10.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFD9D9D9),
                    )
            )
            OutlinedTextField(
                value = email,
                onValueChange = {email=it},
                modifier = Modifier.align(Alignment.Start).padding(10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                ),
                placeholder = {Text(text="Nhập Email")},
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = text_field_color,
                    unfocusedContainerColor = text_field_color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Password",
                modifier = Modifier.align(Alignment.Start).padding(10.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFD9D9D9),
                )
            )
            OutlinedTextField(
                value = password,
                onValueChange = {password=it},
                modifier = Modifier.align(Alignment.Start).padding(10.dp).fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                placeholder = {Text(
                    text="●●●●●●●●",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(400),
                        color = Color(0xFF2D2405),
                        letterSpacing = 8.4.sp,
                    )
                )},
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = text_field_color,
                    unfocusedContainerColor = text_field_color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    ),
                singleLine = true,

            )
            Row {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ){
                    Checkbox(
                        checked = false,
                        onCheckedChange = {}
                    )
                    Text(
                        text = "Ghi nhớ đăng nhập",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFD9D9D9),

                            )
                    )
                }

                TextButton(
                    onClick = {
                        changepassButton()
                    }
                ) {
                    Text(
                        text = "Quên mật khẩu",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFC107),

                            )
                    )
                }
            }
            val coroutineScope=rememberCoroutineScope()
            Button(
                onClick = {
                    coroutineScope.launch {
                        if(userViewModel.checkLogin(email, password)){
                            showSuccess=1
                        }
                        else showSuccess=-1
                        if(showSuccess==1) loginButton()
                    }
                },
                modifier = Modifier.padding(top=35.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = button_color,
                )
            ) {
                Text(
                    text = "Đăng nhập",
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFF093030),
                        textAlign = TextAlign.Center,
                    )
                )
            }
            if(showSuccess==1) Text(text="Đăng nhập thành công",color=Color.Green)
            else if(showSuccess==-1) Text(text="Đăng nhập thất bại",color=Color.Red)
            Image(
                painter = painterResource(R.drawable.social_icon),
                contentDescription = null,
                modifier = Modifier.padding(top=15.dp, bottom = 5.dp).width(33.dp).height(38.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Chưa có tài khoản ?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFD9D9D9),

                        )
                )
                TextButton(
                    onClick = {
                        signinButton()
                    }
                ) {
                    Text(
                        text = "Đăng kí ngay",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFC107),

                            )
                    )
                }
            }
        }
    }
}