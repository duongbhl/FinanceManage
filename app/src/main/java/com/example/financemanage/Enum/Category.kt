package com.example.financemanage.Enum

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.lunchtray.R

enum class Category(val displayName: String,val type:String,val iconDisplay:Int) {
    FOOD("Ăn uống","expense" ,R.drawable.utensils_solid),
    TRANSPORT("Đi lại","expense",R.drawable.car_side_solid),
    GROCERIES("Nhu yếu phẩm","expense",R.drawable.cart_shopping_solid),
    RENT("Khoản thuê","expense",R.drawable.capa_1),
    GIFTS("Quà","expense",R.drawable.gift_solid),
    MEDICINE("Thuốc","expense",R.drawable.pills_solid),
    ENTERTAINMENT("Giải trí","expense",R.drawable.film_solid),
    SAVING("Tiết kiệm","income",R.drawable.piggy_bank_solid),
    OTHER("Khác","expense",R.drawable.coins_solid);

}
