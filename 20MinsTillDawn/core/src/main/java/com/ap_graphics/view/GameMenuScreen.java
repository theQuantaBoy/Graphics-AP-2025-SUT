package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.CursorManager;
import com.ap_graphics.controller.PlayerController;
import com.ap_graphics.model.*;
import com.ap_graphics.model.combat.Bullet;
import com.ap_graphics.model.combat.Enemy;
import com.ap_graphics.model.combat.EnemyBullet;
import com.ap_graphics.model.combat.XpOrb;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.MenuTexts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameMenuScreen implements Screen
{
    private final Stage stage;
    private final Skin skin;
    private final SpriteBatch batch;
    private final PlayerController playerController;
    private final GameWorld gameWorld;

    private OrthographicCamera camera;
    private Texture background;

    private final Player player;
    private Label timerLabel, ammoLabel, killLabel, xpLabel, levelLabel, hpLabel, autoAimLabel, autoReloadLabel;

    private CursorManager cursorManager;

    private ShapeRenderer shapeRenderer;

    private ShaderProgram grayscaleShader;

    public GameMenuScreen(Skin skin)
    {
        this.skin = skin;
        this.stage = new Stage(new ScreenViewport());
        this.batch = new SpriteBatch();

        background = new Texture("images/essential/background.png");
        this.playerController = new PlayerController(App.getCurrentPlayer(), background);

        this.player = App.getCurrentPlayer();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom = 0.5f; // 0.5 means 2.0x zoom (zoom in)

        shapeRenderer = new ShapeRenderer();

        if (App.useSavedGame)
        {
            this.gameWorld = GameSaver.loadGame(camera);
            App.useSavedGame = false;
        } else
        {
            this.gameWorld = new GameWorld(player, background.getWidth(), background.getHeight(), player.getCurrentGameDuration() * 60, camera, background);
        }

        App.setGame(gameWorld);

        GameWorld.getInstance().setUIContext(stage, skin);

        BitmapFont customFont = player.getFont();

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = customFont;

        timerLabel = new Label("", labelStyle);
        timerLabel.setFontScale(0.65f);

        ammoLabel = new Label("", labelStyle);
        ammoLabel.setFontScale(0.65f);

        killLabel = new Label("", labelStyle);
        killLabel.setFontScale(0.65f);

        xpLabel = new Label("", labelStyle);
        xpLabel.setFontScale(0.65f);

        levelLabel = new Label("", labelStyle);
        levelLabel.setFontScale(0.65f);

        hpLabel = new Label("", labelStyle);
        hpLabel.setFontScale(0.65f);

        autoAimLabel = new Label("", labelStyle);
        autoAimLabel.setFontScale(0.65f);

        autoReloadLabel = new Label("", labelStyle);
        autoReloadLabel.setFontScale(0.65f);

        Table uiTable = new Table();
        uiTable.top().left();
        uiTable.setFillParent(true);
        uiTable.add(timerLabel).pad(10).left().row();
        uiTable.add(ammoLabel).pad(10).left().row();
        uiTable.add(killLabel).pad(10).left().row();
        uiTable.add(xpLabel).pad(10).left().row();
        uiTable.add(levelLabel).pad(10).left().row();
        uiTable.add(hpLabel).pad(10).left().row();
        uiTable.add(autoAimLabel).pad(10).left().row();
        uiTable.add(autoReloadLabel).pad(10).left().row();
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
        ShaderProgram.pedantic = false;
        grayscaleShader = new ShaderProgram(
            Gdx.files.internal("shaders/default.vert"),
            Gdx.files.internal("shaders/grayscale.frag")
        );

        if (!grayscaleShader.isCompiled())
        {
            System.err.println("Shader compile error: " + grayscaleShader.getLog());
        }

        Gdx.input.setInputProcessor(stage);

        Player player = App.getCurrentPlayer();
        player.setCurrentFrame(player.getAvatar().getIdleAnimation().getKeyFrame(0f));
    }

    @Override
    public void render(float delta) {
        Player player = App.getCurrentPlayer();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (player.isBlackAndWhiteMode())
            stage.getBatch().setShader(grayscaleShader);
        else
            stage.getBatch().setShader(null); // Use default

        camera.position.set(player.getPosX(), player.getPosY(), 0);
        camera.update();

        batch.setProjectionMatrix(camera.combined);

        gameWorld.update(delta);

        if (player.isBlackAndWhiteMode()) {
            batch.setShader(grayscaleShader);
            stage.getBatch().setShader(grayscaleShader);
        } else {
            batch.setShader(null);
            stage.getBatch().setShader(null);
        }

        batch.begin();

        batch.draw(background, 0, 0);

        int remaining = gameWorld.getRemainingTime();
        int minutes = remaining / 60;
        int seconds = remaining % 60;
        timerLabel.setText(MenuTexts.TIMER.getText() + ": " + String.format("%02d:%02d", minutes, seconds));

        if (player.getCurrentWeapon() != null) {
            int currentAmmo = player.getCurrentWeapon().getCurrentAmmo();
            int maxAmmo = player.getCurrentWeapon().getMaxAmmo();
            ammoLabel.setText(MenuTexts.AMMO.getText() + ": " + currentAmmo + " / " + maxAmmo);
        }

        killLabel.setText(MenuTexts.KILL_COUNT_GAME.getText() + ": " + gameWorld.getKillCount());
        xpLabel.setText(MenuTexts.XP.getText() + ": " + player.getXp());
        levelLabel.setText(MenuTexts.LEVEL.getText() + ": " + player.getLevel());
        hpLabel.setText(MenuTexts.HP_GAME.getText() + ": " + player.getCurrentHP() + " / " + player.getMaxHP());

        autoAimLabel.setText(MenuTexts.AUTO_AIM_GAME.getText() + ": " +
            (gameWorld.isAutoAimOn() ? MenuTexts.ON.getText() : MenuTexts.OFF.getText()));

        autoReloadLabel.setText(MenuTexts.AUTO_RELOAD_GAME.getText() + ": " +
            (player.isAutoReloadEnabled() ? MenuTexts.ON.getText() : MenuTexts.OFF.getText()));


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

            if (Gdx.input.isKeyJustPressed(Input.Keys.T))
            {
                gameWorld.cheatAdvanceTime();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.L))
            {
                player.levelUp();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.H))
            {
                player.heartsUp();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.B))
            {
                gameWorld.spawnElder();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.K))
            {
                gameWorld.killAllEnemies();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.P))
            {
                gameWorld.hitPause();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.F))
            {
                App.setGame(null);
                TillDawn.getGame().setScreen(new MainMenuScreen());
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.N))
            {
                gameWorld.spawnEyebat();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.C))
            {
                gameWorld.flipAutoAim();
            } else if (Gdx.input.isKeyJustPressed(Input.Keys.M))
            {
                player.setAutoReloadEnabled(!player.isAutoReloadEnabled());
            }

            if (Gdx.input.isKeyJustPressed(Input.Keys.EQUALS))
            {
                player.setBlackAndWhiteMode(!player.isBlackAndWhiteMode());
            }
        }

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);

        Vector2 playerPos = new Vector2(player.getPosX(), player.getPosY());
        gameWorld.getPlayerLightMask().draw(shapeRenderer, playerPos, camera);

        gameWorld.drawShield(shapeRenderer);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
    {
        stage.getViewport().update(width, height, true);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
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
