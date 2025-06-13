package com.example.financemanage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financemanage.Enum.Screen
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.CategoryViewModelFactory
import com.example.financemanage.controller.TransactionViewModel
import com.example.financemanage.controller.TransactionViewModelFactory
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.controller.UserViewModelFactory
import com.example.financemanage.controller.WalletViewModel
import com.example.financemanage.controller.WalletViewModelFactory
import com.example.financemanage.ui.Analysis.AnalysisScreen
import com.example.financemanage.ui.ChangePass.EmailScreen
import com.example.financemanage.ui.ChangePass.PassScreen
import com.example.financemanage.ui.HomeScreen
import com.example.financemanage.ui.Login.LoginScreen
import com.example.financemanage.ui.SignUp.SignUpScreen
import com.example.financemanage.ui.Transactions.AddTransactionScreen
import com.example.financemanage.ui.Transactions.CategoryScreen
import com.example.financemanage.ui.Transactions.TransactionScreen
import com.example.financemanage.ui.Wallet.AddWalletScreen
import com.example.financemanage.ui.Wallet.WalletScreen

@Composable
fun AppScreen(){
    val navController= rememberNavController()
    val couroutineScope= rememberCoroutineScope()
    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModelFactory(FinanceDatabase.getDatabase(navController.context).userDao())
    )
    val categoryViewModel: CategoryViewModel = viewModel(
        factory = CategoryViewModelFactory(FinanceDatabase.getDatabase(navController.context).categoryDao())
    )
    val transactionViewModel: TransactionViewModel = viewModel(
        factory = TransactionViewModelFactory(FinanceDatabase.getDatabase(navController.context).transactionDao())
    )
    val context=LocalContext.current
    val walletViewModel:WalletViewModel= viewModel(
        factory = WalletViewModelFactory(FinanceDatabase.getDatabase(navController.context).walletDao(),context)
    )
    NavHost(navController = navController, startDestination = Screen.LOGIN.name){
        composable(route=Screen.LOGIN.name){
            LoginScreen(userViewModel, loginButton = {
                navController.navigate(Screen.HOME.name)
            }, changepassButton = {
                navController.navigate(Screen.EMAIL.name)
            }, signinButton = {
                navController.navigate(Screen.SIGNUP.name)
            })
        }
        composable(route=Screen.SIGNUP.name){
            SignUpScreen(userViewModel, signUpButton = {
                navController.navigate(Screen.HOME.name)
            }, loginButton = {navController.navigate(Screen.LOGIN.name)}
            )
        }
        composable(route = Screen.EMAIL.name){
            EmailScreen(userViewModel,sendButton = {
                navController.navigate(Screen.CHANGEPASS.name)
            },backButton = {navController.navigate(Screen.LOGIN.name)})
        }
        composable(route = Screen.CHANGEPASS.name){
            PassScreen(userViewModel,resetButton = {
                navController.navigate(Screen.HOME.name)
            }, backButton = {navController.navigate(Screen.EMAIL.name)})
        }
        composable(route=Screen.HOME.name){
            HomeScreen(
                userViewModel = userViewModel,
                categoryViewModel = categoryViewModel,
                walletViewModel = walletViewModel,
                transactionViewModel = transactionViewModel,
                navController = navController
            )
        }
        composable(route=Screen.ANALYSIS.name){
            AnalysisScreen(
                userViewModel = userViewModel,
                categoryViewModel = categoryViewModel,
                transactionViewModel = transactionViewModel,
                walletViewModel = walletViewModel,
                navController = navController
            )
        }
        composable(route=Screen.ADD_WALLET.name){
            AddWalletScreen(
                userViewModel = userViewModel,
                walletViewModel = walletViewModel,
                backButton = {navController.navigate(Screen.HOME.name)},
                navController = navController
            )
        }
        composable(route =Screen.WALLET_LIST.name){
            WalletScreen(
                userViewModel = userViewModel,
                walletViewModel = walletViewModel,
                navController = navController
            )
        }
        composable(route=Screen.CATEGORY.name){
            CategoryScreen(
                userViewModel = userViewModel,
                categoryViewModel = categoryViewModel,
                transactionViewModel = transactionViewModel,
                walletViewModel = walletViewModel,
                changeScreen2 = {navController.navigate(Screen.TRANSACTION.name)},
                navController = navController

            )
        }
        composable(route=Screen.TRANSACTION.name){
            TransactionScreen(
                changeCategoryScreen = {navController.navigate(Screen.CATEGORY.name)},
                navController = navController,
                userViewModel = userViewModel,
                transactionViewModel = transactionViewModel,
                walletViewModel = walletViewModel,
                categoryViewModel = categoryViewModel

            )
        }
        composable(route=Screen.ADD_TRANSACTION.name){
            AddTransactionScreen(
                userViewModel = userViewModel,
                categoryViewModel = categoryViewModel,
                transactionViewModel = transactionViewModel,
                walletViewModel = walletViewModel,
                navController = navController

            )
        }
    }
}