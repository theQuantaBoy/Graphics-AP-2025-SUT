package com.ap_graphics.model.combat;

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
    private boolean isReloading;

    private Vector2 position = new Vector2();
    private float rotation;
    private final Vector2 offset; // Distance from player center

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

    public boolean shoot()
    {
        // TODO: commented for debugging
//        if (currentAmmo > 0 && !isReloading)
//        {
            currentAmmo -= 1;
            return true;
//        }

//        return false;
    }

    public void reload()
    {
        isReloading = true;
        // TODO: Simulate reload delay (use LibGDX Timer)
        currentAmmo = maxAmmo;
        isReloading = false;
    }

    public WeaponType getType()
    {
        return type;
    }

    public void updatePosition(Vector2 playerPosition, Vector2 mouseDirection) {
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
}
