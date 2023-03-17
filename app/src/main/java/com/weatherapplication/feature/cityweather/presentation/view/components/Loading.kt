package com.weatherapplication.feature.cityweather.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weatherapplication.R
import com.weatherapplication.core.background

@Composable
fun ViewLoading() {
    Column(
        modifier = Modifier.background(background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.weather_lading))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Please wait...",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,

            ),
        )
    }
}
