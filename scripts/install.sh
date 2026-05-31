#!/usr/bin/env sh
set -eu

SCRIPT_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
PROJECT_DIR=$(CDPATH= cd -- "$SCRIPT_DIR/.." && pwd)
INSTALL_HOME="${MAGIC_HOME:-"$HOME/.magic"}"

while [ "$#" -gt 0 ]; do
  case "$1" in
    --prefix)
      shift
      INSTALL_HOME="$1"
      ;;
    -h|--help)
      echo "Usage: sh scripts/install.sh [--prefix <path>]"
      exit 0
      ;;
    *)
      echo "Unknown option: $1" >&2
      exit 1
      ;;
  esac
  shift
done

if [ ! -d "$PROJECT_DIR/target" ] || ! ls "$PROJECT_DIR"/target/Magic*.jar >/dev/null 2>&1; then
  if command -v mvn >/dev/null 2>&1; then
    (cd "$PROJECT_DIR" && mvn -DskipTests package)
  else
    echo "No built jar found and mvn is not available." >&2
    echo "Install Maven, run 'mvn package', then run this script again." >&2
    exit 1
  fi
fi

JAR_PATH=$(find "$PROJECT_DIR/target" -maxdepth 1 -type f -name "Magic*.jar" ! -name "original-*" | sort | tail -n 1)
if [ -z "$JAR_PATH" ]; then
  echo "Could not find Magic jar in target/." >&2
  exit 1
fi

mkdir -p "$INSTALL_HOME/bin" "$INSTALL_HOME/lib" "$INSTALL_HOME/envs/default/spells"
cp "$JAR_PATH" "$INSTALL_HOME/lib/magic.jar"

cat > "$INSTALL_HOME/bin/magic" <<EOF
#!/usr/bin/env sh
exec java -jar "$INSTALL_HOME/lib/magic.jar" "\$@"
EOF

chmod +x "$INSTALL_HOME/bin/magic"

echo "Magic installed to $INSTALL_HOME"
echo "Add this to your shell profile if it is not already in PATH:"
echo "  export PATH=\"$INSTALL_HOME/bin:\$PATH\""
echo "Try:"
echo "  magic repl"
