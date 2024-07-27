package ru.cityron.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.cityron.R

val RobotoMedium = Font(R.font.roboto_medium)
val RobotoRegular = Font(R.font.roboto_regular)

val FFRobotoMedium = FontFamily(RobotoMedium)
val FFRobotoRegular = FontFamily(RobotoRegular)

val Typography = Typography(
    defaultFontFamily = FFRobotoRegular,
    h1 = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    h2 = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 86.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    h3 = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 44.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    h4 = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    h5 = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.5.sp
    ),
    body1 = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    button = TextStyle(
        fontFamily = FFRobotoMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    caption = TextStyle(
        fontFamily = FFRobotoRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 19.sp,
        letterSpacing = 0.5.sp
    ),
)