package com.weatherapplication.feature.cityweather.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.weatherapplication.R
import com.weatherapplication.feature.cityweather.presentation.WeatherViewModel
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract

// val background = Color(0xFF24293E)
// val textColor = Color(0xFFF4F5FC)
// val element = Color(0xFF8EBBFF)
// val element2 = Color(0xFFCCCCCC)

val background = Color(0xFF171923)
val textColor = Color(0xFFFFFFFF)
val element = Color(0xFFF79E1B)
val element2 = Color(0xFFF79E1B)

// val background = Color(0xFF191D2B)
// val textColor = Color(0xFFF3E1D3)
// val element = Color(0xFFFB7023)
// val element2 = Color(0xFFFB7023)

// val background = Color(0xFF292A31)
// val textColor = Color(0xFFFFFFFF)
// val element = Color(0xFFFFB700)
// val element2 = Color(0xFF4871FF)

// val background = Color(0xFF5D5E67)
// val textColor = Color(0xFFFFFFFF)
// val element = Color(0xFFDDD8AE)
// val element2 = Color(0xFFDDD8AE)
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
    Column(Modifier.background(background)) {
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
                    .size(24.dp),
                colorFilter = ColorFilter.tint(element)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Kraków",
                fontSize = 24.sp,
                color = textColor

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
                color = textColor

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
                    color = textColor

                )
                Text(
                    text = "min 3°/ max 3°",
                    fontSize = 16.sp,
                    color = textColor

                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Divider(
                modifier = Modifier.background(element)
                    .fillMaxHeight().width(2.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.weather_icon),
                    contentDescription = null,
                    modifier = Modifier.height(56.dp).fillMaxWidth(),
                    colorFilter = ColorFilter.tint(textColor)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            ComposeCircularProgressBar(
                percentage = 0.80f,
                fillColor = element,
                backgroundColor = Color(android.graphics.Color.parseColor("#90A4AE")),
                strokeWidth = 5.dp
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(6) { index ->
                    Row(Modifier.height(IntrinsicSize.Min)) {
                        Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                            SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
                            if (index in (0..2)) {
                                Divider(
                                    color = element,
                                    modifier = Modifier
                                        .fillMaxWidth().padding(start = 8.dp, end = 8.dp)
                                        .height(1.dp)
                                )
                            }
                        }
                        if ((index + 1) % 3 != 0) {
                            Column {
                                Divider(
                                    color = element,
                                    modifier = Modifier
                                        .fillMaxHeight().padding(top = 8.dp, bottom = 8.dp)
                                        .width(1.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(14) {
                    SmallItemWeatherContent(title = "Sun", icon = R.drawable.sunny_24, text = "All 12")
                }
            }
        }
    }
}
