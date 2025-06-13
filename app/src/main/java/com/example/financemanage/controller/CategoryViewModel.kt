package com.example.financemanage.controller

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financemanage.Enum.Daily
import com.example.financemanage.dao.CategoryDao
import com.example.financemanage.model.Category
import com.example.financemanage.model.CategoryTransactionSummary
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryDao: CategoryDao) : ViewModel()  {
    private val _currentCategory = mutableStateOf<Category?>(null)
    val currentCategory: State<Category?> get() = _currentCategory

    fun clickCategory(category: Category) {
        _currentCategory.value = category
    }

    suspend fun getCategoryById(id: Int): Category? {
        return categoryDao.getCategoryById(id)
    }

    suspend fun getCategoryByUserId(userid: Int): List<Category> {
        return categoryDao.getCategoriesByUserId(userid)
    }

    suspend fun getCategoryTransactionByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary> {
        return categoryDao.getCategoryTransactionByUserIdAndDaily(userId,timeType)
    }

    suspend fun getCategoryIncomeByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary> {
        return categoryDao.getCategoryIncomeByUserIdAndDaily(userId,timeType)
    }

    suspend fun getCategoryExpenseByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary> {
        return categoryDao.getCategoryExpenseByUserIdAndDaily(userId,timeType)
    }

    suspend fun createCategory(name: String, type: String, icon: String?, userId: Int) {
        viewModelScope.launch {
            try {
                categoryDao.insertCategory(
                    Category(
                        name = name,
                        type = type,
                        icon = icon,
                        userId = userId
                    )
                )
            } catch (e: Exception) {
                return@launch
            }
        }
    }

    suspend fun updateCategory(category: Category) {
        viewModelScope.launch {
            try {
                categoryDao.updateCategory(category)
            } catch (e: Exception) {
                return@launch
            }
        }

            }

}