package com.thumbnailgen.dto;

/**
 * Allowed deviation from 16:9 when checking uploaded thumbnail images.
 */
public class AspectToleranceResponse {

    private final double tolerance;

    public AspectToleranceResponse(double tolerance) {
        this.tolerance = tolerance;
    }

    public double getTolerance() {
        return tolerance;
    }
}
