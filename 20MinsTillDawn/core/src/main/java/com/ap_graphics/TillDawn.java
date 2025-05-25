package com.ap_graphics;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.GameAssetManager;
import com.ap_graphics.view.FirstMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends com.badlogic.gdx.Game
{
    private static TillDawn game;
    private static SpriteBatch batch;
    public static BitmapFont menuFont;

    @Override
    public void create()
    {
        applyCustomCursor(0, 0);
        GameAssetManager.getGameAssetManager().loadAllAssets();
        loadFonts();
        SoundManager.getInstance(); // initialize sounds

        game = this;
        batch = new SpriteBatch();
        getGame().setScreen(new FirstMenuScreen());
    }

    @Override
    public void dispose()
    {
        super.dispose();
        SoundManager.getInstance().dispose();SoundManager.getInstance().dispose();
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

    private void applyCustomCursor(int xHotspot, int yHotspot)
    {
        Pixmap pixmap = new Pixmap(Gdx.files.internal("images/visual/T_Cursor.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, xHotspot, yHotspot));
        pixmap.dispose();
    }

    public static void loadFonts()
    {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        menuFont = generator.generateFont(parameter);
        generator.dispose();
    }
}
