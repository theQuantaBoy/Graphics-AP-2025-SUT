package com.ap_graphics.model.enums;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

public enum Avatar {
    AVATAR_00("images/avatar_portraits/00_T_Abby_Portrait.png", "images/character_sprites/00_T_Abby.png", "Abby", 0,
        List.of("images/idles/00_Abby/idle/Idle_0.png", "images/idles/00_Abby/idle/Idle_1.png", "images/idles/00_Abby/idle/Idle_2.png",
            "images/idles/00_Abby/idle/Idle_3.png", "images/idles/00_Abby/idle/Idle_4.png", "images/idles/00_Abby/idle/Idle_5.png"),
        List.of("images/idles/00_Abby/run/Run_0.png", "images/idles/00_Abby/run/Run_3.png", "images/idles/00_Abby/run/Run_2.png",
            "images/idles/00_Abby/run/Run_3.png")),

    AVATAR_01("images/avatar_portraits/01_T_Dasher_Portrait.png", "images/character_sprites/01_T_Dasher.png", "Dasher", 1,
        List.of("images/idles/01_Dasher/idle/Idle_0.png", "images/idles/01_Dasher/idle/Idle_3.png", "images/idles/01_Dasher/idle/Idle_2.png",
            "images/idles/01_Dasher/idle/Idle_3.png", "images/idles/01_Dasher/idle/Idle_4.png", "images/idles/01_Dasher/idle/Idle_5.png"),
        List.of("images/idles/01_Dasher/run/Run_0.png", "images/idles/01_Dasher/run/Run_1.png", "images/idles/01_Dasher/run/Run_2.png",
            "images/idles/01_Dasher/run/Run_3.png")),

    AVATAR_02("images/avatar_portraits/02_T_Diamond_Portrait.png", "images/character_sprites/02_T_Diamond #7829.png", "Diamond", 2,
        List.of("images/idles/02_Diamond/idle/Idle_0.png", "images/idles/02_Diamond/idle/Idle_1.png", "images/idles/02_Diamond/idle/Idle_2.png",
            "images/idles/02_Diamond/idle/Idle_3.png", "images/idles/02_Diamond/idle/Idle_4.png", "images/idles/02_Diamond/idle/Idle_5.png"),
        List.of("images/idles/02_Diamond/run/Run_0.png", "images/idles/02_Diamond/run/Run_1.png", "images/idles/02_Diamond/run/Run_2.png",
            "images/idles/02_Diamond/run/Run_3.png")),

    AVATAR_03("images/avatar_portraits/03_T_Hastur_Portrait.png", "images/character_sprites/03_T_Hastur.png", "Hastur", 3,
        List.of("images/idles/03_Hastur/idle/Idle_0.png", "images/idles/03_Hastur/idle/Idle_1.png", "images/idles/03_Hastur/idle/Idle_2.png",
            "images/idles/03_Hastur/idle/Idle_3.png", "images/idles/03_Hastur/idle/Idle_4.png", "images/idles/03_Hastur/idle/Idle_5.png"),
        List.of("images/idles/03_Hastur/run/Run_0.png", "images/idles/03_Hastur/run/Run_1.png", "images/idles/03_Hastur/run/Run_2.png",
            "images/idles/03_Hastur/run/Run_3.png")),

    AVATAR_04("images/avatar_portraits/04_T_Hina_Portrait.png", "images/character_sprites/04_T_Hina.png", "Hina", 4,
        List.of("images/idles/04_Tina/idle/Idle_0.png", "images/idles/04_Tina/idle/Idle_1.png", "images/idles/04_Tina/idle/Idle_2.png",
            "images/idles/04_Tina/idle/Idle_3.png", "images/idles/04_Tina/idle/Idle_4.png", "images/idles/04_Tina/idle/Idle_5.png"),
        List.of("images/idles/04_Tina/run/Run_0.png", "images/idles/04_Tina/run/Run_1.png", "images/idles/04_Tina/run/Run_2.png",
            "images/idles/04_Tina/run/Run_3.png")),

    AVATAR_05("images/avatar_portraits/05_T_Lilith_Portrait.png", "images/character_sprites/05_Lilith.png", "Lilith", 5,
        List.of("images/idles/05_Lilith/idle/Idle_0.png", "images/idles/05_Lilith/idle/Idle_1.png", "images/idles/05_Lilith/idle/Idle_2.png",
            "images/idles/05_Lilith/idle/Idle_3.png", "images/idles/05_Lilith/idle/Idle_4.png", "images/idles/05_Lilith/idle/Idle_5.png"),
        List.of("images/idles/05_Lilith/run/Run_0.png", "images/idles/05_Lilith/run/Run_1.png", "images/idles/05_Lilith/run/Run_2.png",
            "images/idles/05_Lilith/run/Run_3.png")),

    AVATAR_06("images/avatar_portraits/06_T_Luna_Portrait.png", "images/character_sprites/06_T_Luna.png", "Luna", 6,
        List.of("images/idles/06_Luna/idle/Idle_0.png", "images/idles/06_Luna/idle/Idle_1.png", "images/idles/06_Luna/idle/Idle_2.png",
            "images/idles/06_Luna/idle/Idle_3.png", "images/idles/06_Luna/idle/Idle_4.png", "images/idles/06_Luna/idle/Idle_5.png"),
        List.of("images/idles/06_Luna/run/Run_0.png", "images/idles/06_Luna/run/Run_1.png", "images/idles/06_Luna/run/Run_2.png",
            "images/idles/06_Luna/run/Run_3.png")),

    AVATAR_07("images/avatar_portraits/07_T_Raven_Portrait.png", "images/character_sprites/07_T_Raven_Idle.png", "Raven", 7,
        List.of("images/idles/07_Ravan/idle/Idle_0.png", "images/idles/07_Ravan/idle/Idle_1.png", "images/idles/07_Ravan/idle/Idle_2.png",
            "images/idles/07_Ravan/idle/Idle_3.png", "images/idles/07_Ravan/idle/Idle_4.png", "images/idles/07_Ravan/idle/Idle_5.png"),
        List.of("images/idles/07_Ravan/run/Run_0.png", "images/idles/07_Ravan/run/Run_1.png", "images/idles/07_Ravan/run/Run_2.png",
            "images/idles/07_Ravan/run/Run_3.png")),

    AVATAR_08("images/avatar_portraits/08_T_Scarlett_Portrait.png", "images/character_sprites/08_T_Scarlett.png", "Scarlett", 8,
        List.of("images/idles/08_Scarlett/idle/Idle_0.png", "images/idles/08_Scarlett/idle/Idle_1.png", "images/idles/08_Scarlett/idle/Idle_2.png",
            "images/idles/08_Scarlett/idle/Idle_3.png", "images/idles/08_Scarlett/idle/Idle_4.png", "images/idles/08_Scarlett/idle/Idle_5.png"),
        List.of("images/idles/08_Scarlett/run/Run_0.png", "images/idles/08_Scarlett/run/Run_1.png", "images/idles/08_Scarlett/run/Run_2.png",
            "images/idles/08_Scarlett/run/Run_3.png")),

    AVATAR_09("images/avatar_portraits/09_T_Shana_Portrait.png", "images/character_sprites/09_T_Shana.png", "Shana", 9,
        List.of("images/idles/09_Shana/idle/Idle_0.png", "images/idles/09_Shana/idle/Idle_1.png", "images/idles/09_Shana/idle/Idle_2.png",
            "images/idles/09_Shana/idle/Idle_3.png", "images/idles/09_Shana/idle/Idle_4.png", "images/idles/09_Shana/idle/Idle_5.png"),
        List.of("images/idles/09_Shana/run/Run_0.png", "images/idles/09_Shana/run/Run_1.png", "images/idles/09_Shana/run/Run_2.png",
            "images/idles/09_Shana/run/Run_3.png")),

    AVATAR_10("images/avatar_portraits/10_T_Spark_Portrait.png", "images/character_sprites/10_T_Spark.png", "Spark", 10,
        List.of("images/idles/10_Spark/idle/Idle_0.png", "images/idles/10_Spark/idle/Idle_1.png", "images/idles/10_Spark/idle/Idle_2.png",
            "images/idles/10_Spark/idle/Idle_3.png", "images/idles/10_Spark/idle/Idle_4.png", "images/idles/10_Spark/idle/Idle_5.png"),
        List.of("images/idles/10_Spark/run/Run_0.png", "images/idles/10_Spark/run/Run_1.png", "images/idles/10_Spark/run/Run_2.png",
            "images/idles/10_Spark/run/Run_3.png")),

    AVATAR_11("images/avatar_portraits/11_T_Yuki_Portrait.png", "images/character_sprites/11_T_Yuki.png", "Yuki", 11,
        List.of("images/idles/11_Yuki/idle/Idle_0.png", "images/idles/11_Yuki/idle/Idle_1.png", "images/idles/11_Yuki/idle/Idle_2.png",
            "images/idles/11_Yuki/idle/Idle_3.png", "images/idles/11_Yuki/idle/Idle_4.png", "images/idles/11_Yuki/idle/Idle_5.png"),
        List.of("images/idles/11_Yuki/run/Run_0.png", "images/idles/11_Yuki/run/Run_1.png", "images/idles/11_Yuki/run/Run_2.png",
            "images/idles/11_Yuki/run/Run_3.png")),
    ;

    private final String portraitPath;
    private final String spritePath;
    private final String name;
    private final int id;
    private final List<String> idlePaths;
    private final List<String> runPaths;

    Avatar(String portraitPath, String spritePath, String name, int id, List<String> idlePaths, List<String> runPaths) {
        this.portraitPath = portraitPath;
        this.spritePath = spritePath;
        this.name = name;
        this.id = id;
        this.idlePaths = idlePaths;
        this.runPaths = runPaths;
    }

    public static Avatar getAvatar(int id) {
        id = id % 12;

        for (Avatar avatar : Avatar.values()) {
            if (avatar.id == id) {
                return avatar;
            }
        }

        return null;
    }

    public String getPortraitPath() {
        return portraitPath;
    }

    public String getSpritePath() {
        return spritePath;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Animation<TextureRegion> getIdleAnimation()
    {
        TextureRegion[] idleRegions = new TextureRegion[idlePaths.size()];
        for (int i = 0; i < idlePaths.size(); i++)
        {
            idleRegions[i] = new TextureRegion(new Texture(idlePaths.get(i)));
        }

        Animation<TextureRegion> animation = new Animation<>(0.1f, idleRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public Animation<TextureRegion> getRunAnimation()
    {
        TextureRegion[] runRegions = new TextureRegion[runPaths.size()];
        for (int i = 0; i < runPaths.size(); i++)
        {
            runRegions[i] = new TextureRegion(new Texture(runPaths.get(i)));
        }

        Animation<TextureRegion> animation = new Animation<>(0.1f, runRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        return animation;
    }

    public Texture getFirstTexture()
    {
        return new Texture(idlePaths.get(0));
    }

    public static int getIndex(Avatar avatar)
    {
        for (int i = 0; i < Avatar.values().length; i++)
        {
            Avatar a = Avatar.values()[i];
            if (a == avatar)
            {
                return i;
            }
        }
        return 0;
    }
}
