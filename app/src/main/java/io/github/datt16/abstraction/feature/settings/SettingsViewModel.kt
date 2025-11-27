package io.github.datt16.abstraction.feature.settings

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.datt16.abstraction.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
  @ApplicationContext private val context: Context,
) : ViewModel() {

  private val _uiState = MutableStateFlow(
    SettingsUiState(
      versionName = BuildConfig.VERSION_NAME,
      versionCode = BuildConfig.VERSION_CODE,
      packageName = context.packageName
    )
  )
  val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()
}
