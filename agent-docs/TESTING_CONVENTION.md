# Testing Convention

このドキュメントはプロジェクト全体のUIテスト方針を定義します。

## 1. テスト戦略の概要

### newsflow-androidで行うテスト

このプロジェクトはUI層のみを担当するため、以下のテストを実施します。

| テスト種別 | 目的 | 実行環境 | 使用ライブラリ |
|-----------|------|---------|---------------|
| **UIコンポーネントテスト** | 画面の状態表示・Intent発行の検証 | JVM (Robolectric) | ComposeTestRule |
| **スクリーンショットテスト** | UIの視覚的リグレッション検出 | JVM (Robolectric) | Roborazzi |

**注意**: ビジネスロジックのテストは外部KMPライブラリ（newsflow-library）側で行います。

### テストランナー

プロジェクト共通の`NewsflowTestRunner`を使用します。

```kotlin
// core/test/.../NewsflowTestRunner.kt
class NewsflowTestRunner(testClass: Class<*>) : RobolectricTestRunner(testClass) {
    override fun buildGlobalConfig(): Config {
        return Config.Builder()
            .setSdk(36)
            .setQualifiers("w400dp-h800dp-xxhdpi")
            .build()
    }
}
```

**テストクラスの基本構成:**

```kotlin
@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class MyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    // ...
}
```

**注意**: `@GraphicsMode`はRunner経由で設定できないため、各テストクラスで指定が必要です。

## 2. テスト設計原則

### 2.1 Robot Pattern

テストの可読性と保守性を高めるため、**Robot Pattern** を採用します。

#### 概念

```kotlin
// ❌ BAD: テストにUI操作の詳細が露出
@Test
fun loginWithValidCredentials() {
    composeTestRule.onNodeWithTag("email_field").performTextInput("user@example.com")
    composeTestRule.onNodeWithTag("password_field").performTextInput("password")
    composeTestRule.onNodeWithTag("login_button").performClick()
    composeTestRule.onNodeWithTag("home_screen").assertIsDisplayed()
}

// ✅ GOOD: Robot Patternで抽象化
@Test
fun loginWithValidCredentials() {
    loginRobot
        .enterEmail("user@example.com")
        .enterPassword("password")
        .clickLogin()
        .verifyHomeScreenDisplayed()
}
```

#### Robot実装規約

```kotlin
/**
 * 画面ごとにRobotクラスを作成
 * - 命名: {ScreenName}Robot
 * - 配置: {feature}/src/test/kotlin/.../robot/
 */
class HomeScreenRobot(
    private val composeTestRule: ComposeContentTestRule
) {
    // === Action Methods ===
    // ユーザー操作をシミュレート
    // 戻り値は自身のインスタンス（メソッドチェーン用）

    fun clickRetryButton(): HomeScreenRobot = apply {
        ShadowSystemClock.advanceBy(Duration.ofMillis(600))
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.ActionButton).performClick()
    }

    fun selectCategory(category: NewsCategory): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.TabRow.tab(category)).performClick()
    }

    // === Verification Methods ===
    // UI状態の検証
    // 戻り値は自身のインスタンス（メソッドチェーン用）

    fun verifyErrorContentDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(DesignSystemTestTags.ErrorContent.Root).assertIsDisplayed()
    }

    fun verifyLoadingDisplayed(): HomeScreenRobot = apply {
        composeTestRule.onTag(HomeTestTags.Loading.Root).assertIsDisplayed()
    }

    // === Setup Methods ===
    // テスト対象のComposableをセットアップ

    fun setupWithState(
        state: HomeState,
        onIntent: (HomeIntent) -> Unit = {}
    ): HomeScreenRobot = apply {
        composeTestRule.setNewsflowContent {
            HomeScreen(state = state, onIntent = onIntent)
        }
    }
}
```

### 2.2 TestTag規約

#### onTag拡張関数の使用

TestTagによる要素検索には、`core/test`モジュールで提供される`onTag`拡張関数を使用します。

```kotlin
// core/test/.../NewsflowTestExtensions.kt
fun ComposeContentTestRule.onTag(testTag: String): SemanticsNodeInteraction =
    onNodeWithTag(testTag)
```

**使用例:**

```kotlin
// ❌ BAD: 標準のonNodeWithTagを直接使用
composeTestRule.onNodeWithTag(HomeTestTags.Loading.Root).assertIsDisplayed()

// ✅ GOOD: onTag拡張関数を使用（簡潔で統一的）
composeTestRule.onTag(HomeTestTags.Loading.Root).assertIsDisplayed()
```

#### TestTagの構成

```kotlin
// core/designsystem/.../DesignSystemTestTags.kt
// 共通コンポーネント用TestTag
object DesignSystemTestTags {
    object ErrorContent {
        const val Root = "ErrorContent"
        const val ActionButton = "ErrorContentActionButton"
    }

    object ArticleCard {
        const val Root = "ArticleCard"
        fun card(id: String) = "ArticleCard$id"
    }
}

// feature/{name}/.../HomeTestTags.kt
// Feature固有のTestTag
object HomeTestTags {
    object HomeScreen {
        const val Root = "HomeScreen"
    }

    object TopAppBar {
        const val Root = "HomeTopAppBar"
        const val SearchButton = "HomeTopAppBarSearchButton"
    }

    object TabRow {
        const val Root = "HomeTabRow"
        fun tab(category: NewsCategory) = "HomeTab${category.name}"
    }

    object Content {
        const val Root = "HomeContent"
    }

    object ArticleList {
        const val Root = "HomeArticleList"
    }

    object Loading {
        const val Root = "HomeLoading"
    }
}
```

### 2.3 State-Based Testing

**状態ベースのテスト** を基本方針とします。

```kotlin
// ✅ GOOD: 状態に基づくテスト
@Test
fun errorState_displaysErrorContent() {
    homeRobot
        .setupWithState(HomeState.Error(error = NetworkFailure("error")))
        .verifyErrorContentDisplayed()
        .verifyRetryButtonDisplayed()
}

@Test
fun loadingState_displaysLoadingIndicator() {
    homeRobot
        .setupWithState(HomeState.Stable(isLoading = true, ...))
        .verifyLoadingDisplayed()
}

// ❌ BAD: 実装詳細に依存するテスト
@Test
fun shouldCallViewModelMethod() {
    // ViewModelの内部メソッド呼び出しを検証（実装詳細）
}
```

## 3. テストファイル構成

```
feature/home/
├── src/main/kotlin/.../
│   ├── HomeScreen.kt
│   └── HomeTestTags.kt           # TestTag定義
└── src/test/kotlin/.../
    ├── HomeScreenTest.kt          # UIコンポーネントテスト
    ├── HomeScreenScreenshotTest.kt # スクリーンショットテスト
    ├── robot/
    │   └── HomeScreenRobot.kt     # Robot Pattern実装
    └── fixture/
        └── HomeTestFixtures.kt    # テストデータファクトリ
```

## 4. テスト命名規約

### テストメソッド命名

```
{状態/条件}_{期待される結果/動作}
```

例:
- `errorState_displaysErrorContent`
- `retryButtonClick_emitsRetryLoadIntent`
- `loadingState_displaysLoadingIndicator`
- `articleClick_emitsNavigateViewerIntent`
- `categoryTabClick_changesCategoryAndEmitsIntent`

### テストクラス命名

- UIテスト: `{ScreenName}Test`
- スクリーンショットテスト: `{ScreenName}ScreenshotTest`

## 5. Intent検証パターン

MVIアーキテクチャにおける `Intent` の発行を検証するパターン:

```kotlin
@Test
fun retryButtonClick_emitsRetryLoadIntent() {
    // 受け取ったIntentを記録
    val receivedIntents = mutableListOf<HomeIntent>()

    homeRobot
        .setupWithState(
            state = HomeState.Error(error = NetworkFailure("error")),
            onIntent = { receivedIntents.add(it) }
        )
        .clickRetryButton()

    // 期待するIntentが発行されたか検証
    assertEquals(listOf(HomeIntent.RetryLoad), receivedIntents)
}

// 複数のIntentを検証する場合
@Test
fun multipleActions_emitCorrectIntents() {
    val receivedIntents = mutableListOf<HomeIntent>()

    homeRobot
        .setupWithState(state = stableState, onIntent = { receivedIntents.add(it) })
        .selectCategory(NewsCategory.TECHNOLOGY)
        .clickArticle(articleId = "123")

    assertEquals(
        listOf(
            HomeIntent.ChangeCategory(NewsCategory.TECHNOLOGY),
            HomeIntent.NavigateViewer("123")
        ),
        receivedIntents
    )
}
```

## 6. Debounce対応

`NewsflowButton` 等のdebounce対応コンポーネントをテストする場合:

```kotlin
@Test
fun buttonWithDebounce_clickAfterDebounceTime_emitsIntent() {
    homeRobot.setupWithState(errorState, onIntent = { ... })

    // debounce時間（500ms）を経過させてからクリック
    ShadowSystemClock.advanceBy(Duration.ofMillis(600))

    homeRobot.clickRetryButton()

    // Intentが発行されることを確認
}
```

## 7. スクリーンショットテスト

### 基本構成

```kotlin
@RunWith(NewsflowTestRunner::class)
@GraphicsMode(GraphicsMode.Mode.NATIVE)
class HomeScreenScreenshotTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeScreen_errorState() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeState.Error(error = NetworkFailure("error")),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }

    @Test
    fun homeScreen_loadingState() {
        composeTestRule.setNewsflowContent {
            HomeScreen(
                state = HomeState.Stable(isLoading = true, ...),
                onIntent = {},
            )
        }
        composeTestRule.onRoot().captureRoboImage()
    }
}
```

### カバレッジ目標

各画面で以下の状態をカバー:
- Loading状態
- Success状態（データあり）
- Empty状態（データなし）
- Error状態
- ダークモード/ライトモード（将来対応）

## 8. テストデータ

### Fake/Fixtureの管理

```kotlin
// テスト用データファクトリ
// 配置: {feature}/src/test/kotlin/.../fixture/
object HomeTestFixtures {

    // === State Factory Methods ===

    fun createStableState(
        isLoading: Boolean = false,
        isRefreshing: Boolean = false,
        currentCategory: NewsCategory = NewsCategory.GENERAL,
        articlesByCategory: Map<NewsCategory, List<Article>> = mapOf(
            currentCategory to createArticles()
        ),
        selectedArticle: Article? = null
    ) = HomeState.Stable(...)

    fun createLoadingState(
        currentCategory: NewsCategory = NewsCategory.GENERAL
    ) = HomeState.Stable(isLoading = true, ...)

    fun createRefreshingState(
        currentCategory: NewsCategory = NewsCategory.GENERAL,
        articlesByCategory: Map<NewsCategory, List<Article>> = ...
    ) = HomeState.Stable(isRefreshing = true, ...)

    fun createEmptyState(
        currentCategory: NewsCategory = NewsCategory.GENERAL
    ) = HomeState.Stable(articlesByCategory = mapOf(currentCategory to emptyList()), ...)

    fun createErrorState(
        error: NewsflowError = NewsflowError.NetworkError.NetworkFailure("error")
    ) = HomeState.Error(error = error)

    // エラー種別ごとのファクトリ
    fun createNetworkErrorState() = createErrorState(
        error = NewsflowError.NetworkError.NetworkFailure("Network failure")
    )

    fun createServerErrorState() = createErrorState(
        error = NewsflowError.NetworkError.ServerError("Internal Server Error")
    )

    // === Data Factory Methods ===

    fun createArticles(count: Int = 5): List<Article> =
        List(count) { index -> createArticle(id = "article_$index", ...) }

    fun createArticle(
        id: String = "test_article_1",
        source: String = "Test Source",
        author: String = "Test Author",
        title: String = "Test Article Title",
        description: String = "Test article description",
        url: String = "https://example.com/article",
        imageUrl: String = "https://example.com/image.png",
        publishedAt: Long = 1700000000000L
    ) = Article(...)
}
```

## 9. 参考資料

- [Test your Compose layout | Android Developers](https://developer.android.com/develop/ui/compose/testing)
- [Robot Pattern for UI Testing](https://michaelevans.org/blog/2024/12/14/ui-testing-made-easy-the-robot-test-pattern-on-android/)
- [Jetpack Compose Testing Codelab](https://developer.android.com/codelabs/jetpack-compose-testing)