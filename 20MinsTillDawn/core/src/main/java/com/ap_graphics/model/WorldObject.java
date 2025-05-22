package com.ap_graphics.model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public interface WorldObject
{
    void update(float delta);
    void render(SpriteBatch batch, float delta);
    Rectangle getBounds();
}
