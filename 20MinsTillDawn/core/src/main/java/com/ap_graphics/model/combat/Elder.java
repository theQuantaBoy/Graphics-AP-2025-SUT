package com.ap_graphics.model.combat;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.EnemyType;
import com.ap_graphics.model.enums.SoundEffectType;
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
            dash(delta, player);
            SoundManager.getInstance().playSFX(SoundEffectType.STANDARD_WEAPON_WHOOSH_01);
            lastDash = 0f;
        }
    }

    public void dash(float delta, Player player)
    {
        if (player == null) return;

        float dx = - position.x + player.getPosX();
        float dy = - position.y + player.getPosY();

        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance > 1e-3)
        {
            float normX = dx / distance;
            float normY = dy / distance;

            float knockbackSpeed = speed * 90f;

            position.x += normX * knockbackSpeed * delta;
            position.y += normY * knockbackSpeed * delta;
        }
    }
}
