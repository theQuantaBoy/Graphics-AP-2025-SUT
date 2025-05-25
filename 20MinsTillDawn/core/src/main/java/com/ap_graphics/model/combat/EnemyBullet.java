package com.ap_graphics.model.combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class EnemyBullet extends Bullet
{
    public EnemyBullet(float startX, float startY, Vector2 dir)
    {
        super(startX, startY, dir, 1);
        texture = new Texture("images/weapons/Enemy_Bullet.png");
        speed = 300f;
    }
}
