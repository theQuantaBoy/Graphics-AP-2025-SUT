package com.ap_graphics.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager
{
    private static GameAssetManager gameAssetManager;
    private final Skin skin = new Skin(Gdx.files.internal("skins/pixthulhu/skin/pixthulhu-ui.json"));

    public static GameAssetManager getGameAssetManager()
    {
        if (gameAssetManager == null)
        {
            gameAssetManager = new GameAssetManager();
        }

        return gameAssetManager;
    }

    public Skin getSkin()
    {
        return skin;
    }
}
