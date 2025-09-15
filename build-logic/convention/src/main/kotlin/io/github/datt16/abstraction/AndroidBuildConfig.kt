package io.github.datt16.abstraction

import org.gradle.api.JavaVersion

object AndroidBuildConfig {
    const val COMPILE_SDK = 36
    const val MIN_SDK = 29
    const val TARGET_SDK = 36

    val JAVA_VERSION = JavaVersion.VERSION_21

    const val APPLICATION_ID = "io.github.datt16.abstraction"
    const val VERSION_NAME = "1.0"
    const val VERSION_CODE = 1

    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
}