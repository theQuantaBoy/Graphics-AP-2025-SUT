package com.ap_graphics;

import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.GameAssetManager;
import com.ap_graphics.view.RegisterMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game
{
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create()
    {
        main = this;
        batch = new SpriteBatch();
        getMain().setScreen(new RegisterMenuScreen(new RegisterMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void dispose()
    {
        super.dispose();
    }

    @Override
    public void render()
    {
        super.render();
    }

    public static Main getMain()
    {
        return main;
    }

    public static SpriteBatch getBatch()
    {
        return batch;
    }
}
