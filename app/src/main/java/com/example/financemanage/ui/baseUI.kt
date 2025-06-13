package com.example.financemanage.ui

import android.graphics.Paint.Align
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanage.Enum.Daily
import com.example.financemanage.Enum.Screen
import com.example.financemanage.model.Item
import com.example.financemanage.ui.theme.button_color
import com.example.lunchtray.R

@Composable
fun baseUI(
    navController: androidx.navigation.NavHostController
){
    var selectedScreen by remember { mutableStateOf(Screen.HOME) }
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        bottomBar = { BottomBar(
            modifier = Modifier.fillMaxWidth(),
            screen = selectedScreen,
            onScreenSelected = { selectedScreen = it },
            navController = navController
        ) }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ){
            Row(
                modifier = Modifier.padding(10.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text="Tên Màn Hình",
                    modifier = Modifier.padding(start = 130.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF1FFF3),

                        )
                )
                Spacer(modifier = Modifier.width(100.dp))
                Box(
                    modifier = Modifier.background(color = Color(0xFFFFA000), shape = RoundedCornerShape(size = 25.dp))
                ){
                    Icon(
                        modifier = Modifier.padding(10.dp).width(14.57143.dp).height(18.85714.dp),
                        painter = painterResource(R.drawable.vector),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

            }
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxSize()
            ){
                Image(
                    painter = painterResource(R.drawable.base_shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }

    }
}