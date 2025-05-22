package com.ap_graphics.model;

import com.ap_graphics.model.enums.WeaponType;

public class Weapon
{
    WeaponType type;
    private final String name;
    private int maxAmmo;
    private float reloadTime;
    private int projectiles;
    private int damage;
    private int currentAmmo;
    private boolean isReloading;

    public Weapon(WeaponType type)
    {
        this.type = type;
        this.name = type.getName();
        this.maxAmmo = type.getMaxAmmo();
        this.reloadTime = type.getReloadTime();
        this.projectiles = type.getProjectile();
        this.damage = type.getDamage();
        this.currentAmmo = maxAmmo;

        isReloading = false;
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

    public void reload()
    {
        isReloading = true;
        // TODO: Simulate reload delay (use LibGDX Timer)
        currentAmmo = maxAmmo;
        isReloading = false;
    }
}
