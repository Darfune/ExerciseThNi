package com.example.threenitasapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.threenitasapp.R

//private val HelvetiCaneue = FontFamily(
//    Font(R.font.helvetica_neue_regular, FontWeight(400)),
//    Font(R.font.helvetica_neue_regular, FontWeight.Bold),
//
//
//    )
//private val SFProText_SemiBold = FontFamily(
//    Font(R.font.sfprotext_semibold, FontWeight(600)),
//)
//private val FiraSans = FontFamily(
//    Font(R.font.firasans_medium, FontWeight(500))
//)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily.Default
    ),
//    bodyLarge = TextStyle(
//        fontFamily = HelvetiCaneue,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 18.sp,
//        letterSpacing = 0.5.sp
//    ),
//    bodyMedium = TextStyle(
//        fontFamily = HelvetiCaneue,
//        fontWeight = FontWeight.Normal,
//        fontSize = 16.sp,
//        lineHeight = 17.sp,
//        letterSpacing = 0.32.sp
//    ),
//    bodySmall = TextStyle(
//        fontFamily = HelvetiCaneue,
//        fontWeight = FontWeight.Normal,
//        fontSize = 13.sp,
//        lineHeight = 17.sp,
//        letterSpacing = 0.26.sp
//    ),
//    titleMedium = TextStyle(
//        fontFamily = HelvetiCaneue,
//        fontWeight = FontWeight.Normal,
//        fontSize = 18.sp,
//        lineHeight = 26.sp,
//        letterSpacing = 0.18.sp
//    ),
//    titleLarge = TextStyle(
//        fontFamily = HelvetiCaneue,
//        fontWeight = FontWeight.Bold,
//        fontSize = 24.sp,
//        lineHeight = 41.sp,
//        letterSpacing = 0.48.sp
//    ),
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */

)
