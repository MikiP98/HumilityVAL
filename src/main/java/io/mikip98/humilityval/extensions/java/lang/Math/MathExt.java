package io.mikip98.humilityval.extensions.java.lang.Math;

import manifold.ext.rt.api.Extension;

@Extension
public class MathExt {
    @Extension
    public static int clamp(long value, int min, int max) {
        validate(min, max);
        return (int) Math.max(min, Math.min(max, value));
    }

    @Extension
    public static long clamp(long value, long min, long max) {
        validate(min, max);
        return Math.max(min, Math.min(max, value));
    }

    @Extension
    public static float clamp(float value, float min, float max) {
        validate(min, max);
        return Math.max(min, Math.min(max, value));
    }

    @Extension
    public static double clamp(double value, double min, double max) {
        validate(min, max);
        return Math.max(min, Math.min(max, value));
    }

    private static <T extends Comparable<T>> void validate(T min, T max) {
        if (min > max) throw new IllegalArgumentException(min + " > " + max);
    }
}
