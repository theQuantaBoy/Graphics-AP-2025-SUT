package com.ap_graphics.model.combat;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class XpOrb
{
    private Vector2 position;
    private boolean collected;
    private final Texture texture = new Texture("images/essential/orb.png");

    private float lifespan = 3f; // 3 seconds
    private float alpha = 1f;

    public XpOrb(float x, float y)
    {
        position = new Vector2(x, y);
    }

    public void update(float delta)
    {
        lifespan -= delta;
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, position.x, position.y);
    }

    public boolean shouldRemove()
    {
        return lifespan <= 0;
    }

    public Rectangle getBounds()
    {
        return new Rectangle(
            position.x - texture.getWidth() / 2f,  // Center X
            position.y - texture.getHeight() / 2f, // Center Y
            texture.getWidth(),                     // Width
            texture.getHeight()                     // Height
        );
    }

    public Vector2 getPosition()
    {
        return position;
    }

    public Texture getTexture()
    {
        return texture;
    }
}
