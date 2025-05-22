package com.ap_graphics.model;

import com.badlogic.gdx.math.Vector2;

public class XPOrb
{
    private Vector2 position;
    private boolean isCollected = false;
    private static final float ATTRACTION_SPEED = 100f;

    public XPOrb(float x, float y)
    {
        this.position = new Vector2(x, y);
    }

    public void update(float delta, Player player)
    {
        // Move towards player
        Vector2 direction = new Vector2(
            player.getPosX() - position.x,
            player.getPosY() - position.y
        ).nor();
        position.x += direction.x * ATTRACTION_SPEED * delta;
        position.y += direction.y * ATTRACTION_SPEED * delta;
    }
}
