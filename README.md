# Abstraction Android

モダンなAndroid開発手法を用いたAndroidアプリケーションテンプレートです。KotlinベースのプロジェクトでJetpack ComposeとMaterial3デザインを使用したUI、Dagger Hiltによる依存性注入、Navigation Composeによる画面遷移を実装しています。

## 技術スタック

### プラットフォーム
- **最小SDK**: 29 (Android Q)
- **ターゲットSDK**: 36 (Android Baklava)
- **コンパイルSDK**: 36 (Android Baklava)

### 言語・フレームワーク
- **Kotlin**: 2.2.20
- **Java版**: 21

### UI・デザイン
- **Jetpack Compose**: 2025.09.00 (BOM)
- **Material3**: 最新版（Compose BOMに含まれる）
- **Navigation Compose**: 2.9.4
- **Activity Compose**: 1.11.0

### アーキテクチャ・依存性注入
- **Dagger Hilt**: 2.57.1
- **Hilt Navigation Compose**: 1.3.0

### 開発ツール・品質管理
- **Detekt**: 1.23.8（リンター・フォーマッター）
- **Detekt Compose Rules**: 0.4.19（Compose特有のルールセット）
- **自動フォーマット対応**

### ライブラリ
- **Timber**: 5.0.1（ログ出力）
- **Kotlinx Serialization**: 1.9.0
- **Kotlinx Coroutines**: 1.10.2

## プロジェクト構造

```
app/src/main/java/io/github/datt16/abstraction/
├── core/                          # コアアプリケーションコンポーネント
│   ├── AbstractionApplication.kt  # Hiltアプリケーションクラス
│   ├── MainActivity.kt            # メインアクティビティ
│   ├── designsystem/             # デザインシステム
│   │   ├── AbstractionAppTheme.kt
│   │   └── ColorScheme.kt
│   ├── navigation/               # ナビゲーションシステム
│   │   ├── AbstractionAppDestinations.kt
│   │   └── AbstractionAppNavHost.kt
│   └── ext/                      # 拡張関数
├── screens/                      # UI画面とComposable
│   ├── common/                   # 共通コンポーネント
│   └── home/                     # ホーム画面
└── viewmodels/                   # UI状態管理用ViewModel
```

## ビルド設定

### ビルドタイプ
- **Debug**: アプリケーションID `io.github.datt16.abstraction.debug`
- **Release**: アプリケーションID `io.github.datt16.abstraction.release`、難読化有効

### 設定ファイル
- **バージョンカタログ**: `gradle/libs.versions.toml`
- **Detekt設定**: `config/detekt/detekt.yml`

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

## 特徴

- **モダンUI**: Jetpack ComposeとMaterial3による最新のUIデザイン
- **依存性注入**: Dagger Hiltによる効率的な依存性管理
- **ナビゲーション**: Navigation Composeによる型安全な画面遷移
- **コード品質**: Detektによる自動リンティングとフォーマット
- **アーキテクチャ**: MVVM+Clean Architectureベースの構造
- **拡張性**: 新機能追加が容易な設計
