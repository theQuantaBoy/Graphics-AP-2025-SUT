package com.ap_graphics.model.enums;

public enum Avatar {
    AVATAR_00("images/avatar_portraits/00_T_Abby_Portrait.png", "images/character_sprites/00_T_Abby.png", "Abby", 0),
    AVATAR_01("images/avatar_portraits/01_T_Dasher_Portrait.png", "images/character_sprites/01_T_Dasher.png", "Dasher",
            1),
    AVATAR_02("images/avatar_portraits/02_T_Diamond_Portrait.png", "images/character_sprites/02_T_Diamond #7829.png",
            "Diamond", 2),
    AVATAR_03("images/avatar_portraits/03_T_Hastur_Portrait.png", "images/character_sprites/03_T_Hastur.png", "Hastur",
            3),
    AVATAR_04("images/avatar_portraits/04_T_Hina_Portrait.png", "images/character_sprites/04_T_Hina.png", "Hina", 4),
    AVATAR_05("images/avatar_portraits/05_T_Lilith_Portrait.png", "images/character_sprites/05_Lilith.png", "Lilith",
            5),
    AVATAR_06("images/avatar_portraits/06_T_Luna_Portrait.png", "images/character_sprites/06_T_Luna.png", "Luna", 6),
    AVATAR_07("images/avatar_portraits/07_T_Raven_Portrait.png", "images/character_sprites/07_T_Raven_Idle.png",
            "Raven", 7),
    AVATAR_08("images/avatar_portraits/08_T_Scarlett_Portrait.png", "images/character_sprites/08_T_Scarlett.png",
            "Scarlett", 8),
    AVATAR_09("images/avatar_portraits/09_T_Shana_Portrait.png", "images/character_sprites/09_T_Shana.png", "Shana", 9),
    AVATAR_10("images/avatar_portraits/10_T_Spark_Portrait.png", "images/character_sprites/10_T_Spark.png", "Spark",
            10),
    AVATAR_11("images/avatar_portraits/11_T_Yuki_Portrait.png", "images/character_sprites/11_T_Yuki.png", "Yuki", 11),
    ;

    private final String portraitPath;
    private final String spritePath;
    private final String name;
    private final int id;

    Avatar(String portraitPath, String spritePath, String name, int id) {
        this.portraitPath = portraitPath;
        this.spritePath = spritePath;
        this.name = name;
        this.id = id;
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
}
