package io.github.datt16.abstraction.feature.settings

data class SettingsUiState(
  val versionName: String,
  val versionCode: Int,
  val packageName: String,
) {
  companion object {
    val Dummy = SettingsUiState(
      versionName = "0.0.0",
      versionCode = 0,
      packageName = "com.example.app"
    )
  }
}
