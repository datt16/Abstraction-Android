package io.github.datt16.abstraction.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.datt16.abstraction.feature.home.HomeScreen

@Composable
fun AbstractionAppNavHost(
  modifier: Modifier = Modifier,
) {
  val backStack = rememberNavBackStack(AbstractionAppDestinations.Home)

  NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = { key ->
      NavEntry(key) {
        HomeScreen(
          modifier = modifier,
          sampleKey = "datt11"
        )
      }
    }
  )
}
