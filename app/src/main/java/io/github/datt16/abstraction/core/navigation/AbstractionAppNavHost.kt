package io.github.datt16.abstraction.core.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.datt16.abstraction.core.ui.AbstractionAppScaffold
import io.github.datt16.abstraction.feature.home.HomeScreen
import io.github.datt16.abstraction.feature.settings.SettingsScreen

@Composable
fun AbstractionAppNavHost(
  modifier: Modifier = Modifier,
) {
  val backStack = rememberNavBackStack(AbstractionAppDestinations.Home)

  AbstractionAppScaffold(
    currentRoute = backStack.lastOrNull() ?: AbstractionAppDestinations.Home,
    onNavigate = { destination ->
      if (backStack.lastOrNull() != destination) {
        backStack.clear()
        backStack.add(destination)
      }
    },
    modifier = modifier
  ) { paddingValues ->
    NavDisplay(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      backStack = backStack,
      onBack = { backStack.removeLastOrNull() },
      entryProvider = { key ->
        when (key) {
          AbstractionAppDestinations.Home -> NavEntry(key) {
            HomeScreen(sampleKey = "datt11")
          }
          AbstractionAppDestinations.Settings -> NavEntry(key) {
            SettingsScreen()
          }
          else -> NavEntry(key) {}
        }
      }
    )
  }
}
