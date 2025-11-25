package io.github.kei_1111.newsflow.android.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Viewer(
    val articleId: String,
) : NavKey
