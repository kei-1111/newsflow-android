# 命名規則 (Naming Conventions)

このドキュメントは、このテンプレートプロジェクト固有の命名規則を定義します。

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

**命名パターン**: `on + 動作 + 対象`

動作によって対象が異なる：

| 動作 | 対象 | 理由 |
|------|------|------|
| `onClick` | UIコンポーネント | Actionで直接実行される |
| `onDismiss` | UIコンポーネント | Actionで直接実行される |
| `onChange` | データ/状態 | Action感がない |

**例**
- `onClickRetryButton`
- `onClickNewsCategoryTab`
- `onDismissDialog`
- `onChangeSearchQuery`