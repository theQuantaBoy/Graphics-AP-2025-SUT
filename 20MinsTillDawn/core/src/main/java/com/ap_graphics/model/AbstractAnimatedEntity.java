package com.ap_graphics.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractAnimatedEntity implements WorldObject
{
    protected Vector2 position;
    protected Animation<TextureRegion> currentAnimation;
    protected float stateTime = 0f;

    public AbstractAnimatedEntity(float x, float y)
    {
        this.position = new Vector2(x, y);
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        stateTime += delta;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        float angleToPlayer = getAngleToPlayer();
        boolean faceLeft = shouldFaceLeft();

        if (faceLeft && !frame.isFlipX())
        {
            frame.flip(true, false);
        } else if (!faceLeft && frame.isFlipX())
        {
            frame.flip(true, false);
        }

        float drawAngle = angleToPlayer;
        if (faceLeft)
        {
            drawAngle += 180;
            if (drawAngle > 180) drawAngle -= 360;
        }

        // for rotation
        batch.draw(
            frame,
            position.x, position.y,
            frame.getRegionWidth() / 2f, frame.getRegionHeight() / 2f, // originX/Y
            frame.getRegionWidth(), frame.getRegionHeight(),
            1f, 1f, // scale
            drawAngle
        );
    }

    @Override
    public void update(float delta)
    {
        stateTime += delta;
    }

    @Override
    public Rectangle getBounds()
    {
        TextureRegion sample = currentAnimation.getKeyFrame(0);
        return new Rectangle(position.x, position.y, sample.getRegionWidth(), sample.getRegionHeight());
    }

    private boolean shouldFaceLeft()
    {
        Player player = App.getCurrentPlayer();
        float baseX = player.getPosX();
        return (baseX < position.x);
    }

    private float getAngleToPlayer()
    {
        Player player = App.getCurrentPlayer();
        float dx = player.getPosX() - position.x;
        float dy = player.getPosY() - position.y;

        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }
}
