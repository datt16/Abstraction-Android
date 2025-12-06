# Repository Guidelines

## プロジェクト構成
- `app/`: Android アプリ本体（Jetpack Compose, Hilt, Navigation3）。ソースは `app/src/main/java`、リソースは `app/src/main/res`。
- `app/src/main/java/io/github/datt16/abstraction/`: 機能別＋共通レイヤで構成。
  - `feature/<name>/`（例: `feature/home`, `feature/settings`）に `Screen` / `ViewModel` / `UiState` を配置。
  - `core/` は共通の `designsystem/`, `navigation/`（例: `AbstractionAppNavContainer.kt`, `AbstractionAppDestinations.kt`）, `ui/`, `ext/` を配置。
- `build-logic/`: Gradle コンベンションプラグイン（`abstraction.android.*`, `abstraction.detekt`）。
- `config/detekt/`: Detekt ルール。その他設定は `gradle/` およびルートの `*.kts` に定義。

## アーキテクチャ概要
- Jetpack Compose + MVVM。機能優先のパッケージ構成。
- ナビゲーションホスト: `core/navigation/AbstractionAppNavContainer.kt`。ルート定義: `AbstractionAppDestinations.kt`。
- 依存性注入: `AbstractionApplication` に `@HiltAndroidApp`、Android コンポーネントに `@AndroidEntryPoint`、ViewModel は Hilt 管理。

## テンプレート利用時のリネーム
- 本リポジトリはテンプレートです。利用開始時にプロジェクト名・パッケージをリネームしてください。
- コマンド例（プレビュー）: `tools/rename.sh --dry-run YourApp`
- コマンド例（適用）: `tools/rename.sh YourApp`
- 変更内容: `rootProject.name`、`applicationId`/`namespace`、パッケージディレクトリ移動、`Theme.*` と `strings.xml` の `app_name` 更新。
- 実行後: Android Studio を再起動/再インポートし、クリーンビルドしてください。

## ビルド・テスト・開発コマンド
- `./gradlew clean build` — 検証込みのフルビルド。
- `./gradlew assembleDebug` — Debug APK を生成。
- `./gradlew detekt` — Kotlin の lint/format（自動修正有効）。
- `./gradlew lintFormat` — 全サブプロジェクトに対して detekt 実行。
- `./gradlew detektBaseline` — detekt ベースラインの生成/更新。
- `./gradlew test` — JVM ユニットテスト（Robolectric 対応）。
- `./gradlew connectedAndroidTest` — 実機/エミュレータの計測テスト。
- `./gradlew recordRoborazziDebug` — Compose スクショ生成（`app/build/outputs/roborazzi/`）。
- Android Studio を開く（`studio` CLI がある場合）: `studio .`

## コーディング規約・命名
- Kotlin・インデント 2 スペース（`.editorconfig`）。Detekt + Compose Rules 使用（`config/detekt/detekt.yml`）。
- クラス: `PascalCase`、関数/プロパティ: `camelCase`、定数: `UPPER_SNAKE_CASE`。
- Compose Preview は `...Preview`、ViewModel は `...ViewModel`、UI 状態は `...UiState` を末尾に付与。
- ワイルドカード import を避け、機能単位で配置。

## テスト方針
- 使用: JUnit4 / Robolectric / AndroidX Test / Espresso / Compose UI Test / Roborazzi。
- テストクラス名は `*Test.kt`。小さく決定的なテストを優先。
- ユニットテストは `test`、デバイス検証は `connectedAndroidTest` を実行。
- UI 変更時は `recordRoborazziDebug` でスクショ更新し差分を確認。

## コミット／PR ガイドライン
- コミットは簡潔な命令形。任意でタイプ接頭辞（`feat`, `fix`, `chore`, `docs`, `refactor`）。例: `feat(home): add toolbar actions`。
- 課題/PR を参照（例: `(#42)`）。関連変更は 1 コミットにまとめる。
- PR には説明、関連 Issue、テスト手順/実行結果、UI 変更のスクショを含める。ローカルで `./gradlew build detekt test` を通す。

## セキュリティと設定
- 秘密情報・キーストア・`local.properties` はコミットしない。環境変数/GitHub Secrets を使用。
- モジュール追加時はコンベンションプラグイン ID と構造を保持。

## エージェント向け指示
- 内部思考（推論・Chain of Thought）は英語で行うこと。
- 対話/出力は日本語。一般的な技術用語は英語併記可（例: `ViewModel`, `Detekt`）。
- コード/コマンド/パスは英語表記のまま提示。
- 例外: ユーザーが英語で依頼した場合や英語引用が必要な場合は英語で回答可。
