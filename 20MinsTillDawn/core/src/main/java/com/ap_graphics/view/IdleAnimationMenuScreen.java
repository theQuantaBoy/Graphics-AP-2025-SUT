package com.ap_graphics.view;

import com.ap_graphics.model.App;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IdleAnimationMenuScreen implements Screen
{
    private final Stage stage;
    private final Skin skin;
    private final SpriteBatch batch;

    private final Animation<Texture> idleAnimation;
    private float elapsedTime = 0f;

    public IdleAnimationMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();

        Avatar avatar = App.getCurrentPlayer().getAvatar();
        this.idleAnimation = avatar.getIdleAnimation();

        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Label label = new Label("Idle Animation Demo", skin);
        table.add(label).center().padBottom(20).row();

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
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsedTime += delta;

        stage.act(delta);
        stage.draw();

        batch.begin();
        Texture currentFrame = idleAnimation.getKeyFrame(elapsedTime);
        batch.draw(currentFrame, 300, 200, currentFrame.getWidth() * 2f, currentFrame.getHeight() * 2f);
        batch.end();
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
        batch.dispose();
    }
}
