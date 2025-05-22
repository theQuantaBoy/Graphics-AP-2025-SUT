package com.ap_graphics.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Tree implements WorldObject
{
    private final com.badlogic.gdx.graphics.Texture texture;
    private final float x, y;
    public Tree(com.badlogic.gdx.graphics.Texture tex, float x, float y)
    {
        this.texture = tex;
        this.x = x;
        this.y = y;
    }

    @Override public void update(float delta) {}

    @Override public void render(SpriteBatch batch, float delta)
    {
        batch.draw(texture, x, y);
    }

    @Override public Rectangle getBounds()
    {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
}
