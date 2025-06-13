package com.example.financemanage.controller

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financemanage.dao.WalletDao
import com.example.financemanage.datastore.WalletDataStoreManager

class WalletViewModelFactory(
    private val walletDao: WalletDao,
    private val context: Context,
//    private val dataStoreManager: WalletDataStoreManager
) : ViewModelProvider.Factory {
    val dataStoreManager = WalletDataStoreManager(context.applicationContext)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModel = WalletViewModel(walletDao, dataStoreManager)
        viewModel.restoreWalletFromDataStore()
        return viewModel as T
    }
}
