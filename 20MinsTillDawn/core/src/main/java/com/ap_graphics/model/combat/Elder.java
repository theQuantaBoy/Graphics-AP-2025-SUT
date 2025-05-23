package com.ap_graphics.model.combat;

import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Elder extends Enemy
{
    private final float dashCooldown = 5f;
    private float lastDash = 0f;

    public Elder(float x, float y)
    {
        super(EnemyType.ELDER_BRAIN, x, y);
        this.hp = 400;
        this.speed = 80f;
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

        lastDash += delta;

        if (lastDash >= dashCooldown)
        {
            float originalSpeed = speed;
            float dashMultiplier = 4f;
            speed = originalSpeed * dashMultiplier;
            moveTowardPlayer(delta, player);
            speed = originalSpeed;
            lastDash = 0f;
        }
    }
}
