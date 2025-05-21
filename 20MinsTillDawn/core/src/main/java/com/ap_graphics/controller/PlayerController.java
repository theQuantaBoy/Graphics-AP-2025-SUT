package com.ap_graphics.controller;

import com.ap_graphics.TillDawn;
import com.ap_graphics.model.Player;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayerController
{
    private final Player player;
    private final Animation<Texture> idleAnimation;
    private float time;

    public PlayerController(Player player)
    {
        this.player = player;
        this.idleAnimation = player.getAvatar().getIdleAnimation();
        this.time = 0f;
    }

    public void update(float delta, SpriteBatch batch) {
        handlePlayerInput();
        updateIdleAnimation(delta);
        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());
        player.getPlayerSprite().draw(batch);
    }


    public void handlePlayerInput()
    {
        float speed = player.getSpeed();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.setPosY(player.getPosY() + speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.setPosY(player.getPosY() - speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.setPosX(player.getPosX() + speed);
            player.getPlayerSprite().setFlip(false, false);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.setPosX(player.getPosX() - speed);
            player.getPlayerSprite().setFlip(true, false); // Flip horizontally when going left
        }
    }


    private void updateIdleAnimation(float delta)
    {
        time += delta;
        Texture frame = idleAnimation.getKeyFrame(time, true);
        player.setCurrentFrame(frame);
    }

    public void resetAnimation() {
        time = 0f;
    }
}

