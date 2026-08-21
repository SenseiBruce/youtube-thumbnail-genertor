package com.thumbnailgen.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageServiceTest {

    private ImageService imageService;

    @BeforeEach
    void setUp() {
        imageService = new ImageService();
    }

    @Test
    void generateThumbnail_fromInMemoryImage_returnsDecodablePng() throws IOException {
        byte[] inputPng = solidColorPng(640, 360, Color.DARK_GRAY);

        byte[] output = imageService.generateThumbnail(inputPng, "TEST TITLE");

        assertNotNull(output);
        assertTrue(output.length > 0, "thumbnail bytes should be non-empty");

        BufferedImage decoded = ImageIO.read(new ByteArrayInputStream(output));
        assertNotNull(decoded, "output should be a decodable PNG");
        assertTrue(decoded.getWidth() > 0);
        assertTrue(decoded.getHeight() > 0);
    }

    @Test
    void generateAIThumbnail_appliesStyleAndReturnsPng() throws IOException {
        byte[] inputPng = solidColorPng(320, 180, Color.BLUE);
        AIAssistantService.ThumbnailStyle style = new AIAssistantService.ThumbnailStyle(
                "EPIC CLIP", "#FFFFFF", "#FFFF00", "Impact", "center");

        byte[] output = imageService.generateAIThumbnail(inputPng, style);

        assertNotNull(output);
        assertTrue(output.length > 0);
        assertNotNull(ImageIO.read(new ByteArrayInputStream(output)));
    }

    private static byte[] solidColorPng(int width, int height, Color color) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(color);
        g.fillRect(0, 0, width, height);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
