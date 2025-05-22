package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import javax.swing.text.Position;

public abstract class Enemy extends AbstractAnimatedEntity
{
    protected int hp;
    protected boolean isDead;
    protected final EnemyType type;
    protected float speed;

    public Enemy(EnemyType type, float x, float y)
    {
        super(x, y);
        this.type = type;
        this.currentAnimation = type.getIdleAnimation();
        this.position = new Vector2(x, y);
    }

    public void update(float delta, Player player)
    {
        super.update(delta);
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void moveTowardPlayer(float delta, Player player) { // ✅ Accept Player parameter
        if (player == null) return;

        float dx = player.getPosX() - position.x;
        float dy = player.getPosY() - position.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1e-3) {
            float normX = dx / distance;
            float normY = dy / distance;

            position.x += normX * speed * delta;
            position.y += normY * speed * delta;
        }
    }

    public void takeDamage(int dmg)
    {
        hp -= dmg;
        if (hp <= 0)
        {
            isDead = true;
            // Spawn XP Orb (see next section)
        }
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Rectangle getBounds() {
        TextureRegion frame = currentAnimation.getKeyFrame(0);
        return new Rectangle(
            position.x,
            position.y,
            frame.getRegionWidth(),
            frame.getRegionHeight()
        );
    }
}
