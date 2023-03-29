package com.weatherapplication.feature.choosesearchlocationorgetfromgps

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.weatherapplication.R
import com.weatherapplication.core.background
import com.weatherapplication.core.element
import com.weatherapplication.core.textColor

@Preview
@Composable
fun ShowLocationScreen() {
    LocationScreenComponent({}, {})
}

@Composable
fun LocationScreen(navController: NavController) {
    LocationScreenComponent(
        openSearchFragment = {
            navController.navigate(
                ChooseGetCityFragmentDirections.actionChooseGetCityFragmentToSearchCityFragment(),
                NavOptions.Builder()
                    .setPopUpTo(R.id.chooseGetCityFragment, true)
                    .setLaunchSingleTop(true)
                    .build(),
            )
        },
        openWeatherFragment = {
            navController.navigate(
                ChooseGetCityFragmentDirections.actionChooseGetCityFragmentToWeatherNewFragment(null),
                NavOptions.Builder()
                    .setPopUpTo(R.id.chooseGetCityFragment, true)
                    .setLaunchSingleTop(true)
                    .build(),
            )
        },
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreenComponent(openSearchFragment: () -> Unit, openWeatherFragment: () -> Unit) {
    Column(
        modifier = Modifier
            .background(background)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val locationPermissionsState = rememberMultiplePermissionsState(
            listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ),
        )
        val counterState = remember { mutableStateOf(0) }
        if (locationPermissionsState.allPermissionsGranted) {
            if (counterState.value == 1) {
                openWeatherFragment()
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.choose_location),
            fontSize = 18.sp,
            color = textColor,
        )
        Spacer(modifier = Modifier.weight(1f))
        LocationOptionButton(
            label = stringResource(R.string.get_gps),
            onClick = {
                if (locationPermissionsState.allPermissionsGranted) {
                    openWeatherFragment()
                } else {
                    counterState.value = 1
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            },
            modifier = Modifier.padding(start = 48.dp),
        )
        Spacer(modifier = Modifier.height(16.dp))
        LocationOptionButton(
            label = stringResource(R.string.search_location),
            onClick = { openSearchFragment() },
            modifier = Modifier.padding(start = 48.dp),
        )
        Spacer(modifier = Modifier.weight(1f))
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.beautiful_city))
        val progress by animateLottieCompositionAsState(composition)
        LottieAnimation(
            modifier = Modifier
                .weight(6f),
            composition = composition,
            progress = { progress },
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun LocationOptionButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp),
        onClick = onClick,
        shape = RoundedCornerShape(bottomStart = 8.dp, topStart = 8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = element,
        ),
    ) {
        Row {
            Text(
                text = label,
                color = background,
            )
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.ic_round_keyboard_arrow_right_24),
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)

            )
        }
    }
}
