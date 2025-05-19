package com.ap_graphics.view;

import com.ap_graphics.controller.RegisterMenuController;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class RegisterMenuScreen implements Screen
{
    private final Stage stage;
    private final Skin skin;
    private final TextField usernameField, passwordField, nameField;
    private final Label errorLabel;
    private RegisterMenuController controller;

    public RegisterMenuScreen(RegisterMenuController controller, Skin skin)
    {
        this.controller = controller;
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(10).width(260);

        // Name
        table.add(new Label("Name", skin)).left().row();
        nameField = new TextField("", skin);
        table.add(nameField).row();

        // Username
        table.add(new Label("Username", skin)).left().row();
        usernameField = new TextField("", skin);
        table.add(usernameField).row();

        // Password
        table.add(new Label("Password", skin)).left().row();
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        table.add(passwordField).row();

        errorLabel = new Label("", skin);
        errorLabel.setColor(Color.RED);
        table.add(errorLabel).colspan(1).row();

        TextButton registerButton = new TextButton("Register", skin);

        registerButton.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                String result = controller.onRegister(usernameField.getText(), passwordField.getText(), nameField.getText());
                errorLabel.setText(result);
            }
        });

        table.add(registerButton).padTop(12);
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
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose()
    {
        stage.dispose();
    }
}
