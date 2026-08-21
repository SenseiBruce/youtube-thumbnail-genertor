#!/usr/bin/env bash
# Primary test entrypoint for fresh clones and CI-equivalent local runs.
set -euo pipefail
cd "$(dirname "$0")/.."
chmod +x ./mvnw
exec ./mvnw -B test "$@"
