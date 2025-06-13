package com.example.financemanage.ui.Analysis

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanage.Enum.Daily
import com.example.financemanage.Enum.Money
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.model.Category
import com.example.financemanage.ui.BottomBar
import com.example.financemanage.ui.DailyBar
import com.example.financemanage.ui.Transactions.info
import com.example.financemanage.ui.theme.button_color
import com.example.lunchtray.R
import com.example.financemanage.model.CategoryTransactionSummary
import kotlin.math.abs
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalysisScreen(
    userViewModel: UserViewModel,
    transactionViewModel: TransactionViewModel,
    categoryViewModel: CategoryViewModel,
    walletViewModel:WalletViewModel,
    navController: androidx.navigation.NavHostController
){
    var selectedScreen by remember { mutableStateOf(Screen.ANALYSIS) }
    var selectedMoney by remember { mutableStateOf(Money.ALL) }
    var selectedDaily by remember { mutableStateOf(Daily.DAY) }
    val user=userViewModel.currentUser
    val wallet=walletViewModel.currentWallet
    var balance by remember { mutableStateOf<Double?>(null) }
    var income by remember { mutableStateOf<Double?>(null) }
    var expense by remember { mutableStateOf<Double?>(null) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    var categoryTransactionSummary by remember { mutableStateOf<List<CategoryTransactionSummary>>(emptyList()) }


    LaunchedEffect(key1 = user.value?.id,selectedDaily,selectedMoney) {
        income = transactionViewModel.getIncome(userId = user.value?.id!!)
        expense = transactionViewModel.getExpense(userId = user.value?.id!!)
        categories=user.value?.let { categoryViewModel.getCategoryByUserId(userid = it.id) }!!

        categoryTransactionSummary=when(selectedMoney){
            Money.INCOME->user.value?.let { categoryViewModel.getCategoryIncomeByUserIdAndDaily(userId = user.value?.id!!, timeType = selectedDaily)}!!
            Money.EXPENSE->user.value?.let { categoryViewModel.getCategoryExpenseByUserIdAndDaily(userId = user.value?.id!!, timeType = selectedDaily)}!!
            else->user.value?.let { categoryViewModel.getCategoryTransactionByUserIdAndDaily(userId = user.value?.id!!, timeType = selectedDaily)}!!
        }
        transactionViewModel.refreshSummary(user.value?.id!!)
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
                    Text(
                        text = "Phân tích",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 30.sp,
                            lineHeight = 22.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(600)
                        )
                    )
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
                color = button_color,
                style = TextStyle(
                    fontSize = 15.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(500),

                    )

            )
            Text(
                text="$"+balance.toString(),
                color = button_color,
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(700),

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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Spacer(modifier = Modifier.height(20.dp))
                    DailyBar(
                        modifier = Modifier.fillMaxSize(),
                        daily = selectedDaily,
                        onDailySelected = { selectedDaily = it }
                    )
                    val data = categoryTransactionSummary.map { it.totalAmount.toFloat() }
                    val labels = categoryTransactionSummary.map { it.name }
                    val baseColors = listOf(
                        Color(0xFFE57373),
                        Color(0xFF64B5F6),
                        Color(0xFF81C784),
                        Color(0xFFFFB74D),
                        Color(0xFFBA68C8),
                        Color(0xFFFF8A65)
                    )
                    val neededSize = categoryTransactionSummary.size
                    val colors = if (neededSize <= baseColors.size) {
                        baseColors.take(neededSize)
                    } else {
                        // Thêm các màu random để đủ số lượng
                        baseColors + List(neededSize - baseColors.size) {
                            Color(
                                red = Random.nextFloat(),
                                green = Random.nextFloat(),
                                blue = Random.nextFloat(),
                                alpha = 1f
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(40.dp))
                    CustomPieChart(
                        data = data,
                        colors = colors,
                        labels = labels,
                        size = 200.dp,
                        color = Color.White
                    )

                }


            }

        }

    }
}


@Composable
fun CustomPieChart(
    data: List<Float>,
    colors: List<Color>,
    labels: List<String>,
    size: Dp,
    color: Color
) {
    val absData = data.map { abs(it) }  // Dùng giá trị tuyệt đối
    val total = absData.sum()
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(data) {
        animationProgress.snapTo(0f)
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 30.dp)
    ) {
        Canvas(modifier = Modifier.size(size)) {
            var startAngle = -90f

            absData.forEachIndexed { index, value ->
                val sweepAngle = (value / total) * 360f * animationProgress.value
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true
                )
                startAngle += sweepAngle
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            data.forEachIndexed { index, value -> // Dùng data gốc để giữ dấu âm nếu cần hiển thị
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(16.dp)
                            .background(color = colors[index])
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${labels[index]}: $${"%.2f".format(value)}",
                        color = color,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}










