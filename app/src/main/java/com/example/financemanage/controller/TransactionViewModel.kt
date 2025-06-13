package com.example.financemanage.controller

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanage.Enum.Daily
import com.example.financemanage.dao.TransactionDao
import com.example.financemanage.model.SummaryByTime
import com.example.financemanage.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TransactionViewModel(private val transactionDao: TransactionDao) : ViewModel() {

    suspend fun createTransaction(
        amount: Double,
        categoryId: Int,
        walletId: Int,
        userId: Int,
        timetype: Daily
    ) {
        viewModelScope.launch {
            try {
                transactionDao.insertTransaction(
                    Transaction(
                        amount = amount,
                        categoryId = categoryId,
                        walletId = walletId,
                        userId = userId,
                        timetype = timetype
                    )
                )
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    suspend fun deleteTransaction(id: Int) {
        viewModelScope.launch {
            try {
                transactionDao.deleteTransaction(id)
            } catch (e: Exception) {
                return@launch
            }


        }
    }

    suspend fun updateTransaction(transaction: Transaction) {
        viewModelScope.launch {
            try {
                transactionDao.updateTransaction(transaction)
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    suspend fun getIncome(userId: Int):Double {
        if(transactionDao.getIncome(userId = userId)!=null)  return transactionDao.getIncome(userId)
        else return 0.0
    }

    suspend fun getExpense(userId: Int): Double {
        if(transactionDao.getExpense(userId = userId)!=null) return transactionDao.getExpense(userId)
        else return 0.0
    }

    suspend fun getIncomeByCategory(userId: Int,categoryId: Int): Double {
        if(transactionDao.getIncomeByCategory(userId = userId,categoryId = categoryId)!=null) return transactionDao.getIncomeByCategory(userId,categoryId)
        else return 0.0
    }

    suspend fun getExpenseByCategory(userId: Int,categoryId: Int): Double {
        if (transactionDao.getExpenseByCategory(
                userId = userId,
                categoryId = categoryId
            ) != null
        ) return transactionDao.getExpenseByCategory(userId, categoryId)
        else return 0.0
    }

    suspend fun getTransactionByUserId(userId: Int,categoryId: Int): List<Transaction> {
        return transactionDao.getTransactionsByUser(userId,categoryId)
    }

    suspend fun getIncomeTransactionsByUser(userId: Int,categoryId: Int): List<Transaction> {
        return transactionDao.getIncomeTransactionsByUser(userId,categoryId)
    }

    suspend fun getExpenseTransactionsByUser(userId: Int,categoryId: Int): List<Transaction> {
        return transactionDao.getExpenseTransactionsByUser(userId,categoryId)
    }

    suspend fun getTransactionByDaily(userId: Int,daily: Daily): List<Transaction> {
        return transactionDao.getTransactionByDaily(userId,daily)
    }

    suspend fun getIncomeByDaily(userId: Int,daily: Daily): Double {
        if(transactionDao.getIncomeByDaily(userId = userId,daily = daily)!=null) return transactionDao.getIncomeByDaily(userId,daily)
        else return 0.0
    }

    suspend fun getExpenseByDaily(userId: Int,daily: Daily): Double {
        if(transactionDao.getExpenseByDaily(userId = userId,daily = daily)!=null) return transactionDao.getExpenseByDaily(userId,daily)
        else return 0.0
    }


    private val _dailySummary = MutableStateFlow(SummaryByTime())
    val dailySummary: StateFlow<SummaryByTime> = _dailySummary.asStateFlow()

    private val _weeklySummary = MutableStateFlow(SummaryByTime())
    val weeklySummary: StateFlow<SummaryByTime> = _weeklySummary.asStateFlow()

    private val _monthlySummary = MutableStateFlow(SummaryByTime())
    val monthlySummary: StateFlow<SummaryByTime> = _monthlySummary.asStateFlow()

    fun refreshSummary(userId: Int) {
        viewModelScope.launch {
            _dailySummary.value = SummaryByTime(
                income = transactionDao.getIncomeByDaily(userId, Daily.DAY) ?: 0.0,
                expense = transactionDao.getExpenseByDaily(userId, Daily.DAY) ?: 0.0
            )
            _weeklySummary.value = SummaryByTime(
                income = transactionDao.getIncomeByDaily(userId, Daily.WEEK) ?: 0.0,
                expense = transactionDao.getExpenseByDaily(userId, Daily.WEEK) ?: 0.0
            )
            _monthlySummary.value = SummaryByTime(
                income = transactionDao.getIncomeByDaily(userId, Daily.MONTH) ?: 0.0,
                expense = transactionDao.getExpenseByDaily(userId, Daily.MONTH) ?: 0.0
            )
        }
    }



}



