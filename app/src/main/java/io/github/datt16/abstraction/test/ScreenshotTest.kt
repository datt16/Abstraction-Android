package io.github.datt16.abstraction.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.github.takahirom.roborazzi.captureRoboImage
import io.github.datt16.abstraction.core.designsystem.AbstractionAppTheme
import org.junit.Rule

abstract class ScreenshotTest {

  @get:Rule
  val composeTestRule = createComposeRule()

  fun captureScreenshot(
    name: String,
    content: @Composable () -> Unit,
  ) {
    composeTestRule.setContent {
      AbstractionAppTheme {
        content()
      }
    }

    composeTestRule
      .onRoot()
      .captureRoboImage("build/outputs/roborazzi/$name.png")
  }
}
