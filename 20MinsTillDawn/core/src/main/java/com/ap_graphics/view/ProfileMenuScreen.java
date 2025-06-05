package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.App;
import com.ap_graphics.model.GameSaver;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.MenuTexts;
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

public class ProfileMenuScreen implements Screen {
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
    private final RegisterMenuController registerController = new RegisterMenuController();
    private final LoginMenuController loginController = new LoginMenuController();
    private Label feedbackLabel;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;
    private final float DIALOG_FONT_SCALE = 1.2f;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = App.getCurrentPlayer().getFont();

        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font = App.getCurrentPlayer().getFont();

        Table root = new Table();
        root.setFillParent(true);
        root.center();
        stage.addActor(root);

        // Title
        Label title = new Label(MenuTexts.PROFILE_MENU.getText(), labelStyle);
        title.setFontScale(2.2f);
        root.add(title).padBottom(60).row();

        // Buttons
        float buttonWidth = 320;
        float buttonHeight = 65;

        TextButton changeUsername = createMenuButton(MenuTexts.CHANGE_USERNAME.getText(), buttonStyle);
        TextButton changePassword = createMenuButton(MenuTexts.CHANGE_PASSWORD.getText(), buttonStyle);
        TextButton changeAvatar = createMenuButton(MenuTexts.CHANGE_AVATAR.getText(), buttonStyle);
        TextButton deleteAccount = createMenuButton(MenuTexts.DELETE_ACCOUNT.getText(), buttonStyle);
        TextButton goBack = createMenuButton(MenuTexts.GO_BACK.getText(), buttonStyle);

        root.add(changeUsername).width(buttonWidth + 40).height(buttonHeight).pad(12).row();
        root.add(changePassword).width(buttonWidth + 40).height(buttonHeight).pad(12).row();
        root.add(changeAvatar).width(buttonWidth + 40).height(buttonHeight).pad(12).row();
        root.add(deleteAccount).width(buttonWidth + 40).height(buttonHeight).pad(12).row();
        root.add(goBack).width(buttonWidth + 40).height(buttonHeight).pad(30).row();

        // Feedback label
        feedbackLabel = new Label("", skin);
        feedbackLabel.setFontScale(1.1f);
        root.add(feedbackLabel).padTop(25).row();

        // Button listeners
        setupButtonListeners(changeUsername, changePassword, changeAvatar, deleteAccount, goBack);

        // Leaves decoration
        leavesTex = new Texture("images/visual/T_TitleLeaves.png");
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);
        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);
    }

    private TextButton createMenuButton(String text, TextButton.TextButtonStyle style) {
        TextButton button = new TextButton(text, style);
        button.getLabel().setFontScale(1.2f);
        return button;
    }

    private void setupButtonListeners(TextButton changeUsername, TextButton changePassword,
                                      TextButton changeAvatar, TextButton deleteAccount,
                                      TextButton goBack) {
        changeUsername.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                showChangeFieldDialog(
                    MenuTexts.CHANGE_USERNAME_TITLE.getText(),
                    MenuTexts.CHANGE_USERNAME_PROMPT.getText(),
                    true
                );
            }
        });

        changePassword.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                showChangeFieldDialog(
                    MenuTexts.CHANGE_PASSWORD_TITLE.getText(),
                    MenuTexts.CHANGE_PASSWORD_PROMPT.getText(),
                    false
                );
            }
        });

        changeAvatar.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                showAvatarDialog();
            }
        });

        deleteAccount.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
               Player player = App.getCurrentPlayer();

               if (GameSaver.saveExists(player.getUsername()))
               {
                   GameSaver.deleteSave(player.getUsername());
               }

               if (App.getPlayers().contains(player))
               {
                   App.getPlayers().remove(player);
               }

               App.setCurrentPlayer(null);
               app.setScreen(new FirstMenuScreen());
            }
        });

        goBack.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                app.setScreen(new MainMenuScreen());
            }
        });
    }

    private void showChangeFieldDialog(String title, String labelText, boolean isUsername)
    {
        final TextField field = new TextField("", skin);
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
            CheckBox showPasswordCheckBox = new CheckBox(MenuTexts.SHOW_PASSWORD.getText(), skin);
            showPasswordCheckBox.getLabel().setFontScale(DIALOG_FONT_SCALE * 0.8f);

            showPasswordCheckBox.addListener(new ClickListener()
            {
                @Override
                public void clicked(InputEvent event, float x, float y)
                {
                    showPassword[0] = !showPassword[0];
                    field.setPasswordMode(!showPassword[0]);

                    String updatedText = showPassword[0]
                        ? MenuTexts.HIDE_PASSWORD.getText()
                        : MenuTexts.SHOW_PASSWORD.getText();

                    showPasswordCheckBox.setText(updatedText);
                }
            });

            content.add(showPasswordCheckBox).colspan(2).padTop(10).row();
        }

        content.add(errorLabel).padTop(5).row();

        dialog.getContentTable().add(content);

        TextButton changeBtn = new TextButton(MenuTexts.CHANGE.getText(), skin);
        TextButton cancelBtn = new TextButton(MenuTexts.CANCEL.getText(), skin);

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

    private boolean validateUsername(String input, Label errorLabel)
    {
        if (!registerController.isUnique(input))
        {
            errorLabel.setText(MenuTexts.USERNAME_TAKEN.getText());
            errorLabel.setColor(Color.RED);
            return false;
        }
        else
        {
            errorLabel.setText(MenuTexts.USERNAME_AVAILABLE.getText());
            errorLabel.setColor(Color.GREEN);
            return true;
        }
    }

    private boolean validatePassword(String input, Label errorLabel)
    {
        if (!registerController.isLongEnough(input))
        {
            errorLabel.setText(MenuTexts.PASSWORD_TOO_SHORT.getText());
            errorLabel.setColor(Color.YELLOW);
            return false;
        }
        else if (!registerController.hasCapital(input))
        {
            errorLabel.setText(MenuTexts.PASSWORD_NO_CAPITAL.getText());
            errorLabel.setColor(Color.RED);
            return false;
        }
        else if (!registerController.hasDigit(input))
        {
            errorLabel.setText(MenuTexts.PASSWORD_NO_DIGIT.getText());
            errorLabel.setColor(Color.RED);
            return false;
        }
        else if (!registerController.hasSpecial(input))
        {
            errorLabel.setText(MenuTexts.PASSWORD_NO_SPECIAL.getText());
            errorLabel.setColor(Color.RED);
            return false;
        }
        else
        {
            errorLabel.setText(MenuTexts.PASSWORD_STRONG.getText());
            errorLabel.setColor(Color.GREEN);
            return true;
        }
    }

    private void handleFieldChange(String input, boolean isUsername, Label errorLabel, String title)
    {
        boolean isValid = isUsername
            ? validateUsername(input, errorLabel)
            : validatePassword(input, errorLabel);

        if (!isValid) return;

        // Use proper controller methods - you'll need to implement these in your controllers
        boolean success = isUsername
            ? registerController.updateUsername(App.getCurrentPlayer().getUsername(), input)
            : loginController.setPassword(App.getCurrentPlayer().getUsername(), input);

        if (success)
        {
            feedbackLabel.setText(String.format(MenuTexts.UPDATE_SUCCESSFUL.getText(), title));
            feedbackLabel.setColor(Color.GREEN);
        } else
        {
            feedbackLabel.setText(String.format(MenuTexts.UPDATE_FAILED.getText(), title));
            feedbackLabel.setColor(Color.RED);
        }
    }

    // Remaining original methods
    private void showAvatarDialog()
    {
        Dialog dialog = new Dialog(MenuTexts.CHANGE_AVATAR.getText(), skin);
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

        TextButton confirmBtn = new TextButton(MenuTexts.CONFIRM.getText(), skin);
        TextButton cancelBtn = new TextButton(MenuTexts.CANCEL.getText(), skin);

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

    @Override public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
        float leavesRatio = (float) leavesTex.getWidth() / leavesTex.getHeight();
        float leavesHeight = height;
        float leavesWidth = leavesHeight * leavesRatio;
        leftLeavesImage.setSize(leavesWidth, leavesHeight);
        leftLeavesImage.setPosition(0, 0);
        rightLeavesImage.setSize(leavesWidth, leavesHeight);
        rightLeavesImage.setPosition(width - leavesWidth, 0);
    }

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override public void dispose()
    {
        stage.dispose();
        if (leavesTex != null) leavesTex.dispose();
    }
}
