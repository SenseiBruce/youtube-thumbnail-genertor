# YouTube Thumbnail Generator (AI-Powered)

## Features
- **ChatGPT Integration**: AI-generated titles and color schemes
- **HuggingFace Vision**: Smart text placement based on image analysis
- **YouTube Optimization**: Follows best practices for engagement
- **Image Enhancement**: Brightness/contrast/sharpen filters
- **Safe Margins**: Mobile and TV compatible positioning
- **Multiple Endpoints**: Basic and AI-powered generation
- **Fully Offline**: Runs locally with optional AI enhancement

## AI Capabilities
- **Smart Placement**: Detects faces/objects for optimal text positioning
- **Title Generation**: Creates catchy YouTube-optimized titles
- **Color Intelligence**: Auto-selects high-contrast color schemes
- **Font Optimization**: Uses Impact/Arial for maximum readability

## Endpoints
- `/api/thumbnail/generate` - Basic thumbnail generation
- `/api/thumbnail/ai-generate` - AI-powered with ChatGPT + HuggingFace
- `/api/thumbnail/ai-style` - Get AI style suggestions only

## Setup
1. Add OpenAI API key to `AIAssistantService.java`
2. Add HuggingFace token to `HuggingFaceService.java`

## Run
```bash
mvn clean package
mvn spring-boot:run
```
Visit: http://localhost:8080/swagger-ui/index.html
