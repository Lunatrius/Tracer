package com.github.lunatrius.tracer.trace.registry;

public class TraceRenderInformation {
    private final int red;
    private final int green;
    private final int blue;
    private final int alpha;
    private final int ttl;
    private final double thickness;
    private final double offsetY;

    public TraceRenderInformation(final int red, final int green, final int blue, final int alpha, final int ttl, final double thickness, final double offsetY) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.ttl = ttl;
        this.thickness = thickness;
        this.offsetY = offsetY;
    }

    public int getRed() {
        return this.red;
    }

    public int getGreen() {
        return this.green;
    }

    public int getBlue() {
        return this.blue;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public int getTTL() {
        return this.ttl;
    }

    public float getThickness() {
        return (float) this.thickness;
    }

    public double getOffsetY() {
        return this.offsetY;
    }
}
