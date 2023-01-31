package com.weatherapplication.feature.searchcity.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.weatherapplication.R
import com.weatherapplication.feature.searchcity.presentation.SearchCityViewModel
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable

@Composable
fun SearchView(value: SearchCityContract.SearchCityState, viewModel: SearchCityViewModel, openFragment: (SearchCityDisplayable) -> Unit) {
    Column(Modifier.background(colorResource(R.color.background))) {
        Text(
            text = "Search city",
            style = typography.h6,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp)
                .fillMaxWidth()
        )
        SearchLabel(value.searchText, viewModel::search)
        CityList(getList(value.actualSearchCityList, value.historySearchCityList), openFragment)
    }
}

fun getList(actualSearchCityList: List<SearchCityDisplayable>, historySearchCityList: List<SearchCityDisplayable>): List<SearchCityDisplayable> {
    return actualSearchCityList.ifEmpty { historySearchCityList }
}

@Composable
fun SearchLabel(text: String, onSearchQueryChanged: (query: String) -> Unit) {
    var searchQuery by remember { mutableStateOf(TextFieldValue(text)) }
    TextField(
        value = searchQuery,
        onValueChange = { value ->
            searchQuery = value
            onSearchQueryChanged(value.text)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            Icon(Icons.Filled.Search, "", tint = White)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            backgroundColor = colorResource(R.color.search_background),
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun CityList(botList: List<SearchCityDisplayable>, onClick: (SearchCityDisplayable) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = Modifier
    ) {
        items(botList) { item: SearchCityDisplayable ->
            CityItem(item = item, onClick = onClick)
        }
    }
}

@Composable
fun CityItem(item: SearchCityDisplayable, onClick: (SearchCityDisplayable) -> Unit) {
    val myFont = FontFamily(
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_regular, FontWeight.Normal)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { onClick(item) }
    ) {
        IconList(icon = if (item.isHistory) R.drawable.ic_round_history_24 else R.drawable.ic_round_location_on_24)
        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f)

        ) {
            Text(
                text = item.cityName,
                style = typography.subtitle2,
                color = Color.White,
                fontFamily = myFont,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = item.countryName,
                style = typography.subtitle2,
                color = Color.Gray,
                fontFamily = myFont,
                fontWeight = FontWeight.Normal
            )
        }

        IconList(icon = R.drawable.ic_round_keyboard_arrow_right_24)
    }
}

@Composable
fun IconList(modifier: Modifier = Modifier, icon: Int) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier
            .size(40.dp, 40.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small)
    )
}

@Preview
@Composable
fun ScreenPreview() {
}
