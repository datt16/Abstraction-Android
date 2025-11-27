#!/bin/bash

# Check if a new name is provided
if [ -z "$1" ]; then
    echo "使用方法: $0 [--dry-run] <新しいプロジェクト名>"
    echo "例: $0 ChatIt"
    echo "例: $0 --dry-run ChatIt  # 変更内容をプレビュー"
    exit 1
fi

# Set locale for sed
export LC_ALL=C
export LANG=C

# Check for dry-run flag
DRY_RUN=false
if [ "$1" = "--dry-run" ]; then
    DRY_RUN=true
    shift
    if [ -z "$1" ]; then
        echo "エラー: --dry-run フラグの後に新しいプロジェクト名を指定してください"
        echo "使用方法: $0 --dry-run <新しいプロジェクト名>"
        exit 1
    fi
fi

NEW_NAME=$1
OLD_NAME="Abstraction"
OLD_PACKAGE="io.github.datt16.abstraction"
NEW_PACKAGE="io.github.datt16.$(echo "$1" | tr '[:upper:]' '[:lower:]')"

if [ "$DRY_RUN" = true ]; then
    echo "=========================================="
    echo "DRY-RUN MODE: 実際の変更は行いません"
    echo "=========================================="
    echo ""
fi

echo "プロジェクト名を $OLD_NAME から $NEW_NAME に変更します"
echo "パッケージ名を $OLD_PACKAGE から $NEW_PACKAGE に変更します"
echo ""

# Confirm with the user (skip in dry-run mode)
if [ "$DRY_RUN" = false ]; then
    read -p "続行しますか？ (y/N): " confirm
    if [ "$confirm" != "y" ] && [ "$confirm" != "Y" ]; then
        echo "操作をキャンセルしました"
        exit 1
    fi
fi

# Replace strings in files
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📝 ファイル内の文字列置換"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ "$DRY_RUN" = true ]; then
    echo "[プレビュー] 以下のファイルで文字列が置換されます："
    # Find files that contain the old package or name
    find . -type f -not -path "*/\.*" -not -path "*/build/*" -not -path "*/tools/*" | while read -r file; do
        if grep -q "${OLD_PACKAGE}\|${OLD_NAME}" "$file" 2>/dev/null; then
            echo "  - $file"
            # Show sample changes
            grep -n "${OLD_PACKAGE}\|${OLD_NAME}" "$file" 2>/dev/null | head -3 | while read -r line; do
                echo "      ${line}"
            done
        fi
    done
    echo ""
else
    echo "パッケージ名を置換しています..."
    # Replace package names (lowercase)
    find . -type f -not -path "*/\.*" -not -path "*/build/*" -not -path "*/tools/*" -exec sed -i '' "s/${OLD_PACKAGE}/${NEW_PACKAGE}/g" {} +

    echo "複合ワードを置換しています..."
    # Complex replacements for various word combinations
    find . -type f -not -path "*/\.*" -not -path "*/build/*" -not -path "*/tools/*" -exec sed -i '' "
        # Replace when Abstraction is at the start of a camelCase or PascalCase word
        s/${OLD_NAME}\([A-Z][a-zA-Z0-9]*\)/${NEW_NAME}\1/g
        # Replace when Abstraction is after a dot (e.g., .Abstraction)
        s/\\.${OLD_NAME}/\\.${NEW_NAME}/g
        # Replace when Abstraction is standalone
        s/\\b${OLD_NAME}\\b/${NEW_NAME}/g
        # Replace Theme.Abstraction pattern specifically
        s/Theme\\.${OLD_NAME}/Theme.${NEW_NAME}/g
        # Replace package-style references
        s/package\\s\\+${OLD_NAME}/package ${NEW_NAME}/g
        # Replace import-style references
        s/import\\s\\+${OLD_NAME}/import ${NEW_NAME}/g
    " {} +
fi

# Update specific files
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "⚙️  設定ファイル"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ "$DRY_RUN" = true ]; then
    echo "[プレビュー] 以下の設定ファイルが更新されます："

    if [ -f "settings.gradle.kts" ]; then
        echo "  - settings.gradle.kts"
        echo "      rootProject.name = \"${OLD_NAME}\" → \"${NEW_NAME}\""
    fi

    if [ -f ".idea/.name" ]; then
        echo "  - .idea/.name"
        echo "      内容: ${OLD_NAME} → ${NEW_NAME}"
    fi

    if [ -f "app/build.gradle.kts" ]; then
        echo "  - app/build.gradle.kts"
        echo "      namespace = \"${OLD_PACKAGE}\" → \"${NEW_PACKAGE}\""
        echo "      applicationId = \"${OLD_PACKAGE}\" → \"${NEW_PACKAGE}\""
    fi
    echo ""
else
    # Update settings.gradle.kts
    if [ -f "settings.gradle.kts" ]; then
        sed -i '' "s/rootProject.name = \"${OLD_NAME}\"/rootProject.name = \"${NEW_NAME}\"/g" settings.gradle.kts
        echo "  ✓ settings.gradle.kts を更新しました"
    fi

    # Update .idea/.name if it exists
    if [ -f ".idea/.name" ]; then
        echo "${NEW_NAME}" > .idea/.name
        echo "  ✓ .idea/.name を更新しました"
    fi

    # Update build.gradle.kts
    if [ -f "app/build.gradle.kts" ]; then
        sed -i '' "s/namespace = \"${OLD_PACKAGE}\"/namespace = \"${NEW_PACKAGE}\"/g" app/build.gradle.kts
        sed -i '' "s/applicationId = \"${OLD_PACKAGE}\"/applicationId = \"${NEW_PACKAGE}\"/g" app/build.gradle.kts
        echo "  ✓ app/build.gradle.kts を更新しました"
    fi
fi

# Rename files with complex patterns
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📄 ファイル名変更"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

find . -type f -not -path "*/\.*" -not -path "*/build/*" -not -path "*/tools/*" | while read -r file; do
    # Get directory and filename separately
    dir=$(dirname "$file")
    filename=$(basename "$file")

    # Apply different replacement patterns to filename
    new_filename=$(echo "$filename" | sed -E "
        # Replace when Abstraction is at the start of a camelCase or PascalCase word
        s/${OLD_NAME}([A-Z][a-zA-Z0-9]*)/${NEW_NAME}\1/g
        # Replace when Abstraction is standalone
        s/\\b${OLD_NAME}\\b/${NEW_NAME}/g
        # Replace when Abstraction is part of a compound word
        s/([a-z])${OLD_NAME}/\1${NEW_NAME}/g
    ")

    # Only rename if the filename actually changed
    if [ "$filename" != "$new_filename" ]; then
        if [ "$DRY_RUN" = true ]; then
            echo "  [プレビュー] $file"
            echo "    $filename → $new_filename"
        else
            mv "$file" "$dir/$new_filename"
            echo "  ✓ $filename → $new_filename"
        fi
    fi
done
echo ""

# Move and reorganize package directories
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "📁 パッケージディレクトリ移動"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

# Function to move package directory
move_package_dir() {
    local old_dir=$1
    local new_dir=$2

    if [ -d "$old_dir" ]; then
        if [ "$DRY_RUN" = true ]; then
            echo "  [プレビュー] $old_dir → $new_dir"
            # List files that would be moved
            local file_count=$(find "$old_dir" -type f | wc -l | tr -d ' ')
            echo "    移動されるファイル数: $file_count"
        else
            echo "  $old_dir → $new_dir"
            # Create new directory
            mkdir -p "$new_dir"

            # Find all directories in the old location and recreate them in the new location
            find "$old_dir" -type d | while read -r dir; do
                # Skip the root directory itself
                if [ "$dir" = "$old_dir" ]; then
                    continue
                fi
                # Get the relative path from old_dir
                rel_path="${dir#$old_dir/}"
                # Create the same directory structure in new location
                if [ ! -z "$rel_path" ]; then
                    mkdir -p "$new_dir/$rel_path"
                fi
            done

            # Move all files maintaining the directory structure
            find "$old_dir" -type f | while read -r file; do
                # Get the relative path from old_dir
                rel_path="${file#$old_dir/}"
                # Move the file to the same relative path in new location
                mv "$file" "$new_dir/$rel_path"
            done

            # Remove the old directory after all files have been moved
            rm -rf "$old_dir"
        fi
    fi
}

# Move main source directory
move_package_dir \
    "app/src/main/java/io/github/datt16/abstraction" \
    "app/src/main/java/io/github/datt16/$(echo "$1" | tr '[:upper:]' '[:lower:]')"

# Move test directory
move_package_dir \
    "app/src/test/java/io/github/datt16/abstraction" \
    "app/src/test/java/io/github/datt16/$(echo "$1" | tr '[:upper:]' '[:lower:]')"

# Move androidTest directory
move_package_dir \
    "app/src/androidTest/java/io/github/datt16/abstraction" \
    "app/src/androidTest/java/io/github/datt16/$(echo "$1" | tr '[:upper:]' '[:lower:]')"

echo ""

# Update theme references in XML files
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo "🎨 リソースファイル"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

if [ "$DRY_RUN" = true ]; then
    echo "[プレビュー] 以下のファイルが更新されます："

    theme_files=$(find . -name "themes.xml" 2>/dev/null)
    if [ ! -z "$theme_files" ]; then
        echo "$theme_files" | while read -r file; do
            echo "  - $file (Theme.${OLD_NAME} → Theme.${NEW_NAME})"
        done
    fi

    string_files=$(find . -name "strings.xml" 2>/dev/null)
    if [ ! -z "$string_files" ]; then
        echo "$string_files" | while read -r file; do
            echo "  - $file (app_name: ${OLD_NAME} → ${NEW_NAME})"
        done
    fi
    echo ""
else
    find . -name "themes.xml" -exec sed -i '' "s/Theme.${OLD_NAME}/Theme.${NEW_NAME}/g" {} +
    find . -name "strings.xml" -exec sed -i '' "s/name=\"app_name\">${OLD_NAME}/name=\"app_name\">${NEW_NAME}/g" {} +
    echo "  ✓ リソースファイルを更新しました"
    echo ""
fi

if [ "$DRY_RUN" = true ]; then
    echo "=========================================="
    echo "✅ DRY-RUN 完了"
    echo "=========================================="
    echo ""
    echo "実際に変更を適用するには、--dry-run フラグなしで実行してください："
    echo "  $0 $NEW_NAME"
else
    echo "=========================================="
    echo "✅ 完了しました！"
    echo "=========================================="
    echo ""
    echo "以下の作業を行ってください："
    echo "1. Android Studioでプロジェクトを再度開く"
    echo "2. プロジェクトを再ビルドする"
    echo "3. 必要に応じてR.javaを再生成する"
fi
