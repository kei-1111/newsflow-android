# 命名規則 (Naming Conventions)

このドキュメントは、newsflow-android固有の命名規則を定義します。

---

## ConventionPlugin

自作プラグインはプロジェクト固有の命名であるため他のプラグインとの命名差別化を図るためにConventionPluginをSuffixにつける

**例**:
- `AndroidApplicationConventionPlugin`
- `AndroidLibraryConventionPlugin`
- `AndroidLibraryComposeConventionPlugin`
- `AndroidFeatureConventionPlugin`
- `DetektConventionPlugin`

**Plugin ID命名パターン**: `<prefix>.<type>.<subtype>`

**例**:
- `newsflow.android.android.application`
- `newsflow.android.android.library`
- `newsflow.android.android.library.compose`
- `newsflow.android.android.feature`
- `newsflow.android.detekt`

---

## パッケージ名

プロジェクト名がmy-appであればパッケージ名はハイフンを除いたmyappとする

**ベースパッケージ**: `io.github.kei_1111.newsflow.android`

**サブパッケージ構造**:
```
io.github.kei_1111.newsflow.android
├── app
├── core
│   ├── designsystem.theme
│   ├── ui.modifier
│   ├── ui.preview
│   └── ui.provider
└── feature (今後の追加用)
```

---

## モジュール命名

**Coreモジュールパターン**: `core:<module-name>`
- 例: `core:designsystem`, `core:ui`

**Featureモジュールパターン**: `feature:<module-name>`
- 例: `feature:home`, `feature:settings`

**Appモジュール**: `app`

**settings.gradle.kts での記述例**:
```kotlin
include(":app")
include(":core:designsystem")
include(":core:ui")
```

---

## バージョンカタログ

- versionsはキャメルケース
- librariesはケバブケース
- pluginｓはケバブケース

**例**:
```toml
[versions]
androidGradlePlugin = "8.12.3"
kotlin = "2.2.21"

[libraries]
androidx-core-ktx = { group = "androidx.core", name = "core-ktx", version.ref = "androidxCore" }

[plugins]
android-application = { id = "com.android.application", version.ref = "androidGradlePlugin" }
```

---

## Composable関数

**Theme関数命名**: `<ProjectName>Theme`
- 例: `NewsflowAndroidTheme`

---

## Applicationクラス

**命名パターン**: `<ProjectName>Application`
- 例: `NewsflowAndroidApplication`

---

## スクリプト

**命名パターン**: ケバブケース + `.sh`
- `convert-project.sh`
- `create-module.sh`

---

## リソース

**アイコン命名パターン**: `ic_<icon_name>`

スネークケースで命名し、`ic`をプレフィックスとして付ける。

**例**:
- `ic_home`
- `ic_settings`
- `ic_arrow_back`

**画像命名パターン**: `img_<image_name>[_preview]`

スネークケースで命名し、`img`をプレフィックスとして付ける。プレビュー用の画像には`preview`をサフィックスとして付ける。

**例**:
- `img_banner`
- `img_logo`
- `img_sample_preview`
- `img_article_preview`

---

## 汎用コンポーネント

**命名パターン**: `Newsflow<ComponentName>`

`core:designsystem`の`component/common`に配置する汎用コンポーネントは、プロジェクト固有のコンポーネントであることを明示するため、`Newsflow`をプレフィックスとして付ける。

**例**:
- `NewsflowButton`

---

## コンポーネント関数のコールバック

コンポーネントはViewに徹するべきであり、ビジネスロジックやIntent/Actionの知識を持たない。そのためコールバックには「何が起きたか」を表すアクション系の命名を使用する。

**命名パターン**: `on + 操作/イベント + UI要素`

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

2. **UI要素名はコンポーネント内での名前**（親コンポーネント名は含めない）
   - ArticleCard内: `onClickArticle`, `onClickMore`
   - ❌ `onClickArticleCardArticle`, `onClickArticleCardMore`

3. **XXXButtonコンポーネントの場合は`onClick`のみ**
   - ボタン自体がコンポーネント名に含まれるため、UI要素名は不要
   - ✅ `ShareButton(onClick = ...)`
   - ❌ `ShareButton(onClickShare = ...)`

### 例

```kotlin
// ArticleCard.kt
@Composable
fun ArticleCard(
    article: Article,
    onClickArticle: () -> Unit,
    onClickMore: () -> Unit,
)


// ArticleCardList.kt
@Composable
fun ArticleCardList(
    articles: List<Article>,
    onClickArticle: (Article) -> Unit,
    onClickMore: (Article) -> Unit,
)
```