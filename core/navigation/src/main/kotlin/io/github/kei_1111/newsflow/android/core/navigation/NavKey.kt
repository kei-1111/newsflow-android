package io.github.kei_1111.newsflow.android.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavKey {
    @Serializable
    data object Home : NavKey

    @Serializable
    data class Viewer(
        val articleId: String,
    ) : NavKey
}