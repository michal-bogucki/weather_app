package com.weatherapplication.feature.choosesearchlocationorgetfromgps

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationScreen(navController: NavController) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Choose location option",
            style = MaterialTheme.typography.h5,
        )
        Spacer(modifier = Modifier.height(32.dp))
        LocationOptionButton(
            label = "Get location from GPS",
            iconPainter = rememberImagePainter("https://www.example.com/gps_icon.png"),
            onClick = {
                if (locationPermissionsState.allPermissionsGranted) {
                    navController.navigate(ChooseGetCityFragmentDirections.actionChooseGetCityFragmentToWeatherNewFragment(null))
                } else {
                    locationPermissionsState.launchMultiplePermissionRequest()
                }
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        LocationOptionButton(
            label = "Search for location",
            iconPainter = rememberImagePainter("https://www.example.com/search_icon.png"),
            onClick = { navController.navigate(ChooseGetCityFragmentDirections.actionChooseGetCityFragmentToSearchCityFragment()) },
        )
    }
}

@Composable
fun LocationOptionButton(
    label: String,
    iconPainter: Painter,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primary,
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = iconPainter,
                contentDescription = null,
                modifier = Modifier.size(150.dp),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.button,
                color = MaterialTheme.colors.onPrimary,
            )
        }
    }
}

@Preview
@Composable
fun showLocationScreen() {
//    LocationScreen(findNavController())
}
