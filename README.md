# YouTube Thumbnail Generator

AI-powered YouTube thumbnail generator built with Spring Boot. Overlay catchy titles
on uploaded images with optional ChatGPT styling and HuggingFace-based text placement.

## Architecture

```
Client ──► ThumbnailController
              ├── PromptEnhancerService   (local title hooks)
              ├── ImageService            (resize, enhance, draw text)
              ├── AIAssistantService      (OpenAI chat completions)
              └── HuggingFaceService      (object detection → placement)
```

| Layer | Responsibility |
|-------|----------------|
| Controller | Multipart upload endpoints, response headers |
| Services | Prompt enhancement, image processing, AI clients |
| Exception handler | Typed JSON errors for I/O and validation failures |
| Actuator | `/actuator/health` for liveness/readiness |

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

Keys are bound via `src/main/resources/application.properties`:

```properties
openai.api.key=${OPENAI_API_KEY:}
huggingface.api.key=${HUGGINGFACE_API_KEY:}
```

If keys are unset, AI endpoints fall back to deterministic local styles so the app still runs.

> **Security note:** Any API keys previously committed to this repository should be
> rotated in the OpenAI and HuggingFace dashboards.

## Install, build, and test

From a fresh clone:

```bash
./mvnw -B verify
```

Or with a system Maven:

```bash
mvn -B verify
```

Run only unit tests:

```bash
./mvnw -B test
```

Lint (Checkstyle):

```bash
./mvnw -B checkstyle:check
```

Dependency audit (OWASP):

```bash
./mvnw -B org.owasp:dependency-check-maven:check
```

Refresh the committed runtime dependency lock:

```bash
./mvnw -B dependency:list -DincludeScope=runtime -DoutputFile=.mvn/dependency-list.lock
```

## Run locally

```bash
export OPENAI_API_KEY=your-key       # optional
export HUGGINGFACE_API_KEY=your-key  # optional
./mvnw spring-boot:run
```

- API docs: http://localhost:8080/swagger-ui/index.html
- Health: http://localhost:8080/actuator/health

## Docker (one-command startup)

```bash
cp .env.example .env   # add keys if you want AI features
docker compose up --build
```

The compose file starts the app on port `8080` with health checks against `/actuator/health`.

## API

| Method | Path | Description |
|--------|------|-------------|
| `POST` | `/api/thumbnail/generate` | Basic thumbnail (`file`, `title`, optional `enhancePrompt`) |
| `POST` | `/api/thumbnail/ai-generate` | AI title/colors + HF placement (`file`, `topic`) |
| `POST` | `/api/thumbnail/ai-style` | Style suggestions only (`topic`) |
| `GET` | `/actuator/health` | Health probe |

## CI

GitHub Actions (`.github/workflows/ci.yml`) runs Checkstyle, `./mvnw verify`, and
`./mvnw test` on every push/PR. Dependabot (`.github/dependabot.yml`) watches Maven
and GitHub Actions weekly. An OWASP dependency-check job also runs (non-blocking
without an `NVD_API_KEY` secret).

## License

Private / as configured by the repository owner.
