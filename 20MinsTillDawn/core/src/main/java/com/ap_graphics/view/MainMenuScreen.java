package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.controller.MainMenuController;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen
{
    private final Game app = TillDawn.getGame();
    private final Stage stage;
    private final Skin skin;
    private final MainMenuController controller = new MainMenuController();

    private final Table table;

    public MainMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(10).width(280);

        // Title
        Label title = new Label("Main Menu", skin, "title");
        title.setFontScale(1.3f);

        title.setAlignment(Align.center);
        table.add(title).colspan(2).center().padBottom(60).expandX().row();

        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new SettingsMenuScreen(skin));
            }
        });

        TextButton profileButton = new TextButton("Profile", skin);
        profileButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new ProfileMenuScreen(skin));
            }
        });

        table.add(settingsButton).pad(20).width(240).height(60);
        table.add(profileButton).pad(20).width(240).height(60);
        table.row();

        TextButton preGameButton = new TextButton("Pre Game", skin);
        preGameButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new PreGameMenuScreen(skin));
            }
        });

        TextButton scoreBoardButton = new TextButton("Score Board", skin);
        scoreBoardButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new ScoreBoardMenuScreen(skin));
            }
        });

        table.add(preGameButton).pad(20).width(240).height(60);
        table.add(scoreBoardButton).pad(20).width(240).height(60);
        table.row();

        TextButton hintButton = new TextButton("Hints (Talent)", skin);
        hintButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new HintMenuScreen(skin));
            }
        });

        table.add(hintButton).colspan(2).center().padBottom(60).expandX().row();

        // In your MainMenuScreen's create() method, add:
        TextButton playGameButton = new TextButton("Play Game", skin);
        playGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen gameScreen = new GameScreen(Avatar.AVATAR_00); // Start with Abby
                app.setScreen(gameScreen);
            }
        });
        table.add(playGameButton).pad(10).row();

        table.setFillParent(true);
        stage.addActor(table);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta)
    {
        // Set background color
        Gdx.gl.glClearColor(0.0588f, 0.3882f, 0.3098f, 1f); // #0f634f
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
