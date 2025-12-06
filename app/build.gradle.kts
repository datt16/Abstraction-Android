@file:OptIn(ExperimentalRoborazziApi::class)

import com.github.takahirom.roborazzi.ExperimentalRoborazziApi

plugins {
  id("abstraction.android.application.compose")
  id("abstraction.android.hilt")
  alias(libs.plugins.detekt)
  alias(libs.plugins.kotlin.seriazation)
  alias(libs.plugins.roborazzi)
}

roborazzi {
  generateComposePreviewRobolectricTests {
    enable = true
    packages = listOf("io.github.datt16.abstraction.feature")
    robolectricConfig = mapOf(
      // Robolectricが安定対応しているAPIに固定（CIでの互換性確保）
      "sdk" to "[34]",
      "qualifiers" to "RobolectricDeviceQualifiers.Pixel5",
    )
    includePrivatePreviews = true
  }
}

android {
  namespace = "io.github.datt16.abstraction"

  defaultConfig {
    applicationId = "io.github.datt16.abstraction"
    versionCode = 29360001
    versionName = "0.1"
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildFeatures {
    buildConfig = true
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      all {
        it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
        it.jvmArgs("-noverify")
      }
    }
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.navigation3.runtime)
  implementation(libs.androidx.navigation3.ui)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.kotlinx.serialization)
  implementation(libs.kotlinx.coroutines)
  implementation(libs.timber)

  implementation(libs.dagger.hilt.android)
  testImplementation(libs.dagger.hilt.android.testing)
  ksp(libs.dagger.hilt.compiler)

  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.foundation)
  implementation(libs.androidx.compose.ui.tooling.prview)
  debugImplementation(libs.androidx.compose.ui.tooling)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)

  // Compose UI テストはユニットテストで使用するため testImplementation に
  testImplementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  testImplementation(libs.robolectric)
  testImplementation(libs.espresso.core)

  // TODO: テストツール定義用のモジュールをappモジュールから独立させる
  testImplementation(libs.roborazzi.core)
  testImplementation(libs.roborazzi.compose)
  testImplementation(libs.roborazzi.compose.preview.scanner)
  testImplementation(libs.roborazzi.junit.rule)
  testImplementation(libs.composable.preview.scanner)

  detektPlugins(libs.detekt.formatting)
  detektPlugins(libs.detekt.compose.rules)
}

detekt {
  parallel = true
  toolVersion = libs.versions.detekt.get()
  config.setFrom(file("${rootProject.projectDir}/config/detekt/detekt.yml"))
  buildUponDefaultConfig = true
  autoCorrect = true
}
