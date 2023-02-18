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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.weatherapplication.R
import com.weatherapplication.feature.cityweather.presentation.WeatherViewModel
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract

@Preview
@Composable
fun WeatherPreview() {
    WeatherViewContent(value = WeatherContract.WeatherState())
}

@Composable
fun WeatherView(viewModel: WeatherViewModel, navController: NavController) {
    // val value by viewModel.state.collectAsState()
}

@Composable
fun WeatherViewContent(value: WeatherContract.WeatherState) {
    Column(Modifier.background(colorResource(R.color.background))) {
        Row(
            Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_menu_24),
                    contentDescription = null

                )
            }

            Spacer(modifier = Modifier.weight(1f))
            Column(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_round_search_24),
                    contentDescription = null
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_round_location_on_24),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kraków",
                fontSize = 24.sp,
                color = Color.White

            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sat, 3 Aug",
                fontSize = 12.sp,
                color = Color.White

            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            Modifier
                .fillMaxWidth().height(54.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "3°",
                    fontSize = 24.sp,
                    color = Color.White

                )
                Text(
                    text = "min 3°/ max 3°",
                    fontSize = 16.sp,
                    color = Color.White

                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Divider(modifier = Modifier.background(Color.Gray).fillMaxHeight().width(1.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weather_icon),
                    contentDescription = null,
                    modifier = Modifier.height(56.dp).fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            items(6) {
                Divider(modifier = Modifier.background(Color.Gray))
                SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}
