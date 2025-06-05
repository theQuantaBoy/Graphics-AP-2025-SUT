package com.ap_graphics.model.enums;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum AbilityType
{
    VITALITY("Increase HP", "+1 Max HP"),
    DAMAGER("Increase Damage", "+25% Weapon Damage for 10s"),
    PROCREASE("Increase Projectiles", "+1 Projectile"),
    AMOCREASE("Increase Ammo Capacity", "+5 Max Ammo"),
    SPEEDY("Increase Speed", "2x Move Speed for 10s");

    private final String name;
    private final String effectText;

    AbilityType(String faName, String effectText)
    {
        this.name = faName;
        this.effectText = effectText;
    }

    public String getName()
    {
        return name;
    }

    public String getEffectText()
    {
        return effectText;
    }

    public static AbilityType[] randomThree()
    {
        List<AbilityType> list = new ArrayList<>(List.of(values()));
        Collections.shuffle(list);
        return new AbilityType[] { list.get(0), list.get(1), list.get(2) };
    }

    public static AbilityType random()
    {
        AbilityType[] values = values();
        return values[new Random().nextInt(values.length)];
    }
}

