package io.github.datt16.abstraction.sample

import io.github.datt16.abstraction.feature.home.HomeScreen
import io.github.datt16.abstraction.feature.home.HomeViewModel
import io.github.datt16.abstraction.test.ScreenshotTest
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

// VRTのサンプル
@RunWith(RobolectricTestRunner::class)
class SampleScreenVrt : ScreenshotTest() {

  @Test
  fun homeScreenScreenshot() {
    captureScreenshot("homeScreenshotTest") {
      HomeScreen(
        viewModel = HomeViewModel()
      )
    }
  }
}
