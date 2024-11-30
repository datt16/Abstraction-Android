package io.github.datt16.abstraction.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.datt16.abstraction.core.designsystem.AbstractionAppTheme
import io.github.datt16.abstraction.core.ext.black

@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  sampleKey: String = "datt16",
) {
  Column(modifier = modifier.fillMaxWidth()) {
    Text(
      style = AbstractionAppTheme.typography.displayMedium.black(),
      text = "Hello $sampleKey,\nThis is Home Screen",
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Preview
@Composable
private fun HomeScreenPreview() {
  AbstractionAppTheme {
    HomeScreen()
  }
}
