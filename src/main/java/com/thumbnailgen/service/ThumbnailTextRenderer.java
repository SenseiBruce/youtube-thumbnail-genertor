package com.thumbnailgen.service;

import org.springframework.stereotype.Service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Draws titles, CTAs, and overlays onto thumbnail canvases.
 */
@Service
public class ThumbnailTextRenderer {

    /** Font used for the main YouTube title overlay. */
    public static final String TITLE_FONT = "Impact";
    /** Font used for CTA overlays under the main title. */
    public static final String CTA_FONT = "Arial";
    public static final String DEFAULT_CTA = "WATCH NOW";
    public static final int OVERLAY_VARIANCE_THRESHOLD = 50;
    public static final int TITLE_STROKE_WIDTH = 8;
    public static final int CTA_STROKE_WIDTH = 5;
    /** Drop-shadow offset in pixels for the main title. */
    public static final int TITLE_SHADOW_OFFSET = 6;

    /** Drop-shadow offset in pixels for the CTA overlay. */
    public static final int CTA_SHADOW_OFFSET = 4;

    public BufferedImage drawSmartTitle(BufferedImage img, String title) {
        Canvas canvas = prepareCanvas(img);
        Rectangle safeZone = TextPlacement.safeZone(canvas.width, canvas.height);
        List<Rectangle> safeRegions = findSafeRegionsInZone(img, safeZone);
        Rectangle mainArea = safeRegions.get(0);

        if (needsOverlay(img, mainArea)) {
            drawGradientOverlay(canvas.graphics, mainArea);
        }

        drawYouTubeText(canvas.graphics, img, title, mainArea, true);

        if (safeRegions.size() > 1) {
            drawYouTubeText(canvas.graphics, img, defaultCta(), safeRegions.get(1), false);
        }

        return canvas.finish();
    }

    public BufferedImage drawAIStyledTitle(BufferedImage img, AIAssistantService.ThumbnailStyle aiStyle) {
        Canvas canvas = prepareCanvas(img);
        Rectangle safeZone = TextPlacement.safeZone(canvas.width, canvas.height);
        Rectangle textArea = TextPlacement.getPlacementArea(safeZone, aiStyle.placement);

        if (needsOverlay(img, textArea)) {
            drawGradientOverlay(canvas.graphics, textArea);
        }

        drawAIText(canvas.graphics, aiStyle, textArea, canvas.height);
        return canvas.finish();
    }

    String defaultCta() {
        return DEFAULT_CTA;
    }

    boolean needsOverlay(BufferedImage img, Rectangle area) {
        return brightnessVariance(img, area.x, area.y, area.width, area.height) > 50;
        return "WATCH NOW";
    }

    boolean needsOverlay(BufferedImage img, Rectangle area) {
        return brightnessVariance(img, area.x, area.y, area.width, area.height) > OVERLAY_VARIANCE_THRESHOLD;
        return brightnessVariance(img, area.x, area.y, area.width, area.height) > 50;
    }

    private static Canvas prepareCanvas(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        g.drawImage(img, 0, 0, null);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        return new Canvas(out, g, w, h);
    }

    private static final class Canvas {
        private final BufferedImage image;
        private final Graphics2D graphics;
        private final int width;
        private final int height;

        private Canvas(BufferedImage image, Graphics2D graphics, int width, int height) {
            this.image = image;
            this.graphics = graphics;
            this.width = width;
            this.height = height;
        }

        private BufferedImage finish() {
            graphics.dispose();
            return image;
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

        int x = area.x + (area.width - (int) bounds.getWidth()) / 2;
        int y = area.y + (area.height + fm.getAscent() - fm.getDescent()) / 2;

        Color primary = parseColor(aiStyle.primaryColor);
        Color accent = parseColor(aiStyle.accentColor);

        g.setColor(new Color(0, 0, 0, 180));
        g.drawString(aiStyle.title, x + 5, y + 5);

        g.setStroke(new BasicStroke(6));
        g.setColor(getContrastColor(primary));
        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {
                if (dx != 0 || dy != 0) {
                    g.drawString(aiStyle.title, x + dx, y + dy);
                }
            }
        }

        GradientPaint gp = new GradientPaint(0, y - 30, primary, 0, y + 10, accent);
        g.setPaint(gp);
        g.drawString(aiStyle.title, x, y);
    }

    Color parseColor(String colorStr) {
        try {
            if (colorStr != null && colorStr.startsWith("#")) {
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

        int fontSize = isMainTitle
                ? Math.max(120, Math.min(w / 6, h / 4))
                : Math.max(60, Math.min(w / 12, h / 8));

        String fontName = isMainTitle ? TITLE_FONT : "Arial";
        String fontName = isMainTitle ? "Impact" : CTA_FONT;
        String fontName = isMainTitle ? "Impact" : "Arial";
        Font font = new Font(fontName, Font.BOLD, fontSize);
        g.setFont(font);

        font = ensureFitsInArea(g, text, font, area, 0.95);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        Rectangle2D bounds = fm.getStringBounds(text, g);

        int x = area.x + (area.width - (int) bounds.getWidth()) / 2;
        int y = area.y + (area.height + fm.getAscent() - fm.getDescent()) / 2;

        ColorScheme colors = getYouTubeColors(img, area, isMainTitle);

        g.setColor(new Color(0, 0, 0, 180));
        int shadowOffset = isMainTitle ? 6 : 4;
        g.drawString(text, x + shadowOffset, y + shadowOffset);

        g.setStroke(new BasicStroke(isMainTitle ? 8 : 5));
        g.setStroke(new BasicStroke(isMainTitle ? TITLE_STROKE_WIDTH : CTA_STROKE_WIDTH));
        int shadowOffset = isMainTitle ? TITLE_SHADOW_OFFSET : CTA_SHADOW_OFFSET;
        g.drawString(text, x + shadowOffset, y + shadowOffset);

        g.setStroke(new BasicStroke(isMainTitle ? 8 : 5));
        g.setColor(colors.outline);
        for (int dx = -3; dx <= 3; dx++) {
            for (int dy = -3; dy <= 3; dy++) {
                if (dx != 0 || dy != 0) {
                    g.drawString(text, x + dx, y + dy);
                }
            }
        }

        GradientPaint gp = new GradientPaint(0, y - 40, colors.primary, 0, y + 15, colors.secondary);
        g.setPaint(gp);
        g.drawString(text, x, y);
    }

    Font ensureFitsInArea(Graphics2D g, String text, Font font, Rectangle area, double utilization) {
        FontMetrics fm = g.getFontMetrics(font);
        Rectangle2D bounds = fm.getStringBounds(text, g);

        while ((bounds.getWidth() > area.width * utilization || fm.getHeight() > area.height * utilization)
                && font.getSize() > 30) {
            int nextSize = Math.max(30, font.getSize() - 6);
            font = new Font(font.getName(), font.getStyle(), nextSize);
            fm = g.getFontMetrics(font);
            bounds = fm.getStringBounds(text, g);
            if (nextSize == 30) {
                break;
            }
        }

        return font;
    }

    private ColorScheme getYouTubeColors(BufferedImage img, Rectangle area, boolean isMainTitle) {
        Color avgColor = averageColorInArea(img, area);
        double brightness = (avgColor.getRed() * 0.299 + avgColor.getGreen() * 0.587 + avgColor.getBlue() * 0.114) / 255.0;

        if (isMainTitle) {
            if (brightness > 0.6) {
                return new ColorScheme(new Color(255, 255, 0), new Color(255, 255, 255), new Color(0, 0, 0));
            } else if (brightness < 0.3) {
                return new ColorScheme(new Color(255, 255, 255), new Color(255, 220, 0), new Color(0, 0, 0));
            }
            return new ColorScheme(new Color(255, 255, 255), new Color(255, 255, 0), new Color(0, 0, 0));
        }
        return new ColorScheme(new Color(255, 69, 0), new Color(255, 140, 0), new Color(255, 255, 255));
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

        return count > 0 ? new Color((int) (sumR / count), (int) (sumG / count), (int) (sumB / count)) : Color.GRAY;
    }

    private void drawGradientOverlay(Graphics2D g, Rectangle area) {
        GradientPaint overlay = new GradientPaint(
                0, area.y, new Color(0, 0, 0, 0),
                0, area.y + area.height, new Color(0, 0, 0, 120));
        g.setPaint(overlay);
        g.fillRect(area.x, area.y, area.width, area.height);
    }

    private List<Rectangle> findSafeRegionsInZone(BufferedImage img, Rectangle safeZone) {
        int rows = 2, cols = 2;
        int cellW = safeZone.width / cols, cellH = safeZone.height / rows;

        List<RegionScore> regions = new ArrayList<>();

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x0 = safeZone.x + c * cellW, y0 = safeZone.y + r * cellH;
                double variance = brightnessVariance(img, x0, y0, cellW, cellH);
                regions.add(new RegionScore(new Rectangle(x0, y0, cellW, cellH), variance));
            }
        }

        regions.sort((a, b) -> Double.compare(a.variance, b.variance));

        List<Rectangle> result = new ArrayList<>();
        result.add(regions.get(0).rect);

        for (int i = 1; i < regions.size(); i++) {
            Rectangle candidate = regions.get(i).rect;
            if (!overlapsSignificantly(result.get(0), candidate)) {
                Rectangle ctaRect = new Rectangle(
                        candidate.x, candidate.y + candidate.height / 2,
                        candidate.width, candidate.height / 2);
                result.add(ctaRect);
                break;
            }
        }

        return result;
    }

    private static class ColorScheme {
        private final Color primary;
        private final Color secondary;
        private final Color outline;

        ColorScheme(Color primary, Color secondary, Color outline) {
            this.primary = primary;
            this.secondary = secondary;
            this.outline = outline;
        }
    }

    private boolean overlapsSignificantly(Rectangle r1, Rectangle r2) {
        Rectangle intersection = r1.intersection(r2);
        double overlapArea = (double) intersection.width * intersection.height;
        double minArea = Math.min((double) r1.width * r1.height, (double) r2.width * r2.height);
        return overlapArea > minArea * 0.3;
    }

    private static class RegionScore {
        private final Rectangle rect;
        private final double variance;

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
                sum += br;
                sq += br * br;
                count++;
            }
        }
        double mean = sum / count;
        return Math.sqrt(sq / count - mean * mean);
    }
}
