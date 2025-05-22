package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class TentacleMonster extends Enemy
{
    private final Animation<TextureRegion> spawnAnimation;

    public TentacleMonster(EnemyType type, float x, float y)
    {
        super(type, x, y);
        spawnAnimation = type.getSpawnAnimation();
        this.hp = 25;
    }

    @Override
    public void update(float delta, Player player)
    {
        if (position == null)
        {
            position = new Vector2(0, 0); // default position or handle appropriately
        }

        Vector2 direction = new Vector2(player.getPosX(), player.getPosY()).sub(position).nor();
        position.add(direction.scl(speed * delta));
        super.update(delta, player);
    }

    @Override
    public void update(float delta)
    {

    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        super.render(batch, delta); // Use superclass rendering
    }

    @Override
    public Rectangle getBounds() {
        TextureRegion sample = currentAnimation.getKeyFrame(0);
        return new Rectangle(position.x, position.y, sample.getRegionWidth(), sample.getRegionHeight());
    }

    @Override
    public void takeDamage(int dmg)
    {

    }
}
