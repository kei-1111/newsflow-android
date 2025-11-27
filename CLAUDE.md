# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

### Top-Level Rules

- To maximize efficiency, **if you need to execute multiple independent processes, invoke those tools concurrently, not sequentially**.
- **You must think exclusively in English**. However, you are required to **respond in Japanese**.

## プロジェクト概要

newsflow-androidは、マルチモジュール構成のAndroidニュースアプリケーションです。UIはJetpack Compose、DIはKoin、ビジネスロジックは外部KMPライブラリ（`io.github.kei-1111.newsflow.library`）を使用しています。

## アーキテクチャ

```
app/                    # メインアプリ（Navigation、DI設定）
core/
├── designsystem/       # テーマ、共通コンポーネント
├── navigation/         # ナビゲーション定義（Navigation3）
└── ui/                 # プレビューアノテーション、Modifier拡張
feature/
├── home/               # ホーム画面（記事一覧）
└── viewer/             # 記事閲覧画面（WebView）
build-logic/convention/ # Convention Plugins
```

**依存関係の流れ**: `app` → `feature/*` → `core/*` → 外部KMPライブラリ

**外部KMPライブラリ**: ViewModelとビジネスロジックは`newsflow.library.*`として提供（`libs.newsflow.library.home`等）。このリポジトリはUI層のみを担当。

## 主要コマンド

```bash
./gradlew build                  # ビルド
./gradlew test                   # テスト実行
./gradlew detekt                 # 静的解析
./scripts/create-module.sh       # 新規モジュール作成
```

## Convention Plugins

| プラグインID | 用途 |
|-------------|------|
| `newsflow.android.android.application` | アプリモジュール |
| `newsflow.android.android.library` | 基本ライブラリ |
| `newsflow.android.android.library.compose` | Compose対応ライブラリ |
| `newsflow.android.android.feature` | フィーチャーモジュール（Compose + core依存） |
| `newsflow.android.detekt` | コード品質チェック |

## UI実装ガイドライン

UI作成時は **Material 3 Expressive** のガイドラインに従う。

- 詳細: `.claude/skills/material3-expressive/SKILL.md`
- `MotionScheme.expressive()`を使用
- `LoadingIndicator`、`DockedToolbar`等のExpressiveコンポーネントを優先

## プレビューアノテーション

`core:ui`に定義されたカスタムアノテーション:

- `@ScreenPreviews`: 画面用（ライト/ダーク、Phone/Tablet、標準/大フォント）
- `@ComponentPreviews`: コンポーネント用（ライト/ダーク、標準/大フォント）

## 命名規則

| 対象 | パターン | 例 |
|------|----------|-----|
| 汎用コンポーネント | `Newsflow<Name>` | `NewsflowButton` |
| アイコン | `ic_<name>` | `ic_arrow_back` |
| 画像 | `img_<name>` | `img_banner` |
| コールバック | `on + 動作 + 対象` | `onClickRetryButton` |

詳細: `.claude/skills/naming-convention/SKILL.md`

## 依存関係の追加

`gradle/libs.versions.toml`でバージョン管理:
- versions: キャメルケース
- libraries/plugins: ケバブケース

```kotlin
dependencies {
    implementation(libs.my.library)
    implementation(projects.core.ui)  // typesafe project accessor
}
```