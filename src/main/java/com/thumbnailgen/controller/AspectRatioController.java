package com.thumbnailgen.controller;

import com.thumbnailgen.dto.AspectRatioResponse;
import com.thumbnailgen.service.ImageEnhancer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Advertises the fixed YouTube thumbnail canvas used by image generation.
 */
@RestController
@RequestMapping("/api")
public class AspectRatioController {

    @GetMapping("/aspect-ratio")
    public ResponseEntity<AspectRatioResponse> aspectRatio() {
        int width = ImageEnhancer.TARGET_WIDTH;
        int height = ImageEnhancer.TARGET_HEIGHT;
        return ResponseEntity.ok(new AspectRatioResponse(simplifyRatio(width, height), width, height));
    }

    static String simplifyRatio(int width, int height) {
        if (width <= 0 || height <= 0) {
            return "1:1";
        }
        int divisor = gcd(width, height);
        return (width / divisor) + ":" + (height / divisor);
    }

    private static int gcd(int a, int b) {
        int x = Math.abs(a);
        int y = Math.abs(b);
        while (y != 0) {
            int next = x % y;
            x = y;
            y = next;
        }
        return x == 0 ? 1 : x;
    }
}
