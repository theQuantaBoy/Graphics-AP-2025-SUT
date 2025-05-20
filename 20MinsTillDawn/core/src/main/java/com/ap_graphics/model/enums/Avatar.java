package com.ap_graphics.model.enums;

public enum Avatar
{
    AVATAR_00("images/avatar_portraits/00_T_Abby_Portrait.png", "Abby", 0),
    AVATAR_01("images/avatar_portraits/01_T_Dasher_Portrait.png", "Dasher", 1),
    AVATAR_02("images/avatar_portraits/02_T_Diamond_Portrait.png", "Diamond", 2),
    AVATAR_03("images/avatar_portraits/03_T_Hastur_Portrait.png", "Hastur", 3),
    AVATAR_04("images/avatar_portraits/04_T_Hina_Portrait.png", "Hina", 4),
    AVATAR_05("images/avatar_portraits/05_T_Lilith_Portrait.png", "Lilith", 5),
    AVATAR_06("images/avatar_portraits/06_T_Luna_Portrait.png", "Luna", 6),
    AVATAR_07("images/avatar_portraits/07_T_Raven_Portrait.png", "Raven", 7),
    AVATAR_08("images/avatar_portraits/08_T_Scarlett_Portrait.png", "Scarlett", 8),
    AVATAR_09("images/avatar_portraits/09_T_Shana_Portrait.png", "Shana", 9),
    AVATAR_10("images/avatar_portraits/10_T_Spark_Portrait.png", "Spark", 10),
    AVATAR_11("images/avatar_portraits/11_T_Yuki_Portrait.png", "Yuki", 11),
    ;

    private final String path;
    private final String name;
    private final int id;

    Avatar(String path, String name, int id)
    {
        this.path = path;
        this.name = name;
        this.id = id;
    }

    public static Avatar getAvatar(int id)
    {
        id = id % 12;

        for (Avatar avatar : Avatar.values())
        {
            if (avatar.id == id)
            {
                return avatar;
            }
        }

        return null;
    }

    public String getPath()
    {
        return path;
    }

    public String getName()
    {
        return name;
    }
}
