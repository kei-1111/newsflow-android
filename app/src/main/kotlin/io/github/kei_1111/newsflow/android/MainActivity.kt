package io.github.kei_1111.newsflow.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import io.github.kei_1111.newsflow.android.core.designsystem.theme.NewsflowAndroidTheme
import io.github.kei_1111.newsflow.android.core.ui.provider.DebouncedClickProvider
import io.github.kei_1111.newsflow.android.navigation.NewsflowNavDisplay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsflowAndroidTheme {
                DebouncedClickProvider {
                    Surface {
                        NewsflowNavDisplay()
                    }
                }
            }
        }
    }
}
