package com.music.vibe;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SquigglyProgress extends Drawable {
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path path = new Path();
    private float waveLength = 20f;
    private float lineAmplitude = 5f;
    private float phaseSpeed = 10f;
    private float strokeWidth = 4f;
    private boolean transitionEnabled = true;
    private boolean animate = true;

    public SquigglyProgress() {
        paint.setStyle(Paint.Style.STROKE);
    }

    public void setWaveLength(float waveLength) {
        this.waveLength = waveLength;
        invalidateSelf();
    }

    public void setLineAmplitude(float lineAmplitude) {
        this.lineAmplitude = lineAmplitude;
        invalidateSelf();
    }

    public void setPhaseSpeed(float phaseSpeed) {
        this.phaseSpeed = phaseSpeed;
        invalidateSelf();
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        paint.setStrokeWidth(strokeWidth);
        invalidateSelf();
    }

    public void setTransitionEnabled(boolean enabled) {
        this.transitionEnabled = enabled;
        invalidateSelf();
    }

    public void setAnimate(boolean animate) {
        this.animate = animate;
        invalidateSelf();
    }

    public void setTint(int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int width = getBounds().width();
        int height = getBounds().height();
        if (width <= 0 || height <= 0) return;

        float centerY = height / 2f;
        path.reset();
        path.moveTo(0, centerY);

        if (waveLength <= 0) {
            path.lineTo(width, centerY);
        } else {
            for (float x = 0; x <= width; x += waveLength) {
                path.quadTo(x + waveLength / 4f, centerY - lineAmplitude, x + waveLength / 2f, centerY);
                path.quadTo(x + waveLength * 3f / 4f, centerY + lineAmplitude, x + waveLength, centerY);
            }
        }
        canvas.drawPath(path, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
