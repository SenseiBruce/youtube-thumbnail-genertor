# Security Policy

## Reporting

If you discover a vulnerability in this project, please open a private security advisory
on GitHub or contact the maintainers. Do not commit secrets or credentials.

## Secrets handling

- API keys are supplied only via environment variables (`OPENAI_API_KEY`, `HUGGINGFACE_API_KEY`).
- `.env` is gitignored; use `.env.example` as the template.
- Historical commits prior to secret removal may still contain old key material in git
  history. Those keys must be **rotated** in the OpenAI and HuggingFace dashboards and
  treated as compromised. Do not reuse them.

## Dependency auditing

CI runs OWASP Dependency-Check on every push (`dependency-audit` job).
