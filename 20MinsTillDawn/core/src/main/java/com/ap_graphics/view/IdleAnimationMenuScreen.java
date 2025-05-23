package com.ap_graphics.view;

import com.ap_graphics.controller.CursorManager;
import com.ap_graphics.controller.PlayerController;
import com.ap_graphics.model.*;
import com.ap_graphics.model.enums.Avatar;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    private final Animation<TextureRegion> idleAnimation;
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
        this.gameWorld = new GameWorld(player, background.getWidth(), background.getHeight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Initialize timerLabel
        timerLabel = new Label("Time: 0", skin); // <-- THIS LINE WAS MISSING

        // In the constructor
        Table uiTable = new Table();
        uiTable.top().left();
        uiTable.setFillParent(true);
        uiTable.add(timerLabel).pad(20).row();
        stage.addActor(uiTable);

        cursorManager = new CursorManager();

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
    public void render(float delta) {
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

        // Draw background
        batch.draw(background, 0, 0);

        // Update world state
        gameWorld.update(delta);

        // Update timer UI
        timerLabel.setText("Time: " + (int) gameWorld.getTotalGameTime());

        // Get mouse world position
        Vector3 mouseScreenPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        Vector3 mouseWorldPos = camera.unproject(mouseScreenPos);

        // Update weapon direction
        if (player.getCurrentWeapon() != null) {
            Vector2 playerPos = new Vector2(player.getPosX(), player.getPosY());
            Vector2 mouseDir = new Vector2(mouseWorldPos.x - playerPos.x, mouseWorldPos.y - playerPos.y);
            player.getCurrentWeapon().updatePosition(playerPos, mouseDir);
        }

        // Handle shooting
        playerController.handleShooting(player, gameWorld, camera);

        // Draw player
        playerController.update(delta, batch);

        // Draw player's weapon
        if (player.getCurrentWeapon() != null) {
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

        // ✅ RENDER ORDER FIXES BEGIN HERE

        // Draw live enemies first
        for (Enemy enemy : gameWorld.getEnemies()) {
            enemy.render(batch, delta);
        }

        // Then draw dying enemies immediately after
//        for (Enemy enemy : gameWorld.getDyingEnemies()) {
//            enemy.render(batch, delta);
//        }

        // Draw bullets next
        for (Bullet bullet : gameWorld.getBullets()) {
            bullet.render(batch);
        }

        // Draw XP orbs (transparent, so after bullets)
        for (XpOrb orb : gameWorld.getXpOrbs()) {
            orb.render(batch);
        }

        // Now safe to render UI elements
        gameWorld.renderUI(batch);

        // Cursor state
        boolean isMouseDown = Gdx.input.isButtonPressed(Input.Buttons.LEFT);
        cursorManager.update(isMouseDown);

        // Finish drawing
        batch.end();

        // 🟣 Draw Stage UI (buttons, overlays)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height); // Add this line
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
//       Enemy.disposeTextures();
        batch.dispose();
    }
}
