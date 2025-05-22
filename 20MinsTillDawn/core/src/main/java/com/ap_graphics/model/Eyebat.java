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
    public void render(SpriteBatch batch, float delta)
    {
        super.render(batch, delta); // Use superclass rendering
    }

    @Override
    public void takeDamage(int dmg)
    {

    }

    @Override
    public void update(float delta)
    {

    }
}
