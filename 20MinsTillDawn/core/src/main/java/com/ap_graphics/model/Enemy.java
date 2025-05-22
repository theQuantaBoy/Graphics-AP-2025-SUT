package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class Enemy extends AbstractAnimatedEntity
{
    protected float x, y;
    protected int hp;
    protected boolean isDead;
    protected final EnemyType type;
    protected float speed;

    protected TextureRegion currentFrame;
    protected Vector2 position;

    public Enemy(EnemyType type, float x, float y)
    {
        super(x, y);
        this.type = type;
        this.currentAnimation = type.getIdleAnimation();
    }

    public void update(float delta, Player player)
    {
        Vector2 direction = new Vector2(player.getPosX(), player.getPosY()).sub(position).nor();
        position.add(direction.scl(speed * delta));

        facingRight = (direction.x > 0);
        super.update(delta); // Updates animation timing via AbstractAnimatedEntity
    }

    public abstract void takeDamage(int dmg);

    public boolean isDead()
    {
        return isDead;
    }
}
