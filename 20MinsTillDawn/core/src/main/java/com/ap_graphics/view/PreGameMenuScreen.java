package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.combat.Weapon;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.g2d.Animation;

public class PreGameMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Skin skin;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;

    private Player player = App.getCurrentPlayer();

    private int heroIndex = Avatar.getIndex(player.getAvatar());
    private int weaponIndex = WeaponType.getIndex(player.getCurrentWeapon().getType());
    private int timeIndex = 0;
    private final int[] timeOptions = {2, 5, 10, 20};

    private Label heroLabel;
    private Image heroImage;
    private float heroAnimTime = 0f;
    private Animation<TextureRegion> currentHeroAnimation;

    private Label weaponLabel;
    private Image weaponImage;

    private Label timeLabel;

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        leavesTex = new Texture("images/visual/T_TitleLeaves.png");
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);
        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = TillDawn.menuFont;
        titleStyle.fontColor = com.badlogic.gdx.graphics.Color.WHITE;
        Label title = new Label("Pre Game Menu", titleStyle);

        heroLabel = new Label("", skin);
        heroImage = new Image();
        updateHeroView();
        TextButton heroLeft = new TextButton("<", skin);
        TextButton heroRight = new TextButton(">", skin);

        heroLeft.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                heroIndex = (heroIndex - 1 + Avatar.values().length) % Avatar.values().length;
                player.setAvatar(Avatar.values()[heroIndex]);
                updateHeroView();
            }
        });

        heroRight.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                heroIndex = (heroIndex + 1) % Avatar.values().length;
                player.setAvatar(Avatar.values()[heroIndex]);
                updateHeroView();
            }
        });

        weaponLabel = new Label("", skin);
        weaponImage = new Image();
        updateWeaponView();
        TextButton weaponLeft = new TextButton("<", skin);
        TextButton weaponRight = new TextButton(">", skin);

        weaponLeft.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                weaponIndex = (weaponIndex - 1 + WeaponType.values().length) % WeaponType.values().length;
                player.setCurrentWeapon(new Weapon(WeaponType.values()[weaponIndex]));
                updateWeaponView();
            }
        });
        weaponRight.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                weaponIndex = (weaponIndex + 1) % WeaponType.values().length;
                player.setCurrentWeapon(new Weapon(WeaponType.values()[weaponIndex]));
                updateWeaponView();
            }
        });

        timeLabel = new Label("", skin);
        updateTimeView();
        TextButton timeLeft = new TextButton("<", skin);
        TextButton timeRight = new TextButton(">", skin);

        timeLeft.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                timeIndex = (timeIndex - 1 + timeOptions.length) % timeOptions.length;
                player.setCurrentGameDuration(timeOptions[timeIndex]);
                updateTimeView();
            }
        });
        timeRight.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                timeIndex = (timeIndex + 1) % timeOptions.length;
                player.setCurrentGameDuration(timeOptions[timeIndex]);
                updateTimeView();
            }
        });

        TextButton startButton = new TextButton("Start New Game", skin);

        TextButton backButton = new TextButton("Go Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                leftLeavesImage.addAction(Actions.moveTo(-leftLeavesImage.getWidth(), 0, 0.8f));
                rightLeavesImage.addAction(Actions.moveTo(Gdx.graphics.getWidth(), 0, 0.8f));

                stage.addAction(Actions.sequence(
                    Actions.delay(0.85f),
                    Actions.run(() -> app.setScreen(new MainMenuScreen()))
                ));
            }
        });

        Table selectionTable = new Table();
        selectionTable.add(title).colspan(3).padBottom(40).row();

        selectionTable.add(heroLeft).padRight(10);
        selectionTable.add(heroImage).size(64).padBottom(5);
        selectionTable.add(heroRight).padLeft(10).row();
        selectionTable.add().colspan(3).padBottom(10);
        selectionTable.add().padRight(10);
        selectionTable.add(heroLabel);
        selectionTable.add().padLeft(10).row();

        selectionTable.add(weaponLeft).padRight(10).padTop(20);
        selectionTable.add(weaponImage).size(48).padTop(20);
        selectionTable.add(weaponRight).padLeft(10).padTop(20).row();
        selectionTable.add().colspan(3).padBottom(10);
        selectionTable.add().padRight(10);
        selectionTable.add(weaponLabel);
        selectionTable.add().padLeft(10).row();

        selectionTable.add(timeLeft).padRight(10).padTop(20);
        selectionTable.add(timeLabel).padTop(20);
        selectionTable.add(timeRight).padLeft(10).padTop(20).row();

        selectionTable.add(startButton).colspan(3).padTop(40).row();
        selectionTable.add(backButton).colspan(3).padTop(10);

        rootTable.add(selectionTable);
    }

    private void updateHeroView()
    {
        Avatar avatar = Avatar.values()[heroIndex];
        currentHeroAnimation = avatar.getIdleAnimation();
        heroAnimTime = 0f;
        heroLabel.setText(avatar.getName());
    }

    private void updateWeaponView()
    {
        WeaponType weapon = WeaponType.values()[weaponIndex];
        weaponImage.setDrawable(new Image(weapon.getTexture()).getDrawable());
        weaponLabel.setText(weapon.name());
    }

    private void updateTimeView()
    {
        timeLabel.setText(timeOptions[timeIndex] + " minutes");
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        if (currentHeroAnimation != null)
        {
            heroAnimTime += delta;
            TextureRegion frame = currentHeroAnimation.getKeyFrame(heroAnimTime, true);
            heroImage.setDrawable(new TextureRegionDrawable(frame));
        }

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
        leavesTex.dispose();
    }
}
