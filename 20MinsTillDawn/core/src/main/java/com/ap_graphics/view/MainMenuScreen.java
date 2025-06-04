package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MainMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Skin skin;
    private Image topStripe, bottomStripe;

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Player player = App.getCurrentPlayer();
        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = TillDawn.menuFont;

        Label titleLabel = new Label("Main Menu", labelStyle);
        titleLabel.setFontScale(2.1f);
        titleLabel.setColor(Color.RED);

        Label usernameLabel = new Label(player.getUsername(), labelStyle);
        usernameLabel.setFontScale(1.5f);
        Label scoreLabel = new Label("Score: " + player.getScore(), labelStyle);
        scoreLabel.setFontScale(1.5f);

        TextButton.TextButtonStyle btnStyle = skin.get(TextButton.TextButtonStyle.class);
        btnStyle.font = TillDawn.menuFont;

        TextButton settingsButton = new TextButton("Settings", btnStyle);
        TextButton scoreboardButton = new TextButton("Scoreboard", btnStyle);
        TextButton newGameButton = new TextButton("New Game", btnStyle);
        TextButton continueButton = new TextButton("Continue", btnStyle);
        TextButton hintsButton = new TextButton("Hints", btnStyle);

        TextButton.TextButtonStyle logoutStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        logoutStyle.font = TillDawn.menuFont;
        logoutStyle.fontColor = Color.BLUE;
        TextButton logoutButton = new TextButton("Logout", logoutStyle);

        TextButton profileButton = new TextButton("Profile", btnStyle);

        logoutButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                App.setCurrentPlayer(null);
                app.setScreen(new FirstMenuScreen());
            }
        });

        settingsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new SettingsMenuScreen());
            }
        });

        scoreboardButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new ScoreBoardMenuScreen());
            }
        });

        newGameButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new PreGameMenuScreen());
            }
        });

        continueButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {

            }
        });

        hintsButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new HintMenuScreen());
            }
        });

        profileButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new ProfileMenuScreen(skin));
            }
        });

        settingsButton.pack(); scoreboardButton.pack();
        newGameButton.pack(); continueButton.pack();
        hintsButton.pack(); logoutButton.pack();

        float maxWidth = Math.max(
            Math.max(settingsButton.getWidth(), scoreboardButton.getWidth()),
            Math.max(
                Math.max(newGameButton.getWidth(), continueButton.getWidth()),
                Math.max(hintsButton.getWidth(), logoutButton.getWidth())
            )
        ) + 80;

        topStripe = new Image(new Texture(Gdx.files.internal("images/visual/T_TopStripe.png")));
        bottomStripe = new Image(new Texture(Gdx.files.internal("images/visual/T_BottomDiagonalStripe.png")));
        stage.addActor(topStripe);
        stage.addActor(bottomStripe);

        Table root = new Table();
        root.setFillParent(true);
        root.padTop(50).padLeft(80).padRight(80);
        stage.addActor(root);

        Table leftSide = new Table();
        leftSide.top().left();
        leftSide.add(titleLabel).left().padBottom(30).row();
        leftSide.add(usernameLabel).left().padBottom(10).row();
        leftSide.add(scoreLabel).left().padBottom(80).row();

        Table buttons = new Table();
        buttons.defaults().width(maxWidth).height(80).padBottom(60);
        buttons.add(settingsButton).padRight(60);
        buttons.add(scoreboardButton).row();
        buttons.add(newGameButton).padRight(60);
        buttons.add(continueButton).row();
        buttons.add(hintsButton).padRight(60);
        buttons.add(logoutButton).row();

        leftSide.add(buttons).colspan(2).left().expandY().top().padTop(100).padLeft(100);

        Table rightCol = new Table();
        Texture avatarTex = player.getImage();
        Image avatarImage = new Image(new TextureRegion(avatarTex));

        float avatarRatio = (float) avatarTex.getWidth() / avatarTex.getHeight();
        float avatarWidth = 720f;
        float avatarHeight = avatarWidth / avatarRatio;

        avatarImage.setSize(avatarWidth, avatarHeight);
        RepeatAction floatAnim = Actions.forever(
            Actions.sequence(
                Actions.moveBy(0, 8, 1.1f),
                Actions.moveBy(0, -8, 1.1f)
            )
        );
        avatarImage.addAction(floatAnim);

        rightCol.add(avatarImage).size(avatarWidth, avatarHeight).expand().center().padBottom(20);
        rightCol.row();
        rightCol.add(profileButton).width(180).height(50).center();

        root.add(leftSide.padTop(50)).top().left().expandX();
        root.add(rightCol).center().expand();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        topStripe.setSize(Gdx.graphics.getWidth(), topStripe.getHeight());
        topStripe.setPosition(0, Gdx.graphics.getHeight() - topStripe.getHeight());
        bottomStripe.setSize(Gdx.graphics.getWidth(), bottomStripe.getHeight());
        bottomStripe.setPosition(0, 0);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height)
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
}
