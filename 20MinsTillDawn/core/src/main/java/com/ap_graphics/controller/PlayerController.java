package com.ap_graphics.controller;

import com.ap_graphics.TillDawn;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class PlayerController
{
    private final Player player;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> currentAnimation;
    private float time;
    private final float beWidth;
    private final float beHeight;

    public PlayerController(Player player, Texture background)
    {
        this.player = player;
        Avatar avatar = player.getAvatar();
        this.time = 0f;
        player.getPlayerSprite();
        this.idleAnimation = avatar.getIdleAnimation();
        this.runAnimation = avatar.getRunAnimation(); // Create this similar to idle
        this.currentAnimation = idleAnimation;
        this.beWidth = background.getWidth();
        this.beHeight = background.getHeight();
    }

    public void update(float delta, SpriteBatch batch)
    {
        handlePlayerInput();
        updateAnimation(delta);

        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());
        player.getPlayerSprite().draw(batch);
    }

    public void handlePlayerInput()
    {
        float speed = player.getSpeed();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.updateLocation(0, +speed); // Move up
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.updateLocation(0, -speed); // Move down
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            player.updateLocation(+speed, 0); // Move right
            player.setHeadedRight(true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.updateLocation(-speed, 0); // Move left
            player.setHeadedRight(false);  // ✅ Facing left
        }
    }

    private void updateAnimation(float delta)
    {
        boolean moving = Gdx.input.isKeyPressed(Input.Keys.W)
            || Gdx.input.isKeyPressed(Input.Keys.S)
            || Gdx.input.isKeyPressed(Input.Keys.A)
            || Gdx.input.isKeyPressed(Input.Keys.D);

        currentAnimation = moving ? runAnimation : idleAnimation;

        time += delta;

        TextureRegion frame = currentAnimation.getKeyFrame(time, true);

        player.setCurrentFrame(frame);              // Optional
        player.getPlayerSprite().setRegion(frame);  // Required
        player.getPlayerSprite().setFlip(!player.isHeadedRight(), false); // ✅ This line replaces flip logic
    }

    public void resetAnimation() {
        time = 0f;
    }
}

