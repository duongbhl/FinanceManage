package com.example.financemanage.ui.Transactions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.model.Category
import com.example.financemanage.ui.BottomBar
import com.example.financemanage.ui.total_expense
import com.example.financemanage.ui.total_income
import com.example.lunchtray.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    categoryViewModel: CategoryViewModel,
    userViewModel: UserViewModel,
    transactionViewModel: TransactionViewModel,
    walletViewModel: WalletViewModel,
    changeScreen2: () -> Unit,
    navController: NavHostController
) {
    var selectedScreen by remember { mutableStateOf(Screen.CATEGORY) }
    var showAddCategory by remember { mutableStateOf(false) }
    var categories by remember { mutableStateOf<List<Category>>(emptyList()) }
    val user = userViewModel.currentUser
    var income by remember { mutableStateOf<Double?>(null) }
    var expense by remember { mutableStateOf<Double?>(null) }

    LaunchedEffect(user.value?.id) {
        expense = transactionViewModel.getExpense(userId = user.value?.id!!)
        income = transactionViewModel.getIncome(userId = user.value?.id!!)
        categories = user.value?.let { categoryViewModel.getCategoryByUserId(userid = it.id) }!!
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                screen = selectedScreen,
                onScreenSelected = { selectedScreen = it },
                navController = navController
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Thể Loại",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 10.dp),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF1FFF3),
                    )
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = Color(0xFFFFA000),
                            shape = RoundedCornerShape(size = 25.dp)
                        )
                        .padding(10.dp)
                ) {
                    Icon(
                        modifier = Modifier
                            .width(14.57.dp)
                            .height(18.86.dp),
                        painter = painterResource(R.drawable.vector),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                income?.let { total_income(value = it) }
                Divider(
                    modifier = Modifier
                        .width(1.dp)
                        .height(42.dp)
                        .background(color = Color(0xFFDFF7E2))
                )
                expense?.let { total_expense(value = it) }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(R.drawable.base_shape),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(
                        onClick = { showAddCategory = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .width(105.dp)
                                .height(97.63.dp)
                                .background(
                                    color = Color(0xFFFFC107),
                                    shape = RoundedCornerShape(size = 25.79.dp)
                                ),
                            contentAlignment = Alignment.Center,
                        ) {
                            Image(
                                modifier = Modifier
                                    .width(39.dp)
                                    .height(39.dp),
                                painter = painterResource(R.drawable.group_390),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.Black)
                            )
                        }
                    }

                    Text(
                        text = "Thêm danh mục",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(categories) { category ->
                            CategoryItem(category = category, onCategoryClick = {
                                categoryViewModel.clickCategory(category)
                                changeScreen2()
                            }
                            )
                        }
                    }
                }

                if (showAddCategory) {
                    user.value?.let {
                        AddCategoryScreen(
                            onSave = { newCategory ->
                                categories = categories + newCategory
                                showAddCategory = false
                            },
                            onCancel = { showAddCategory = false },
                            categoryViewModel = categoryViewModel,
                            userId = it.id
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category, onCategoryClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = onCategoryClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(105.dp)
                    .height(97.63.dp)
                    .background(color = Color(0xFFFFC107), shape = RoundedCornerShape(25.79.dp)),
                contentAlignment = Alignment.Center
            ) {
                category.icon?.let { iconResId ->
                    Image(
                        painter = painterResource(id = Integer.parseInt(iconResId)),
                        contentDescription = null,
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                    )
                }
            }

        }
        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

