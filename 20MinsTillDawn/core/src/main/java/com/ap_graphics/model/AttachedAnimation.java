package com.ap_graphics.model;

import com.ap_graphics.model.enums.GameAnimationType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class AttachedAnimation
{
    private final Animation<TextureRegion> animation;
    private float stateTime = 0f;
    private final boolean attachToPlayer; // true = player, false = weapon
    private final float scale;
    private boolean finished = false;
    private final GameAnimationType animationType;

    public AttachedAnimation(GameAnimationType animationType, boolean attachToPlayer, float scale)
    {
        this.animationType = animationType;
        this.animation = animationType.createAnimation();
        this.attachToPlayer = attachToPlayer;
        this.scale = scale;
    }

    public void update(float delta)
    {
        stateTime += delta;
        if (animation.isAnimationFinished(stateTime)) {
            finished = true;
        }
    }

    public void render(SpriteBatch batch)
    {
        if (finished) return;

        Vector2 pos;
        TextureRegion frame = animation.getKeyFrame(stateTime, false);

        if (attachToPlayer)
        {
            Player player = GameWorld.getInstance().getPlayer();
            float x = player.getPosX();
            float y = player.getPosY() - player.getPlayerSprite().getRegionHeight() / 2f;

            // Align bottom of animation with bottom of player sprite
            if (animationType == GameAnimationType.LEVEL_UP)
            {
                y += (frame.getRegionHeight() * scale) + 18;
                x += 8;
            }

            pos = new Vector2(x, y);
        } else
        {
            float y = GameWorld.getInstance().getPlayer().getCurrentWeapon().getPosition().y + 20;
            float x = GameWorld.getInstance().getPlayer().getCurrentWeapon().getPosition().x + 10;

            pos = new Vector2(x, y);
        }

        batch.draw(
            frame,
            pos.x - frame.getRegionWidth() / 2f * scale,
            pos.y - frame.getRegionHeight() * scale,
            frame.getRegionWidth() * scale,
            frame.getRegionHeight() * scale
        );
    }

    public boolean isFinished()
    {
        return finished;
    }

    public GameAnimationType getAnimationType()
    {
        return animationType;
    }
}
