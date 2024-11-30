import com.android.sdklib.AndroidVersion.VersionCodes

plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.compose.compiler)
  alias(libs.plugins.detekt)
}

android {
  namespace = "io.github.datt16.abstraction"
  compileSdk = VersionCodes.VANILLA_ICE_CREAM

  defaultConfig {
    applicationId = "io.github.datt16.abstraction"
    minSdk = VersionCodes.Q
    targetSdk = VersionCodes.VANILLA_ICE_CREAM
    versionCode = 29350001
    versionName = "0.1"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
      proguardFiles(
        getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
      )
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
  kotlinOptions {
    jvmTarget = "17"
  }
}

dependencies {
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.activity.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)

  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.compose.material3)
  implementation(libs.androidx.compose.foundation)

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
