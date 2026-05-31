#!/usr/bin/env sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROJECT_DIR=$(CDPATH= cd -- "$SCRIPT_DIR/.." && pwd)
DIST_DIR="${1:-"$PROJECT_DIR/dist"}"
DIST_DIR=$(mkdir -p "$DIST_DIR" && cd "$DIST_DIR" && pwd)
WORK_DIR="$DIST_DIR/work"
PACKAGE_DIR="$WORK_DIR/Magic"

case "$DIST_DIR" in
  "$PROJECT_DIR"/*) ;;
  *)
    echo "Refusing to package outside the project directory: $DIST_DIR" >&2
    exit 1
    ;;
esac

if [ ! -d "$PROJECT_DIR/target" ]; then
  echo "target/ does not exist. Run 'mvn package' first." >&2
  exit 1
fi

JAR_PATH=$(find "$PROJECT_DIR/target" -maxdepth 1 -type f -name "Magic*.jar" ! -name "original-*" | sort | tail -n 1)
if [ -z "$JAR_PATH" ]; then
  echo "Could not find Magic jar in target/." >&2
  exit 1
fi

rm -rf "$DIST_DIR"
mkdir -p "$PACKAGE_DIR/bin" "$PACKAGE_DIR/lib" "$PACKAGE_DIR/envs/default/spells"

cp "$JAR_PATH" "$DIST_DIR/magic.jar"
cp "$JAR_PATH" "$PACKAGE_DIR/lib/magic.jar"
cp "$PROJECT_DIR/README.md" "$PACKAGE_DIR/README.md"
cp "$PROJECT_DIR/scripts/install.sh" "$PACKAGE_DIR/install.sh"
cp "$PROJECT_DIR/scripts/install.ps1" "$PACKAGE_DIR/install.ps1"

cat > "$PACKAGE_DIR/bin/magic" <<'EOF'
#!/usr/bin/env sh
SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
exec java -jar "$SCRIPT_DIR/../lib/magic.jar" "$@"
EOF

cat > "$PACKAGE_DIR/bin/magic.cmd" <<'EOF'
@echo off
java -jar "%~dp0..\lib\magic.jar" %*
EOF

cat > "$PACKAGE_DIR/bin/magic.ps1" <<'EOF'
& java -jar "$PSScriptRoot\..\lib\magic.jar" @args
EOF

chmod +x "$PACKAGE_DIR/bin/magic" "$PACKAGE_DIR/install.sh"

tar -czf "$DIST_DIR/magic-cli.tar.gz" -C "$WORK_DIR" "Magic"

if command -v zip >/dev/null 2>&1; then
  (cd "$WORK_DIR" && zip -qr "$DIST_DIR/magic-cli.zip" "Magic")
fi

if command -v sha256sum >/dev/null 2>&1; then
  (
    cd "$DIST_DIR"
    if [ -f magic-cli.zip ]; then
      sha256sum magic.jar magic-cli.tar.gz magic-cli.zip > SHA256SUMS
    else
      sha256sum magic.jar magic-cli.tar.gz > SHA256SUMS
    fi
  )
fi

rm -rf "$WORK_DIR"

echo "Packaged artifacts in $DIST_DIR"
