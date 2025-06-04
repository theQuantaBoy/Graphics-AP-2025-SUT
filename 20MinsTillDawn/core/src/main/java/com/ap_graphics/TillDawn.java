package com.ap_graphics;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.App;
import com.ap_graphics.model.DataBaseManager;
import com.ap_graphics.model.GameAssetManager;
import com.ap_graphics.view.FirstMenuScreen;
import com.ap_graphics.view.GameMenuScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class TillDawn extends com.badlogic.gdx.Game
{
    private static TillDawn game;
    private static SpriteBatch batch;

    public static BitmapFont menuFont;
    public static BitmapFont japaneseFont;

    @Override
    public void create()
    {
        applyCustomCursor(0, 0);
        GameAssetManager.getGameAssetManager().loadAllAssets();
        loadFonts();
        SoundManager.getInstance(); // initialize sounds

        DataBaseManager.initialize();
        App.getPlayers().addAll(DataBaseManager.loadPlayers());

        game = this;
        batch = new SpriteBatch();
        getGame().setScreen(new FirstMenuScreen());
    }

    @Override
    public void dispose()
    {
        DataBaseManager.savePlayers(App.getPlayers());

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
        // English font
        FreeTypeFontGenerator generatorEn = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ChevyRay.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramEn = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramEn.size = 40;
        menuFont = generatorEn.generateFont(paramEn);
        generatorEn.dispose();

        // Japanese font
        FreeTypeFontGenerator generatorJp = new FreeTypeFontGenerator(Gdx.files.internal("fonts/MPLUS1p-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter paramJp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        paramJp.size = 36;
        paramJp.characters = FreeTypeFontGenerator.DEFAULT_CHARS +
            // Hiragana
            "あいうえおかきくけこさしすせそたちつてとなにぬねの" +
            "はひふへほまみむめもやゆよらりるれろわをん" +
            // Katakana
            "アイウエオカキクケコサシスセソタチツテトナニヌネノ" +
            "ハヒフヘホマミムメモヤユヨラリルレロワヲン" +
            "ガギグゲゴザジズゼゾダヂヅデドバビブベボパピプペポ" +
            // Common punctuation
            "ー「」、。・" +
            // Game UI Kanji
            "日本語設定開始終了保存読込敵勇者武器弾体力時間経験値";
        japaneseFont = generatorJp.generateFont(paramJp);
        generatorJp.dispose();
    }
}
