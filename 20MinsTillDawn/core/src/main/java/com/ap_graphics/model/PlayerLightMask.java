package com.ap_graphics.model;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector3;

public class PlayerLightMask
{
    private float halfSize;

    public PlayerLightMask(float radius)
    {
        this.halfSize = radius;
    }

    public void draw(ShapeRenderer shapeRenderer, Vector2 playerPos, Camera camera)
    {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        float radius = halfSize;

        float worldWidth = camera.viewportWidth;
        float worldHeight = camera.viewportHeight;
        float camX = camera.position.x - worldWidth / 2f;
        float camY = camera.position.y - worldHeight / 2f;

        float x = playerPos.x;
        float y = playerPos.y;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.50f);

        // TOP
        shapeRenderer.rect(camX, y + radius, worldWidth, camY + worldHeight - (y + radius));
        // BOTTOM
        shapeRenderer.rect(camX, camY, worldWidth, y - radius - camY);
        // LEFT
        shapeRenderer.rect(camX, y - radius, x - radius - camX, 2 * radius);
        // RIGHT
        shapeRenderer.rect(x + radius, y - radius, camX + worldWidth - (x + radius), 2 * radius);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }
}
