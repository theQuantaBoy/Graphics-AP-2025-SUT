package com.ap_graphics.model.enums;

public enum SoundEffectType
{
    BLOOD_SPLASH_QUICK_01("audio/sfx/Blood_Splash_Quick_01.wav"),
    BUFF_POWER_UP_01("audio/sfx/Buff_Power_Up_01.wav"),
    COINS_10("audio/sfx/Coins (10).wav"),
    DEBUFF_SPEED("audio/sfx/Debuff_Speed.wav"),
    DRUM_ROLL_3("audio/sfx/Drum Roll (3).wav"),
    EXPLOSION_BLOOD_01("audio/sfx/Explosion_Blood_01.wav"),
    FOOTSTEPS_CASUAL_GRASS_01("audio/sfx/Footsteps_Casual_Grass_01.wav"),
    PRETTY_DUNGEON_LOOP("audio/sfx/Pretty Dungeon LOOP.wav"),
    SPECIAL_AND_POWERUP_8("audio/sfx/Special & Powerup (8).wav"),
    SPELL_EXPLOSION_MAGIC_02("audio/sfx/Spell_Explosion_Magic_02.wav"),
    STANDARD_WEAPON_WHOOSH_01("audio/sfx/Standard_Weapon_Whoosh_01.wav"),
    UI_CLICK_36("audio/sfx/UI Click 36.wav"),
    WEAPON_SHOTGUN_RELOAD("audio/sfx/Weapon_Shotgun_Reload.wav"),
    YOU_LOSE_4("audio/sfx/You Lose (4).wav"),
    YOU_WIN_2("audio/sfx/You Win (2).wav"),
    SFX_LOWHEALTH_ALARMLOOP1("audio/sfx/sfx_lowhealth_alarmloop1.wav"),
    ;

    private final String filePath;

    SoundEffectType(String filePath)
    {
        this.filePath = filePath;
    }

    public String getFilePath()
    {
        return filePath;
    }
}
