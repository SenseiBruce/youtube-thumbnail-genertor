package com.thumbnailgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageEnhancerTest {

    private ImageEnhancer enhancer;

    @BeforeEach
    void setUp() {
        enhancer = new ImageEnhancer();
    }

    @Test
    void enhance_smallSource_returnsExactYoutubeDimensions() {
        BufferedImage src = solid(64, 48, Color.RED);
        BufferedImage out = enhancer.enhance(src);
        assertEquals(ImageEnhancer.TARGET_WIDTH, out.getWidth());
        assertEquals(ImageEnhancer.TARGET_HEIGHT, out.getHeight());
    }

    @Test
    void enhance_largeSource_returnsExactYoutubeDimensions() {
        BufferedImage src = solid(2400, 1600, Color.BLUE);
        BufferedImage out = enhancer.enhance(src);
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
