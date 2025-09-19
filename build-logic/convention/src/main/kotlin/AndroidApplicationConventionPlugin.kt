import com.android.build.api.dsl.ApplicationExtension
import io.github.datt16.abstraction.AndroidBuildConfig
import io.github.datt16.abstraction.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidBuildConfig.TARGET_SDK

                buildTypes {
                    debug {
                        applicationIdSuffix = ".debug"
                        isMinifyEnabled = false
                    }
                    release {
                        applicationIdSuffix = ".release"
                        isMinifyEnabled = true
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}