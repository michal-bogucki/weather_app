package com.weatherapplication.feature.cityweather.presentation.view.weather

class WeatherNewViewModelTest {
//    @Test
//    fun `given when then`() = kotlinx.coroutines.test.runTest {
// //        Assertions.assertEquals(4, 2 + 3)
// //  4 shouldBe 2 + 3
//        // cityId = ""
// //        val savedStateHandle: SavedStateHandle = mockk {
// //            every {
// // //                operator fun <T> get(key: String): T? {
// // //                    @Suppress("UNCHECKED_CAST")
// // //                    return regular[key] as T?
// // //                }
// //                get<String>("cityId")
// //            } returns "123"
// //        }
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
// //        print(turbine.awaitItem())
// //
// //        turbine.cancelAndConsumeRemainingEvents()
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
