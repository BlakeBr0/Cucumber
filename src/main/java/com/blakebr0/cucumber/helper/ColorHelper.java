package com.blakebr0.cucumber.helper;

public final class ColorHelper {
    public static int intColor(int r, int g, int b) {
        return (r * 65536 + g * 256 + b);
    }

    public static int[] hexToRGB(int hex) {
        int[] colors = new int[3];

        colors[0] = hex >> 16 & 255;
        colors[1] = hex >> 8 & 255;
        colors[2] = hex & 255;

        return colors;
    }

    private static float interpolate(float a, float b, float proportion) {
        return (a + ((b - a) * proportion));
    }

    public static int interpolateColor(int a, int b, float proportion) {
        float[] hsva = new float[3];
        float[] hsvb = new float[3];

        RGBtoHSB((a >> 16) & 0xFF, (a >> 8) & 0xFF, a & 0xFF, hsva);
        RGBtoHSB((b >> 16) & 0xFF, (b >> 8) & 0xFF, b & 0xFF, hsvb);

        for (int i = 0; i < 3; i++) {
            hsvb[i] = interpolate(hsva[i], hsvb[i], proportion);
        }

        float alpha = interpolate((a >> 24) & 0xFF, (b >> 24) & 0xFF, proportion);

        return HSBtoRGB(hsvb[0], hsvb[1], hsvb[2]) | ((int) (alpha * 255) & 0xFF);
    }

    public static int saturate(int color, float saturation) {
        float[] hsv = new float[3];
        RGBtoHSB((color >> 16) & 255, (color >> 8) & 255, color & 255, hsv);
        hsv[1] *= saturation;
        return HSBtoRGB(hsv[0], hsv[1], hsv[2]);
    }

    public static int hexToIntWithAlpha(int hex, int alpha) {
        return alpha << 24 | hex & 0x00FFFFFF;
    }

    public static int calcAlpha(double current, double max) {
        return (int) ((max - current) / max) * 255;
    }

    public static int HSBtoRGB(float hue, float saturation, float brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            float h = (hue - (float)Math.floor(hue)) * 6.0f;
            float f = h - (float)java.lang.Math.floor(h);
            float p = brightness * (1.0f - saturation);
            float q = brightness * (1.0f - saturation * f);
            float t = brightness * (1.0f - (saturation * (1.0f - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (t * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 1:
                    r = (int) (q * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (p * 255.0f + 0.5f);
                    break;
                case 2:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (brightness * 255.0f + 0.5f);
                    b = (int) (t * 255.0f + 0.5f);
                    break;
                case 3:
                    r = (int) (p * 255.0f + 0.5f);
                    g = (int) (q * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 4:
                    r = (int) (t * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (brightness * 255.0f + 0.5f);
                    break;
                case 5:
                    r = (int) (brightness * 255.0f + 0.5f);
                    g = (int) (p * 255.0f + 0.5f);
                    b = (int) (q * 255.0f + 0.5f);
                    break;
            }
        }

        return 0xff000000 | (r << 16) | (g << 8) | b;
    }


    public static float[] RGBtoHSB(int r, int g, int b, float[] hsbvals) {
        float hue, saturation, brightness;
        if (hsbvals == null) {
            hsbvals = new float[3];
        }

        int cmax = Math.max(r, g);
        if (b > cmax) cmax = b;
        int cmin = Math.min(r, g);
        if (b < cmin) cmin = b;

        brightness = ((float) cmax) / 255.0f;
        if (cmax != 0) {
            saturation = ((float) (cmax - cmin)) / ((float) cmax);
        } else {
            saturation = 0;
        }

        if (saturation == 0) {
            hue = 0;
        } else {
            float redc = ((float) (cmax - r)) / ((float) (cmax - cmin));
            float greenc = ((float) (cmax - g)) / ((float) (cmax - cmin));
            float bluec = ((float) (cmax - b)) / ((float) (cmax - cmin));
            if (r == cmax)
                hue = bluec - greenc;
            else if (g == cmax)
                hue = 2.0f + redc - bluec;
            else
                hue = 4.0f + greenc - redc;
            hue = hue / 6.0f;
            if (hue < 0)
                hue = hue + 1.0f;
        }

        hsbvals[0] = hue;
        hsbvals[1] = saturation;
        hsbvals[2] = brightness;

        return hsbvals;
    }
}
