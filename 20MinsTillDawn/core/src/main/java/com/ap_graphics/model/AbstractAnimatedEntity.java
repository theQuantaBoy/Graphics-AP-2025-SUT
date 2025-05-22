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

    protected boolean facingRight = true;

    public AbstractAnimatedEntity(float x, float y)
    {
        this.position = new Vector2(x, y);
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        stateTime += delta;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        // چرخش بافت بر اساس جهت
        if (!facingRight) {
            frame.flip(true, false);
        }

        batch.draw(frame, position.x, position.y);
        frame.flip(true, false); // بازگرداندن جهت اصلی برای فریم بعدی
    }

    // In AbstractAnimatedEntity.java
    @Override
    public void update(float delta) {
        // Update animation timer
        stateTime += delta;
    }

    @Override
    public Rectangle getBounds()
    {
        TextureRegion sample = currentAnimation.getKeyFrame(0);
        return new Rectangle(position.x, position.y, sample.getRegionWidth(), sample.getRegionHeight());
    }
}
