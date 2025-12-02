# UI実装ガイドライン

## コンポーネントの責務分離

### 基本原則

コンポーネントはViewに徹し、ビジネスロジックやIntent/Actionの知識を持たない。

### Intent知識の境界

| レイヤー | Intent知識 | コールバック形式 |
|---------|-----------|-----------------|
| `*Screen.kt` | ✅ 持つ | `onIntent: (Intent) -> Unit` |
| `*Content.kt` | ✅ 持つ | `onIntent: (Intent) -> Unit` |
| その他コンポーネント | ❌ 持たない | UIコールバック（例: `onClickArticle`） |

### 構造例

```
HomeScreen.kt          ← onIntent を ViewModel から受け取る
└── HomeContent.kt     ← onIntent を受け取り、子コンポーネントのコールバックをIntentにマッピング
    ├── HomeTabRow.kt  ← onClickNewsCategory: (NewsCategory) -> Unit
    └── HomeHorizontalPager.kt ← onClickArticle: (Article) -> Unit
```

### 実装例

```kotlin
// HomeContent.kt - Intentを知っている
@Composable
internal fun HomeContent(
    state: HomeState.Stable,
    onIntent: (HomeIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    HomeTabRow(
        selectedCategory = state.currentNewsCategory,
        onClickNewsCategory = { category ->
            onIntent(HomeIntent.ChangeCategory(category))
        },
    )
    HomeHorizontalPager(
        articlesByCategory = state.articlesByCategory,
        onClickArticle = { onIntent(HomeIntent.NavigateViewer(it)) },
        onClickMore = { onIntent(HomeIntent.ShowArticleOverview(it)) },
    )
}

// HomeTabRow.kt - Intentを知らない（純粋なUIコンポーネント）
@Composable
internal fun HomeTabRow(
    selectedCategory: NewsCategory,
    onClickNewsCategory: (NewsCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    // UIのみを担当
}
```

### メリット

1. **テスト容易性**: UIコンポーネントは単純なコールバックでテスト可能
2. **再利用性**: Intentに依存しないためモジュール間で再利用可能
3. **関心の分離**: UIはViewに徹し、ビジネスロジックはContent/Screenで処理