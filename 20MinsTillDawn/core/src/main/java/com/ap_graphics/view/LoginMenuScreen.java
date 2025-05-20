package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.model.Result;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginMenuScreen implements Screen
{
    private final Game app = TillDawn.getGame();
    private final Stage stage;
    private final Skin skin;
    private final LoginMenuController controller = new LoginMenuController();

    private final TextField usernameField, passwordField;
    private final Label errorLabel;
    private final Table table;

    public LoginMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(10).width(280);

        // Title
        Label title = new Label("Login", skin, "title");
        title.setFontScale(1.3f);
        table.add(title).colspan(2).center().padBottom(25).row();

        // username
        table.add(new Label("username", skin)).left();
        usernameField = new TextField("", skin);
        table.add(usernameField).left().row();

        // password
        table.add(new Label("password", skin)).left();
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(passwordField).left().row();

        // Error label (for validation)
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        table.add(errorLabel).colspan(2).left().row();

        // Login button
        TextButton loginButton = new TextButton("Login", skin);

        loginButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                if (username.isEmpty() || password.isEmpty())
                {
                    errorLabel.setText("All fields are required!");
                    return;
                }

                Result result = controller.onLogin(username, password);
                if (result.isSuccessful())
                {
                    app.setScreen(new MainMenuScreen(skin));
                } else
                {
                    errorLabel.setText(result.toString());
                }
            }
        });

        table.add(loginButton).colspan(2).center().padTop(16).row();

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
