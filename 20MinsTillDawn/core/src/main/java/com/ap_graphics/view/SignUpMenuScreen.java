package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.RegisterMenuController;
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
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SignUpMenuScreen implements Screen
{
    private final TillDawn app;
    private Stage stage;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;
    private TextField usernameField, passwordField;
    private TextButton registerButton;
    private Label feedbackLabel;
    private CheckBox showPasswordCheckBox;
    private RegisterMenuController controller = new RegisterMenuController();

    public SignUpMenuScreen()
    {
        this.app = TillDawn.getGame();
    }

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

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
        showPasswordCheckBox = new CheckBox(" Show password", cbStyle);
        showPasswordCheckBox.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                passwordField.setPasswordMode(!showPasswordCheckBox.isChecked());
            }
        });

        // Button
        TextButton.TextButtonStyle btnStyle = skin.get(TextButton.TextButtonStyle.class);
        btnStyle.font = TillDawn.menuFont;
        registerButton = new TextButton("Sign Up", btnStyle);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                controller.register(usernameField.getText(), passwordField.getText());
            }
        });

        Table table = new Table(skin);
        table.setFillParent(true);
        table.center();

        table.add(usernameLabel).padBottom(5).left().row();
        table.add(usernameField).width(300).height(45).padBottom(20).row();
        table.add(passwordLabel).padBottom(5).left().row();
        table.add(passwordField).width(300).height(45).padBottom(5).row();
        table.add(feedbackLabel).padBottom(10).row();
        table.add(showPasswordCheckBox).padBottom(20).row();
        table.add(registerButton).width(180).height(50);

        stage.addActor(table);

        passwordField.setTextFieldListener((textField, c) -> updatePasswordFeedback());
    }

    private void updatePasswordFeedback() {
        String password = passwordField.getText();
        boolean hasDigit = RegisterMenuController.hasDigit(password);
        boolean hasSpecial = RegisterMenuController.hasSpecial(password);
        boolean hasCapital = RegisterMenuController.hasCapital(password);
        boolean isLongEnough = RegisterMenuController.isLongEnough(password);

        if (!isLongEnough) {
            feedbackLabel.setText("Password too short");
            feedbackLabel.setColor(Color.RED);
        } else if (!hasDigit || !hasSpecial || !hasCapital) {
            feedbackLabel.setText("Password is weak");
            feedbackLabel.setColor(Color.YELLOW);
        } else {
            feedbackLabel.setText("Password is strong");
            feedbackLabel.setColor(Color.GREEN);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

    @Override
    public void dispose() {
        stage.dispose();
        leavesTex.dispose();
    }
}
