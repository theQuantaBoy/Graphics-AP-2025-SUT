package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Eyebat extends Enemy
{
    public Eyebat(float x, float y)
    {
        super(EnemyType.EYE_BAT, x, y);
        this.hp = 50;
        this.speed = 100f;
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        super.render(batch, delta); // Use superclass rendering
    }

    @Override
    public void update(float delta, Player player)
    {
        moveTowardPlayer(delta, player);
        super.update(delta);
    }

    @Override
    public void takeDamage(int dmg) {
        hp -= dmg;
        if(hp <= 0) {
            // Special death behavior for tentacle monsters
            die();
            // Add tentacle-specific death effects
        }
    }

    private void die() {
        // Play death animation
        // Spawn tentacle-specific loot
        isDead = true;
        GameWorld.getInstance().addXpOrb(new XPOrb(position.x, position.y));
    }
}
