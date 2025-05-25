package com.ap_graphics.model.combat;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.App;
import com.ap_graphics.model.AttachedAnimation;
import com.ap_graphics.model.GameWorld;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.GameAnimationType;
import com.ap_graphics.model.enums.SoundEffectType;
import com.ap_graphics.model.enums.WeaponType;
import com.badlogic.gdx.math.Vector2;

public class Weapon
{
    private final WeaponType type;
    private final String name;
    private int maxAmmo;
    private float reloadTime;
    private int projectiles;
    private int damage;
    private int currentAmmo;
    private boolean isReloading = false;

    private Vector2 position = new Vector2();
    private float rotation;
    private final Vector2 offset; // Distance from player center

    private float reloadTimer = 0f;

    private AttachedAnimation reloadAnimation = null;

    public Weapon(WeaponType type)
    {
        this.type = type;
        this.name = type.getName();
        this.maxAmmo = type.getMaxAmmo();
        this.reloadTime = type.getReloadTime();
        this.projectiles = type.getProjectile();
        this.damage = type.getDamage();
        this.currentAmmo = maxAmmo;
        this.offset = type.getOffset();

        isReloading = false;
    }

    public void update(float delta)
    {
        if (isReloading)
        {
            reloadTimer -= delta;
            if (reloadTimer <= 0)
            {
                currentAmmo = type.getMaxAmmo();
                isReloading = false;
                SoundManager.getInstance().playSFX(SoundEffectType.WEAPON_SHOTGUN_RELOAD);

                GameWorld gameWorld = App.getGame();
                gameWorld.getAttachedAnimations().remove(reloadAnimation);
                reloadAnimation = null;
            }
        }

        if (currentAmmo <= 0 && !isReloading)
        {
            Player player = App.getCurrentPlayer();
            if (player != null && player.isAutoReloadEnabled())
            {
                isReloading = true;
                reloadTimer = reloadTime;

                GameWorld gameWorld = App.getGame();
                AttachedAnimation animation = new AttachedAnimation(GameAnimationType.RELOAD, false, 1.5f);
                reloadAnimation = animation;
                gameWorld.getAttachedAnimations().add(animation);
            }
        }
    }

    public void startReloading()
    {
        if (!isReloading)
        {
            isReloading = true;
            reloadTimer = reloadTime;

            GameWorld gameWorld = App.getGame();
            AttachedAnimation animation = new AttachedAnimation(GameAnimationType.RELOAD, false, 1.5f);
            reloadAnimation = animation;
            gameWorld.getAttachedAnimations().add(animation);
        }
    }

    public boolean shoot()
    {
        if (currentAmmo > 0 && !isReloading)
        {
            currentAmmo -= 1;
            return true;
        }

        return false;
    }

    public WeaponType getType()
    {
        return type;
    }

    public void updatePosition(Vector2 playerPosition, Vector2 mouseDirection)
    {
        // Calculate direction-based offset
        Vector2 dir = mouseDirection.cpy().nor();
        position.set(
            playerPosition.x + dir.x * offset.x,
            playerPosition.y + dir.y * offset.y
        );

        // Calculate rotation angle
        rotation = dir.angleDeg();
    }

    public Vector2 getPosition() { return position; }

    public float getRotation() { return rotation; }

    public boolean isReloading()
    {
        return isReloading;
    }

    public int getCurrentAmmo()
    {
        return currentAmmo;
    }
}
