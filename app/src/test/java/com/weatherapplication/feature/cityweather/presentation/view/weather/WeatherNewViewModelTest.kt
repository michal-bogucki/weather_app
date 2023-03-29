package com.weatherapplication.feature.cityweather.presentation.view.weather

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.testIn
import com.weatherapplication.core.base.Resource
import com.weatherapplication.feature.cityweather.domain.model.WeatherData
import com.weatherapplication.feature.cityweather.domain.usecase.GetTodayWeatherUseCase
import com.weatherapplication.feature.cityweather.presentation.model.WeatherContract
import com.weatherapplication.feature.searchcity.domain.model.SearchCity
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class WeatherNewViewModelTest {
//    @Test
//    fun `given when then`() = kotlinx.coroutines.test.runTest {
////        Assertions.assertEquals(4, 2 + 3)
////  4 shouldBe 2 + 3
//        // cityId = ""
////        val savedStateHandle: SavedStateHandle = mockk {
////            every {
//// //                operator fun <T> get(key: String): T? {
//// //                    @Suppress("UNCHECKED_CAST")
//// //                    return regular[key] as T?
//// //                }
////                get<String>("cityId")
////            } returns "123"
////        }
//
//        val savedStateHandle = SavedStateHandle().apply {
//            set("cityId", "123")
//        }
//        val city = SearchCity("q", "w", 1.1, 1.2, false)
//
//        val getTodayWeatherUseCase: GetTodayWeatherUseCase = GetTodayWeatherUseCase(
//            mockk {
//                coEvery {
//                    getCity("123")
//                } returns city
//                every {
//                    getWeatherToday(city)
//                } returns flowOf(Resource.error<WeatherData>(""))
//            },
//        )
//        // val turbine = getTodayWeatherUseCase.flow.testIn(this)
//        // getTodayWeatherUseCase(GetTodayWeatherUseCase.Params("123"))
//
////        print(turbine.awaitItem())
////
////        turbine.cancelAndConsumeRemainingEvents()
//
//        val weatherNewViewModel = WeatherNewViewModel(savedStateHandle, getTodayWeatherUseCase)
//
//        val turbine = weatherNewViewModel.state.testIn(this)
//        val firstState = turbine.awaitItem()
//        Assertions.assertEquals(WeatherContract.Loading, firstState)
//        val secondState = turbine.awaitItem()
//        Assertions.assertEquals(WeatherContract.Error(""), secondState)
//        turbine.cancelAndConsumeRemainingEvents()
//
//        // Assertions.assertEquals("123", savedStateHandle["cityId"]!!)
//
//        // viewModel init
//
//        // Resource.Error
//    }
}
