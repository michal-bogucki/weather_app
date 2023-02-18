package com.weatherapplication.feature.cityweather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapplication.R

class SmallItemWeather

@Preview
@Composable
fun SmallItemWeatherPreview() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)

    ) {
        items(6) {

            SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
        }
    }
}

@Composable
fun SmallItemWeatherView() {
    // val value by viewModel.state.collectAsState()
}

@Composable
fun SmallItemWeatherContent(title: String, icon: Int, text: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White

        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = text,
            fontSize = 14.sp,
            color = Color.White

        )
    }
}
