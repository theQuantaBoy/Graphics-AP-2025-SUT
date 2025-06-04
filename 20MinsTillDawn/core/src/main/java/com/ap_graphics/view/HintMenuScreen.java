package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.MenuTexts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.Input;

public class HintMenuScreen implements Screen {
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = App.getCurrentPlayer().getFont();

        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font = App.getCurrentPlayer().getFont();

        Table root = new Table();
        root.setFillParent(true);
        root.padTop(100).padLeft(60).padRight(60);
        stage.addActor(root);

        Label titleLabel = new Label(MenuTexts.HINT_MENU.getText(), labelStyle);
        titleLabel.setFontScale(2f);
        titleLabel.setColor(Color.SKY);
        root.add(titleLabel).colspan(4).center().padBottom(70).row();

        Player player = App.getCurrentPlayer();

        Table heroGuideTable = createSection(MenuTexts.HERO_GUIDE.getText(), labelStyle,
            MenuTexts.SHANA.getText(), "  " + MenuTexts.HP.getText() + ": 4", "  " + MenuTexts.SPEED.getText() + ": 4", " ",
            MenuTexts.DIAMOND.getText(), "  " + MenuTexts.HP.getText() + ": 7", "  " + MenuTexts.SPEED.getText() + ": 1", " ",
            MenuTexts.SCARLET.getText(), "  " + MenuTexts.HP.getText() + ": 3", "  " + MenuTexts.SPEED.getText() + ": 5", " ",
            MenuTexts.LILITH.getText(), "  " + MenuTexts.HP.getText() + ": 5", "  " + MenuTexts.SPEED.getText() + ": 3", " ",
            MenuTexts.DASHER.getText(), "  " + MenuTexts.HP.getText() + ": 2", "  " + MenuTexts.SPEED.getText() + ": 10"
        );

        Table keyGuideTable = createSection(MenuTexts.GAME_KEYS.getText(), labelStyle,
            MenuTexts.UP.getText() + ": " + keyName(player.getMoveUpKey()), " ",
            MenuTexts.LEFT.getText() + ": " + keyName(player.getMoveLeftKey()), " ",
            MenuTexts.DOWN.getText() + ": " + keyName(player.getMoveDownKey()), " ",
            MenuTexts.RIGHT.getText() + ": " + keyName(player.getMoveRightKey())
        );

        Table cheatCodeTable = createSection(MenuTexts.CHEAT_CODES.getText(), labelStyle,
            MenuTexts.ADVANCE_TIME.getText(), " ",
            MenuTexts.LEVEL_UP.getText(), " ",
            MenuTexts.INCREASE_HP.getText(), " ",
            MenuTexts.BOSS_FIGHT.getText(), " ",
            MenuTexts.KILL_ALL.getText(), " ",
            MenuTexts.PAUSE_MENU.getText(), " ",
            MenuTexts.INSTANT_EXIT.getText(), " ",
            MenuTexts.AUTO_AIM.getText(), " ",
            MenuTexts.AUTO_RELOAD.getText()
        );

        Table abilityTable = createSection(MenuTexts.ABILITIES.getText(), labelStyle,
            MenuTexts.INCREASE_HP_AB.getText(), "  " + MenuTexts.HP_EFFECT.getText(), " ",
            MenuTexts.INCREASE_DMG.getText(), "  " + MenuTexts.DMG_EFFECT.getText(), " ",
            MenuTexts.INCREASE_PROJECTILES.getText(), "  " + MenuTexts.PROJECTILES_EFFECT.getText(), " ",
            MenuTexts.INCREASE_AMMO.getText(), "  " + MenuTexts.AMMO_EFFECT.getText(), " ",
            MenuTexts.INCREASE_SPEED.getText(), "  " + MenuTexts.SPEED_EFFECT.getText()
        );

        root.defaults().padLeft(20).padRight(20).top();
        root.add(heroGuideTable).expand().padRight(10);
        root.add(keyGuideTable).expand().padLeft(10).padRight(10);
        root.add(cheatCodeTable).expand().padLeft(10).padRight(10);
        root.add(abilityTable).expand().padLeft(10);
        root.row();

        TextButton backButton = new TextButton(MenuTexts.GO_BACK.getText(), buttonStyle);
        backButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new MainMenuScreen());
            }
        });
        root.add(backButton).colspan(4).center().padTop(80).padBottom(180).height(70).width(300);
    }

    private Table createSection(String title, Label.LabelStyle style, String... lines)
    {
        Table container = new Table();
        container.pad(10).defaults().left().padBottom(5);

        Label titleLabel = new Label(title, style);
        titleLabel.setFontScale(1.0f);
        container.add(titleLabel).center().padBottom(15).row();
        titleLabel.setColor(Color.PINK);


        for (String line : lines)
        {
            Label lineLabel = new Label(line, style);
            lineLabel.setFontScale(0.85f);
            container.add(lineLabel).left().row();
        }

        // Outer table with background style
        Table background = new Table(skin);

        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(1, 1, 1, 0.05f)); // Very light white-transparent
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        background.setBackground(new Image(texture).getDrawable());

        background.pad(15);
        background.add(container);

        return background;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override public void dispose()
    {
        stage.dispose();
    }

    private String keyName(int keycode)
    {
        return Input.Keys.toString(keycode);
    }
}
