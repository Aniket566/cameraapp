package com.snap.cameraapp;


import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.annotation.NonNull;

import com.otaliastudios.cameraview.filter.BaseFilter;

import java.nio.ByteBuffer;

public class BlackAndWhiteFilter extends BaseFilter {

    private final static String FRAGMENT_SHADER = "#extension GL_OES_EGL_image_external : require\n"
            + "precision mediump float;\n"
            + "varying vec2 "+DEFAULT_FRAGMENT_TEXTURE_COORDINATE_NAME+";\n"
            + "uniform samplerExternalOES sTexture;\n" + "void main() {\n"
            + "  vec4 color = texture2D(sTexture, "+DEFAULT_FRAGMENT_TEXTURE_COORDINATE_NAME+");\n"
            + "  float colorR = (color.r + color.g + color.b) / 3.0;\n"
            + "  float colorG = (color.r + color.g + color.b) / 3.0;\n"
            + "  float colorB = (color.r + color.g + color.b) / 3.0;\n"
            + "  gl_FragColor = vec4(colorR, colorG, colorB, color.a);\n"
            + "}\n";

    public BlackAndWhiteFilter() { }

    @NonNull
    @Override
    public String getFragmentShader() {
        return FRAGMENT_SHADER;
    }

    public Bitmap processFilter(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap processedBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = processedBitmap.getPixel(x, y);

                int red = Color.red(pixel);
                int green = Color.green(pixel);
                int blue = Color.blue(pixel);

                int gray = (int) (red * 0.3 + green * 0.59 + blue * 0.11);

                int newPixel = Color.argb(Color.alpha(pixel), gray, gray, gray);

                processedBitmap.setPixel(x, y, newPixel);
            }
        }

        return processedBitmap;
    }


}
