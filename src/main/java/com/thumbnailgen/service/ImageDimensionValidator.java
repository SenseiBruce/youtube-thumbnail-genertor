package com.thumbnailgen.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Advisory YouTube thumbnail size check (1280x720, 16:9).
 */
@Service
public class ImageDimensionValidator {

    public static final int RECOMMENDED_WIDTH = 1280;
    public static final int RECOMMENDED_HEIGHT = 720;
    private static final double TARGET_ASPECT = 16.0 / 9.0;
    public static final double ASPECT_TOLERANCE = 0.05;

    public ImageCheck inspect(byte[] imageBytes) throws IOException {
        if (imageBytes == null || imageBytes.length == 0) {
            throw new IllegalArgumentException("Uploaded file must not be empty");
        }
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (image == null) {
            throw new IllegalArgumentException("Uploaded file must be a readable image");
        }
        int width = image.getWidth();
        int height = image.getHeight();
        double aspect = height == 0 ? 0 : (double) width / (double) height;
        List<String> warnings = new ArrayList<>();
        if (width < RECOMMENDED_WIDTH || height < RECOMMENDED_HEIGHT) {
            warnings.add("YouTube recommends at least 1280x720");
        }
        if (Math.abs(aspect - TARGET_ASPECT) > ASPECT_TOLERANCE) {
            warnings.add("Aspect ratio is not 16:9");
        }
        boolean ok = warnings.isEmpty();
        return new ImageCheck(width, height, aspect, ok, warnings);
    }

    public static final class ImageCheck {
        private final int width;
        private final int height;
        private final double aspectRatio;
        private final boolean ok;
        private final List<String> warnings;

        public ImageCheck(int width, int height, double aspectRatio, boolean ok, List<String> warnings) {
            this.width = width;
            this.height = height;
            this.aspectRatio = aspectRatio;
            this.ok = ok;
            this.warnings = warnings;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public double getAspectRatio() {
            return aspectRatio;
        }

        public boolean isOk() {
            return ok;
        }

        public List<String> getWarnings() {
            return warnings;
        }
    }
}
