package io.github.kei_1111.newsflow.android.feature.home.fixture

import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsCategory
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.home.HomeState

object HomeTestFixtures {

    fun createStableState(
        isLoading: Boolean = false,
        isRefreshing: Boolean = false,
        currentCategory: NewsCategory = NewsCategory.GENERAL,
        articlesByCategory: Map<NewsCategory, List<Article>> = mapOf(
            currentCategory to createArticles()
        ),
        selectedArticle: Article? = null
    ) = HomeState.Stable(
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        currentNewsCategory = currentCategory,
        articlesByCategory = articlesByCategory,
        selectedArticle = selectedArticle
    )

    fun createLoadingState(
        currentCategory: NewsCategory = NewsCategory.GENERAL
    ) = HomeState.Stable(
        isLoading = true,
        isRefreshing = false,
        currentNewsCategory = currentCategory,
        articlesByCategory = emptyMap(),
        selectedArticle = null
    )

    fun createRefreshingState(
        currentCategory: NewsCategory = NewsCategory.GENERAL,
        articlesByCategory: Map<NewsCategory, List<Article>> = mapOf(
            currentCategory to createArticles()
        )
    ) = HomeState.Stable(
        isLoading = false,
        isRefreshing = true,
        currentNewsCategory = currentCategory,
        articlesByCategory = articlesByCategory,
        selectedArticle = null
    )

    fun createEmptyState(
        currentCategory: NewsCategory = NewsCategory.GENERAL
    ) = HomeState.Stable(
        isLoading = false,
        isRefreshing = false,
        currentNewsCategory = currentCategory,
        articlesByCategory = mapOf(currentCategory to emptyList()),
        selectedArticle = null
    )

    fun createErrorState(
        error: NewsflowError = NewsflowError.NetworkError.NetworkFailure("Network error")
    ) = HomeState.Error(error = error)

    fun createNetworkErrorState() = createErrorState(
        error = NewsflowError.NetworkError.NetworkFailure("Network failure")
    )

    fun createServerErrorState() = createErrorState(
        error = NewsflowError.NetworkError.ServerError("Internal Server Error")
    )

    fun createArticles(count: Int = 5): List<Article> =
        List(count) { index ->
            createArticle(
                id = "article_$index",
                title = "Test Article $index",
                description = "Description for article $index"
            )
        }

    fun createArticle(
        id: String = "test_article_1",
        source: String = "Test Source",
        author: String = "Test Author",
        title: String = "Test Article Title",
        description: String = "Test article description",
        url: String = "https://example.com/article",
        imageUrl: String = "https://example.com/image.png",
        publishedAt: Long = 1700000000000L
    ) = Article(
        id = id,
        source = source,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt
    )

    fun createStateWithSelectedArticle(
        article: Article = createArticle()
    ) = createStableState(selectedArticle = article)

    fun createAllCategoriesState(): HomeState.Stable {
        val articlesByCategory = NewsCategory.entries.associateWith { category ->
            createArticles(count = 3).mapIndexed { index, article ->
                article.copy(
                    id = "${category.name}_article_$index",
                    title = "${category.name} Article $index"
                )
            }
        }
        return HomeState.Stable(
            isLoading = false,
            isRefreshing = false,
            currentNewsCategory = NewsCategory.GENERAL,
            articlesByCategory = articlesByCategory,
            selectedArticle = null
        )
    }
}
