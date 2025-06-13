package com.example.financemanage.ui.Wallet


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.dao.WalletDao
import com.example.financemanage.model.Wallet
import com.example.financemanage.ui.BottomBar
import com.example.lunchtray.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreen(
    userViewModel: UserViewModel,
    walletViewModel: WalletViewModel,
    navController: NavHostController
) {
    var selectedScreen by remember { mutableStateOf(Screen.WALLET_LIST) }
    var wallets by remember { mutableStateOf<List<Wallet>>(emptyList()) }
    val user = userViewModel.currentUser

    LaunchedEffect(user.value?.id) {
        user.value?.id?.let { userId ->
            // Khôi phục ví đã chọn
            walletViewModel.restoreWalletFromDataStore()
            // Lấy danh sách ví
            wallets = walletViewModel.getWalletByUserId(userId)
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Black,
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Danh sách các ví",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .padding(start = 60.dp),
                            style = TextStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFFF1FFF3),
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .background(Color(0xFFFFA000), shape = RoundedCornerShape(25.dp))
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .width(14.5.dp)
                                    .height(18.8.dp),
                                painter = painterResource(R.drawable.vector),
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.ADD_WALLET.name) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .padding(1.dp)
                                .size(30.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black,
                )
            )
        },
        bottomBar = {
            BottomBar(
                modifier = Modifier.fillMaxWidth(),
                screen = selectedScreen,
                onScreenSelected = { selectedScreen = it },
                navController = navController
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            items(wallets) { wallet ->
                WalletItem(
                    wallet = wallet,
                    selected = walletViewModel.currentWallet.value?.id == wallet.id,
                    onClick = {
                        walletViewModel.changeWallet(wallet) // Lưu ví hiện tại
                    }
                )
            }
        }
    }
}


@Composable
fun WalletItem(
    wallet: Wallet,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onClick()
                       },
        colors = CardDefaults.cardColors(
            containerColor = if (selected) Color(0xFFFF9800) else Color(0xFF5A4F36)
        ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ví: "+wallet.id.toString(),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = "Số dư ví: ${wallet.balance}",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White
                )
            )
        }
    }
}


