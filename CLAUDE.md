# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Top-Level Rules

- To maximize efficiency, **if you need to execute multiple independent processes, invoke those tools concurrently, not sequentially**.
- **You must think exclusively in English**. However, you are required to **respond in Japanese**.

## WHAT: プロジェクト概要

マルチモジュール構成のAndroidニュースアプリ。Jetpack Compose + Koinを使用。
ビジネスロジックは外部KMPライブラリ（`newsflow.library.*`）が提供し、このリポジトリはUI層のみ担当。

**アーキテクチャ:**
```
app/                    # Navigation、DI設定
core/
├── designsystem/       # テーマ、共通コンポーネント
├── navigation/         # Navigation3定義
└── ui/                 # プレビューアノテーション
feature/
├── home/               # ホーム画面
├── search/             # 検索画面
└── viewer/             # 記事閲覧画面
build-logic/convention/ # Convention Plugins
```

**依存関係**: `app` → `feature/*` → `core/*` → 外部KMPライブラリ

## WHY: 設計原則

### 絶対に守るべきルール

1. **UI層のみ** - ビジネスロジックはnewsflow-libraryが担当、ここではUI実装のみ
2. **Material 3 Expressive** - UI実装はM3 Expressiveガイドラインに従う
3. **多重タップ対応** - ボタン系コンポーネントは`Newsflow*`を使用（debounce対応済み）

### よくある間違い

- ❌ ビジネスロジックをfeatureモジュールに書く → ✅ newsflow-libraryに実装
- ❌ 標準のButtonを直接使用 → ✅ NewsflowButton等を使用
- ❌ CircularProgressIndicatorを使用 → ✅ ContainedLoadingIndicator（M3 Expressive）

## HOW: 開発方法

### よく使うコマンド

| コマンド | 説明 |
|---------|------|
| `./gradlew build` | ビルド |
| `./gradlew detekt` | 静的解析（コード品質チェック） |
| `./scripts/create-module.sh` | 新規モジュール作成 |

### 重要ファイル参照

| 内容 | 参照先 |
|------|--------|
| Newsflowコンポーネント例 | `core/designsystem/.../NewsflowButton.kt:28` |
| Feature画面例 | `feature/home/.../HomeScreen.kt:38` |
| 多重タップ対応 | `core/ui/.../DebounceClicker.kt:22` |
| バージョン情報 | `gradle/libs.versions.toml` |

### 推奨ワークフロー

**新機能実装時:**
1. **探索**: 既存の類似コンポーネントを確認
2. **計画**: 実装ステップを整理（必要に応じてTodoWriteを使用）
3. **実装**: 既存パターンに従ってUI作成
4. **検証**: `./gradlew build detekt`で品質確認

## 詳細ドキュメント

| ドキュメント | 内容 |
|-------------|------|
| `agent-docs/NAMING_CONVENTION.md` | 命名規則 |
| `agent-docs/UI_CONVENTION.md` | UI実装ガイドライン |
| `.claude/skills/material3-expressive/SKILL.md` | M3 Expressive UI実装ガイドライン |