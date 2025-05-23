package com.ap_graphics.view;

import com.ap_graphics.controller.CursorManager;
import com.ap_graphics.controller.PlayerController;
import com.ap_graphics.model.*;
import com.ap_graphics.model.combat.Enemy;
import com.ap_graphics.model.combat.EnemyBullet;
import com.ap_graphics.model.combat.XpOrb;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class IdleAnimationMenuScreen implements Screen
{
    private final Stage stage;
    private final Skin skin;
    private final SpriteBatch batch;
    private final PlayerController playerController;
    private final GameWorld gameWorld;

    private OrthographicCamera camera;
    private Texture background;

    private final Player player;
    private Label timerLabel;

    private CursorManager cursorManager;

    public IdleAnimationMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();

        background = new Texture("images/essential/background.png");
        this.playerController = new PlayerController(App.getCurrentPlayer(), background);

        this.player = App.getCurrentPlayer();
        this.gameWorld = new GameWorld(player, background.getWidth(), background.getHeight(), 240);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        timerLabel = new Label("Time: 0", skin);

        Table uiTable = new Table();
        uiTable.top().left();
        uiTable.setFillParent(true);
        uiTable.add(timerLabel).pad(20).row();
        stage.addActor(uiTable);

        cursorManager = new CursorManager();

        Avatar avatar = App.getCurrentPlayer().getAvatar();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
    }

    @Override
    public void show()
    {
        Gdx.input.setInputProcessor(stage);

        Player player = App.getCurrentPlayer();
        player.setCurrentFrame(player.getAvatar().getIdleAnimation().getKeyFrame(0f));
    }

    @Override
    public void render(float delta)
    {
        Player player = App.getCurrentPlayer();

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0);

        gameWorld.update(delta);

        timerLabel.setText("Time: " + (int) gameWorld.getTotalGameTime());

        Vector3 mouseScreenPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouseWorldPos = camera.unproject(mouseScreenPos);

        if (player.getCurrentWeapon() != null)
        {
            Vector2 playerPos = new Vector2(player.getPosX(), player.getPosY());
            Vector2 mouseDir = new Vector2(mouseWorldPos.x - playerPos.x, mouseWorldPos.y - playerPos.y);
            player.getCurrentWeapon().updatePosition(playerPos, mouseDir);
        }

        playerController.handleShooting(player, gameWorld, camera);

        playerController.update(delta, batch);

        if (player.getCurrentWeapon() != null)
        {
            TextureRegion weaponTex = player.getCurrentWeapon().getType().getTextureRegion();
            Vector2 weaponPos = player.getCurrentWeapon().getPosition();

            batch.draw(
                weaponTex,
                weaponPos.x,
                weaponPos.y,
                weaponTex.getRegionWidth() / 2f,
                weaponTex.getRegionHeight() / 2f,
                weaponTex.getRegionWidth(),
                weaponTex.getRegionHeight(),
                1f,
                1f,
                player.getCurrentWeapon().getRotation()
            );
        }

        for (Enemy enemy : gameWorld.getEnemies())
        {
            enemy.render(batch, delta);
        }

        for (Bullet bullet : gameWorld.getBullets())
        {
            bullet.render(batch);
        }

        for (EnemyBullet bullet : gameWorld.getEnemyBullets())
        {
            bullet.render(batch);
        }

        for (XpOrb orb : gameWorld.getXpOrbs())
        {
            orb.render(batch);
        }

        gameWorld.renderUI(batch);

        boolean isMouseDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        cursorManager.update(isMouseDown);

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        camera.setToOrtho(false, width, height);
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
        cursorManager.dispose();
        batch.dispose();
    }
}
