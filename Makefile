.PHONY: test lint verify build run docker-up lock lock-check deps-updates

## Install dependencies / compile
build:
	./mvnw -B -DskipTests package

## Run the full automated test suite (unit + WireMock integration)
test:
	./mvnw -B test

## Lint with Checkstyle
lint:
	./mvnw -B checkstyle:check

## Compile, test, and enforce JaCoCo line coverage >= 60%
verify:
	./mvnw -B verify

## Start the app locally
run:
	./mvnw spring-boot:run

## One-command container startup
docker-up:
	docker compose up --build

## Refresh committed Maven dependency lockfiles
lock:
	./scripts/lock-deps.sh

## Fail if lockfiles drift from pom.xml
lock-check:
	./scripts/lock-deps.sh --check

## Show available dependency updates
deps-updates:
	./mvnw -B versions:display-dependency-updates
