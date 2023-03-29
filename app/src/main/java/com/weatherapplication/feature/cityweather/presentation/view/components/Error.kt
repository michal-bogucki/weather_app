package com.weatherapplication.feature.cityweather.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.feature.choosesearchlocationorgetfromgps.LocationOptionButton

@Composable
fun ViewError(error: String, refreshData: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.background(background)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            composition = composition,
            progress = { progress },
        )
        Text(
            text = "Error \n $error",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,

                ),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LocationOptionButton(
            label = stringResource(R.string.refresh_data),
            onClick = { refreshData() },
            modifier = Modifier.padding(start = 168.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}
