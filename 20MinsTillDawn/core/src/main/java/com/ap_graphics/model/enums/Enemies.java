package com.ap_graphics.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.List;

public enum Enemies
{
    TREE("Tree", List.of("images/enemies/Tree/T_TreeMonster_0.png", "images/enemies/Tree/T_TreeMonster_1.png",
        "images/enemies/Tree/T_TreeMonster_2.png", "images/enemies/Tree/T_TreeMonster_3.png"), null),

    TENTACLE_1("Tentacle", List.of("TentacleIdle0.png", "TentacleIdle1.png", "TentacleIdle2.png",
        "TentacleIdle3.png"), List.of("images/enemies/Tentacle/type_0/TentacleSpawn0.png",
        "images/enemies/Tentacle/type_0/TentacleSpawn1.png", "images/enemies/Tentacle/type_0/TentacleSpawn2.png")),

    TENTACLE_2("Tentacle", List.of("images/enemies/Tentacle/type_1/T_TentacleEnemy_0.png",
        "images/enemies/Tentacle/type_1/T_TentacleEnemy_1.png", "images/enemies/Tentacle/type_1/T_TentacleEnemy_2.png",
        "images/enemies/Tentacle/type_1/T_TentacleEnemy_3.png"), List.of("images/enemies/Tentacle/type_1/TentacleSpawn1.png",
        "images/enemies/Tentacle/type_1/TentacleSpawn2.png")),

    EYE_BAT("Eye Bat", List.of("images/enemies/EyeBat/T_EyeBat_0.png", "images/enemies/EyeBat/T_EyeBat_1.png",
        "images/enemies/EyeBat/T_EyeBat_2.png", "images/enemies/EyeBat/T_EyeBat_3.png"), null),

    ELDER_BRAIN("Elder Brain", List.of("images/enemies/ElderBrain/ElderBrain.png"), null)
    ;

    private final String name;
    private final List<String> idlePaths;
    private final List<String> spawnPaths;

    Enemies(String name, List<String> idlePaths, List<String> spawnPaths)
    {
        this.name = name;
        this.idlePaths = idlePaths;
        this.spawnPaths = spawnPaths;
    }

    public String getName()
    {
        return name;
    }

    public Animation<Texture> getIdleAnimation()
    {
        Texture[] idleRegions = new Texture[idlePaths.size()];
        for (int i = 0; i < idlePaths.size(); i++)
        {
            idleRegions[i] = new Texture(idlePaths.get(i));
        }

        Animation<Texture> animation = new Animation<>(0.1f, idleRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public Animation<Texture> getSpawnAnimation()
    {
        Texture[] idleRegions = new Texture[spawnPaths.size()];
        for (int i = 0; i < idlePaths.size(); i++)
        {
            idleRegions[i] = new Texture(spawnPaths.get(i));
        }

        Animation<Texture> animation = new Animation<>(0.1f, idleRegions);
        animation.setPlayMode(Animation.PlayMode.NORMAL);
        return animation;
    }
}
