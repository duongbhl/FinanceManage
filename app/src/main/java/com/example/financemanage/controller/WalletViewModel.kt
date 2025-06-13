package com.example.financemanage.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanage.dao.WalletDao
import com.example.financemanage.datastore.WalletDataStoreManager
import com.example.financemanage.model.Wallet
import kotlinx.coroutines.launch

class WalletViewModel(private val walletDao: WalletDao,private val dataStoreManager: WalletDataStoreManager): ViewModel() {
    private val _currentWallet = mutableStateOf<Wallet?>(null)
    val currentWallet: State<Wallet?> get() = _currentWallet

    suspend fun createWallet(name: String, balance: Double, userId: Int){
       viewModelScope.launch {
           try {
               val wallet=Wallet(
                   name = name,
                   balance = balance,
                   userId = userId
               )
               walletDao.insertWallet(
                   wallet = wallet
               )
           }catch (e: Exception){
               return@launch
           }
       }
       }

    fun changeWallet(wallet: Wallet) {
        _currentWallet.value = wallet
        viewModelScope.launch {
            dataStoreManager.saveWalletId(wallet.id)
        }
    }

    fun restoreWalletFromDataStore() {
        viewModelScope.launch {
            val id = dataStoreManager.getWalletId()
            if (id != null) {
                val wallet = walletDao.getWalletById(id)
                _currentWallet.value = wallet
            }
        }
    }


    suspend fun getWalletByUserId(userId: Int): List<Wallet> {
        return walletDao.getWalletsByUser(userId = userId)
    }

    suspend fun updateBalance(walletId: Int, newBalance: Double){
        viewModelScope.launch {
            try {
                walletDao.updateWalletBalance(walletId, newBalance)
            }catch (e: Exception){
                return@launch
            }
        }
    }

    suspend fun getTotalBalance(userId: Int, walletId: Int): Double {
        return walletDao.getTotalBalance(userId, walletId)
    }

}


