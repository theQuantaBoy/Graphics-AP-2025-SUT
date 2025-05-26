package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.MusicPlaylist;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Skin skin;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));
        skin.get(Label.LabelStyle.class).font = TillDawn.menuFont;

        Player player = App.getCurrentPlayer();

        Table root = new Table();
        root.setFillParent(true);

        leavesTex = new Texture("images/visual/T_TitleLeaves.png");
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);

        rightLeavesImage = new Image(flippedRegion);

        leftLeavesImage.setTouchable(Touchable.disabled);
        rightLeavesImage.setTouchable(Touchable.disabled);

        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);

        stage.addActor(root);

        Table content = new Table();
        content.center();
        content.defaults().pad(8);

        Label title = new Label("Settings Menu", skin);
        title.setFontScale(1.5f);
        content.add(title).colspan(4).padBottom(40);
        content.row();

        Label volumeLabel = new Label("Music Volume", skin);
        Slider volumeSlider = new Slider(0f, 1f, 0.01f, false, skin);
        Label volumePercent = new Label("" + (int)(player.getMusicVolume() * 100) + " %", skin);
        volumeSlider.setValue(player.getMusicVolume());
        volumeSlider.addListener(e -> {
            float value = volumeSlider.getValue();
            player.setMusicVolume(value);
            volumePercent.setText((int)(value * 100) + " %");
            SoundManager.getInstance().updatePlayerSettings();
            SoundManager.getInstance().stopLoopingSFX();
            SoundManager.getInstance().playMusic(player);
            return false;
        });
        content.add(volumeLabel).left();
        content.add(volumeSlider).width(200);
        content.add(volumePercent);
        content.row();

        Label playlistLabel = new Label("Playlist:", skin);
        Label playlistName = new Label(player.getMusicPlaylist().name(), skin);
        Label songName = new Label(SoundManager.getInstance().getCurrentTrackName(), skin);
        TextButton prevButton = new TextButton("<", skin);
        TextButton nextButton = new TextButton(">", skin);

        prevButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                int totalPlaylists = MusicPlaylist.values().length;
                int newIndex = (player.getSelectedPlaylist() - 1 + totalPlaylists) % totalPlaylists;
                player.setSelectedPlaylist(newIndex);
                playlistName.setText(player.getMusicPlaylist().name());
                SoundManager.getInstance().playMusic(player);
                songName.setText(SoundManager.getInstance().getCurrentTrackName());
            }
        });

        nextButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                int totalPlaylists = MusicPlaylist.values().length;
                int newIndex = (player.getSelectedPlaylist() + 1) % totalPlaylists;
                player.setSelectedPlaylist(newIndex);
                playlistName.setText(player.getMusicPlaylist().name());
                SoundManager.getInstance().playMusic(player);
                songName.setText(SoundManager.getInstance().getCurrentTrackName());
            }
        });

        content.add(playlistLabel);
        content.add(prevButton);
        content.add(playlistName);
        content.add(nextButton);
        content.row();

        Label songLabel = new Label("Song:", skin);
        TextButton songPrev = new TextButton("<", skin);
        TextButton songNext = new TextButton(">", skin);

        songPrev.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                SoundManager.getInstance().playPreviousTrack();
                songName.setText(SoundManager.getInstance().getCurrentTrackName());
            }
        });
        songNext.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                SoundManager.getInstance().playNextTrack();
                songName.setText(SoundManager.getInstance().getCurrentTrackName());
            }
        });

        content.add(songLabel);
        content.add(songPrev);
        content.add(songName);
        content.add(songNext);
        content.row();

        CheckBox.CheckBoxStyle checkboxStyle = skin.get(CheckBox.CheckBoxStyle.class);
        checkboxStyle.font = TillDawn.menuFont;

        CheckBox sfxToggle = new CheckBox("Enable SFX", skin);
        sfxToggle.setStyle(checkboxStyle);
        sfxToggle.setChecked(player.isSfxEnabled());
        sfxToggle.addListener(e ->
        {
            player.setSfxEnabled(sfxToggle.isChecked());
            return false;
        });
        content.add(sfxToggle).colspan(4);
        content.row();

        CheckBox autoReloadToggle = new CheckBox("Enable Auto-Reload", skin);
        autoReloadToggle.setStyle(checkboxStyle);
        autoReloadToggle.setChecked(player.isAutoReloadEnabled());
        autoReloadToggle.addListener(e ->
        {
            player.setAutoReloadEnabled(autoReloadToggle.isChecked());
            return false;
        });
        content.add(autoReloadToggle).colspan(4);
        content.row();

        CheckBox bwToggle = new CheckBox("Black & White Mode", skin);
        bwToggle.setStyle(checkboxStyle);
        bwToggle.setChecked(player.isBlackAndWhiteMode());
        bwToggle.addListener(e ->
        {
            player.setBlackAndWhiteMode(bwToggle.isChecked());
            return false;
        });
        content.add(bwToggle).colspan(4);
        content.row();

        TextButton keybindButton = new TextButton("Change Movement Keys", skin);
        keybindButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                showKeyRemapDialog(player);
            }
        });
        content.add(keybindButton).colspan(4);
        content.row();

        TextButton backButton = new TextButton("Go Back", skin);
        backButton.addListener(new ClickListener()
        {
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

        content.add(backButton).colspan(4).padTop(16);

        root.add(content).center().expand();
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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

    @Override
    public void dispose()
    {
        stage.dispose();
        leavesTex.dispose();
        skin.dispose();
    }

    private void showKeyRemapDialog(Player player) {
        Dialog dialog = new Dialog("Remap Movement Keys", skin);
        Table table = new Table(skin);
        table.defaults().pad(8);

        Label info = new Label("Click a button, then press a new key", skin);
        table.add(info).colspan(2).center();
        table.row();

        String[] directions = {"Up", "Down", "Left", "Right"};
        int[] currentKeys = {
            player.getMoveUpKey(),
            player.getMoveDownKey(),
            player.getMoveLeftKey(),
            player.getMoveRightKey()
        };

        TextButton[] buttons = new TextButton[4];

        for (int i = 0; i < 4; i++) {
            final int index = i;
            String keyName = Input.Keys.toString(currentKeys[i]);
            buttons[i] = new TextButton(directions[i] + ": " + keyName, skin);
            buttons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.input.setInputProcessor(new InputAdapter() {
                        @Override
                        public boolean keyDown(int keycode) {
                            if (!player.isValidMovementKey(keycode)) {
                                buttons[index].setText(directions[index] + ": INVALID");
                            } else {
                                switch (index) {
                                    case 0:
                                        player.setMoveUpKey(keycode);
                                        break;
                                    case 1:
                                        player.setMoveDownKey(keycode);
                                        break;
                                    case 2:
                                        player.setMoveLeftKey(keycode);
                                        break;
                                    case 3:
                                        player.setMoveRightKey(keycode);
                                        break;
                                }
                                buttons[index].setText(directions[index] + ": " + Input.Keys.toString(keycode));
                            }
                            Gdx.input.setInputProcessor(stage); // Restore input
                            return true;
                        }
                    });
                }
            });
            table.add(buttons[i]).width(220).height(40).colspan(2);
            table.row();
        }

        TextButton doneButton = new TextButton("Done", skin);
        doneButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                dialog.hide();
            }
        });

        table.add(doneButton).center().colspan(2).padTop(16);
        dialog.getContentTable().add(table);
        dialog.show(stage);
    }
}
