# Abstraction Android

モダンなAndroid開発手法を用いたAndroidアプリケーションテンプレートです。KotlinベースのプロジェクトでJetpack ComposeとMaterial3デザインを使用したUI、Dagger Hiltによる依存性注入、Navigation Composeによる画面遷移を実装しています。

## 技術スタック

### プラットフォーム
- **最小SDK**: 29 (Android Q)
- **ターゲットSDK**: 36 (Android Baklava)
- **コンパイルSDK**: 36 (Android Baklava)

### 言語・フレームワーク
- **Kotlin**: 2.2.21
- **Java版**: 21
- **Android Gradle Plugin**: 8.13.1
- **KSP**: 2.3.3

### UI・デザイン
- **Jetpack Compose**: 2025.11.01 (BOM)
- **Material3**: 最新版（Compose BOMに含まれる）
- **Navigation3**: 1.0.0（型安全なナビゲーション）
- **Activity Compose**: 1.12.0

### アーキテクチャ・依存性注入
- **Dagger Hilt**: 2.57.2
- **Hilt Navigation Compose**: 1.3.0

### 開発ツール・品質管理
- **Detekt**: 1.23.8（リンター・フォーマッター）
- **Detekt Compose Rules**: 0.4.19（Compose特有のルールセット）
- **自動フォーマット対応**

### テスト関連
- **JUnit**: 4.13.2
- **AndroidX JUnit**: 1.3.0
- **Robolectric**: 4.16（Android UIテストのJVM実行）
- **Espresso Core**: 3.5.1（UIテスト）
- **Roborazzi**: 1.52.0（スクリーンショットテスト）
- **Composable Preview Scanner**: 0.7.1（Previewの自動検出）

### ライブラリ
- **Timber**: 5.0.1（ログ出力）
- **Kotlinx Serialization**: 1.9.0
- **Kotlinx Coroutines**: 1.10.2

## プロジェクト構造

シンプルなMVVMアーキテクチャを採用し、画面（Feature）ごとにコードを集約しています。

```
app/src/main/java/io/github/datt16/abstraction/
├── AbstractionApplication.kt      # Hiltアプリケーションクラス
├── MainActivity.kt                # メインアクティビティ
├── core/                          # アプリ全体で共有する基盤
│   ├── designsystem/              # テーマ、カラースキーム
│   │   ├── AbstractionAppTheme.kt
│   │   └── ColorScheme.kt
│   ├── navigation/                # ナビゲーション定義
│   │   ├── AbstractionAppNavHost.kt
│   │   └── AbstractionAppDestinations.kt
│   ├── ui/                        # 共通UIコンポーネント
│   │   └── AbstractionAppScaffold.kt
│   └── ext/                       # 拡張関数
│       └── TextStyleExt.kt
├── feature/                       # UI層（画面ごとに集約）
│   ├── home/                      # ホーム画面
│   │   ├── HomeScreen.kt          # UI
│   │   ├── HomeViewModel.kt       # 状態管理
│   │   └── HomeUiState.kt         # UI状態モデル
│   └── settings/                  # 設定画面
│       ├── SettingsScreen.kt      # UI
│       ├── SettingsViewModel.kt   # 状態管理
│       └── SettingsUiState.kt     # UI状態モデル
└── test/                          # テストユーティリティ
    └── ScreenshotTest.kt          # スクリーンショットテスト
```

**アーキテクチャの方針**
- 現在はUIレイヤーのみの実装（MVVM）
- Domain層とData層は必要に応じて追加予定
- 画面ごとにScreen / ViewModel / UiStateを集約

## ビルド設定

### アプリケーション情報
- **アプリケーションID**: `io.github.datt16.abstraction`
- **バージョンコード**: 29360001
- **バージョン名**: 0.1

### ビルドタイプ
- **Debug**: デバッグビルド、BuildConfigが有効
- **Release**: リリースビルド、ProGuard/R8による難読化有効

### Convention Plugins
このプロジェクトは`build-logic`モジュールでカスタムConvention Pluginsを定義しています：
- `abstraction.android.application.compose`: Compose対応のアプリケーション設定
- `abstraction.android.hilt`: Hilt依存性注入の設定

### 設定ファイル
- **バージョンカタログ**: `gradle/libs.versions.toml`
- **Detekt設定**: `config/detekt/detekt.yml`
- **Roborazzi設定**: `app/build.gradle.kts`内で定義

## セットアップ・使用方法

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

### プレビュー画像生成（Roborazzi）
Roborazziを使用して、Compose Previewから自動的にスクリーンショット画像を生成できます。

```bash
# プレビュー画像を生成
./gradlew recordRoborazziDebug
```

生成された画像は `app/build/outputs/roborazzi/` 配下に保存されます。

**自動生成の仕組み**
- ComposePreviewScannerが `@Preview` アノテーションを持つComposable関数を自動検出
- 検出されたプレビューごとにスクリーンショット画像を生成
- Visual Regression Testing（ビジュアルリグレッションテスト）やUI確認に活用可能

**生成される画像例**
- `HomeScreenKt.HomeScreenContentPreview.WITH_BACKGROUND.png`
- `SettingsScreenKt.SettingsScreenPreview.WITH_BACKGROUND.png`

## 特徴

- **モダンUI**: Jetpack Compose（BOM 2025.11.01）とMaterial3による最新のUIデザイン
- **型安全なナビゲーション**: Navigation3によるコンパイル時の型チェック
- **依存性注入**: Dagger Hilt 2.57.2による効率的な依存性管理
- **コード品質**: Detekt + Compose Rulesによる自動リンティングとフォーマット
- **Visual Regression Testing**: Roborazziによる自動スクリーンショットテスト
  - Compose Previewから自動的にテスト画像を生成
  - RobolectricベースでJVM上で高速に実行
- **Convention Plugins**: build-logicモジュールでビルド設定を一元管理
- **シンプルなアーキテクチャ**: MVVMベースの理解しやすい構造
- **拡張性**: 新機能追加が容易な設計
