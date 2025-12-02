# 命名規則 詳細リファレンス

## ConventionPlugin

自作プラグインは `ConventionPlugin` をサフィックスにつけて、他のプラグインと差別化する。

### クラス名
```kotlin
AndroidApplicationConventionPlugin
AndroidLibraryConventionPlugin
AndroidLibraryComposeConventionPlugin
AndroidFeatureConventionPlugin
DetektConventionPlugin
```

### Plugin ID
パターン: `<prefix>.<type>.<subtype>`

```
newsflow.android.android.application
newsflow.android.android.library
newsflow.android.android.library.compose
newsflow.android.android.feature
newsflow.android.detekt
```

---

## パッケージ名

プロジェクト名のハイフンは除去する（my-app → myapp）

### ベースパッケージ
```
io.github.kei_1111.newsflow.android
```

### サブパッケージ構造
```
io.github.kei_1111.newsflow.android
├── app
├── core
│   ├── designsystem.theme
│   ├── ui.modifier
│   ├── ui.preview
│   └── ui.provider
└── feature
    ├── home
    └── settings
```

---

## モジュール命名

### パターン
| タイプ | パターン | 例 |
|--------|----------|-----|
| Core | `core:<module-name>` | `core:designsystem`, `core:ui` |
| Feature | `feature:<module-name>` | `feature:home`, `feature:settings` |
| App | `app` | `app` |

### settings.gradle.kts
```kotlin
include(":app")
include(":core:designsystem")
include(":core:ui")
include(":feature:home")
```

---

## バージョンカタログ（libs.versions.toml）

| セクション | ケース | 例 |
|------------|--------|-----|
| versions | キャメルケース | `androidGradlePlugin = "8.12.3"` |
| libraries | ケバブケース | `androidx-core-ktx = { ... }` |
| plugins | ケバブケース | `android-application = { ... }` |

### 例
```toml
[versions]
androidGradlePlugin = "8.12.3"
kotlin = "2.2.21"
androidxCore = "1.12.0"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "androidxCore" }

[plugins]
android-application = { id = "com.android.application", version.ref = "androidGradlePlugin" }
```

---

## Composable関数

### Theme関数
パターン: `<ProjectName>Theme`
```kotlin
@Composable
fun NewsflowAndroidTheme(
    content: @Composable () -> Unit
) { ... }
```

### 汎用コンポーネント
パターン: `Newsflow<ComponentName>`

`core:designsystem`の`component/common`に配置するコンポーネントはプレフィックス `Newsflow` をつける。

```kotlin
@Composable
fun NewsflowButton(...) { ... }

@Composable
fun NewsflowCard(...) { ... }

@Composable
fun NewsflowTextField(...) { ... }
```

---

## Applicationクラス

パターン: `<ProjectName>Application`

```kotlin
class NewsflowAndroidApplication : Application() { ... }
```

---

## リソース

### アイコン
パターン: `ic_<icon_name>`（スネークケース）

```
ic_home
ic_settings
ic_arrow_back
ic_chevron_right
```

### 画像
パターン: `img_<image_name>[_preview]`（スネークケース）

```
img_banner
img_logo
img_article
img_sample_preview      # プレビュー用
img_article_preview     # プレビュー用
```

---

## スクリプト

パターン: ケバブケース + `.sh`

```
convert-project.sh
create-module.sh
setup-environment.sh
```

---

## コールバック関数

コンポーネントはViewに徹するべきであり、ビジネスロジックやIntent/Actionの知識を持たない。そのためコールバックには「何が起きたか」を表すアクション系の命名を使用する。

パターン: `on + 操作/イベント + UI要素`

### 操作/イベントの種類

| 種類 | 用途 | 例 |
|------|------|-----|
| `Click` | タップ/クリック | `onClickArticle`, `onClickShare` |
| `LongClick` | 長押し | `onLongClickArticle` |
| `Dismiss` | 閉じる | `onDismissDialog`, `onDismissBottomSheet` |
| `Change` | 値/選択の変化 | `onChangeQuery`, `onChangeCategory` |
| `Settle` | 確定/落ち着く | `onSettlePage` |
| `Input` | テキスト入力 | `onInputSearch` |
| `Swipe` | スワイプ | `onSwipePage` |
| `Drag` | ドラッグ | `onDragItem` |
| `Scroll` | スクロール | `onScrollList` |

### 命名ルール

1. **UIコンポーネント種別（Button, Card等）は省略**
   - ✅ `onClickArticle`, `onClickShare`
   - ❌ `onClickArticleCard`, `onClickShareButton`

2. **UI要素名はコンポーネント内での名前**
   - ArticleCard内: `onClickArticle`, `onClickMore`
   - ❌ `onClickArticleCardArticle`

3. **XXXButtonコンポーネントの場合は`onClick`のみ**
   - ✅ `ShareButton(onClick = ...)`
   - ❌ `ShareButton(onClickShare = ...)`

```kotlin
// ArticleCard.kt
@Composable
fun ArticleCard(
    article: Article,
    onClickArticle: () -> Unit,
    onClickMore: () -> Unit,
)
```