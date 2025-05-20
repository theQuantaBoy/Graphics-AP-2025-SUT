package com.ap_graphics;

import com.ap_graphics.model.GameAssetManager;
import com.ap_graphics.view.RegisterMenuScreen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends com.badlogic.gdx.Game
{
    private static TillDawn game;
    private static SpriteBatch batch;

    @Override
    public void create()
    {
        game = this;
        batch = new SpriteBatch();
        getGame().setScreen(new RegisterMenuScreen(GameAssetManager.getGameAssetManager().getSkin()));
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

    public static TillDawn getGame()
    {
        return game;
    }

    public static SpriteBatch getBatch()
    {
        return batch;
    }
}
