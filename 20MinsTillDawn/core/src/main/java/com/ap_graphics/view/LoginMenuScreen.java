package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.function.Consumer;

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
        TextButton forgotPasswordButton = new TextButton("Forgot Password", skin);

        loginButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                if (username.isEmpty() || password.isEmpty())
                {
                    showDialog("All fields are required!");
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

        forgotPasswordButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String username = usernameField.getText().trim();
                String password = passwordField.getText();

                if (username.isEmpty())
                {
                    errorLabel.setText("You must enter a username!");
                    return;
                }

                showSecurityQuestionDialog(selectedOption ->
                {
                    Result result = controller.validSecurityQuestion(username, password, selectedOption);
                    showDialog(result.toString());
                });
            }
        });

        table.add(loginButton).colspan(2).center().padTop(16).row();
        table.add(forgotPasswordButton).colspan(2).center().padTop(16).row();

        stage.addActor(table);
    }

    private void showDialog(String message)
    {
        Dialog dialog = new Dialog("Error!", skin);
        dialog.text(message);
        dialog.button("OK");
        dialog.show(stage);
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

    private void showSecurityQuestionDialog(Consumer<SecurityQuestionOptions> onChoiceSelected)
    {
        Dialog dialog = new Dialog("Security Question", skin);

        Table content = new Table();
        content.defaults().pad(12);

        Label questionLabel = new Label("Which one would you rather have?", skin);
        content.add(questionLabel).colspan(2).center().padBottom(20).row();

        for (SecurityQuestionOptions option : SecurityQuestionOptions.values())
        {
            Texture texture = new Texture(Gdx.files.internal(option.getPath()));
            Image image = new Image(texture);
            image.invalidateHierarchy();

            Label label = new Label(option.getName(), skin);

            TextButton button = new TextButton("Choose", skin);
            button.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    dialog.hide();
                    onChoiceSelected.accept(option);
                }
            });

            VerticalGroup group = new VerticalGroup();
            group.space(6);
            group.addActor(image);
            group.addActor(label);
            group.addActor(button);
            group.align(Align.center);

            content.add(group).pad(10);
        }

        dialog.getContentTable().add(content).center();
        dialog.show(stage);
    }
}
