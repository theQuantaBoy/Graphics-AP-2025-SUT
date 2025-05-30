package com.ap_graphics.model;

import com.badlogic.gdx.math.Rectangle;

public class ShieldZone
{
    private Rectangle bounds;
    private float shrinkRate; // pixels per second

    public ShieldZone(float centerX, float centerY, float width, float height, float shrinkRate)
    {
        this.bounds = new Rectangle(centerX - width / 2f, centerY - height / 2f, width, height);
        this.shrinkRate = shrinkRate;
    }

    public void update(float delta)
    {
        float shrinkAmount = shrinkRate * delta;

        float newWidth = Math.max(0, bounds.width - shrinkAmount);
        float newHeight = Math.max(0, bounds.height - shrinkAmount);

        float centerX = bounds.x + bounds.width / 2f;
        float centerY = bounds.y + bounds.height / 2f;

        bounds.setSize(newWidth, newHeight);
        bounds.setCenter(centerX, centerY);
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public boolean isOutside(float x, float y)
    {
        return !bounds.contains(x, y);
    }
}
