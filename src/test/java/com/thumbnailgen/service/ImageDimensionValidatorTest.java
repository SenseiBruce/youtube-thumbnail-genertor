package com.thumbnailgen.service;

import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageDimensionValidatorTest {

    private final ImageDimensionValidator validator = new ImageDimensionValidator();

    @Test
    void recommendedSizeIsOk() throws IOException {
        byte[] png = png(1280, 720);
        ImageDimensionValidator.ImageCheck check = validator.inspect(png);
        assertTrue(check.isOk());
        assertTrue(check.getWarnings().isEmpty());
    }

    @Test
    void smallImageWarns() throws IOException {
        byte[] png = png(640, 360);
        ImageDimensionValidator.ImageCheck check = validator.inspect(png);
        assertFalse(check.isOk());
        assertTrue(check.getWarnings().stream().anyMatch(w -> w.contains("1280x720")));
    }

    @Test
    void unreadableBytesRejected() {
        assertThrows(IllegalArgumentException.class, () -> validator.inspect(new byte[]{1, 2, 3}));
    }

    private static byte[] png(int width, int height) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImageIO.write(image, "png", out);
        return out.toByteArray();
    }
}
