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

パターン: `on + 動作 + 対象`

動作によって対象が異なる：

| 動作 | 対象 | 理由 |
|------|------|------|
| `onClick` | UIコンポーネント | Actionで直接実行される |
| `onDismiss` | UIコンポーネント | Actionで直接実行される |
| `onChange` | データ/状態 | Action感がない |

```kotlin
onClickRetryButton: () -> Unit
onClickNewsCategoryTab: () -> Unit
onDismissDialog: () -> Unit
onChangeSearchQuery: (String) -> Unit
```