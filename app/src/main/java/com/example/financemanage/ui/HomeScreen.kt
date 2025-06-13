package com.example.financemanage.ui

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.financemanage.Enum.Daily
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.model.Category
import com.example.financemanage.model.Transaction
import com.example.financemanage.ui.Analysis.CustomPieChart
import com.example.financemanage.ui.theme.bottom_bar_color
import com.example.financemanage.ui.theme.button_color
import com.example.financemanage.ui.theme.daily_color
import com.example.financemanage.ui.theme.divider_color
import com.example.financemanage.ui.theme.group_390_button
import com.example.financemanage.ui.theme.md_theme_light_error
import com.example.financemanage.ui.theme.md_theme_light_scrim
import com.example.financemanage.ui.theme.transaction_color
import com.example.lunchtray.R
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(
    userViewModel: UserViewModel,
    categoryViewModel: CategoryViewModel,
    transactionViewModel: TransactionViewModel,
    walletViewModel: WalletViewModel,
    navController: NavHostController){
    val user=userViewModel.currentUser
    var selectedScreen by remember { mutableStateOf(Screen.HOME) }
    var selectedDaily by remember { mutableStateOf(Daily.DAY)}
    var balance by remember { mutableStateOf<Double?>(null) }
    var expense by remember { mutableStateOf<Double?>(null) }
    var transactions by remember { mutableStateOf<List<Transaction>>(emptyList()) }

    LaunchedEffect(key1 = user.value?.id,selectedDaily) {
        balance = transactionViewModel.getIncome(userId = user.value?.id!!)
        expense = transactionViewModel.getExpense(userId = user.value?.id!!)
        transactions=transactionViewModel.getTransactionByDaily(userId = user.value?.id!!,daily = selectedDaily)
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(), // Đảm bảo Scaffold chiếm toàn bộ màn hình
        containerColor = Color.Black,
        bottomBar = { BottomBar(
            modifier = Modifier.fillMaxWidth(),
            screen = selectedScreen,
            onScreenSelected = { selectedScreen=it },
            navController = navController
        ) }
    ) {paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ){
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()
            ){
                Text(
                    text=user.value?.username.toString(),
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 22.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFF1FFF3),

                        )
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier.background(color = Color(0xFFFFA000), shape = RoundedCornerShape(size = 25.dp))
                ){
                    Icon(
                        modifier = Modifier
                            .padding(10.dp)
                            .width(14.57143.dp)
                            .height(18.85714.dp),
                        painter = painterResource(R.drawable.vector),
                        contentDescription = null,
                        tint = Color.White
                    )
                }

            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                balance?.let{
                    total_income(value = it)
                }

                Divider(
                    modifier = Modifier
                        .padding(0.dp)
                        .width(1.dp)
                        .height(42.dp)
                        .background(color = Color(0xFFDFF7E2))
                )

                expense?.let {
                    total_expense(value = it)
                }

            }
            Card(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(152.dp)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(color = button_color, shape = RoundedCornerShape(size = 31.dp)),
                colors= CardDefaults.cardColors(
                    containerColor = button_color

                )
            ) {

                saving_goal(userViewModel = userViewModel, transactionViewModel = transactionViewModel, selectedDaily = selectedDaily)
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


                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Divider(modifier = Modifier.padding(top=45.dp))
                    LazyColumn( modifier = Modifier.weight(1f)) {
                        items(transactions){ transaction->
                            Itemmake(transaction = transaction, categoryViewModel = categoryViewModel)
                        }
                    }
                }
                DailyBar(
                    modifier = Modifier.fillMaxSize(),
                    daily = selectedDaily,
                    onDailySelected = { selectedDaily = it }
                )


            }

        }

    }

}

@Composable
fun total_income(modifier: Modifier=Modifier,value:Double){
    Column(
        modifier = Modifier
    ) {
        Row(){
            Image(
                painter = painterResource(R.drawable.group_208),
                contentDescription = null,
                modifier=Modifier.padding(end=5.dp)
            )
            Text(
                text="Tổng Thu Nhập",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFF1FFF3),

                    )
            )

        }
        Text(
            text="$"+value.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(700),
                color = Color(0xFFF1FFF3),

                )
        )

    }
}

@Composable
fun total_expense(modifier: Modifier=Modifier, value: Double){
    Column(
        modifier = Modifier,
    ) {
        Row(){
            Image(
                painter = painterResource(R.drawable.group_209),
                contentDescription = null,
                modifier=Modifier.padding(end=5.dp)
            )
            Text(
                text="Tổng Chi Tiêu",
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(400),
                    color = Color(0xFFF1FFF3),

                    )
            )

        }
        Text(
            text="$"+value.toString(),
            style = TextStyle(
                fontSize = 24.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(700),
                color =md_theme_light_error,

                )
        )

    }

}

@Composable
fun saving_goal(
    userViewModel: UserViewModel,
    transactionViewModel: TransactionViewModel,
    modifier: Modifier=Modifier,
    selectedDaily: Daily) {
    val user=userViewModel.currentUser
    val dailySummary by transactionViewModel.dailySummary.collectAsState()
    val weeklySummary by transactionViewModel.weeklySummary.collectAsState()
    val monthlySummary by transactionViewModel.monthlySummary.collectAsState()

    LaunchedEffect(key1 = user.value?.id,selectedDaily) {
        transactionViewModel.refreshSummary(user.value?.id!!)
    }
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ){
        when(selectedDaily){
            Daily.DAY -> CustomPieChart(
                data = listOf(dailySummary.income.toFloat(), dailySummary.expense.toFloat()),
                colors = listOf(Color.White, Color.Black),
                labels = listOf("Thu nhập", "Chi tiêu"),
                size = 100.dp,
                color = Color.Black
            )
            Daily.WEEK -> CustomPieChart(
                data = listOf(weeklySummary.income.toFloat(), weeklySummary.expense.toFloat()),
                colors = listOf(Color.White, Color.Black),
                labels = listOf("Thu nhập", "Chi tiêu"),
                size = 100.dp,
                color = Color.Black
            )
            Daily.MONTH -> CustomPieChart(
                data = listOf(monthlySummary.income.toFloat(), monthlySummary.expense.toFloat()),
                colors = listOf(Color.White, Color.Black),
                labels = listOf("Thu nhập", "Chi tiêu"),
                size = 100.dp,
                color = Color.Black
            )
        }

    }
}

@Composable
fun DailyBar(
    modifier: Modifier,
    daily:Daily,
    onDailySelected: (Daily) -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(color = daily_color, shape = RoundedCornerShape(size = 22.dp)),


        ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(40.dp, Alignment.CenterHorizontally)
        ){
            TextButton(
                onClick = {
                    onDailySelected(Daily.DAY)
                },

                ) {
                Box(
                    modifier=Modifier
                        .size(80.dp)
                        .background(
                            color = if (daily == Daily.DAY) button_color else daily_color,
                            shape = when (daily) {
                                Daily.DAY -> RoundedCornerShape(size = 19.dp)
                                else -> RoundedCornerShape(size = 0.dp)
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ){
                    Text(text="Ngày",style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(400),
                        color = if(daily==Daily.DAY) md_theme_light_scrim else Color(0xFFF1FFF3),
                        textAlign = TextAlign.Center,
                    ))
                }

            }
            TextButton(
                onClick = {
                    onDailySelected(Daily.WEEK)
                },

                ) {
                Box(
                    modifier=Modifier
                        .size(80.dp)
                        .background(
                            color = if (daily == Daily.WEEK) button_color else daily_color,
                            shape = when (daily) {
                                Daily.WEEK -> RoundedCornerShape(size = 19.dp)
                                else -> RoundedCornerShape(size = 0.dp)
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ){
                    Text(text="Tuần",style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(400),
                        color = if(daily==Daily.WEEK) md_theme_light_scrim else Color(0xFFF1FFF3),
                        textAlign = TextAlign.Center,
                    ))
                }

            }
            TextButton(
                onClick = {
                    onDailySelected(Daily.MONTH)
                },

                ) {
                Box(
                    modifier=Modifier
                        .size(80.dp)
                        .background(
                            color = if (daily == Daily.MONTH) button_color else daily_color,
                            shape = when (daily) {
                                Daily.MONTH -> RoundedCornerShape(size = 19.dp)
                                else -> RoundedCornerShape(size = 0.dp)
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ){
                    Text(
                        text="Tháng",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight(400),
                            color= if(daily==Daily.MONTH) md_theme_light_scrim else Color(0xFFF1FFF3),
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Itemmake(transaction: Transaction,modifier: Modifier=Modifier,categoryViewModel: CategoryViewModel){
    var category by remember { mutableStateOf<Category?>(null) }
    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    LaunchedEffect(key1 = transaction.categoryId){
        category=categoryViewModel.getCategoryById(transaction.categoryId)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp).background(color = transaction_color,shape = RoundedCornerShape(size = 30.dp)),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .padding(end = 10.dp)
                .width(70.dp)
                .height(80.dp),
            contentAlignment = Alignment.Center,
        ) {
            // Box nhỏ bao quanh icon có màu nền và bo góc
            Box(
                modifier = Modifier
                    .size(50.dp) // icon + nền thu nhỏ gọn lại
                    .background(
                        color = Color(0xFFFFC107),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                category?.icon?.let { Integer.valueOf(it) }?.let { painterResource(it) }?.let {
                    Image(
                        painter = it,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp), // icon nhỏ bên trong
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }


        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,

        ){
            category?.let {
                Text(
                    text= it.name,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Default,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFF1FFF3),

                        )
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text= formatTimestampMillisToReadableDate(transaction.timestamp),
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFA000),

                    )

            )
        }
        Divider(
            modifier = Modifier
                .padding(0.dp)
                .width(1.01412.dp)
                .height(35.49435.dp)
                .background(color = divider_color)
        )
        Text(
            text=transaction.timetype.toString().uppercase(),
            modifier = Modifier.padding(5.dp),
            fontWeight = FontWeight.Bold,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 15.sp,
                fontFamily = FontFamily.Default,
                color = Color(0xFFF1FFF3),
                )
        )
        Divider(
            modifier = Modifier
                .padding(0.dp)
                .width(1.01412.dp)
                .height(35.49435.dp)
                .background(color = divider_color)
        )
        Text(
            text="$"+transaction.amount.toString(),
            modifier = Modifier.padding(start = 3.dp, top=5.dp)
                .width(75.dp)
                .height(23.dp),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight(500),
                color = Color(0xFFF1FFF3),

                )
        )
    }

}

@Composable
fun BottomBar(
    modifier: Modifier,
    screen: Screen,
    onScreenSelected: (Screen) -> Unit = {},
    navController: NavHostController
    ) {

    Box(
        modifier = Modifier
            .background(color = bottom_bar_color)
            .height(100.dp),
    ){
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            IconButton(
                onClick = {
                    navController.navigate(Screen.HOME.name)
                    onScreenSelected(Screen.HOME)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.home),
                    contentDescription = null,
                    tint = if(screen==Screen.HOME) button_color else Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)

                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.ANALYSIS.name)
                    onScreenSelected(Screen.ANALYSIS)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.analysis),
                    contentDescription = null,
                    tint = if(screen==Screen.ANALYSIS) button_color else Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)

                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.ADD_WALLET.name)
                    onScreenSelected(Screen.ADD_WALLET)
                }
            ) {
                Box(
                    modifier = Modifier
                        .size(80.dp) // 👉 Kích thước tổng thể của nền (to lên)
                        .background(
                            color = group_390_button,
                            shape = RoundedCornerShape(80.dp)
                        ),
                    contentAlignment = Alignment.Center
                ){
                    Icon(
                        painter = painterResource(R.drawable.group_390),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .width(19.31429.dp)
                            .height(19.97305.dp)

                    )
                }
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.CATEGORY.name)
                    onScreenSelected(Screen.CATEGORY)
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.category),
                    contentDescription = null,
                    tint = if(screen==Screen.CATEGORY) button_color else Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)

                )
            }
            IconButton(
                onClick = {
                    navController.navigate(Screen.PROFILE.name)
                    onScreenSelected(Screen.PROFILE)
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = null,
                    tint = if(screen==Screen.PROFILE) button_color else Color.White,
                    modifier = Modifier
                        .width(30.dp)
                        .height(30.dp)

                )
            }

        }
    }
}


@Composable
fun formatTimestampMillisToReadableDate(timestampMillis: Long): String {
    // Chuyển đổi mili giây sang đối tượng Instant (đại diện cho một điểm thời gian cụ thể trên dòng thời gian UTC)
    val instant = Instant.ofEpochMilli(timestampMillis)

    // Chuyển đổi Instant sang LocalDateTime sử dụng múi giờ mặc định của hệ thống
    // Bạn có thể chỉ định một ZoneId cụ thể nếu muốn, ví dụ: ZoneId.of("Asia/Ho_Chi_Minh")
    val localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())

    // Định dạng LocalDateTime thành chuỗi dễ đọc
    // Bạn có thể tùy chỉnh mẫu định dạng theo ý muốn
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    return localDateTime.format(formatter)
}
