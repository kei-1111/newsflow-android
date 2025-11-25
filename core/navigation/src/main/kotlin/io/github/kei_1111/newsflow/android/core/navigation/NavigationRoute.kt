package io.github.kei_1111.newsflow.android.core.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface NavigationRoute {
    @Serializable
    data object Home : NavigationRoute

    @Serializable
    data class Viewer(
        val articleId: String,
    ) : NavigationRoute
}