package com.thumbnailgen.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageService {

    private final ImageEnhancer imageEnhancer;
    private final ThumbnailTextRenderer textRenderer;

    public ImageService(ImageEnhancer imageEnhancer, ThumbnailTextRenderer textRenderer) {
        this.imageEnhancer = imageEnhancer;
        this.textRenderer = textRenderer;
    }

    public byte[] generateThumbnail(byte[] imageBytes, String title) throws IOException {
        BufferedImage src = readImage(imageBytes);
        BufferedImage enhanced = imageEnhancer.enhance(src);
        BufferedImage finalImage = textRenderer.drawSmartTitle(enhanced, title);
        return toPng(finalImage);
    }

    public byte[] generateAIThumbnail(byte[] imageBytes, AIAssistantService.ThumbnailStyle aiStyle) throws IOException {
        BufferedImage src = readImage(imageBytes);
        BufferedImage enhanced = imageEnhancer.enhance(src);
        BufferedImage finalImage = textRenderer.drawAIStyledTitle(enhanced, aiStyle);
        return toPng(finalImage);
    }

    private static BufferedImage readImage(byte[] imageBytes) throws IOException {
        BufferedImage src = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (src == null) {
            throw new IOException("Invalid image file");
        }
        return src;
    }

    private static byte[] toPng(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return baos.toByteArray();
    }
}
