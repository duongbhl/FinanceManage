package com.example.financemanage.ui.Waiting

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.financemanage.ui.theme.FinanceManageTheme
import com.example.lunchtray.R

@Composable
fun WaitingScreen(modifier: Modifier=Modifier){
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black // Đặt background của Scaffold là màu đen
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
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.app_name),
            style = TextStyle(
                fontSize = Variables.DisplayLargeSize,
                lineHeight = Variables.DisplayLargeLineHeight,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight(400),
                color = Color(0xFFD9D9D9),
                textAlign = TextAlign.Center,
            ),
        )
    }  }


}
