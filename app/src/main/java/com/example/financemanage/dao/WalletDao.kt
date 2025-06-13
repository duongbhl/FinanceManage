package com.example.financemanage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.financemanage.model.Wallet
import kotlinx.coroutines.flow.Flow

@Dao
interface WalletDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertWallet(wallet: Wallet)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateWallet(wallet: Wallet)

    @Query("SELECT * FROM wallets WHERE userId = :userId")
    suspend fun getWalletsByUser(userId: Int):List<Wallet>

    @Query("UPDATE wallets SET balance = :newBalance WHERE id = :walletId")
    suspend fun updateWalletBalance(walletId: Int, newBalance: Double)

    @Query("SELECT SUM(balance) FROM wallets WHERE userId = :userId and id=:walletId")
    suspend fun getTotalBalance(userId: Int, walletId: Int): Double

    @Query("SELECT * FROM wallets WHERE id = :id")
    suspend fun getWalletById(id: Int): Wallet?


}
