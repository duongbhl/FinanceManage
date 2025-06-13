package com.example.financemanage.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "categories",
    foreignKeys = [
        ForeignKey(entity = User::class, parentColumns = ["id"], childColumns = ["userId"])
    ],
    indices = [Index("userId"),Index(value = ["name"], unique = true)]
)
data class Category(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,               // Ăn uống, lương, giải trí, v.v.
    val type: String,              // "income" hoặc "expense"
    val icon: String? = null,      // (optional) lưu tên icon để hiển thị UI
    val userId: Int
)

