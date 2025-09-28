FROM --platform=linux/amd64 buildpack-deps:noble-curl

# ---- base ----
RUN set -eux; \
  apt-get update -y; \
  apt-get upgrade -y; \
  apt-get autoremove -y; \
  apt-get install -y --no-install-recommends \
    software-properties-common \
    wget curl unzip tar gpg apt-transport-https ca-certificates acl

# ---- Temurin JDK 21 ----
RUN set -eux; \
  wget -qO - https://packages.adoptium.net/artifactory/api/gpg/key/public \
    | gpg --dearmor | tee /etc/apt/trusted.gpg.d/adoptium.gpg > /dev/null; \
  echo "deb https://packages.adoptium.net/artifactory/deb $(awk -F= '/^VERSION_CODENAME/{print$2}' /etc/os-release) main" \
    > /etc/apt/sources.list.d/adoptium.list; \
  apt-get update -y; \
  apt-get install -y --no-install-recommends temurin-21-jdk
ENV JAVA_HOME=/usr/lib/jvm/temurin-21-jdk-amd64

# ---- Android SDK (shared) ----
ENV ANDROID_HOME=/opt/android-sdk
ENV PATH="${ANDROID_HOME}/cmdline-tools/latest/bin:${ANDROID_HOME}/platform-tools:${ANDROID_HOME}/emulator:/usr/local/bin:${PATH}"

RUN set -eux; \
  mkdir -p "${ANDROID_HOME}/cmdline-tools"; \
  cd "${ANDROID_HOME}/cmdline-tools"; \
  wget -q https://dl.google.com/android/repository/commandlinetools-linux-13114758_latest.zip; \
  unzip -q commandlinetools-linux-*_latest.zip; \
  rm -f commandlinetools-linux-*_latest.zip; \
  mv cmdline-tools latest

# android グループ作成
RUN groupadd -r android || true

# 初期権限設定（setgidビット付き）
RUN chown -R root:android "${ANDROID_HOME}" && \
    chmod -R 2775 "${ANDROID_HOME}"

# umaskを設定してsdkmanagerを実行（新規作成ファイルもグループ書き込み可能に）
RUN umask 002 && \
    yes | sdkmanager --licenses && \
    sdkmanager \
        "build-tools;35.0.0" \
        "build-tools;36.1.0" \
        "platforms;android-35" \
        "platforms;android-36" \
        "platform-tools"

# 最終的な権限修正（sdkmanagerが作成したファイル/ディレクトリも確実にグループ書き込み可能に）
RUN chown -R root:android "${ANDROID_HOME}" && \
    find "${ANDROID_HOME}" -type d -exec chmod 2775 {} \; && \
    find "${ANDROID_HOME}" -type f -exec chmod 664 {} \; && \
    find "${ANDROID_HOME}" -type f -perm /u+x -exec chmod 775 {} \;
