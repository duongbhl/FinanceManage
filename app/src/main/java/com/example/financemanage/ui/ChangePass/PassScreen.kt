package com.example.financemanage.ui.ChangePass

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PassScreen(userViewModel: UserViewModel,resetButton:()->Unit,backButton:()->Unit){
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordConfirm by remember { mutableStateOf("") }
    var passwordConfirmVisible by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(0) }
    val user = userViewModel.currentUser
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon ={
                    IconButton(onClick = {backButton()}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null,
                            tint = Color.White, modifier = Modifier.padding(1.dp).width(30.dp).height(30.dp))
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors( // Use largeTopAppBarColors
                    containerColor = Color.Black,
                ),

                )

        }

    ){
            innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text="MẬT KHẨU MỚI",
                style = TextStyle(
                    fontSize = 30.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFD9D9D9),
                )

            )
            Spacer(modifier = Modifier.height(45.dp))
            Box(){
                Image(
                    painter = painterResource(R.drawable.base_shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxWidth().fillMaxHeight()
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    Spacer(modifier = Modifier.height(100.dp))
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
                        placeholder = {
                            Text(
                                text="●●●●●●●●",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF2D2405),
                                    letterSpacing = 8.4.sp,
                                )
                            )
                        },
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
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Password Confirm",
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
                        value = passwordConfirm,
                        onValueChange = {passwordConfirm=it},
                        modifier = Modifier.align(Alignment.Start).padding(10.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordConfirmVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            val description = if (passwordConfirmVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordConfirmVisible = !passwordConfirmVisible }) {
                                Icon(imageVector = image, description)
                            }
                        },
                        placeholder = {
                            Text(
                                text="●●●●●●●●",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF2D2405),
                                    letterSpacing = 8.4.sp,
                                )
                            )
                        },
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
                    Text(
                        modifier = Modifier.align(Alignment.Start).padding(10.dp),
                        text="Mật khẩu phải có ít nhất 8 kí tự",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(400),
                            color = Color(0xFFD9D9D9),

                            )
                    )
                    val coroutineScope=rememberCoroutineScope()
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if(password==passwordConfirm&& user.value?.let { userViewModel.changePassword(email = it.email, newPassword = password) } == true){
                                    showSuccess=1
                                }
                                else{
                                    showSuccess=-1

                                }
                                if(showSuccess==1) resetButton()
                            }
                        },
                        modifier = Modifier.padding(top=35.dp).width(207.dp)
                            .height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = button_color,
                        )
                    ) {
                        Text(
                            text = "GỬI",
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
                    Spacer(modifier=Modifier.height(20.dp))
                    if(showSuccess==1) Text(text="Đổi mật khẩu thành công",color=Color.Green)
                    else if(showSuccess==-1) Text(text="Đổi mật khẩu thất bại",color=Color.Red)
                }

            }
        }
    }
}