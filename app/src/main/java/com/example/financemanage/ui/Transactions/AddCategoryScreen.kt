package com.example.financemanage.ui.Transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financemanage.Enum.Category
import com.example.financemanage.controller.CategoryViewModel
import com.example.financemanage.controller.UserViewModel
import com.example.financemanage.ui.theme.button_color
import com.example.financemanage.ui.theme.text_field_color
import com.example.lunchtray.R
import kotlinx.coroutines.launch

@Composable
fun AddCategoryScreen(
    modifier: Modifier = Modifier,
    onSave: (com.example.financemanage.model.Category) -> Unit,
    onCancel: () -> Unit,
    categoryViewModel: CategoryViewModel,
    userId: Int
) {
    var categoryName by remember { mutableStateOf("") }
    var selectedIconCategory by remember { mutableStateOf<Category?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Thêm danh mục mới",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = categoryName,
                onValueChange = { categoryName = it },
                modifier = Modifier
                    .padding(10.dp)
                    .width(280.dp)
                    .height(55.dp),
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                placeholder = { Text("Nhập tên danh mục...") },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedContainerColor = text_field_color,
                    unfocusedContainerColor = text_field_color,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Chọn biểu tượng",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                modifier = Modifier
                    .height(220.dp)
                    .padding(horizontal = 8.dp)
            ) {
                items(Category.entries) { category ->
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { selectedIconCategory = category }
                            .background(
                                if (selectedIconCategory == category) Color.Gray else Color.Transparent,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = category.iconDisplay),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = category.displayName,
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    selectedIconCategory?.let { selected ->
                        val newCategory = com.example.financemanage.model.Category(
                            name = categoryName,
                            type = selected.type,
                            icon = selected.iconDisplay.toString(),
                            userId = userId
                        )

                        coroutineScope.launch {
                            categoryViewModel.createCategory(
                                name = categoryName,
                                type = selected.type,
                                icon = selected.iconDisplay.toString(),
                                userId = userId
                            )
                            onSave(newCategory)
                            onCancel()
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 10.dp)
                    .width(218.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = button_color),
                enabled = categoryName.isNotBlank() && selectedIconCategory != null
            ) {
                Text(
                    text = "Lưu",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )
            }

            Button(
                onClick = onCancel,
                modifier = Modifier
                    .padding(top = 15.dp)
                    .width(218.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(containerColor = text_field_color)
            ) {
                Text(
                    text = "Hủy",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF093030)
                )
            }
        }
    }
}


