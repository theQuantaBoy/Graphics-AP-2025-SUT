package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class GameAssetManager
{
    private static GameAssetManager gameAssetManager;
    private final AssetManager manager = new AssetManager();
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

    public void loadAllAssets()
    {
        for (Avatar avatar : Avatar.values())
        {
            manager.load(avatar.getPath(), Texture.class);
        }

        manager.finishLoading(); // Load them synchronously (or use async if needed)
    }
}
