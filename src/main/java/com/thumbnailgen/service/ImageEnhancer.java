package com.thumbnailgen.service;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;

/**
 * Resizes and enhances source images to YouTube thumbnail dimensions.
 */
@Service
public class ImageEnhancer {

    public static final int TARGET_WIDTH = 1280;
    public static final int TARGET_HEIGHT = 720;

    public BufferedImage enhance(BufferedImage src) {
        BufferedImage resized = Scalr.resize(
                src, Scalr.Method.QUALITY, Scalr.Mode.FIT_EXACT, TARGET_WIDTH, TARGET_HEIGHT, Scalr.OP_ANTIALIAS);
        RescaleOp rescale = new RescaleOp(1.05f, 5f, null);
        BufferedImage bright = new BufferedImage(resized.getWidth(), resized.getHeight(), BufferedImage.TYPE_INT_ARGB);
        rescale.filter(resized, bright);
        float[] sharpen = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
        return new ConvolveOp(new Kernel(3, 3, sharpen)).filter(bright, null);
    }
}
