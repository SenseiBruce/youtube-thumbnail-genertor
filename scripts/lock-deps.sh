#!/usr/bin/env bash
# Regenerate committed dependency lock artifacts and optionally check for drift.
set -euo pipefail
cd "$(dirname "$0")/.."
chmod +x ./mvnw

./mvnw -B dependency:list \
  -DincludeScope=runtime \
  -DoutputFile="${PWD}/dependencies.lock"

cp dependencies.lock pom.lock
cp dependencies.lock .mvn/dependency-list.lock

./mvnw -B dependency:tree \
  -DoutputFile="${PWD}/maven-dependencies.txt"

python3 - <<'PY'
import json
import pathlib
import re

text = pathlib.Path("dependencies.lock").read_text()
deps = []
pattern = re.compile(
    r"(?P<group>[\w.\-]+):(?P<artifact>[\w.\-]+):(?P<type>[\w.\-]+):(?P<version>[\w.\-]+):(?P<scope>[\w.\-]+)"
)
for line in text.splitlines():
    match = pattern.search(line.strip())
    if match:
        deps.append(match.groupdict())

payload = {
    "generator": "scripts/lock-deps.sh",
    "packageManager": "maven",
    "dependencies": deps,
}
pathlib.Path("dependencies-lock.json").write_text(json.dumps(payload, indent=2) + "\n")
print(f"Wrote dependencies-lock.json with {len(deps)} entries")
PY

if [[ "${1:-}" == "--check" ]]; then
  git diff --exit-code -- dependencies.lock pom.lock .mvn/dependency-list.lock \
    dependencies-lock.json maven-dependencies.txt
  echo "Lockfiles are up to date."
fi
