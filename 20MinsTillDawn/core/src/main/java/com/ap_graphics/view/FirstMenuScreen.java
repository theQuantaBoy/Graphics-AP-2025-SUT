package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class FirstMenuScreen implements Screen {

    private final Game app = TillDawn.getGame();

    private Stage stage;
    private Texture titleTex, leavesTex;
    private Image titleImage, leftLeavesImage, rightLeavesImage;

    // Eye animation fields
    private Texture eyeTex1, eyeTex2, eyeTex3;
    private Animation<TextureRegion> eyeBlinkAnimation;
    private float animationTimer = 0f;
    private Image eyeBlinkImage;

    private float baseEyeY;

    public FirstMenuScreen() {
        stage = new Stage(new ScreenViewport());

        // Load textures
        titleTex = new Texture("images/visual/T_20Logo.png");
        leavesTex = new Texture("images/visual/T_TitleLeaves.png");

        // Load eye animation textures
        eyeTex1 = new Texture("images/visual/eye_blink/T_EyeBlink_0.png");
        eyeTex2 = new Texture("images/visual/eye_blink/T_EyeBlink_1.png");
        eyeTex3 = new Texture("images/visual/eye_blink/T_EyeBlink_2.png");

        // Setup animation
        eyeBlinkAnimation = new Animation<>(
            0.18f,
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex2),
            new TextureRegion(eyeTex3),
            new TextureRegion(eyeTex2),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1),
            new TextureRegion(eyeTex1)
        );
        eyeBlinkAnimation.setPlayMode(Animation.PlayMode.LOOP);

        // Create eye animation image
        eyeBlinkImage = new Image(eyeBlinkAnimation.getKeyFrame(0));
        stage.addActor(eyeBlinkImage);

        // Title
        titleImage = new Image(titleTex);
        stage.addActor(titleImage);

        // Leaves
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);
        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);

        // Set input
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Animate eyes
        animationTimer += delta;
        TextureRegion currentFrame = eyeBlinkAnimation.getKeyFrame(animationTimer);
        eyeBlinkImage.setDrawable(new TextureRegionDrawable(currentFrame));

        // Floating effect
        float floatOffset = (float) Math.sin(animationTimer * 2.0f) * 5f; // 5px float, adjust as needed
        eyeBlinkImage.setY(baseEyeY + floatOffset);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);

        // Title
        float titleScale = 0.33f;
        float titleWidth = width * titleScale;
        float titleHeight = titleImage.getPrefHeight() * (titleWidth / titleImage.getPrefWidth());
        titleImage.setSize(titleWidth, titleHeight);
        titleImage.setPosition((width - titleWidth) / 2f, height * 0.6f);

        // Leaves
        float leavesRatio = (float) leavesTex.getWidth() / leavesTex.getHeight();
        float leavesHeight = height;
        float leavesWidth = leavesHeight * leavesRatio;

        leftLeavesImage.setSize(leavesWidth, leavesHeight);
        leftLeavesImage.setPosition(0, 0);

        rightLeavesImage.setSize(leavesWidth, leavesHeight);
        rightLeavesImage.setPosition(width - leavesWidth, 0);

        // Eyes
        float eyeWidth = width * 0.41f;
        float eyeHeight = eyeWidth * ((float) eyeTex1.getHeight() / eyeTex1.getWidth());
        eyeBlinkImage.setSize(eyeWidth, eyeHeight);

        baseEyeY = height * 0.36f;
        eyeBlinkImage.setPosition((width - eyeWidth) / 2f, baseEyeY);
    }

    @Override
    public void show() {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        titleTex.dispose();
        leavesTex.dispose();
        eyeTex1.dispose();
        eyeTex2.dispose();
        eyeTex3.dispose();
    }
}
