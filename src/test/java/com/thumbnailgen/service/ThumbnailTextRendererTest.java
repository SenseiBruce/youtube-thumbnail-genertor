package com.thumbnailgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ThumbnailTextRendererTest {

    private ThumbnailTextRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new ThumbnailTextRenderer();
    }

    @Test
    void defaultCta_isWatchNow() {
        assertEquals("WATCH NOW", renderer.defaultCta());
    }

    @Test
    void parseColor_hexAndInvalid() {
        assertEquals(Color.RED, renderer.parseColor("#FF0000"));
        assertEquals(Color.WHITE, renderer.parseColor("not-a-color"));
        assertEquals(Color.WHITE, renderer.parseColor(null));
    }

    @Test
    void needsOverlay_uniformArea_isFalse() {
        BufferedImage img = solid(200, 200, Color.GRAY);
        assertFalse(renderer.needsOverlay(img, new Rectangle(10, 10, 100, 100)));
    }

    @Test
    void needsOverlay_highVarianceArea_isTrue() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 100, 200);
        g.setColor(Color.WHITE);
        g.fillRect(100, 0, 100, 200);
        g.dispose();
        assertTrue(renderer.needsOverlay(img, new Rectangle(0, 0, 200, 200)));
    }

    @Test
    void ensureFitsInArea_shrinksOversizedFont() {
        BufferedImage img = solid(200, 100, Color.DARK_GRAY);
        Graphics2D g = img.createGraphics();
        Font huge = new Font("Arial", Font.BOLD, 200);
        Font fitted = renderer.ensureFitsInArea(g, "HELLO WORLD TITLE", huge, new Rectangle(0, 0, 180, 80), 0.9);
        g.dispose();
        assertTrue(fitted.getSize() < huge.getSize());
        assertTrue(fitted.getSize() >= 30);
    }

    @Test
    void drawAIStyledTitle_preservesCanvasSize() {
        BufferedImage src = solid(1280, 720, Color.BLUE);
        AIAssistantService.ThumbnailStyle style = new AIAssistantService.ThumbnailStyle(
                "TITLE", "#FFFFFF", "#FFFF00", "Impact", "top");
        BufferedImage out = renderer.drawAIStyledTitle(src, style);
        assertEquals(1280, out.getWidth());
        assertEquals(720, out.getHeight());
    }

    private static BufferedImage solid(int w, int h, Color color) {
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        g.dispose();
        return image;
    }
}
