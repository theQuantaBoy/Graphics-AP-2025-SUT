package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.LoginMenuController;
import com.ap_graphics.controller.RegisterMenuController;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LoginMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;
    private TextField usernameField, passwordField;
    private TextButton loginButton, forgetPasswordButton, goBackButton;
    private Label feedbackLabel;
    private RegisterMenuController registerController = new RegisterMenuController();
    private LoginMenuController loginController = new LoginMenuController();
    private Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
    private SecurityQuestionOptions selectedOption;

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        leavesTex = new Texture("images/visual/T_TitleLeaves.png");
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);
        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = TillDawn.menuFont;

        // Labels
        Label usernameLabel = new Label("Username", labelStyle);
        Label passwordLabel = new Label("Password", labelStyle);

        // Fields
        TextField.TextFieldStyle textFieldStyle = skin.get(TextField.TextFieldStyle.class);
        textFieldStyle.font = TillDawn.menuFont;

        usernameField = new TextField("", textFieldStyle);
        passwordField = new TextField("", textFieldStyle);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        // Feedback
        feedbackLabel = new Label("", labelStyle);
        feedbackLabel.setFontScale(0.7f);

        // Show password
        CheckBox.CheckBoxStyle cbStyle = skin.get(CheckBox.CheckBoxStyle.class);
        cbStyle.font = TillDawn.menuFont;

        CheckBox.CheckBoxStyle customStyle = new CheckBox.CheckBoxStyle(cbStyle);
        customStyle.checkboxOff = null;
        customStyle.checkboxOn = null;
        customStyle.font = TillDawn.menuFont;

        CheckBox showPasswordCheckBox = new CheckBox("[ ] Show password", customStyle);

        showPasswordCheckBox.addListener(event ->
        {
            boolean checked = showPasswordCheckBox.isChecked();
            passwordField.setPasswordMode(!checked);
            showPasswordCheckBox.setText(checked ? "[x] Show password" : "[ ] Show password");
            return true;
        });

        // Button
        TextButton.TextButtonStyle btnStyle = skin.get(TextButton.TextButtonStyle.class);
        btnStyle.font = TillDawn.menuFont;

        forgetPasswordButton = new TextButton("Forgot Password", btnStyle);
        forgetPasswordButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String username = usernameField.getText();
                String password = passwordField.getText();

                if (registerController.isUnique(username))
                {
                    updatePasswordFeedback("user not found");
                    return;
                }

                showSecurityQuestionDialog(username, password);
            }
        });

        loginButton = new TextButton("Login", btnStyle);
        loginButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                String username = usernameField.getText();
                String password = passwordField.getText();

                Result result = loginController.onLogin(username, password);

                if (result.isSuccessful())
                {
                    leftLeavesImage.addAction(Actions.moveTo(-leftLeavesImage.getWidth(), 0, 0.8f));
                    rightLeavesImage.addAction(Actions.moveTo(Gdx.graphics.getWidth(), 0, 0.8f));

                    stage.addAction(Actions.sequence(
                        Actions.delay(0.85f),
                        Actions.run(() -> app.setScreen(new MainMenuScreen()))
                    ));
                }
            }
        });

        goBackButton = new TextButton("Go Back", btnStyle);
        goBackButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                app.setScreen(new FirstMenuScreen());
            }
        });

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center();

        Label title = new Label("Log In", skin);
        title.setFontScale(2.8f);
        table.add(title).colspan(2).center().padBottom(60).padTop(40).row();

        table.add(usernameLabel).right().padRight(20).padBottom(15);
        table.add(usernameField).left().width(320).height(40).padBottom(15).padLeft(10);
        table.row();

        table.add(passwordLabel).right().padRight(20).padBottom(15);
        table.add(passwordField).left().width(320).height(40).padBottom(15).padLeft(10);
        table.row();

        table.add(feedbackLabel).colspan(2).center().padTop(20).padBottom(40).row();
        table.add(showPasswordCheckBox).colspan(2).center().padBottom(20);
        table.row();

        forgetPasswordButton.pack();
        loginButton.pack();
        goBackButton.pack();

        float maxWidth = Math.max(Math.max(forgetPasswordButton.getWidth(), loginButton.getWidth()), goBackButton.getWidth()) + 40;

        table.add(forgetPasswordButton).colspan(2).center().width(maxWidth).height(50).padBottom(10).row();
        table.add(loginButton).colspan(2).center().width(maxWidth).height(50).padTop(0).row();
        table.add(goBackButton).colspan(2).center().width(maxWidth).height(50);

        stage.addActor(table);

        usernameField.setTextFieldListener((textField, c) -> updatePasswordFeedback());
        passwordField.setTextFieldListener((textField, c) -> updatePasswordFeedback());
    }

    private void updatePasswordFeedback()
    {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String errorMessage;
        registerController = new RegisterMenuController();

        if (registerController.isUnique(username))
        {
            errorMessage = "username not found";
            feedbackLabel.setColor(Color.RED);
        } else if (!loginController.correctPassword(username, password))
        {
            errorMessage = "wrong password";
            feedbackLabel.setColor(Color.RED);
        } else
        {
            errorMessage = "";
            feedbackLabel.setColor(Color.GREEN);
        }

        feedbackLabel.setText(errorMessage);
    }

    private void updatePasswordFeedbackComplete()
    {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String errorMessage;
        registerController = new RegisterMenuController();

        if (registerController.isUnique(username))
        {
            errorMessage = "username not found";
            feedbackLabel.setColor(Color.RED);
        } else if (!registerController.isLongEnough(password))
        {
            errorMessage = "password too short!";
            feedbackLabel.setColor(Color.YELLOW);
        } else if (!registerController.hasCapital(password))
        {
            errorMessage = "password must have a capital letter!";
            feedbackLabel.setColor(Color.RED);
        } else if (!registerController.hasDigit(password))
        {
            errorMessage = "password must have a digit!";
            feedbackLabel.setColor(Color.RED);
        } else if (!registerController.hasSpecial(password))
        {
            errorMessage = "password must have a special character!";
            feedbackLabel.setColor(Color.RED);
        } else
        {
            errorMessage = "password is strong";
            feedbackLabel.setColor(Color.GREEN);
        }

        feedbackLabel.setText(errorMessage);
    }

    private void updatePasswordFeedback(String errorMessage)
    {
        feedbackLabel.setText(errorMessage);
        feedbackLabel.setColor(Color.RED);
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
        float leavesRatio = (float) leavesTex.getWidth() / leavesTex.getHeight();
        float leavesHeight = height;
        float leavesWidth = leavesHeight * leavesRatio;
        leftLeavesImage.setSize(leavesWidth, leavesHeight);
        leftLeavesImage.setPosition(0, 0);
        rightLeavesImage.setSize(leavesWidth, leavesHeight);
        rightLeavesImage.setPosition(width - leavesWidth, 0);
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
        leavesTex.dispose();
    }

    private void showSecurityQuestionDialog(String username, String newPassword)
    {
        Dialog dialog = new Dialog("Security Question", skin);

        Table content = new Table();
        content.defaults().pad(12);

        Label questionLabel = new Label("Which one would you rather have?", skin);
        questionLabel.setFontScale(1.2f);
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

                    if (!loginController.validSecurityQuestion(username, option).isSuccessful())
                    {
                        updatePasswordFeedback("wrong answer");
                    }
                    else if (!loginController.validPassword(newPassword))
                    {
                        updatePasswordFeedbackComplete();
                    }
                    else if (loginController.setPassword(username, newPassword))
                    {
                        feedbackLabel.setText("password set successfully");
                        feedbackLabel.setColor(Color.GREEN);
                    }
                    else
                    {
                        updatePasswordFeedback("invalid password");
                    }
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
