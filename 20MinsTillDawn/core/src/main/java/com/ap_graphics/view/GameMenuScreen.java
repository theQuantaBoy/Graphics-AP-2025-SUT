package com.ap_graphics.view;

import com.ap_graphics.controller.CursorManager;
import com.ap_graphics.controller.PlayerController;
import com.ap_graphics.model.*;
import com.ap_graphics.model.combat.Bullet;
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

public class GameMenuScreen implements Screen {
    private final Stage stage;
    private final Skin skin;
    private final SpriteBatch batch;
    private final PlayerController playerController;
    private final GameWorld gameWorld;

    private OrthographicCamera camera;
    private Texture background;

    private final Player player;
    private Label timerLabel, ammoLabel, xpLabel, levelLabel, hpLabel;

    private CursorManager cursorManager;

    public GameMenuScreen(Skin skin) {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();

        background = new Texture("images/essential/background.png");
        this.playerController = new PlayerController(App.getCurrentPlayer(), background);

        this.player = App.getCurrentPlayer();

        this.gameWorld = new GameWorld(player, background.getWidth(), background.getHeight(), player.getCurrentGameDuration() * 60);
        App.setGame(gameWorld);

        GameWorld.getInstance().setUIContext(stage, skin);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        timerLabel = new Label("", skin);
        ammoLabel = new Label("", skin);
        xpLabel = new Label("", skin);
        levelLabel = new Label("", skin);
        hpLabel = new Label("", skin);

        Table uiTable = new Table();
        uiTable.top().left();
        uiTable.setFillParent(true);
        uiTable.add(timerLabel).pad(10).left().row();
        uiTable.add(ammoLabel).pad(10).left().row();
        uiTable.add(xpLabel).pad(10).left().row();
        uiTable.add(levelLabel).pad(10).left().row();
        uiTable.add(hpLabel).pad(10).left().row();
        stage.addActor(uiTable);

        cursorManager = new CursorManager();

        Avatar avatar = App.getCurrentPlayer().getAvatar();

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Player player = App.getCurrentPlayer();
        player.setCurrentFrame(player.getAvatar().getIdleAnimation().getKeyFrame(0f));
    }

    @Override
    public void render(float delta) {
        Player player = App.getCurrentPlayer();

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(background, 0, 0);

        gameWorld.update(delta);

        int remaining = gameWorld.getRemainingTime();
        int minutes = remaining / 60;
        int seconds = remaining % 60;
        timerLabel.setText("Time: " + String.format("%02d:%02d", minutes, seconds));

        if (player.getCurrentWeapon() != null) {
            int currentAmmo = player.getCurrentWeapon().getCurrentAmmo();
            int maxAmmo = player.getCurrentWeapon().getType().getMaxAmmo();
            ammoLabel.setText("Ammo: " + currentAmmo + " / " + maxAmmo);
        }

        xpLabel.setText("XP: " + player.getXp());
        levelLabel.setText("Level: " + player.getLevel());
        hpLabel.setText("HP: " + player.getCurrentHP() + " / " + player.getMaxHP());

        Vector3 mouseScreenPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouseWorldPos = camera.unproject(mouseScreenPos);

        if (player.getCurrentWeapon() != null) {
            Vector2 playerPos = new Vector2(player.getPosX(), player.getPosY());
            Vector2 mouseDir = new Vector2(mouseWorldPos.x - playerPos.x, mouseWorldPos.y - playerPos.y);
            player.getCurrentWeapon().updatePosition(playerPos, mouseDir);
        }

        if (!App.getGame().isPaused()) {
            playerController.handleShooting(player, gameWorld, camera);

            playerController.update(delta, batch);

            for (Enemy enemy : gameWorld.getEnemies()) {
                enemy.render(batch, delta);
            }

            for (Bullet bullet : gameWorld.getBullets()) {
                bullet.render(batch);
            }

            for (EnemyBullet bullet : gameWorld.getEnemyBullets()) {
                bullet.render(batch);
            }

            for (XpOrb orb : gameWorld.getXpOrbs()) {
                orb.render(batch);
            }

            gameWorld.renderUI(batch);

            boolean isMouseDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
            cursorManager.update(isMouseDown);
        }

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
    public void dispose() {
        stage.dispose();
        cursorManager.dispose();
        batch.dispose();
    }
}
