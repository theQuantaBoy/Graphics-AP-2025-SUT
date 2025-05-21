package com.ap_graphics.view;

import com.ap_graphics.controller.PlayerController;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private final Animation<TextureRegion> idleAnimation;
    private final PlayerController playerController;

    private OrthographicCamera camera;
    private Texture background;

    public IdleAnimationMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();

        background = new Texture("images/essential/background.png");
        this.playerController = new PlayerController(App.getCurrentPlayer(), background);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Avatar avatar = App.getCurrentPlayer().getAvatar();
        this.idleAnimation = avatar.getIdleAnimation();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);

        // Set first idle frame for safety
        Player player = App.getCurrentPlayer();
        player.setCurrentFrame(player.getAvatar().getIdleAnimation().getKeyFrame(0f));
    }

    @Override
    public void render(float delta)
    {
        Player player = App.getCurrentPlayer();

        // 🟢 Move camera to follow player
        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        // 🟡 Apply camera transformation to the batch
        batch.setProjectionMatrix(camera.combined);

        // 🔵 Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // 🔴 Start drawing
        batch.begin();
        batch.draw(background, 0, 0); // Draw the full background at origin
        playerController.update(delta, batch); // Draw the player
        batch.end();

        // 🟣 Draw UI elements separately (unaffected by camera)
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
        batch.dispose();
    }
}
