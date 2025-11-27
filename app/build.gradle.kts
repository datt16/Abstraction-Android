plugins {
  id("abstraction.android.application.compose")
  id("abstraction.android.hilt")
  alias(libs.plugins.detekt)
  alias(libs.plugins.kotlin.seriazation)
  alias(libs.plugins.roborazzi)
}

android {
  namespace = "io.github.datt16.abstraction"

  defaultConfig {
    applicationId = "io.github.datt16.abstraction"
    versionCode = 29360001
    versionName = "0.1"
  }

  buildFeatures {
    buildConfig = true
  }

  testOptions {
    unitTests {
      isIncludeAndroidResources = true
      all {
        it.systemProperties["robolectric.pixelCopyRenderMode"] = "hardware"
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

  implementation(libs.androidx.compose.ui.test.junit4)
  debugImplementation(libs.androidx.compose.ui.test.manifest)
  testImplementation(libs.robolectric)
  testImplementation(libs.espresso.core)

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