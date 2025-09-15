plugins {
  id("abstraction.android.application.compose")
  id("abstraction.android.hilt")
  alias(libs.plugins.detekt)
  alias(libs.plugins.kotlin.seriazation)
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
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.androidx.hilt.navigation.compose)
  implementation(libs.kotlinx.serialization)
  implementation(libs.kotlinx.coroutines)
  implementation(libs.timber)

  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)

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