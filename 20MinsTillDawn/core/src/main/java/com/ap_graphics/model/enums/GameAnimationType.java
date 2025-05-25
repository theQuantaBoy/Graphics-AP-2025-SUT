package com.ap_graphics.model.enums;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum GameAnimationType
{
    RELOAD(new String[] {
        "images/weapons/Revolver/reload/RevolverReload_0.png",
        "images/weapons/Revolver/reload/RevolverReload_1.png",
        "images/weapons/Revolver/reload/RevolverReload_2.png",
        "images/weapons/Revolver/reload/RevolverReload_3.png"
    }, 0.1f),

    LEVEL_UP(new String[] {
        "images/effects/level-up/T_LevelUpFX_0.png",
        "images/effects/level-up/T_LevelUpFX_1.png",
        "images/effects/level-up/T_LevelUpFX_2.png",
        "images/effects/level-up/T_LevelUpFX_3.png",
        "images/effects/level-up/T_LevelUpFX_4.png",
        "images/effects/level-up/T_LevelUpFX_5.png",
        "images/effects/level-up/T_LevelUpFX_6.png",
        "images/effects/level-up/T_LevelUpFX_7.png",
        "images/effects/level-up/T_LevelUpFX_8.png"
    }, 0.1f),

    TENTACLE_DEATH(new String[] {
        "images/effects/death-fx/DeathFX_0.png",
        "images/effects/death-fx/DeathFX_1.png",
        "images/effects/death-fx/DeathFX_2.png",
        "images/effects/death-fx/DeathFX_3.png"
    }, 0.1f),

    BRAIN_MONSTER_DEATH(new String[] {
        "images/effects/explosion-fx/ExplosionFX_0.png",
        "images/effects/explosion-fx/ExplosionFX_1.png",
        "images/effects/explosion-fx/ExplosionFX_2.png",
        "images/effects/explosion-fx/ExplosionFX_3.png",
        "images/effects/explosion-fx/ExplosionFX_4.png",
        "images/effects/explosion-fx/ExplosionFX_5.png",
    }, 0.1f),

    EYE_BAT_DEATH(new String[] {
        "images/effects/fire_explosion_small/T_FireExplosionSmall_0.png",
        "images/effects/fire_explosion_small/T_FireExplosionSmall_1.png",
        "images/effects/fire_explosion_small/T_FireExplosionSmall_2.png",
        "images/effects/fire_explosion_small/T_FireExplosionSmall_3.png",
        "images/effects/fire_explosion_small/T_FireExplosionSmall_4.png",
        "images/effects/fire_explosion_small/T_FireExplosionSmall_5.png",
    }, 0.1f),

    BIG_CHEST(new String[] {
        "images/effects/large_chest/T_LargeChestAnimation_0.png",
        "images/effects/large_chest/T_LargeChestAnimation_1.png",
        "images/effects/large_chest/T_LargeChestAnimation_2.png",
        "images/effects/large_chest/T_LargeChestAnimation_3.png",
    }, 0.25f),
    ;

    private final String[] framePaths;
    private final float frameDuration;

    GameAnimationType(String[] framePaths, float frameDuration) {
        this.framePaths = framePaths;
        this.frameDuration = frameDuration;
    }

    public Animation<TextureRegion> createAnimation() {
        TextureRegion[] regions = new TextureRegion[framePaths.length];
        for (int i = 0; i < framePaths.length; i++) {
            Texture texture = new Texture(Gdx.files.internal(framePaths[i]));
            regions[i] = new TextureRegion(texture);
        }
        return new Animation<>(frameDuration, regions);
    }
}
