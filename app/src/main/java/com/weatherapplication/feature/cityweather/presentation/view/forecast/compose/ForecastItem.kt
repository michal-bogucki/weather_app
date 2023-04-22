package com.weatherapplication.feature.cityweather.presentation.view.forecast.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.weatherapplication.R
import com.weatherapplication.core.backgroundGray
import com.weatherapplication.core.base.ValueState
import com.weatherapplication.core.base.getValue
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor
import com.weatherapplication.feature.cityweather.presentation.TEMPERATURE
import com.weatherapplication.feature.cityweather.presentation.getListWidget
import com.weatherapplication.feature.cityweather.presentation.model.WeatherDisplayable
import com.weatherapplication.feature.cityweather.presentation.view.components.SmallItemWeatherContent
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun ForecastItemContent(weatherDisplayable: WeatherDisplayable) {
    Card(
        elevation = 20.dp,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, element),
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            Modifier
                .background(backgroundGray)
                .padding(4.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(16.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_round_location_on_24),
                    contentDescription = null,
                    modifier = Modifier
                        .size(12.dp),
                    colorFilter = ColorFilter.tint(element)

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weatherDisplayable.cityName,
                    fontSize = 12.sp,
                    color = textColor

                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = weatherDisplayable.date.toString(),
                    fontSize = 12.sp,
                    color = textColor

                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(5f)
                        .height(92.dp),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(getListWidget(weatherDisplayable).subList(0, 3)) { index, item ->
                        Row(Modifier.height(IntrinsicSize.Min)) {
                            Column(
                                Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SmallItemWeatherContent(
                                    title = item.second,
                                    icon = item.third,
                                    text = item.first
                                )
                            }
                            if ((index + 1) % 3 != 0) {
                                Column {
                                    Divider(
                                        color = element,
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(top = 8.dp, bottom = 8.dp)
                                            .width(1.dp)
                                    )
                                }
                            }
                        }
                    }
                }
                Column(
                    Modifier
                        .weight(1f)
                        .height(92.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        Modifier
                            .height(54.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val icon = weatherDisplayable.listHourTemperature.mapNotNull {
                            getValue(it.weatherIcon)
                        }.groupingBy { it }.eachCount().maxBy { it.value }
                        AsyncImage(
                            model = "https:" + icon.key,
                            contentDescription = null,
                            modifier = Modifier.height(56.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val valueMaxTemperature = getValue(
                            weatherDisplayable.maxTemperature,
                            TEMPERATURE
                        )
                        val valueMinTemperature = getValue(
                            weatherDisplayable.minTemperature,
                            TEMPERATURE
                        )
                        Text(
                            text = "$valueMinTemperature/$valueMaxTemperature",
                            fontSize = 12.sp,
                            color = textColor

                        )
                    }
                }
            }
            Column {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(weatherDisplayable.listHourTemperature) {
                        val temperature = getValue(it.temperature, TEMPERATURE)
                        val hour = getValue(it.hour)
                        val url = "https:" + getValue(it.weatherIcon)
                        SmallItemWeatherContent(title = hour, icon = url, text = temperature)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ForecastItemPreview() {
    ForecastItemContent(
        WeatherDisplayable(
            "Kielce",
            LocalDate.now(),
            LocalDateTime.now(),
            "PL",
            ValueState.initValueState(""),
            ValueState.initValueState(3.3),
            ValueState.initValueState(3.3),
            ValueState.initValueState(3.3),
            ValueState.initValueState(""),
            LocalTime.now(),
            LocalTime.now(),
            ValueState.initValueState(3.5),
            ValueState.initValueState(3),
            ValueState.initValueState(3.5),
            ValueState.initValueState(3.5),
            ValueState.initValueState(3.5),
            ValueState.initValueState(3.5),
            listOf()
        )
    )
}
