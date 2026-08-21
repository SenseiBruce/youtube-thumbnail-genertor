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

> **Security note:** Any API keys previously committed to this repository should be
> rotated in the OpenAI and HuggingFace dashboards.

## Install, build, and test

From a fresh clone (preferred):

```bash
make verify
```

Equivalent Maven commands:

```bash
./mvnw -B test          # unit + WireMock integration tests
./mvnw -B checkstyle:check
./mvnw -B verify        # tests + JaCoCo line coverage gate (≥ 60%)
```

Makefile shortcuts: `make test`, `make lint`, `make build`, `make docker-up`, `make lock`.

JaCoCo HTML report: `target/site/jacoco/index.html`.

Dependency audit (OWASP):

```bash
./mvnw -B org.owasp:dependency-check-maven:check
```

Refresh committed lockfiles:

```bash
make lock
# writes dependencies.lock, pom.lock, and .mvn/dependency-list.lock
```

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
| `POST` | `/api/thumbnail/generate` | Basic thumbnail (`file`, `title`, optional `enhancePrompt`) |
| `POST` | `/api/thumbnail/ai-generate` | AI title/colors + HF placement (`file`, `topic`) |
| `POST` | `/api/thumbnail/ai-style` | Style suggestions only (`topic`) |
| `GET` | `/actuator/health` | Health probe |
| `GET` | `/actuator/metrics` | Micrometer metrics |

## CI

GitHub Actions (`.github/workflows/ci.yml`) runs separate **`lint`** and **`test`** jobs
(`checkstyle:check`, `./mvnw test`, `./mvnw verify` with JaCoCo). Dependabot watches Maven
and GitHub Actions weekly. See [CONTRIBUTING.md](CONTRIBUTING.md) and [CHANGELOG.md](CHANGELOG.md).

## License

Private / as configured by the repository owner.
