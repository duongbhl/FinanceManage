package com.example.financemanage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financemanage.Enum.Daily
import com.example.financemanage.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertTransaction(transaction: Transaction)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTransaction(transaction: Transaction)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransaction(id: Int)

    @Query("SELECT * FROM transactions")
    fun getAllTransaction(): Flow<List<Transaction>>

    @Query("SELECT * FROM transactions WHERE userId = :userid and timetype=:daily")
    suspend fun getTransactionByDaily(userid: Int,daily: Daily): List<Transaction>

    @Query("SELECT * FROM transactions WHERE userId = :userId and categoryId=:categoryId")
    suspend fun getTransactionsByUser(userId: Int,categoryId:Int): List<Transaction>

    @Query("SELECT * FROM transactions WHERE userId = :userId and categoryId=:categoryId and amount>0")
    suspend fun getIncomeTransactionsByUser(userId: Int,categoryId:Int): List<Transaction>

    @Query("SELECT * FROM transactions WHERE userId = :userId and categoryId=:categoryId and amount<0")
    suspend fun getExpenseTransactionsByUser(userId: Int,categoryId:Int): List<Transaction>

    @Query("SELECT sum(amount) FROM transactions WHERE amount>0 and userId=:userId")
    suspend fun getIncome(userId: Int): Double

    @Query("SELECT sum(amount) FROM transactions WHERE amount<0 and userId=:userId")
    suspend fun getExpense(userId: Int): Double

    @Query("SELECT sum(amount) FROM transactions WHERE amount>0 and userId=:userId and categoryId=:categoryId")
    suspend fun getIncomeByCategory(userId: Int,categoryId: Int): Double

    @Query("SELECT sum(amount) FROM transactions WHERE amount<0 and userId=:userId and categoryId=:categoryId")
    suspend fun getExpenseByCategory(userId: Int,categoryId: Int): Double

    @Query("SELECT sum(amount) FROM transactions WHERE amount>0 and userId=:userId and timetype=:daily")
    suspend fun getIncomeByDaily(userId: Int,daily: Daily): Double

    @Query("SELECT sum(amount) FROM transactions WHERE amount<0 and userId=:userId and timetype=:daily")
    suspend fun getExpenseByDaily(userId: Int,daily: Daily): Double


}
