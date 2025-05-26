package com.ap_graphics.controller;

import com.ap_graphics.model.combat.Bullet;
import com.ap_graphics.model.GameWorld;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.combat.Weapon;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SoundEffectType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class PlayerController
{
    private final Player player;
    private Animation<TextureRegion> idleAnimation;
    private Animation<TextureRegion> runAnimation;
    private Animation<TextureRegion> currentAnimation;
    private float time;
    private final float beWidth;
    private final float beHeight;

    private float footstepTimer = 0f;
    private final float footstepCooldown = 0.35f; // seconds between steps

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
        handlePlayerInput(delta);
        updateAnimation(delta);

        player.getPlayerSprite().setPosition(player.getPosX(), player.getPosY());
        player.getPlayerSprite().draw(batch);
    }

    public void handlePlayerInput(float delta) {
        float speed = player.getSpeed();
        boolean moved = false;

        if (Gdx.input.isKeyPressed(player.getMoveUpKey())) {
            player.updateLocation(0, +speed);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(player.getMoveDownKey())) {
            player.updateLocation(0, -speed);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(player.getMoveRightKey())) {
            player.updateLocation(+speed, 0);
            player.setHeadedRight(true);
            moved = true;
        }
        if (Gdx.input.isKeyPressed(player.getMoveLeftKey())) {
            player.updateLocation(-speed, 0);
            player.setHeadedRight(false);
            moved = true;
        }

        if (moved) {
            footstepTimer -= delta;
            if (footstepTimer <= 0f) {
                SoundManager.getInstance().playSFX(SoundEffectType.FOOTSTEPS_CASUAL_GRASS_01);
                footstepTimer = footstepCooldown;
            }
        } else {
            footstepTimer = 0f; // reset if not moving
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

    public void handleShooting(Player player, GameWorld gameWorld, OrthographicCamera camera)
    {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
        {
            Weapon weapon = player.getCurrentWeapon();
            if (weapon != null)
            {
               if (weapon.shoot())
               {
                   Vector3 mouseScreenPos = new Vector3(
                       Gdx.input.getX(),
                       Gdx.input.getY(),
                       0
                   );
                   Vector3 mouseWorldPos = camera.unproject(mouseScreenPos);

                   Vector2 weaponPos = weapon.getPosition();
                   Vector2 direction = new Vector2(
                       mouseWorldPos.x - weaponPos.x,
                       mouseWorldPos.y - weaponPos.y
                   ).nor();

                   gameWorld.addBullet(new Bullet(
                       weaponPos.x,
                       weaponPos.y,
                       direction,
                       weapon.getType().getDamage()
                   ));
               } else
               {
                   if (!weapon.isReloading() && player.isAutoReloadEnabled())
                   {
                       weapon.startReloading();
                   }
               }
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.R))
        {
            Weapon weapon = player.getCurrentWeapon();
            weapon.startReloading();
        }
    }
}

