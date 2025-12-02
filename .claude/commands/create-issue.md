# Create Issue

newsflow-androidとnewsflow-libraryの両方に相互リンク付きのIssueを作成します。

## 引数
- $ARGUMENTS: Issueの簡単な説明（任意）

## 実行手順

### 1. Issueタイプの選択

選択肢で質問: Bug Report / Feature Request

### 2. 情報収集

**重要**: 選択肢ではなく自由入力を促す。自動補完しない。

#### Bug Report

順番に質問:
1. **タイトル**: 「タイトルを入力してください」
2. **概要**: 「バグの内容を説明してください」
3. **再現手順**: 「再現手順を教えてください」
4. **期待する動作**: 「本来どうなるべきですか？」
5. **実際の動作**: 「実際に何が起きましたか？」

#### Feature Request

順番に質問:
1. **タイトル**: 「タイトルを入力してください」
2. **概要**: 「提案する機能を説明してください」
3. **解決したい問題**: 「なぜこの機能が必要ですか？」

### 3. Issue作成

1. newsflow-android にIssue作成
2. newsflow-library に同じIssue作成（android側へのリンク付き）
3. android側のIssueを更新（library側へのリンク追加）

### 4. 結果報告

両方のIssue URLを表示

## 本文テンプレート

### Bug Report

```markdown
## 概要

{概要}

## 再現手順

{再現手順}

## 期待する動作

{期待する動作}

## 実際の動作

{実際の動作}

**Related (newsflow-library)**: {URL}
```

### Feature Request

```markdown
## 概要

{概要}

## 解決したい問題

{解決したい問題}

**Related (newsflow-library)**: {URL}
```