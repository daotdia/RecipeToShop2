package com.otero.recipetoshop.android.presentation.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.otero.recipetoshop.android.R

val Lora = FontFamily(
    Font(R.font.lora_regular, FontWeight.W300),
    Font(R.font.lora_medium, FontWeight.W400),
    Font(R.font.lora_semibold, FontWeight.W500),
    Font(R.font.lora_bold, FontWeight.W600)
)

val LoraTypography = Typography(
    h1 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W500,
        fontSize = 30.sp,
    ),
    h2 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp,
    ),
    h3 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp,
    ),
    h4 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp,
    ),
    h5 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
    ),
    h6 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    subtitle1 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
    ),
    subtitle2 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
    ),
    body1 = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = Lora,
        fontSize = 14.sp
    ),
    button = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),
    caption = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    overline = TextStyle(
        fontFamily = Lora,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp
    )
)