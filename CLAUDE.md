# CLAUDE.md

このファイルは、Claude Code (claude.ai/code) がこのリポジトリで作業する際のガイドラインを提供します。

## コミュニケーションルール
- 正確で丁寧な日本語でやりとりを行います
- 技術用語は適切に日本語化し、必要に応じて英語を併記します
- コードやコマンドの説明は明確かつ簡潔に行います

## プロジェクト概要
モダンなAndroid開発手法を用いたAndroidアプリケーションテンプレートです。KotlinベースのプロジェクトでJetpack ComposeとMaterial3デザインを使用したUI、Dagger Hiltによる依存性注入、Navigation Composeによる画面遷移を実装しています。

## 必須コマンド

### ビルドと実行
```bash
# アプリをビルド
./gradlew build

# デバッグAPKをビルド
./gradlew assembleDebug

# リリースAPKをビルド
./gradlew assembleRelease

# ビルドをクリーン
./gradlew clean
```

### コード品質
```bash
# detektリンターを自動修正付きで実行
./gradlew detekt

# すべてのリンティングタスクを実行（カスタムタスク）
./gradlew lintFormat

# detektのベースラインを生成
./gradlew detektBaseline
```

### テスト
```bash
# ユニットテストを実行
./gradlew test

# 特定のバリアントのテストを実行
./gradlew testDebugUnitTest
./gradlew testReleaseUnitTest

# 接続されたAndroidテストを実行（デバイス/エミュレータが必要）
./gradlew connectedAndroidTest
```

## アーキテクチャと主要コンポーネント

### プロジェクト構造
- **Appモジュール**: シングルモジュールのAndroidアプリケーション
- **パッケージ構造**: `io.github.datt16.abstraction`
  - `core/`: コアアプリケーションコンポーネント（Applicationクラス、MainActivity、ナビゲーション、デザインシステム）
  - `screens/`: UI画面とComposable
  - `viewmodels/`: UI状態管理のためのViewModel

### 主要技術スタック
- **最小SDK**: 29 (Android Q)
- **ターゲットSDK**: 36 (Android Baklava)
- **Kotlin**: 2.2.20
- **Compose BOM**: 2025.09.00
- **Dagger Hilt**: 2.57.1（依存性注入）
- **Navigation Compose**: 2.9.4（画面遷移）
- **Detekt**: 1.23.8（コード品質チェック、Composeルール付き）

### 依存性注入の設定
Dagger Hiltを使用：
- `AbstractionApplication`に`@HiltAndroidApp`アノテーション
- アクティビティに`@AndroidEntryPoint`
- `hilt-navigation-compose`によるナビゲーション統合

### ナビゲーションアーキテクチャ
- 中央ナビゲーションホスト: `AbstractionAppNavHost`
- `AbstractionAppDestinations`で定義される画面遷移先
- Hilt ViewModelと統合されたCompose Navigation

### ビルド設定
- バージョンカタログ: `gradle/libs.versions.toml`
- Detekt設定: `config/detekt/detekt.yml`
- Detektで自動フォーマット有効
- ビルドタイプ: debug（`.debug`サフィックス付き）とrelease（`.release`サフィックス付き、minify有効）