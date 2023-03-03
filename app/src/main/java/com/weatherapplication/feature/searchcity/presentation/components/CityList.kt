package com.weatherapplication.feature.searchcity.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.core.element
import com.weatherapplication.feature.searchcity.presentation.SearchCityFragmentDirections
import com.weatherapplication.feature.searchcity.presentation.SearchCityViewModel
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityContract
import com.weatherapplication.feature.searchcity.presentation.model.SearchCityDisplayable

@Preview
@Composable
fun ScreenPreview() {
    SearchViewContent(SearchCityContract.SearchCityState.Empty, {}, {}, {})
}

@Composable
fun SearchView(viewModel: SearchCityViewModel, navController: NavController) {
    val value by viewModel.state.collectAsState()
    SearchViewContent(value = value, click = { searchCityDisplayable ->
        viewModel.saveUserChoose(searchCityDisplayable)
        navController.navigate(SearchCityFragmentDirections.actionSearchCityFragmentToWeatherFragment(searchCityDisplayable.id))
    }, search = viewModel::search, delete = viewModel::deleteUserChoose)
}

@Composable
fun SearchViewContent(
    value: SearchCityContract.SearchCityState,
    click: (SearchCityDisplayable) -> Unit,
    search: (String) -> Unit,
    delete: (SearchCityDisplayable) -> Unit,
) {
    Column(Modifier.background(background)) {
        Text(
            text = "Search city",
            style = typography.h6,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp)
                .fillMaxWidth(),
        )
        SearchLabel(value.searchText, search)
        CityList(value.actualSearchCityList, click, delete)
        if (value.isHistoryList) {
            Spacer(modifier = Modifier.height(32.dp))
            val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.search_file))
            val progress by animateLottieCompositionAsState(composition)
            LottieAnimation(
                modifier = Modifier.fillMaxWidth().height(120.dp),
                composition = composition,
                progress = { progress },
            )
        }
    }
}

@Composable
fun SearchLabel(text: String, onSearchQueryChanged: (query: String) -> Unit) {
    TextField(
        value = text,
        onValueChange = { value ->
            onSearchQueryChanged(value)
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
            backgroundColor = element.copy(alpha = 0.5f),
            focusedIndicatorColor = Color.Transparent,
        ),
    )
}

@Composable
fun CityList(botList: List<SearchCityDisplayable>, onClick: (SearchCityDisplayable) -> Unit, delete: (SearchCityDisplayable) -> Unit) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        items(botList) { item: SearchCityDisplayable ->
            CityItem(item = item, onClick = onClick, delete = delete)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityItem(item: SearchCityDisplayable, onClick: (SearchCityDisplayable) -> Unit, delete: (SearchCityDisplayable) -> Unit) {
    val myFont = FontFamily(
        Font(R.font.rubik_medium, FontWeight.Medium),
        Font(R.font.rubik_regular, FontWeight.Normal),
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .combinedClickable(
                onClick = { onClick(item) },
                onLongClick = { delete(item) },
            ),

    ) {
        IconItemList(icon = if (item.isHistory) R.drawable.ic_round_history_24 else R.drawable.ic_round_location_on_24)
        Column(
            modifier = Modifier
                .align(CenterVertically)
                .weight(1f),

        ) {
            Text(
                text = item.cityName,
                style = typography.subtitle2,
                color = Color.White,
                fontFamily = myFont,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = item.countryName,
                style = typography.subtitle2,
                color = Color.Gray,
                fontFamily = myFont,
                fontWeight = FontWeight.Normal,
            )
        }

        IconItemList(icon = R.drawable.ic_round_keyboard_arrow_right_24)
    }
}

@Composable
fun IconItemList(modifier: Modifier = Modifier, icon: Int) {
    Image(
        painter = painterResource(id = icon),
        contentDescription = null,
        modifier = modifier
            .size(40.dp)
            .padding(8.dp)
            .clip(MaterialTheme.shapes.small),
    )
}
