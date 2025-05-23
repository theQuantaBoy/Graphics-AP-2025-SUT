package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Eyebat extends Enemy
{
    private final float shootCooldown = 3f;
    private float timeSinceLastShot = 0f;

    public Eyebat(float x, float y)
    {
        super(EnemyType.EYE_BAT, x, y);
        this.hp = 50;
        this.speed = 100f;
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        super.render(batch, delta);
    }

    @Override
    public void update(float delta, Player player)
    {
        moveTowardPlayer(delta, player);
        super.update(delta);

        timeSinceLastShot += delta;

        if (timeSinceLastShot >= shootCooldown)
        {
            shoot(App.getCurrentPlayer());
            timeSinceLastShot = 0f;
        }
    }

    public void shoot(Player player)
    {
        Vector2 from = new Vector2(position.x, position.y);
        Vector2 to = new Vector2(player.getPosX(), player.getPosY());
        Vector2 direction = to.sub(from).nor();

        EnemyBullet bullet = new EnemyBullet(position.x, position.y, direction);
        GameWorld.getInstance().getEnemyBullets().add(bullet);
    }
}
