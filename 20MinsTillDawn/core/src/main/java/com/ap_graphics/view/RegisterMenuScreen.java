package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.util.Random;
import java.util.function.Consumer;

public class RegisterMenuScreen implements Screen
{
    private final Game app = TillDawn.getGame();
    private final Stage stage;
    private final Skin skin;
    private final RegisterMenuController controller = new RegisterMenuController();

    private final TextField nameField, usernameField, passwordField;
    private final Table table;

    private Image avatarImage;
    private int avatarIndex = (new Random()).nextInt(Avatar.values().length);

    public RegisterMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(8);

        // Title
        Label title = new Label("Sign Up", skin, "title");
        title.setFontScale(1.3f);
        table.add(title).colspan(2).center().padBottom(20).row();

        // Layout: formTable = form left + avatar right
        Table formTable = new Table();
        formTable.defaults().pad(6);

        // ✅ Form Fields (left)
        Table fieldTable = new Table();
        fieldTable.defaults().pad(4).left();

        fieldTable.add(new Label("Name", skin)).row();
        nameField = new TextField("", skin);
        fieldTable.add(nameField).width(200).row();

        fieldTable.add(new Label("Username", skin)).row();
        usernameField = new TextField("", skin);
        fieldTable.add(usernameField).width(200).row();

        fieldTable.add(new Label("Password", skin)).row();
        passwordField = new TextField("", skin);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');
        fieldTable.add(passwordField).width(200).row();

        // ✅ Avatar Table (right)
        Table avatarTable = new Table();
        avatarImage = new Image(new Texture(Gdx.files.internal(Avatar.getAvatar(avatarIndex).getPortraitPath())));
        avatarImage.setSize(240, 240);
        avatarTable.add(avatarImage).size(240, 240).center().row();

        Label avatarName = new Label(Avatar.getAvatar(avatarIndex).getName(), skin);
        avatarTable.add(avatarName).center().padTop(4).row();

        TextButton leftButton = new TextButton("<", skin);
        TextButton rightButton = new TextButton(">", skin);
        Table avatarNav = new Table();
        avatarNav.add(leftButton).padRight(5);
        avatarNav.add(rightButton).padLeft(5);
        avatarTable.add(avatarNav).center().padTop(4);

        // Add to formTable (left: fields, right: avatar)
        formTable.add(fieldTable).top().padRight(40);
        formTable.add(avatarTable).top();

        table.add(formTable).center().row();

        // ✅ Sign Up Button
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
                    showDialog("All fields are required!");
                    return;
                }

                showSecurityQuestionDialog(selectedOption ->
                {
//                    Result result = controller.register(username, password, name, avatarIndex, selectedOption);
//
//                    if (result.isSuccessful())
//                    {
//                        app.setScreen(new LoginMenuScreen(skin));
//                    } else
//                    {
//
//                    }
                });
            }
        });
        table.add(registerButton).colspan(2).center().padTop(20).row();

        // ✅ Guest Login Button
        TextButton guestButton = new TextButton("Play as Guest", skin);
        guestButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                showDialog("Guest login not implemented yet.");
            }
        });
        table.add(guestButton).colspan(2).center().padTop(6).row();

        // ✅ Avatar navigation logic
        leftButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                avatarIndex = (avatarIndex + Avatar.values().length - 1) % Avatar.values().length;
                updateAvatarDisplay(avatarName);
            }
        });

        rightButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                avatarIndex = (avatarIndex + 1) % Avatar.values().length;
                updateAvatarDisplay(avatarName);
            }
        });

        stage.addActor(table);
    }

    private void updateAvatarDisplay(Label avatarName)
    {
        Texture texture = new Texture(Gdx.files.internal(Avatar.getAvatar(avatarIndex).getPortraitPath()));
        avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(texture)));
        avatarImage.setSize(240, 240);
        avatarName.setText(Avatar.getAvatar(avatarIndex).getName());
    }

    private void showDialog(String message)
    {
        Dialog dialog = new Dialog("Error", skin);
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
