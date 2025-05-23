package com.ap_graphics.model.combat;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Tree extends Enemy
{
    public Tree(float x, float y)
    {
        super(EnemyType.TREE, x, y);
    }

    @Override
    public void render(SpriteBatch batch, float delta)
    {
        stateTime += delta;
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(frame, position.x, position.y,
            frame.getRegionWidth() / 2f, frame.getRegionHeight() / 2f);
    }
}
