package io.github.datt16.abstraction.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey
import io.github.datt16.abstraction.R
import io.github.datt16.abstraction.core.designsystem.AbstractionAppTheme
import io.github.datt16.abstraction.core.navigation.AbstractionAppDestinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AbstractionAppScaffold(
  currentRoute: NavKey ,
  onNavigate: (NavKey) -> Unit,
  modifier: Modifier = Modifier,
  content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
  Scaffold(
    modifier = modifier.fillMaxSize(),
    topBar = {
      TopAppBar(
        title = {
          Text("Abstraction App")
        }
      )
    },
    bottomBar = {
      NavigationBar {
        NavigationBarItem(
          icon = {
            Icon(
              painter = painterResource(R.drawable.ic_home),
              contentDescription = "Home"
            )
          },
          label = { Text("Home") },
          selected = currentRoute == AbstractionAppDestinations.Home,
          onClick = { onNavigate(AbstractionAppDestinations.Home) }
        )
        NavigationBarItem(
          icon = {
            Icon(
              painter = painterResource(R.drawable.ic_settings),
              contentDescription = "Settings"
            )
          },
          label = { Text("Settings") },
          selected = currentRoute == AbstractionAppDestinations.Settings,
          onClick = { onNavigate(AbstractionAppDestinations.Settings) }
        )
      }
    }
  ) {
    content(it)
  }
}

@Preview
@Composable
private fun AbstractionAppScaffoldPreview() {
  AbstractionAppTheme {
    AbstractionAppScaffold(
      currentRoute = AbstractionAppDestinations.Home,
      onNavigate = {}
    ) {
      Column {
        Text("Hello, This is sample text")
      }
    }
  }
}
