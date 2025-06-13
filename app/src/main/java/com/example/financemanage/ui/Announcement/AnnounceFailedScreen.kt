package com.example.financemanage.ui.Announcement

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lunchtray.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnounceFailedScreen(){
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon ={
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null,
                            tint = Color.White, modifier =Modifier.padding(1.dp)
                                .width(30.dp)
                                .height(30.dp))
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors( // Use largeTopAppBarColors
                    containerColor = Color.Black,
                ),

                )

        }// Đặt background của Scaffold là màu đen
    ){paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues =paddingValues).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .width(300.dp)
                    .height(300.dp),
                painter = painterResource(id = R.drawable.image_3),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
            )
            Text(
                text = "THAY ĐỔI MẬT KHẨU THẤT BẠI",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 26.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFD9D9D9),
                    textAlign = TextAlign.Center,
                ),
            )
            Spacer(modifier = Modifier.height(25.dp))
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
                    text = "Đã có tài khoản",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(400),
                        color = Color(0xFFD9D9D9),

                        )
                )
                TextButton(
                    onClick = {}
                ) {
                    Text(
                        text = "Đăng nhập ngay",
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