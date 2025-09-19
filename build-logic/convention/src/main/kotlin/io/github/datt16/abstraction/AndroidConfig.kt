package io.github.datt16.abstraction

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = AndroidBuildConfig.COMPILE_SDK

        defaultConfig {
            minSdk = AndroidBuildConfig.MIN_SDK
            testInstrumentationRunner = AndroidBuildConfig.TEST_INSTRUMENTATION_RUNNER
        }

        compileOptions {
            sourceCompatibility = AndroidBuildConfig.JAVA_VERSION
            targetCompatibility = AndroidBuildConfig.JAVA_VERSION
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    configureKotlin()
}

internal fun Project.configureKotlin() {
    extensions.getByType<KotlinAndroidProjectExtension>().apply {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
            freeCompilerArgs.addAll(
                listOf(
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                )
            )
        }
    }
}