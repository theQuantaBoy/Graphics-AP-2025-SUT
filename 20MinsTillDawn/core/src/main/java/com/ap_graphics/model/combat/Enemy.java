package com.ap_graphics.model.combat;

import com.ap_graphics.model.AbstractAnimatedEntity;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends AbstractAnimatedEntity
{
    protected int hp;
    protected boolean isDead;
    protected final EnemyType type;
    protected float speed;

    private float deathStateTime = 0;
    private boolean isDying = false;

    public Enemy(EnemyType type, float x, float y)
    {
        super(x, y);
        this.type = type;
        this.currentAnimation = type.getIdleAnimation();
        this.position = new Vector2(x, y);
    }

    public void update(float delta, Player player)
    {
        if (isDead()) return;
        super.update(delta);
    }

    public boolean isDead()
    {
        return isDead;
    }

    public void moveTowardPlayer(float delta, Player player)
    {
        if (player == null) return;

        float dx = player.getPosX() - position.x;
        float dy = player.getPosY() - position.y;

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1e-3)
        {
            float normX = dx / distance;
            float normY = dy / distance;

            position.x += normX * speed * delta;
            position.y += normY * speed * delta;
        }
    }

    public void moveAwayFromPlayer(float delta, Player player)
    {
        if (player == null) return;

        float dx = position.x - player.getPosX();
        float dy = position.y - player.getPosY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1e-3)
        {
            float normX = dx / distance;
            float normY = dy / distance;

            // Optional: You can tweak the multiplier to increase knockback strength
            float knockbackSpeed = speed * 70f;

            position.x += normX * knockbackSpeed * delta;
            position.y += normY * knockbackSpeed * delta;
        }
    }

    public void takeDamage(int dmg)
    {
        hp -= dmg;
        if (hp <= 0)
        {
            isDead = true;
            die();
        }
    }

    public Vector2 getPosition()
    {
        return position;
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        if (!isDead)
        {
            super.render(batch, delta);
        }
    }

    public void die()
    {
        isDying = true;
        deathStateTime = 0;
    }

    public EnemyType getType()
    {
        return type;
    }

    public int getHp()
    {
        return hp;
    }

    public void setHp(int hp)
    {
        this.hp = hp;
    }
}
