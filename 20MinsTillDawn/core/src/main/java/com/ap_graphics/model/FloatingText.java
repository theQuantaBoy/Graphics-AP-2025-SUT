package com.ap_graphics.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class FloatingText
{
    private String text;
    private Vector2 position;
    private float timer = 0;
    private final float DURATION = 1f;
    private final Color baseColor;

    public FloatingText(String text, Vector2 startPosition, Color color)
    {
        this.text = text;
        this.position = startPosition.cpy();
        this.baseColor = color;
    }

    public boolean update(float delta)
    {
        timer += delta;
        position.y += 20 * delta;
        return timer >= DURATION;
    }

    public void render(SpriteBatch batch, BitmapFont font)
    {
        float alpha = 1 - (timer / DURATION);
        Color faded = baseColor.cpy();
        faded.a = alpha;
        font.setColor(faded);
        font.draw(batch, text, position.x, position.y);
    }
}
