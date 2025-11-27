---
name: naming-convention
description: newsflow-androidプロジェクトの命名規則を適用するスキル。新しいファイル、クラス、モジュール、リソースを作成する際に使用。ConventionPlugin、パッケージ名、モジュール、Composable、リソースなどの命名パターンを提供。
---

# newsflow-android 命名規則スキル

新しいファイル、クラス、モジュール、リソースを作成する際は、このプロジェクトの命名規則に従ってください。

## クイックリファレンス

| 対象 | パターン | 例 |
|------|----------|-----|
| ConventionPlugin | `<Name>ConventionPlugin` | `AndroidLibraryConventionPlugin` |
| Plugin ID | `newsflow.android.<type>.<subtype>` | `newsflow.android.android.library` |
| ベースパッケージ | `io.github.kei_1111.newsflow.android` | - |
| Coreモジュール | `core:<name>` | `core:designsystem` |
| Featureモジュール | `feature:<name>` | `feature:home` |
| Theme関数 | `<ProjectName>Theme` | `NewsflowAndroidTheme` |
| Applicationクラス | `<ProjectName>Application` | `NewsflowAndroidApplication` |
| 汎用コンポーネント | `Newsflow<ComponentName>` | `NewsflowButton` |
| アイコン | `ic_<name>` | `ic_arrow_back` |
| 画像 | `img_<name>[_preview]` | `img_banner`, `img_sample_preview` |
| スクリプト | ケバブケース + `.sh` | `create-module.sh` |
| コールバック | `on + 動作 + 対象` | `onClickRetryButton`, `onChangeSearchQuery` |

## バージョンカタログ（libs.versions.toml）

```toml
[versions]
androidGradlePlugin = "8.12.3"  # キャメルケース

[libraries]
androidx-core-ktx = { ... }     # ケバブケース

[plugins]
android-application = { ... }   # ケバブケース
```

## 詳細

詳細な命名規則と例については `REFERENCE.md` を参照してください。