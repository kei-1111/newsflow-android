package io.github.kei_1111.newsflow.android.feature.search.fixture

import io.github.kei_1111.newsflow.library.core.model.Article
import io.github.kei_1111.newsflow.library.core.model.NewsflowError
import io.github.kei_1111.newsflow.library.feature.search.SearchState
import io.github.kei_1111.newsflow.library.feature.search.model.DateRangePreset
import io.github.kei_1111.newsflow.library.feature.search.model.SearchLanguage
import io.github.kei_1111.newsflow.library.feature.search.model.SearchOptions
import io.github.kei_1111.newsflow.library.feature.search.model.SortBy

object SearchTestFixtures {

    fun createStableState(
        query: String = "",
        isSearching: Boolean = false,
        articles: List<Article> = emptyList(),
        selectedArticle: Article? = null,
        searchOptions: SearchOptions = SearchOptions(),
        isOptionsSheetVisible: Boolean = false
    ) = SearchState.Stable(
        query = query,
        isSearching = isSearching,
        articles = articles,
        selectedArticle = selectedArticle,
        searchOptions = searchOptions,
        isOptionsSheetVisible = isOptionsSheetVisible
    )

    fun createSearchingState(
        query: String = "test query"
    ) = SearchState.Stable(
        query = query,
        isSearching = true,
        articles = emptyList(),
        selectedArticle = null
    )

    fun createEmptyResultState(
        query: String = "no results query"
    ) = SearchState.Stable(
        query = query,
        isSearching = false,
        articles = emptyList(),
        selectedArticle = null
    )

    fun createWithResultsState(
        query: String = "test query",
        articles: List<Article> = createArticles()
    ) = SearchState.Stable(
        query = query,
        isSearching = false,
        articles = articles,
        selectedArticle = null
    )

    fun createErrorState(
        error: NewsflowError = NewsflowError.NetworkError.NetworkFailure("Network error")
    ) = SearchState.Error(error = error)

    fun createNetworkErrorState() = createErrorState(
        error = NewsflowError.NetworkError.NetworkFailure("Network failure")
    )

    fun createServerErrorState() = createErrorState(
        error = NewsflowError.NetworkError.ServerError("Internal Server Error")
    )

    fun createInternalErrorState() = createErrorState(
        error = NewsflowError.InternalError.InvalidParameter("Invalid parameter")
    )

    fun createWithSelectedArticleState(
        query: String = "test query",
        article: Article = createArticle()
    ) = createWithResultsState(query = query).let {
        SearchState.Stable(
            query = it.query,
            isSearching = it.isSearching,
            articles = it.articles,
            selectedArticle = article,
            searchOptions = it.searchOptions,
            isOptionsSheetVisible = it.isOptionsSheetVisible
        )
    }

    fun createWithOptionsSheetVisibleState(
        query: String = "test query"
    ) = SearchState.Stable(
        query = query,
        isSearching = false,
        articles = createArticles(),
        selectedArticle = null,
        searchOptions = SearchOptions(),
        isOptionsSheetVisible = true
    )

    fun createWithCustomOptionsState(
        sortBy: SortBy = SortBy.RELEVANCY,
        dateRange: DateRangePreset = DateRangePreset.ALL,
        language: SearchLanguage = SearchLanguage.ALL
    ) = SearchState.Stable(
        query = "test query",
        isSearching = false,
        articles = createArticles(),
        selectedArticle = null,
        searchOptions = SearchOptions(
            sortBy = sortBy,
            dateRangePreset = dateRange,
            language = language
        ),
        isOptionsSheetVisible = false
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
}
