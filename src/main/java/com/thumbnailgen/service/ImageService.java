package com.thumbnailgen.service;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    public byte[] generateThumbnail(byte[] imageBytes, String title) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (src == null) throw new IOException("Invalid image file");

        BufferedImage enhanced = enhanceImage(src);
        BufferedImage finalImage = drawSmartTitle(enhanced, title);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", baos);
        return baos.toByteArray();
    }

    public byte[] generateAIThumbnail(byte[] imageBytes, AIAssistantService.ThumbnailStyle aiStyle) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (src == null) throw new IOException("Invalid image file");

        BufferedImage enhanced = enhanceImage(src);
        BufferedImage finalImage = drawAIStyledTitle(enhanced, aiStyle);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(finalImage, "png", baos);
        return baos.toByteArray();
    }

    private BufferedImage enhanceImage(BufferedImage src) {
        BufferedImage resized = Scalr.resize(src, Scalr.Method.QUALITY, 1280, 720, Scalr.OP_ANTIALIAS);
        RescaleOp rescale = new RescaleOp(1.05f, 5f, null);
        BufferedImage bright = new BufferedImage(resized.getWidth(), resized.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rescale.filter(resized, bright);
        float[] sharpen = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
        BufferedImage sharp = new ConvolveOp(new Kernel(3, 3, sharpen)).filter(bright, null);
        return sharp;
    }

    private BufferedImage drawSmartTitle(BufferedImage img, String title) {
        int w = img.getWidth(), h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        // Apply safe margins (80px left/right, 60px top/bottom)
        Rectangle safeZone = new Rectangle(80, 60, w - 160, h - 120);
        
        // Find safe regions within the safe zone
        java.util.List<Rectangle> safeRegions = findSafeRegionsInZone(img, safeZone);
        Rectangle mainArea = safeRegions.get(0);
        
        // Draw gradient overlay if background is complex
        if (needsOverlay(img, mainArea)) {
            drawGradientOverlay(g, mainArea);
        }
        
        // Draw main title with YouTube specs
        drawYouTubeText(g, img, title, mainArea, true);
        
        // Add CTA if there's space
        if (safeRegions.size() > 1) {
            String cta = getRandomCTA();
            drawYouTubeText(g, img, cta, safeRegions.get(1), false);
        }
        
        g.dispose();
        return out;
    }

    private BufferedImage drawAIStyledTitle(BufferedImage img, AIAssistantService.ThumbnailStyle aiStyle) {
        int w = img.getWidth(), h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        Rectangle safeZone = new Rectangle(80, 60, w - 160, h - 120);
        Rectangle textArea = getPlacementArea(safeZone, aiStyle.placement);
        
        if (needsOverlay(img, textArea)) {
            drawGradientOverlay(g, textArea);
        }
        
        drawAIText(g, aiStyle, textArea, h);
        
        g.dispose();
        return out;
    }
    
    private Rectangle getPlacementArea(Rectangle safeZone, String placement) {
        switch (placement.toLowerCase()) {
            case "top":
                return new Rectangle(safeZone.x, safeZone.y, safeZone.width, safeZone.height / 3);
            case "bottom":
                return new Rectangle(safeZone.x, safeZone.y + 2 * safeZone.height / 3, safeZone.width, safeZone.height / 3);
            case "left":
                return new Rectangle(safeZone.x, safeZone.y, safeZone.width / 2, safeZone.height);
            case "right":
                return new Rectangle(safeZone.x + safeZone.width / 2, safeZone.y, safeZone.width / 2, safeZone.height);
            default: // center
                return new Rectangle(safeZone.x + safeZone.width / 4, safeZone.y + safeZone.height / 4, 
                                   safeZone.width / 2, safeZone.height / 2);
        }
    }
    
    private void drawAIText(Graphics2D g, AIAssistantService.ThumbnailStyle aiStyle, Rectangle area, int imgHeight) {
        int fontSize = Math.max(80, Math.min(area.width / 6, imgHeight / 5));
        Font font = new Font(aiStyle.font, Font.BOLD, fontSize);
        g.setFont(font);
        
        font = ensureFitsInArea(g, aiStyle.title, font, area, 0.9);
        g.setFont(font);
        
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(aiStyle.title, g);
        
        int x = area.x + (area.width - (int)bounds.getWidth()) / 2;
        int y = area.y + (area.height + fm.getAscent() - fm.getDescent()) / 2;
        
        Color primary = parseColor(aiStyle.primaryColor);
        Color accent = parseColor(aiStyle.accentColor);
        
        // Shadow
        g.setColor(new Color(0, 0, 0, 180));
        g.drawString(aiStyle.title, x + 5, y + 5);
        
        // Outline
        g.setStroke(new BasicStroke(6));
        g.setColor(getContrastColor(primary));
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                if (dx != 0 || dy != 0) {
                    g.drawString(aiStyle.title, x + dx, y + dy);
                }
            }
        }
        
        // Main text with AI colors
        GradientPaint gp = new GradientPaint(0, y - 30, primary, 0, y + 10, accent);
        g.setPaint(gp);
        g.drawString(aiStyle.title, x, y);
    }
    
    private Color parseColor(String colorStr) {
        try {
            if (colorStr.startsWith("#")) {
                return Color.decode(colorStr);
            }
            return Color.WHITE;
        } catch (Exception e) {
            return Color.WHITE;
        }
    }
    
    private Color getContrastColor(Color color) {
        double brightness = (color.getRed() * 0.299 + color.getGreen() * 0.587 + color.getBlue() * 0.114) / 255.0;
        return brightness > 0.5 ? Color.BLACK : Color.WHITE;
    }
    
    private void drawYouTubeText(Graphics2D g, BufferedImage img, String text, Rectangle area, boolean isMainTitle) {
        int h = img.getHeight();
        int w = img.getWidth();
        
        // Much larger font sizes - use more of the available space
        int fontSize = isMainTitle ? 
            Math.max(120, Math.min(w / 6, h / 4)) : // Much larger main text
            Math.max(60, Math.min(w / 12, h / 8));  // Larger secondary text
        
        String fontName = isMainTitle ? "Impact" : "Arial";
        Font font = new Font(fontName, Font.BOLD, fontSize);
        g.setFont(font);
        
        // Allow text to use more of the area (90% instead of previous smaller percentage)
        font = ensureFitsInArea(g, text, font, area, 0.95);
        g.setFont(font);
        
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);
        
        // Center text in area
        int x = area.x + (area.width - (int)bounds.getWidth()) / 2;
        int y = area.y + (area.height + fm.getAscent() - fm.getDescent()) / 2;
        
        ColorScheme colors = getYouTubeColors(img, area, isMainTitle);
        
        // Larger shadow for bigger text
        g.setColor(new Color(0, 0, 0, 180));
        int shadowOffset = isMainTitle ? 6 : 4;
        g.drawString(text, x + shadowOffset, y + shadowOffset);
        
        // Thicker outline for larger text
        g.setStroke(new BasicStroke(isMainTitle ? 8 : 5));
        g.setColor(colors.outline);
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                if (dx != 0 || dy != 0) {
                    g.drawString(text, x + dx, y + dy);
                }
            }
        }
        
        // Draw main text with gradient
        GradientPaint gp = new GradientPaint(0, y - 40, colors.primary, 0, y + 15, colors.secondary);
        g.setPaint(gp);
        g.drawString(text, x, y);
    }
    
    private Font ensureFitsInArea(Graphics2D g, String text, Font font, Rectangle area, double utilization) {
        FontMetrics fm = g.getFontMetrics(font);
        Rectangle2D bounds = fm.getStringBounds(text, g);
        
        // Allow much larger text - only reduce if absolutely necessary
        while ((bounds.getWidth() > area.width * utilization || fm.getHeight() > area.height * utilization) && font.getSize() > 30) {
            font = new Font(font.getName(), font.getStyle(), font.getSize() - 6);
            fm = g.getFontMetrics(font);
            bounds = fm.getStringBounds(text, g);
        }
        
        return font;
    }
    
    private ColorScheme getYouTubeColors(BufferedImage img, Rectangle area, boolean isMainTitle) {
        Color avgColor = averageColorInArea(img, area);
        double brightness = (avgColor.getRed() * 0.299 + avgColor.getGreen() * 0.587 + avgColor.getBlue() * 0.114) / 255.0;
        
        if (isMainTitle) {
            if (brightness > 0.6) {
                // Dark background - bright yellow/white text
                return new ColorScheme(
                    new Color(255, 255, 0),   // Bright yellow
                    new Color(255, 255, 255), // White
                    new Color(0, 0, 0)        // Black outline
                );
            } else if (brightness < 0.3) {
                // Light background - white/yellow text
                return new ColorScheme(
                    new Color(255, 255, 255), // White
                    new Color(255, 220, 0),   // Yellow
                    new Color(0, 0, 0)        // Black outline
                );
            } else {
                // Medium background - high contrast
                return new ColorScheme(
                    new Color(255, 255, 255), // White
                    new Color(255, 255, 0),   // Yellow
                    new Color(0, 0, 0)        // Black outline
                );
            }
        } else {
            // CTA - always vibrant red/orange
            return new ColorScheme(
                new Color(255, 69, 0),     // Red-orange
                new Color(255, 140, 0),    // Orange
                new Color(255, 255, 255)   // White outline
            );
        }
    }
    
    private Color averageColorInArea(BufferedImage img, Rectangle area) {
        long sumR = 0, sumG = 0, sumB = 0;
        int count = 0;
        
        for (int y = area.y; y < area.y + area.height; y += 10) {
            for (int x = area.x; x < area.x + area.width; x += 10) {
                if (x < img.getWidth() && y < img.getHeight()) {
                    int rgb = img.getRGB(x, y);
                    sumR += (rgb >> 16) & 255;
                    sumG += (rgb >> 8) & 255;
                    sumB += rgb & 255;
                    count++;
                }
            }
        }
        
        return count > 0 ? new Color((int)(sumR/count), (int)(sumG/count), (int)(sumB/count)) : Color.GRAY;
    }
    
    private boolean needsOverlay(BufferedImage img, Rectangle area) {
        return brightnessVariance(img, area.x, area.y, area.width, area.height) > 50;
    }
    
    private void drawGradientOverlay(Graphics2D g, Rectangle area) {
        GradientPaint overlay = new GradientPaint(
            0, area.y, new Color(0, 0, 0, 0),
            0, area.y + area.height, new Color(0, 0, 0, 120)
        );
        g.setPaint(overlay);
        g.fillRect(area.x, area.y, area.width, area.height);
    }
    
    private java.util.List<Rectangle> findSafeRegionsInZone(BufferedImage img, Rectangle safeZone) {
        // Use larger regions to accommodate bigger text
        int rows = 2, cols = 2;
        int cellW = safeZone.width / cols, cellH = safeZone.height / rows;
        
        java.util.List<RegionScore> regions = new java.util.ArrayList<>();
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x0 = safeZone.x + c * cellW, y0 = safeZone.y + r * cellH;
                double variance = brightnessVariance(img, x0, y0, cellW, cellH);
                regions.add(new RegionScore(new Rectangle(x0, y0, cellW, cellH), variance));
            }
        }
        
        regions.sort((a, b) -> Double.compare(a.variance, b.variance));
        
        java.util.List<Rectangle> result = new java.util.ArrayList<>();
        result.add(regions.get(0).rect);
        
        // Add a smaller region for CTA if available
        for (int i = 1; i < regions.size(); i++) {
            Rectangle candidate = regions.get(i).rect;
            if (!overlapsSignificantly(result.get(0), candidate)) {
                // Make CTA region smaller but still visible
                Rectangle ctaRect = new Rectangle(
                    candidate.x, candidate.y + candidate.height / 2,
                    candidate.width, candidate.height / 2
                );
                result.add(ctaRect);
                break;
            }
        }
        
        return result;
    }
    
    private static class ColorScheme {
        Color primary, secondary, outline;
        
        ColorScheme(Color primary, Color secondary, Color outline) {
            this.primary = primary;
            this.secondary = secondary;
            this.outline = outline;
        }
    }
    
    private Font findOptimalFont(Graphics2D g, String text, int maxWidth, int maxHeight, boolean isMainTitle) {
        String fontName = isMainTitle ? "Impact" : "Arial";
        int startSize = isMainTitle ? Math.min(80, maxWidth / 6) : Math.min(40, maxWidth / 12);
        int fontSize = Math.max(16, startSize);
        
        Font font = new Font(fontName, Font.BOLD, fontSize);
        
        // Reduce font size until text fits both width and height
        while (fontSize > 16) {
            g.setFont(font);
            java.util.List<String> lines = wrapText(g, text, maxWidth);
            FontMetrics fm = g.getFontMetrics();
            int totalHeight = lines.size() * fm.getHeight();
            
            // Check if all lines fit within width
            boolean fitsWidth = true;
            for (String line : lines) {
                if (fm.getStringBounds(line, g).getWidth() > maxWidth) {
                    fitsWidth = false;
                    break;
                }
            }
            
            if (fitsWidth && totalHeight <= maxHeight) break;
            
            fontSize -= 4;
            font = new Font(fontName, Font.BOLD, fontSize);
        }
        
        return font;
    }
    
    private java.util.List<String> wrapText(Graphics2D g, String text, int maxWidth) {
        java.util.List<String> lines = new java.util.ArrayList<>();
        FontMetrics fm = g.getFontMetrics();
        
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();
        
        for (String word : words) {
            String testLine = currentLine.length() == 0 ? word : currentLine + " " + word;
            Rectangle2D bounds = fm.getStringBounds(testLine, g);
            
            if (bounds.getWidth() <= maxWidth) {
                currentLine = new StringBuilder(testLine);
            } else {
                if (currentLine.length() > 0) {
                    lines.add(currentLine.toString());
                    currentLine = new StringBuilder(word);
                } else {
                    // Single word too long, add it anyway
                    lines.add(word);
                }
            }
        }
        
        if (currentLine.length() > 0) {
            lines.add(currentLine.toString());
        }
        
        return lines.isEmpty() ? java.util.Arrays.asList(text) : lines;
    }

    private Color averageColor(BufferedImage img) {
        long sumR = 0, sumG = 0, sumB = 0;
        int count = 0;
        for (int y = 0; y < img.getHeight(); y += 20) {
            for (int x = 0; x < img.getWidth(); x += 20) {
                int rgb = img.getRGB(x, y);
                sumR += (rgb >> 16) & 255;
                sumG += (rgb >> 8) & 255;
                sumB += rgb & 255;
                count++;
            }
        }
        return new Color((int) (sumR / count), (int) (sumG / count), (int) (sumB / count));
    }

    private java.util.List<Rectangle> findMultipleSafeRegions(BufferedImage img) {
        int w = img.getWidth(), h = img.getHeight();
        int rows = 4, cols = 4;
        int cellW = w / cols, cellH = h / rows;
        
        java.util.List<RegionScore> regions = new java.util.ArrayList<>();
        
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x0 = c * cellW, y0 = r * cellH;
                double variance = brightnessVariance(img, x0, y0, cellW, cellH);
                regions.add(new RegionScore(new Rectangle(x0, y0, cellW, cellH), variance));
            }
        }
        
        // Sort by variance (lower is better for text)
        regions.sort((a, b) -> Double.compare(a.variance, b.variance));
        
        java.util.List<Rectangle> result = new java.util.ArrayList<>();
        result.add(regions.get(0).rect); // Best region for main title
        
        // Find second best region that doesn't overlap significantly
        for (int i = 1; i < regions.size(); i++) {
            Rectangle candidate = regions.get(i).rect;
            if (!overlapsSignificantly(result.get(0), candidate)) {
                result.add(candidate);
                break;
            }
        }
        
        return result;
    }
    
    private boolean overlapsSignificantly(Rectangle r1, Rectangle r2) {
        Rectangle intersection = r1.intersection(r2);
        double overlapArea = intersection.width * intersection.height;
        double minArea = Math.min(r1.width * r1.height, r2.width * r2.height);
        return overlapArea > minArea * 0.3; // 30% overlap threshold
    }
    
    private String getRandomCTA() {
        return "WATCH NOW";
    }
    
    private static class RegionScore {
        Rectangle rect;
        double variance;
        
        RegionScore(Rectangle rect, double variance) {
            this.rect = rect;
            this.variance = variance;
        }
    }

    private double brightnessVariance(BufferedImage img, int x0, int y0, int w, int h) {
        double sum = 0, sq = 0;
        int count = 0;
        for (int y = y0; y < y0 + h; y += 10) {
            for (int x = x0; x < x0 + w; x += 10) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 255, g = (rgb >> 8) & 255, b = rgb & 255;
                double br = (r + g + b) / 3.0;
                sum += br; sq += br * br; count++;
            }
        }
        double mean = sum / count;
        return Math.sqrt(sq / count - mean * mean);
    }
}