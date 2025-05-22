package com.ap_graphics.model;

import com.ap_graphics.model.enums.WeaponType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Bullet
{
    private Vector2 position;
    private Vector2 direction;
    private float speed = 700f; // TODO: maybe change later?
    private int damage;
    private final Texture texture = new Texture("images/weapons/Icon_Bullet_Storm.png");
    private float rotation;

    public Bullet(float startX, float startY, Vector2 dir, int damage)
    {
        this.position = new Vector2(startX, startY);
        this.direction = dir.nor(); // Normalize direction
        this.damage = damage;
        this.rotation = direction.angleDeg();
    }

    public void update(float delta)
    {
        position.x += direction.x * speed * delta;
        position.y += direction.y * speed * delta;
    }

    public Rectangle getBounds()
    {
        // Use texture dimensions for hitbox
        return new Rectangle(
            position.x - texture.getWidth() / 2f,  // Center hitbox on position
            position.y - texture.getHeight() / 2f,
            texture.getWidth(),
            texture.getHeight()
        );
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(
            texture,
            position.x - texture.getWidth() / 2f,  // Center texture
            position.y - texture.getHeight() / 2f,
            texture.getWidth() / 2f,  // Origin X (center)
            texture.getHeight() / 2f, // Origin Y (center)
            texture.getWidth(),
            texture.getHeight(),
            1f,
            1f,
            rotation,  // Rotate texture to face direction
            0,
            0,
            texture.getWidth(),
            texture.getHeight(),
            false,
            false
        );
    }

    public int getDamage()
    {
        return damage;
    }

    public Vector2 getPosition()
    {
        return position;
    }
}
