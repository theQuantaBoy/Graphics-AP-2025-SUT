package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ProfileMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private final Stage stage;
    private final Skin skin;
    private final RegisterMenuController registerController = new RegisterMenuController();
    private final LoginMenuController loginController = new LoginMenuController();
    private final Table table;
    private Label feedbackLabel;
    private final float DIALOG_FONT_SCALE = 1.4f;

    public ProfileMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        table.center();
        table.defaults().pad(12);

        setupUI();
        setupListeners();
    }

    private void setupUI()
    {
        Label title = new Label("Profile Menu", skin);
        title.setFontScale(2.2f);
        table.add(title).padBottom(60).row();

        float buttonWidth = 320;
        float buttonHeight = 65;

        addMenuButton("Change Username", buttonWidth, buttonHeight);
        addMenuButton("Change Password", buttonWidth, buttonHeight);
        addMenuButton("Change Avatar", buttonWidth, buttonHeight);
        addMenuButton("Delete Account", buttonWidth, buttonHeight);
        addMenuButton("Go Back", buttonWidth, buttonHeight).padTop(30);

        feedbackLabel = new Label("", skin);
        feedbackLabel.setFontScale(1.1f);
        table.add(feedbackLabel).padTop(25).row();

        stage.addActor(table);
    }

    private TextButton addMenuButton(String text, float width, float height)
    {
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(1.3f);
        table.add(button).width(width).height(height).row();
        return button;
    }

    private void setupListeners()
    {
        TextButton changeUsername = (TextButton) table.getCells().get(1).getActor();
        TextButton changePassword = (TextButton) table.getCells().get(2).getActor();
        TextButton changeAvatar = (TextButton) table.getCells().get(3).getActor();
        TextButton deleteAccount = (TextButton) table.getCells().get(4).getActor();
        TextButton goBack = (TextButton) table.getCells().get(5).getActor();

        changeUsername.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                showChangeFieldDialog("Change Username", "New Username:", true);
            }
        });

        changePassword.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                showChangeFieldDialog("Change Password", "New Password:", false);
            }
        });

        changeAvatar.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                showAvatarDialog();
            }
        });

        deleteAccount.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                showConfirmationDialog();
            }
        });

        goBack.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new MainMenuScreen());
            }
        });
    }

    private void showChangeFieldDialog(String title, String labelText, boolean isUsername)
    {
        final TextField field = new TextField("", skin);
        field.setMessageText("Enter new " + (isUsername ? "username" : "password"));
        field.getStyle().font.getData().setScale(DIALOG_FONT_SCALE);

        final Label errorLabel = new Label("", skin);
        errorLabel.setFontScale(DIALOG_FONT_SCALE * 0.9f);

        // Track password visibility state
        final boolean[] showPassword = {false};

        field.setTextFieldListener((textField, c) -> validateInput(field.getText(), isUsername, errorLabel));

        Dialog dialog = new Dialog(title, skin)
        {
            @Override
            protected void result(Object result)
            {
                if ((Boolean) result)
                {
                    handleFieldChange(field.getText(), isUsername, errorLabel, title);
                }
            }
        };
        dialog.getTitleLabel().setFontScale(DIALOG_FONT_SCALE);

        Table content = new Table();
        content.defaults().pad(15);

        Label label = new Label(labelText, skin);
        label.setFontScale(DIALOG_FONT_SCALE);
        content.add(label).left().row();
        content.add(field).width(400).padTop(10).row();

        if (!isUsername)
        {
            CheckBox showPasswordCheckBox = new CheckBox("Show Password", skin);
            showPasswordCheckBox.getLabel().setFontScale(DIALOG_FONT_SCALE * 0.8f);
            showPasswordCheckBox.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    showPassword[0] = !showPassword[0];
                    field.setPasswordMode(!showPassword[0]);
                    showPasswordCheckBox.setText(showPassword[0] ? "Hide Password" : "Show Password");
                }
            });
            content.add(showPasswordCheckBox).colspan(2).padTop(10).row();
        }

        content.add(errorLabel).padTop(5).row();

        dialog.getContentTable().add(content);

        TextButton changeBtn = new TextButton("Change", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        changeBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);
        cancelBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);

        dialog.button(changeBtn, true);
        dialog.button(cancelBtn, false);

        dialog.show(stage);
    }

    private void validateInput(String input, boolean isUsername, Label errorLabel)
    {
        if (isUsername)
        {
            validateUsername(input, errorLabel);
        } else
        {
            validatePassword(input, errorLabel);
        }
    }

    private void validateUsername(String input, Label errorLabel)
    {
        if (input.length() < 4)
        {
            errorLabel.setText("Input too short (min 4 characters)!");
            errorLabel.setColor(Color.RED);
        } else if (!registerController.isUnique(input))
        {
            errorLabel.setText("Username already taken!");
            errorLabel.setColor(Color.RED);
        } else {
            errorLabel.setText("Username available");
            errorLabel.setColor(Color.GREEN);
        }
    }

    private void validatePassword(String input, Label errorLabel)
    {
        if (!registerController.isLongEnough(input))
        {
            errorLabel.setText("password too short!");
            errorLabel.setColor(Color.YELLOW);
        } else if (!registerController.hasCapital(input))
        {
            errorLabel.setText("password must have a capital letter!");
            errorLabel.setColor(Color.RED);
        } else if (!registerController.hasDigit(input))
        {
            errorLabel.setText("password must have a digit!");
            errorLabel.setColor(Color.RED);
        } else if (!registerController.hasSpecial(input))
        {
            errorLabel.setText("password must have a special character!");
            errorLabel.setColor(Color.RED);
        } else {
            errorLabel.setText("password is strong");
            errorLabel.setColor(Color.GREEN);
        }
    }

    private void handleFieldChange(String input, boolean isUsername, Label errorLabel, String title)
    {
        validateInput(input, isUsername, errorLabel);

        if (!errorLabel.getText().toString().toLowerCase().contains("available") &&
            !errorLabel.getText().toString().toLowerCase().contains("strong"))
        {
            return;
        }

        // Use proper controller methods - you'll need to implement these in your controllers
        boolean success = isUsername
            ? registerController.updateUsername(App.getCurrentPlayer().getUsername(), input)
            : loginController.setPassword(App.getCurrentPlayer().getUsername(), input);

        if (success)
        {
            feedbackLabel.setText(title + " updated successfully!");
            feedbackLabel.setColor(Color.GREEN);
        } else
        {
            feedbackLabel.setText("Update failed!");
            feedbackLabel.setColor(Color.RED);
        }
    }

    // Remaining original methods
    private void showAvatarDialog()
    {
        Dialog dialog = new Dialog("Change Avatar", skin);
        dialog.getTitleLabel().setFontScale(DIALOG_FONT_SCALE);

        Table content = new Table();
        content.defaults().pad(20);

        final int[] avatarIndex = {0};
        Texture initialTexture = new Texture(App.getCurrentPlayer().getAvatar().getPortraitPath());
        Image avatarImage = new Image(new TextureRegionDrawable(new TextureRegion(initialTexture)));
        Label avatarLabel = new Label(App.getCurrentPlayer().getAvatar().getName(), skin);
        avatarLabel.setFontScale(DIALOG_FONT_SCALE);

        TextButton left = createArrowButton("<");
        TextButton right = createArrowButton(">");

        left.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                avatarIndex[0] = (avatarIndex[0] - 1 + Avatar.values().length) % Avatar.values().length;
                updateAvatarDisplay(avatarImage, avatarLabel, avatarIndex[0]);
                Player player = App.getCurrentPlayer();
                player.setAvatar(Avatar.getAvatar(avatarIndex[0]));
            }
        });

        right.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                avatarIndex[0] = (avatarIndex[0] + 1) % Avatar.values().length;
                updateAvatarDisplay(avatarImage, avatarLabel, avatarIndex[0]);
                Player player = App.getCurrentPlayer();
                player.setAvatar(Avatar.getAvatar(avatarIndex[0]));
            }
        });

        content.add(avatarImage).size(240, 240).colspan(2).row();
        content.add(left).padRight(30);
        content.add(right).padLeft(30).row();
        content.add(avatarLabel).colspan(2).center().padTop(15);

        dialog.getContentTable().add(content);

        TextButton confirmBtn = new TextButton("Confirm", skin);
        TextButton cancelBtn = new TextButton("Cancel", skin);

        confirmBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);
        cancelBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);

        dialog.button(confirmBtn, true);
        dialog.button(cancelBtn, false);

        dialog.show(stage);
    }

    private TextButton createArrowButton(String text)
    {
        TextButton button = new TextButton(text, skin);
        button.getLabel().setFontScale(DIALOG_FONT_SCALE * 1.5f);
        return button;
    }

    private void updateAvatarDisplay(Image avatarImage, Label avatarLabel, int index)
    {
        TextureRegionDrawable previous = (TextureRegionDrawable) avatarImage.getDrawable();
        if (previous != null && previous.getRegion() != null)
        {
            previous.getRegion().getTexture().dispose();
        }

        Texture newTexture = new Texture(Avatar.getAvatar(index).getPortraitPath());
        avatarImage.setDrawable(new TextureRegionDrawable(new TextureRegion(newTexture)));
        avatarLabel.setText(Avatar.getAvatar(index).getName());
    }

    private void showConfirmationDialog()
    {
        Dialog dialog = new Dialog("Confirm Deletion", skin);
        dialog.getTitleLabel().setFontScale(DIALOG_FONT_SCALE);

        Label message = new Label("Are you sure you want to delete your account?", skin);
        message.setFontScale(DIALOG_FONT_SCALE);
        dialog.text(message);

        TextButton yesBtn = new TextButton("Yes", skin);
        TextButton noBtn = new TextButton("No", skin);

        yesBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);
        noBtn.getLabel().setFontScale(DIALOG_FONT_SCALE);

        dialog.button(yesBtn, true);
        dialog.button(noBtn, false);

        dialog.show(stage);
    }

    @Override public void show() { Gdx.input.setInputProcessor(stage); }

    @Override public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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

    @Override public void dispose() { stage.dispose(); }
}
