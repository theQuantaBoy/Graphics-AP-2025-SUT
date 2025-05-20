package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.Result;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

public class RegisterMenuScreen implements Screen
{
    private final Game app = TillDawn.getGame();
    private final Stage stage;
    private final Skin skin;
    private final RegisterMenuController controller = new RegisterMenuController();

    private final TextField nameField, usernameField, passwordField;
    private final Label errorLabel;
    private final Table table;

    public RegisterMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(10).width(280);

        // Title
        Label title = new Label("Sign Up", skin, "title");
        title.setFontScale(1.3f);
        table.add(title).colspan(2).center().padBottom(25).row();

        // Avatar selector placeholder (you can replace with real avatar UI later)
        Image avatarFrame = new Image(skin.getDrawable("window")); // You need a "window" or frame in your skin
        avatarFrame.setSize(80, 80);
        table.add().colspan(2).height(10).row(); // spacing
        table.add(new Label(" ", skin)).width(50);
        table.add(avatarFrame).width(80).height(80).center().row();

        // Name
        table.add(new Label("Name", skin)).left();
        nameField = new TextField("", skin);
        table.add(nameField).left().row();

        // Username
        table.add(new Label("Username", skin)).left();
        usernameField = new TextField("", skin);
        table.add(usernameField).left().row();

        // Password
        table.add(new Label("Password", skin)).left();
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(passwordField).left().row();

        // Error label (for validation)
        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        table.add(errorLabel).colspan(2).left().row();

        // Register button
        TextButton registerButton = new TextButton("Sign Up", skin);
        registerButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String name = nameField.getText().trim();
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty())
                {
                    errorLabel.setText("All fields are required!");
                    return;
                }

                Result result = controller.onRegister(username, password, name);
                if (result.isSuccessful())
                {
                    app.setScreen(new LoginMenuScreen(skin));
                } else
                {
                    errorLabel.setText(result.toString());
                }
            }
        });

        // Play as guest button (optional)
        TextButton guestButton = new TextButton("Play as Guest", skin);
        guestButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                // TODO: Implement guest login logic
            }
        });

        table.add(registerButton).colspan(2).center().padTop(16).row();
        table.add(guestButton).colspan(2).center().padTop(4);

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
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
