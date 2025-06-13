package com.example.financemanage.model

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.financemanage.Enum.Daily
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"]),
        ForeignKey(entity = Category::class, parentColumns = ["id"], childColumns = ["categoryId"]),
        ForeignKey(entity = Wallet::class, parentColumns = ["id"], childColumns = ["walletId"])
    ],
    indices = [Index("userId"), Index("categoryId"), Index("walletId")]
)
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val amount: Double,
    val timestamp: Long=System.currentTimeMillis(),
    val timetype:Daily,
    val categoryId: Int,
    val walletId: Int,
    val userId: Int
)

