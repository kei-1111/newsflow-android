package io.github.kei_1111.newsflow.android.feature.viewer.fixture

import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.viewer.ViewerState

object ViewerTestFixtures {

    // === State Factory Methods ===

    fun createInitState(): ViewerState = ViewerState.Init

    fun createLoadingState(): ViewerState = ViewerState.Loading

    fun createStableState(
        viewingArticle: Article = createArticle(),
        isWebViewLoading: Boolean = false
    ): ViewerState.Stable = ViewerState.Stable(
        viewingArticle = viewingArticle,
        isWebViewLoading = isWebViewLoading
    )

    fun createWebViewLoadingState(
        viewingArticle: Article = createArticle()
    ): ViewerState.Stable = createStableState(
        viewingArticle = viewingArticle,
        isWebViewLoading = true
    )

    fun createErrorState(
        error: NewsflowError = NewsflowError.InternalError.ArticleNotFound("Article not found")
    ): ViewerState.Error = ViewerState.Error(error = error)

    fun createNetworkErrorState(): ViewerState.Error = ViewerState.Error(
        error = NewsflowError.NetworkError.NetworkFailure("Network failure")
    )

    fun createServerErrorState(): ViewerState.Error = ViewerState.Error(
        error = NewsflowError.NetworkError.ServerError("Internal Server Error")
    )

    // === Data Factory Methods ===

    fun createArticle(
        id: String = "test_article_1",
        source: String = "Test Source",
        author: String = "Test Author",
        title: String = "Test Article Title",
        description: String = "Test article description",
        url: String = "https://example.com/article",
        imageUrl: String = "https://example.com/image.png",
        publishedAt: Long = 1700000000000L
    ): Article = Article(
        id = id,
        source = source,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imageUrl,
        publishedAt = publishedAt
    )
}