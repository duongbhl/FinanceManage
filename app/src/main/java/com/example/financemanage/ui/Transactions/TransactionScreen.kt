package com.example.financemanage.ui.Transactions


import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanage.Enum.Money
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.model.Item
import com.example.financemanage.model.Transaction
import com.example.financemanage.ui.BottomBar
import com.example.financemanage.ui.Itemmake
import com.example.financemanage.ui.theme.button_color
import com.example.financemanage.ui.theme.card_color
import com.example.financemanage.ui.theme.expense_color
import com.example.lunchtray.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    userViewModel: UserViewModel,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel,
    walletViewModel:WalletViewModel,
    changeCategoryScreen:()->Unit,
    navController: androidx.navigation.NavHostController
    ){
    var selectedScreen by remember { mutableStateOf(Screen.HOME) }
    val category=categoryViewModel.currentCategory
    val user=userViewModel.currentUser
    val wallet=walletViewModel.currentWallet
    var balance by remember { mutableStateOf<Double?>(null) }
    var income by remember { mutableStateOf<Double?>(null) }
    var expense by remember { mutableStateOf<Double?>(null) }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var selectedMoney by remember { mutableStateOf(Money.ALL) }

    LaunchedEffect(key1 = user.value?.id, key2 = category.value?.id,selectedMoney) {
        income = transactionViewModel.getIncomeByCategory(userId = user.value?.id!!, categoryId = category.value?.id!!)
        expense = transactionViewModel.getExpenseByCategory(userId = user.value?.id!!, categoryId = category.value?.id!!)
        transactions = when(selectedMoney){
            Money.INCOME -> transactionViewModel.getIncomeTransactionsByUser(userId = user.value?.id!!, categoryId = category.value?.id!!)
            Money.EXPENSE -> transactionViewModel.getExpenseTransactionsByUser(userId = user.value?.id!!, categoryId = category.value?.id!!)
            else -> transactionViewModel.getTransactionByUserId(userId = user.value?.id!!, categoryId = category.value?.id!!)
        }
    }
    LaunchedEffect(key1=user.value?.id,key2=wallet.value?.id) {
        balance = walletViewModel.getTotalBalance(userId = user.value?.id!!, walletId = wallet.value?.id!!)
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    category.value?.let {
                        Text(text = it.name, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth().padding(end=33.dp),style = TextStyle(
                            fontSize = 35.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),

                            ))
                    }
                },
                navigationIcon ={
                    IconButton(onClick = {changeCategoryScreen()}) {
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
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Text(
                text="Tổng Số Dư",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),

                    )

            )
            Text(
                text="$"+balance.toString(),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(700),
                    color = Color(0xFFFFFFFF),

                    )

            )

            info(
                totalIncome = income?:00.00,
                totalExpense = expense?:00.00,
                money = selectedMoney,
                onMoneySelected = { selectedMoney = it }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(R.drawable.base_shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    modifier = Modifier.fillMaxSize().padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn( modifier = Modifier.weight(1f)) {
                        items(transactions){transaction->
                            Itemmake(transaction = transaction, categoryViewModel = categoryViewModel)
                        }
                    }
                    Button(
                        onClick = {navController.navigate(Screen.ADD_TRANSACTION.name)},
                        modifier = Modifier.padding(top=35.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = button_color,
                        )
                    ) {
                        Text(
                            text = "Tạo giao dịch",
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
                }

            }

        }

    }
}

@Composable
fun info(totalIncome:Double,
         totalExpense:Double,
         money: Money,
         onMoneySelected: (Money) -> Unit = {}){
    Row(
        modifier=Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ){
        var click_income=0
        Card(
            modifier = Modifier.width(171.dp)
                .height(111.dp).fillMaxSize().padding(top=10.dp),
            colors = if(money==Money.INCOME) CardDefaults.cardColors(button_color) else CardDefaults.cardColors(card_color),

            onClick = {
                ++click_income
                if(click_income==1) onMoneySelected(Money.INCOME)
                else {
                    onMoneySelected(Money.ALL)
                    click_income=0
                }
            }
            ){
            Image(
                modifier = Modifier.padding(top=15.dp).width(25.dp).height(25.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.group_208),
                contentDescription = null,
                colorFilter = if(money==Money.INCOME) ColorFilter.tint(Color.Black) else ColorFilter.tint(Color(0xFFFFFFFF))
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text="Thu Nhập",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),
                    color = if(money==Money.INCOME) Color.Black else Color(0xFFFFFFFF),

                    )
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text="$"+totalIncome.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = if(money==Money.INCOME) Color.Black else Color(0xFFFFFFFF),

                    )
            )

        }
        var click_expense=0
        Card(
            modifier = Modifier.width(171.dp)
                .height(111.dp).fillMaxSize().padding(top=10.dp),
            colors = if(money==Money.EXPENSE) CardDefaults.cardColors(button_color) else CardDefaults.cardColors(card_color),
            onClick = {
                ++click_expense
                if(click_expense==1) onMoneySelected(Money.EXPENSE)
                else {
                    onMoneySelected(Money.ALL)
                    click_expense=0
                }

            }

        ){
            Image(
                modifier = Modifier.padding(top=15.dp).width(25.dp).height(25.dp).align(Alignment.CenterHorizontally),
                painter = painterResource(R.drawable.group_209),
                contentDescription = null,
                colorFilter = if(money==Money.EXPENSE) ColorFilter.tint(Color.Black) else ColorFilter.tint(expense_color)
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text="Chi Phí",
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),
                    color = if(money==Money.EXPENSE) Color.Black else expense_color,

                    )
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text="$"+totalExpense.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = if(money==Money.EXPENSE) Color.Black else expense_color,

                    )
            )

        }
    }

}