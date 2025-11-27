package io.github.datt16.abstraction.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

object AbstractionAppDestinations {
  @Serializable
  data object Home : NavKey
}
