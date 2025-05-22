package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Elder extends Enemy
{
    public Elder(float x, float y)
    {
        super(EnemyType.ELDER_BRAIN, x, y);
        this.hp = 400;
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
    public void update(float delta, Player player)
    {

    }
}
