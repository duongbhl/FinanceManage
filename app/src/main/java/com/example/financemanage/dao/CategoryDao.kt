package com.example.financemanage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financemanage.Enum.Daily
import com.example.financemanage.model.Category
import com.example.financemanage.model.CategoryTransactionSummary
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCategory(category: Category)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<Category>>

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun deleteCategory(id: Int)

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getCategoryById(id: Int): Category?

    @Query("SELECT p.name as name,sum(t.amount) as totalAmount from categories p  join transactions t on p.id=t.categoryId where p.userId=:userId and t.timetype=:timeType GROUP BY p.name;")
    suspend fun getCategoryTransactionByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary>

    @Query("SELECT p.name as name,sum(t.amount) as totalAmount from categories p  join transactions t on p.id=t.categoryId where p.userId=:userId and t.timetype=:timeType and t.amount>0 GROUP BY p.name;")
    suspend fun getCategoryIncomeByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary>

    @Query("SELECT p.name as name,sum(t.amount) as totalAmount from categories p  join transactions t on p.id=t.categoryId where p.userId=:userId and t.timetype=:timeType and t.amount<0 GROUP BY p.name;")
    suspend fun getCategoryExpenseByUserIdAndDaily(userId: Int,timeType: Daily): List<CategoryTransactionSummary>

    @Query("SELECT * FROM categories WHERE name = :name")
    suspend fun getCategoryByName(name: String): Category?

    @Query("SELECT * FROM categories WHERE type = :type")
    suspend fun getCategoriesByType(type: String): List<Category>

    @Query("SELECT * FROM categories WHERE userId = :userId")
    suspend fun getCategoriesByUserId(userId: Int): List<Category>

    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND amount > 0")
    suspend fun getTotalIncome(userId: Int): Double?

    @Query("SELECT SUM(amount) FROM transactions WHERE userId = :userId AND amount < 0")
    suspend fun getTotalExpense(userId: Int): Double?
}