---
name: material3-expressive
description: UI作成時にMaterial 3 Expressiveのガイドラインに沿って実装するためのスキル。Jetpack ComposeでUI/コンポーネントを作成する際に使用してください。
---

# Material 3 Expressive UI作成ガイド

UI作成時は **Material 3 Expressive** のガイドラインに沿って実装してください。

## Instructions

### 概要

Material 3 Expressiveは、Material Design 3の最新進化形であり、より魅力的で使いやすい製品を作るための包括的な拡張です。Android 16のビジュアルスタイルとシステムUIを補完するよう設計されています。

### 必須要件

#### minSdk
```kotlin
minSdk = 23 // 最低でも23が必要
```

#### 依存関係
```toml
# gradle/libs.versions.toml
[versions]
composeBom = "2025.05.00" # または最新版
composeMaterial3 = "1.5.0-alpha" # Expressiveコンポーネント含む

[libraries]
androidx-compose-material3 = { group = "androidx.compose.material3", name = "material3" }
```

#### OptInアノテーション
Expressiveコンポーネントは実験的APIのため、必ず以下のアノテーションを付与:
```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
```

### テーマ設定

#### MotionScheme
MotionSchemeはアプリ全体のモーション設定を制御します:

```kotlin
MaterialTheme(
    colorScheme = colorScheme,
    typography = typography,
    shapes = shapes,
    motionScheme = MotionScheme.expressive() // または MotionScheme.standard()
) {
    // Content
}
```

#### 5つのキーカラー
- **Primary**: 主要なUI要素
- **Secondary**: 二次的な要素
- **Tertiary**: アクセントカラー
- **Error**: エラー表示
- **Surface**: 背景やカード

### 推奨コンポーネント

#### LoadingIndicator（新規）
短い待機時間（5秒未満）には`CircularProgressIndicator`の代わりに使用:

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyLoadingScreen() {
    LoadingIndicator()
    // または
    ContainedLoadingIndicator() // コンテナ付きバリアント
}
```

#### FloatingToolbar（新規）
水平・垂直両方向に対応した多用途ツールバー:

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyFloatingToolbar() {
    FloatingToolbar(
        expanded = expanded,
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        // ツールバーアイテム
    }
}
```

#### DockedToolbar（BottomAppBarの代替）
`BottomAppBar`は非推奨。代わりに`DockedToolbar`を使用:

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyDockedToolbar() {
    DockedToolbar {
        // コンテンツ
    }
}
```

#### FlexibleBottomAppBar
スクロール動作に応じてレイアウトと表示を動的に調整:

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun MyFlexibleBottomAppBar() {
    FlexibleBottomAppBar(
        scrollBehavior = scrollBehavior
    ) {
        // コンテンツ
    }
}
```

### Shapes（形状）

Material 3 Expressiveでは形状のモーフィングが重要な要素です:

```kotlin
val shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(28.dp)
)
```

### Typography（タイポグラフィ）

強調されたタイポグラフィを使用:

```kotlin
val typography = Typography(
    displayLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        lineHeight = 64.sp
    ),
    // display, headline, title, body, label の各サイズ
)
```

### ベストプラクティス

1. **モーションを活用**: `MotionScheme.expressive()`を使用して流れるようなアニメーションを実現
2. **形状のモーフィング**: コンポーネント間で形状が自然に変化するよう設計
3. **カラーロールの遵守**: コントラストとアクセシビリティを自動的に満たすため、定義されたカラーロールを使用
4. **動的カラー**: Android 12+ではユーザーの壁紙から派生したダイナミックカラーをサポート
5. **Elevation**: シャドウの代わりにトーナルカラーオーバーレイを使用

### 非推奨コンポーネント

以下のコンポーネントは代替品を使用:

| 非推奨 | 代替 |
|--------|------|
| `BottomAppBar` | `DockedToolbar` |
| `CircularProgressIndicator`（短い待機時間） | `LoadingIndicator` |

## Examples

### 画面全体の例

```kotlin
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ArticleListScreen(
    articles: List<Article>,
    isLoading: Boolean,
    onArticleClick: (Article) -> Unit
) {
    Scaffold(
        bottomBar = {
            DockedToolbar {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        }
    ) { paddingValues ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                LoadingIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(articles) { article ->
                    ArticleCard(
                        article = article,
                        onClick = { onArticleClick(article) }
                    )
                }
            }
        }
    }
}
```

## References

- [Material 3 公式ドキュメント](https://developer.android.com/develop/ui/compose/designsystems/material3)
- [Material 3 Expressive ブログ](https://m3.material.io/blog/building-with-m3-expressive)
- [サンプルカタログ](https://github.com/meticha/material-3-expressive-catalog)