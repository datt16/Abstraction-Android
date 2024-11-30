package io.github.datt16.abstraction.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import io.github.datt16.abstraction.core.designsystem.AbstractionAppTheme
import io.github.datt16.abstraction.core.ext.black

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AbstractionAppTheme {
                Text(
                    text = "Hello, World!",
                    style = AbstractionAppTheme.typography.bodyLarge.black()
                )
            }
        }
    }
}