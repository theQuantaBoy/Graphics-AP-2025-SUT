package com.ap_graphics.model.combat;

import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TentacleMonster extends Enemy
{
    private final Animation<TextureRegion> spawnAnimation;

    public TentacleMonster(EnemyType type, float x, float y)
    {
        super(type, x, y);
        spawnAnimation = type.getSpawnAnimation();
        this.hp = 25;
        this.speed = 50f;
    }

    @Override
    public void update(float delta, Player player)
    {
        moveTowardPlayer(delta, player);
        super.update(delta);
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        super.render(batch, delta); // Use superclass rendering
    }
}
