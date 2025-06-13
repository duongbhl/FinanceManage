package com.example.financemanage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.financemanage.dao.CategoryDao
import com.example.financemanage.dao.TransactionDao
import com.example.financemanage.dao.UserDao
import com.example.financemanage.dao.WalletDao
import com.example.financemanage.model.Category
import com.example.financemanage.model.Transaction
import com.example.financemanage.model.User
import com.example.financemanage.model.Wallet

@Database(
    entities = [User::class, Category::class, Transaction::class, Wallet::class],
    version = 1,
    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun categoryDao(): CategoryDao
    abstract fun transactionDao(): TransactionDao
    abstract fun walletDao(): WalletDao

    companion object {
        @Volatile
        private var INSTANCE: FinanceDatabase? = null

        fun getDatabase(context: Context): FinanceDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FinanceDatabase::class.java,
                    "finance_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
