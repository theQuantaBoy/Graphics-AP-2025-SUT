package com.ap_graphics.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public enum WeaponType
{
    REVOLVER("Revolver", "images/weapons/Revolver/reload/RevolverStill.png", 20, 1, 1, 6, new Vector2(25, -5)),
    SHOTGUN("Shotgun", "images/weapons/Shotgun/T_Shotgun_SS_0.png", 10, 4, 1, 2, new Vector2(25, -5)),
    DUAL_SMG("SMGs Dual", "images/weapons/SMG/SMGStill.png", 8, 1, 2, 24, new Vector2(25, -5)),
    ;

    private final String name;
    private final String stillTexture;
    private final int damage;
    private final int projectile;
    private final int reloadTime;
    private final int maxAmmo;
    private final Vector2 offset;

    WeaponType(String name, String stillTexture, int damage, int projectile, int reloadTime, int maxAmmo, Vector2 offset)
    {
        this.name = name;
        this.stillTexture = stillTexture;
        this.damage = damage;
        this.projectile = projectile;
        this.reloadTime = reloadTime;
        this.maxAmmo = maxAmmo;
        this.offset = offset;
    }

    public String getName()
    {
        return name;
    }

    public int getDamage()
    {
        return damage;
    }

    public int getProjectile()
    {
        return projectile;
    }

    public int getReloadTime()
    {
        return reloadTime;
    }

    public int getMaxAmmo()
    {
        return maxAmmo;
    }

    public Vector2 getOffset()
    {
        return offset;
    }

    public TextureRegion getTextureRegion()
    {
        TextureRegion textureRegion = new TextureRegion(new Texture(stillTexture));
        return textureRegion;
    }
}
