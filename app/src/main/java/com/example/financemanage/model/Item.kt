package com.example.financemanage.model

import android.media.Image
import androidx.compose.ui.graphics.painter.Painter
import java.sql.Timestamp

data class Item(
    val image:Painter,
    val transaction:String,
    val time:Timestamp,
    val time_type:String,
    val amount:Float
)

