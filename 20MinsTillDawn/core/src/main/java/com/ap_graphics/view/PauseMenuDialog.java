package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.App;
import com.ap_graphics.model.GameWorld;
import com.ap_graphics.model.enums.AbilityType;
import com.ap_graphics.model.enums.MenuTexts;
import com.ap_graphics.model.enums.SoundEffectType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class PauseMenuDialog extends Dialog
{
    public PauseMenuDialog(final Stage stage, Skin skin, BitmapFont font)
    {
        super("", skin);
        pad(20);
        setModal(true);
        setMovable(false);

        Label title = new Label("pause menu", new Label.LabelStyle(font, Color.WHITE));
        title.setAlignment(Align.center);
        getContentTable().add(title).padBottom(30).row();

        TextButton cheatCodesButton = new TextButton("cheat codes", skin);
        cheatCodesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                showCheatCodesDialog(stage, skin, font);
            }
        });

        TextButton abilitiesButton = new TextButton("achievements", skin);
        abilitiesButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                showAbilitiesDialog(stage, skin, font);
            }
        });

        TextButton giveUpButton = new TextButton("give up", skin);
        giveUpButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                // TODO: Handle give up
                EndGameDialog dialog = new EndGameDialog(stage, skin, font, false);
                stage.addActor(dialog);
            }
        });

        TextButton saveAndQuitButton = new TextButton("save and quit", skin);
        saveAndQuitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                // TODO: Save game and quit
                TillDawn.getGame().setScreen(new MainMenuScreen());
                remove();
            }
        });


        CheckBox.CheckBoxStyle checkboxStyle = skin.get(CheckBox.CheckBoxStyle.class);

        CheckBox bwToggle = new CheckBox(MenuTexts.BW_MODE.getText(), skin);
        bwToggle.setStyle(checkboxStyle);
        bwToggle.setChecked(App.getCurrentPlayer().isBlackAndWhiteMode());
        bwToggle.addListener(e ->
        {
            App.getCurrentPlayer().setBlackAndWhiteMode(bwToggle.isChecked());
            return false;
        });

        TextButton resumeButton = new TextButton("resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                GameWorld.getInstance().resume();
                remove();
            }
        });

        getContentTable().add(cheatCodesButton).pad(10).row();
        getContentTable().add(abilitiesButton).pad(10).row();
        getContentTable().add(giveUpButton).pad(10).row();
        getContentTable().add(saveAndQuitButton).pad(10).row();
        getContentTable().add(bwToggle).pad(10).row();
        getContentTable().add(resumeButton).pad(20).padTop(30);
        getContentTable().center();

        show(stage);
    }

    private void showCheatCodesDialog(final Stage stage, Skin skin, BitmapFont font) {
        Dialog cheatDialog = new Dialog("", skin);
        cheatDialog.pad(20);
        cheatDialog.setModal(true);
        cheatDialog.setMovable(false);

        Label title = new Label("Cheat Codes", new Label.LabelStyle(font, Color.YELLOW));
        title.setAlignment(Align.center);
        cheatDialog.getContentTable().add(title).padBottom(30).row();

        String[][] codes = {
            {"T", "pass one minute of the game"},
            {"L", "level up instantly"},
            {"H", "restore full health"},
            {"B", "go to boss fight"},
            {"K", "kill all enemies"}
        };

        for (String[] code : codes) {
            Label line = new Label("[" + code[0] + "] - " + code[1], new Label.LabelStyle(font, Color.LIGHT_GRAY));
            cheatDialog.getContentTable().add(line).left().padBottom(10).row();
        }

        TextButton backButton = new TextButton("go back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                cheatDialog.hide();
                show(stage);
            }
        });

        cheatDialog.getContentTable().add(backButton).padTop(20);
        cheatDialog.show(stage);
    }

    private void showAbilitiesDialog(final Stage stage, Skin skin, BitmapFont font) {
        Dialog abilitiesDialog = new Dialog("", skin);
        abilitiesDialog.pad(20);
        abilitiesDialog.setModal(true);
        abilitiesDialog.setMovable(false);

        Label title = new Label("Achievements", new Label.LabelStyle(font, Color.CYAN));
        title.setAlignment(Align.center);
        abilitiesDialog.getContentTable().add(title).padBottom(30).row();

        for (AbilityType ability : App.getCurrentPlayer().getAbilityTypes()) {
            String lineText = "[" + ability.name() + "] - " + ability.getEffectText();
            Label line = new Label(lineText, new Label.LabelStyle(font, Color.LIGHT_GRAY));
            abilitiesDialog.getContentTable().add(line).left().padBottom(10).row();
        }

        TextButton backButton = new TextButton("go back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                abilitiesDialog.hide();
                show(stage);
            }
        });

        abilitiesDialog.getContentTable().add(backButton).padTop(20);
        abilitiesDialog.show(stage);
    }
}
