package com.ap_graphics.model;

import com.badlogic.gdx.math.Vector2;

public class XPOrb {
    private Vector2 position;
    private boolean collected;
    private static final float ATTRACTION_SPEED = 150f;

    public XPOrb(float x, float y) {
        position = new Vector2(x, y);
    }

    public void update(float delta, Player player) {
        Vector2 direction = new Vector2(
            player.getPosX() - position.x,
            player.getPosY() - position.y
        ).nor();

        position.add(direction.scl(ATTRACTION_SPEED * delta));
    }

    public boolean isCollected() { return collected; }
}
