package com.example.financemanage.ui.Transactions

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.financemanage.Enum.Category
import com.example.financemanage.Enum.Daily
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.ui.BottomBar
import com.example.financemanage.ui.theme.button_color
import com.example.financemanage.ui.theme.text_field_color
import com.example.lunchtray.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    userViewModel: UserViewModel,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel,
    walletViewModel: WalletViewModel,
    walletId:Int=1,
    navController: NavHostController
){
    var selectedScreen by remember { mutableStateOf(Screen.CATEGORY) }
    var balance by remember { mutableStateOf<Double?>(null) }
    var amount by remember { mutableStateOf("0") }
    val user=userViewModel.currentUser
    val category=categoryViewModel.currentCategory
    val wallet=walletViewModel.currentWallet
    LaunchedEffect(key1 = user.value?.id, key2 = wallet.value?.id) {
        balance=walletViewModel.getTotalBalance(userId = user.value?.id!!,walletId = wallet.value?.id!!)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            text="Thêm",
                            modifier= Modifier.align(Alignment.CenterVertically).padding(start = 120.dp),
                            style = TextStyle(
                                fontSize = 25.sp,
                                lineHeight = 22.sp,
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight(600),
                                color = Color(0xFFF1FFF3),

                                )
                        )
                        Spacer(modifier = Modifier.width(120.dp))
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
                },
                navigationIcon ={
                    IconButton(onClick = {navController.navigate(Screen.TRANSACTION.name)}) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null,
                            tint = Color.White, modifier = Modifier.padding(1.dp).width(30.dp).height(30.dp))
                    }

                },
                colors = TopAppBarDefaults.largeTopAppBarColors( // Use largeTopAppBarColors
                    containerColor = Color.Black,
                ),

                )

        },
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
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Spacer(modifier = Modifier.padding(bottom = 50.dp))
                    Text(
                        text="Ngày-Tuần-Tháng",
                        modifier = Modifier.align(Alignment.Start).padding(start=65.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFD9D9D9),

                            )
                    )
                    var expanded by remember { mutableStateOf(false) }
                    var selectedText by remember { mutableStateOf(Daily.DAY)}
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                        modifier = Modifier.padding(10.dp)
                    ) {
                        TextField(
                            value = selectedText.displayName,
                            onValueChange = {selectedText.displayName=it},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                focusedContainerColor = text_field_color,
                                unfocusedContainerColor = text_field_color,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            modifier = Modifier.menuAnchor(),
                            shape = RoundedCornerShape(50)
                        )
                        Spacer(modifier = Modifier.padding(bottom = 10.dp))
                        val daily=Daily.values().toList()
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            shape= RoundedCornerShape(18.dp),
                            containerColor = text_field_color
                        ) {
                            daily.forEach { daily ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = daily.displayName,
                                            color = Color.Black
                                        )
                                    },
                                    onClick = {
                                        selectedText=daily
                                        expanded = false
                                    },
                                    modifier = Modifier.background(color = if(daily==selectedText) button_color
                                    else Color.Transparent, shape = RoundedCornerShape(18.dp)),
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(bottom = 50.dp))
                    Text(
                        text="Số tiền",
                        modifier = Modifier.align(Alignment.Start).padding(start=65.dp),
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(500),
                            color = Color(0xFFD9D9D9),

                            )
                    )

                    OutlinedTextField(
                        value = amount,
                        onValueChange = {amount=it},
                        modifier = Modifier.padding(10.dp).width(280.dp).height(50.dp),
                        shape = RoundedCornerShape(10.dp),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Email
                        ),
                        placeholder = {Text(text="Điền...")},
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
                    Spacer(modifier = Modifier.padding(bottom = 100.dp))
                    var result by remember { mutableStateOf(0) }
                    val couroutineScope= rememberCoroutineScope()
                    Button(
                        onClick = {
                            couroutineScope.launch {
                                user.value?.let {
                                    category.value?.let { it1 ->
                                        transactionViewModel.createTransaction(
                                            categoryId = it1.id,
                                            userId = it.id,
                                            walletId = walletId,
                                            amount = amount.toDouble(),
                                            timetype = selectedText
                                        )
                                    }
                                }
                                walletViewModel.updateBalance(walletId = wallet.value?.id!!, newBalance = balance?.plus(amount.toDouble())?:0.0)
                                result=1
                            }
                        },
                        modifier = Modifier.width(218.dp).height(45.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = button_color,
                        )
                    ) {
                        Text(
                            text = "Lưu",
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
                    Text(
                        text=when(result){
                        1->"Thêm thành công"
                        -1->"Thêm thất bại"
                        else->"Thêm giao dịch"
                    },
                        color=when(result){
                            1->Color.Green
                            -1->Color.Red
                            else->Color.White
                        })
                }

            }

        }

    }
}