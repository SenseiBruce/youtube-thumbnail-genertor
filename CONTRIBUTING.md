# Contributing

Thanks for improving the YouTube Thumbnail Generator.

## Development setup

1. Clone the repo and use JDK 17+.
2. Copy env template: `cp .env.example .env` (keys optional for local fallbacks).
3. Install/build: `make build` or `./mvnw -B -DskipTests package`.

## Commands

| Command | Purpose |
|---------|---------|
| `make test` / `./mvnw -B test` | Run unit + WireMock integration tests |
| `make lint` / `./mvnw -B checkstyle:check` | Lint |
| `make verify` / `./mvnw -B verify` | Tests + JaCoCo ≥ 60% line coverage |
| `make docker-up` | `docker compose up --build` |
| `make lock` | Refresh `dependencies.lock` |

## Commit style

- One behavior per commit, **with the tests that prove it**.
- Prefer small PRs: one service/feature, not mixed formatting + features.
- Do not commit secrets; use `.env` locally only.

## Pull requests

CI must be green (`lint` + `test` + `dependency-audit`). Include a short summary of behavior change and how you verified it (`./mvnw -B verify`).
CI must be green (`lint` + `test` jobs). Include a short summary of behavior change and how you verified it (`./mvnw -B verify`).
