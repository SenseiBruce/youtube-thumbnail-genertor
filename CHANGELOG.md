# Changelog

All notable changes to this project are documented in this file.

## [Unreleased]

### Added
- `GET /api/title-font` returns the main title overlay font (`Impact`)
- `GET /api/cta-font` returns the CTA overlay font (`Arial`)
- `GET /api/default-cta` returns the default overlay CTA (`WATCH NOW`)
- `GET /api/target-dimensions` returns the 1280x720 thumbnail canvas size
- `POST /api/thumbnail/generate-variants` returns a ZIP of 2–5 distinct title-hook thumbnails plus `titles.txt`
- `POST /api/thumbnail/validate-title` advisory 100-character title check
- `POST /api/thumbnail/enhance-prompt` returns original and hook-enhanced titles without generating an image
- `POST /api/thumbnail/validate-image` advisory 1280×720 / 16:9 check
- `GET /api/style-presets` returns named thumbnail palettes (colors, font, placement)
- `GET /api/fonts` lists overlay font families used by the renderer and style presets
- `GET /api/placements` lists overlay placement keys used by `TextPlacement`
- `GET /api/canvas` reports the 1280×720 thumbnail canvas size
- `GET /api/output-format` reports PNG (`image/png`) as the generated thumbnail format
- Weekly scheduled OWASP Dependency-Check (`owasp-weekly.yml`) instead of blocking every PR
- `GET /api/defaults` reports canvas size, PNG output, and recommended title length
- `GET /api/aspect-ratio` reports the 16:9 / 1280×720 YouTube thumbnail canvas
- `GET /api/allowed-image-types` documents accepted thumbnail upload content types
- `GET /api/safe-zone` reports the title layout rectangle for 1280×720
- `GET /api/text-placements` returns top/bottom/left/right/center title rectangles
- `GET /api/default-placement` returns the default text overlay zone (`center`)
- MIT `LICENSE`, `.editorconfig`, GitHub issue templates, and pull request template
- README license and community doc links

### Changed
- PR CI uses Trivy (minutes) instead of OWASP NVD download (hours); drop duplicate `mvn test` before `verify`; cancel in-progress runs; run push CI on `main` only
- `GET /api/overlay-threshold` returns the brightness-variance cutoff for title overlays
## [1.2.1] - 2026-08-21

### Removed
- `package.json` / `package-lock.json` npm shim (this is a Maven project)

### Changed
- CI `lint`/`test` jobs invoke `./mvnw -B checkstyle:check`, `./mvnw -B test`, and `./mvnw -B verify` directly
- Actuator health details always exposed; MockMvc test asserts `/actuator/health` is UP

## [1.2.0] - 2026-08-21


### Added
- `ImageEnhancer` and `ThumbnailTextRenderer` collaborators with dedicated tests
- DTO package (`ThumbnailStyleResponse`, `AiStyleRequest`) and image content-type checks
- `scripts/run-tests.sh`, `scripts/lock-deps.sh --check`, `dependencies-lock.json`, `package-lock.json`
- CI lockfile drift check and Surefire report artifacts
- `SECURITY.md`

### Changed
- `ImageService` is a thin facade over enhancer + text renderer
- README test/lock commands made explicit for fresh-clone buyers

## [1.1.0] - 2026-08-21

### Added
- `TextPlacement` helper with exhaustive placement region tests
- WireMock-backed OpenAI and HuggingFace integration tests (no live network)
- JaCoCo line-coverage gate (60%) bound to `./mvnw verify`
- Micrometer fallback counter `thumbnail.ai.fallback.count`
- Typed `AiIntegrationException` mapped to `ai_service_unavailable`
- Bean Validation (`@NotBlank` / `@NotNull`) on controller inputs
- Root `dependencies.lock` / `pom.lock` plus Makefile `test`/`lint`/`verify` targets
- CONTRIBUTING guide and split CI jobs named `lint` and `test`

### Changed
- Thumbnail output forced to exact 1280×720 via `Scalr.Mode.FIT_EXACT`
- AI provider URLs are configurable for local/stub testing

## [1.0.0] - 2025-11

### Added
- Initial Spring Boot thumbnail generator with ChatGPT + HuggingFace integration
- Docker Compose, Actuator health, Checkstyle, and base unit tests
