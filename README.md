# YouTube Thumbnail Generator

AI-powered YouTube thumbnail generator built with Spring Boot. Overlay catchy titles
on uploaded images with optional ChatGPT styling and HuggingFace-based text placement.

## Architecture

```
Client ──► ThumbnailController (@Validated)
              ├── PromptEnhancerService   (local title hooks)
              ├── ImageService + TextPlacement  (resize 1280x720, draw text)
              ├── AIAssistantService      (OpenAI chat completions)
              ├── HuggingFaceService      (object detection → placement)
              └── ThumbnailMetrics        (Micrometer fallback counter)
```

| Layer | Responsibility |
|-------|----------------|
| Controller | Multipart upload endpoints with Bean Validation |
| Services | Prompt enhancement, image processing, AI clients |
| Exception handler | Typed JSON errors (`validation_error`, `ai_service_unavailable`, …) |
| Actuator | `/actuator/health` and `/actuator/metrics` |

## Prerequisites

- JDK 17+
- Maven 3.9+ **or** use the included Maven Wrapper (`./mvnw`)
- Optional: Docker / Docker Compose
- Optional: OpenAI and HuggingFace API keys for AI endpoints

## Configuration

Copy the example env file and set keys (never commit real secrets):

```bash
cp .env.example .env
# edit .env:
# OPENAI_API_KEY=...
# HUGGINGFACE_API_KEY=...
```

Keys and optional override URLs are bound via `src/main/resources/application.properties`:

```properties
openai.api.key=${OPENAI_API_KEY:}
huggingface.api.key=${HUGGINGFACE_API_KEY:}
```

If keys are unset, AI endpoints fall back to deterministic local styles and increment
`thumbnail.ai.fallback.count` (visible under `/actuator/metrics`).

See [SECURITY.md](SECURITY.md) for secret-handling policy and key rotation guidance.

## Install, build, and test

This is a **Maven / Java 17** project (not npm). From a fresh clone:

```bash
./mvnw -B test
```

Full gate (tests + JaCoCo ≥ 60% line coverage):

```bash
./mvnw -B verify
```

Lint:

```bash
./mvnw -B checkstyle:check
```

Makefile aliases: `make test`, `make lint`, `make verify`, `make build`.

Dependency lockfiles (`pom.lock`, `dependencies.lock`, `dependencies-lock.json`, `maven-dependencies.txt`) are committed. Refresh / check:

```bash
make lock
make lock-check
```

JaCoCo HTML report: `target/site/jacoco/index.html`  
Surefire reports: `target/surefire-reports/`

## Run locally

```bash
export OPENAI_API_KEY=your-key       # optional
export HUGGINGFACE_API_KEY=your-key  # optional
make run
# or: ./mvnw spring-boot:run
```

- API docs: http://localhost:8080/swagger-ui/index.html
- Health: http://localhost:8080/actuator/health
- Metrics: http://localhost:8080/actuator/metrics/thumbnail.ai.fallback.count

## Docker (one-command startup)

```bash
cp .env.example .env   # add keys if you want AI features
make docker-up
# or: docker compose up --build
```

## API

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/api/style-presets` | Named thumbnail palettes (`id`, colors, font, placement) |
| `GET` | `/api/fonts` | Overlay font catalog (`id`, `family`, `usage`) |
| `GET` | `/api/placements` | Overlay placement keys (`id`, `description`: top/bottom/left/right/center) |
| `GET` | `/api/canvas` | Output canvas size (`width`/`height`, 1280×720) |
| `POST` | `/api/thumbnail/generate` | Basic thumbnail (`file`, `title`, optional `enhancePrompt`) |
| `POST` | `/api/thumbnail/validate-title` | Advisory title length check (`title`, max 100 chars) |
| `POST` | `/api/thumbnail/enhance-prompt` | Preview hook-enhanced title (`title`) without generating an image |
| `POST` | `/api/thumbnail/ai-generate` | AI title/colors + HF placement (`file`, `topic`) |
| `POST` | `/api/thumbnail/generate-variants` | ZIP of 2–5 title-hook PNG variants (`file`, `title`, optional `count`) |
| `POST` | `/api/thumbnail/ai-style` | Style suggestions only (`topic`) |
| `POST` | `/api/thumbnail/validate-image` | Advisory size/aspect check (`file`, 1280×720 / 16:9) |
| `GET` | `/actuator/health` | Health probe |
| `GET` | `/actuator/metrics` | Micrometer metrics |

## CI

GitHub Actions (`.github/workflows/ci.yml`) runs separate **`lint`** and **`test`** jobs
(`checkstyle:check`, `./mvnw test`, `./mvnw verify` with JaCoCo). Dependabot watches Maven
and GitHub Actions weekly. See [CONTRIBUTING.md](CONTRIBUTING.md) and [CHANGELOG.md](CHANGELOG.md).

## License

Released under the [MIT License](LICENSE). See also [CONTRIBUTING.md](CONTRIBUTING.md), [CHANGELOG.md](CHANGELOG.md), and [SECURITY.md](SECURITY.md).
