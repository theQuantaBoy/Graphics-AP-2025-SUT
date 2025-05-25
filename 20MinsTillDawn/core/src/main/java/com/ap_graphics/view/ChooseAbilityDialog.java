package com.ap_graphics.view;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.GameWorld;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.AbilityType;
import com.ap_graphics.model.enums.GameAnimationType;
import com.ap_graphics.model.enums.SoundEffectType;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ChooseAbilityDialog extends Window {
    private final AbilityType[] abilities;
    private final Animation<TextureRegion> chestAnimation = GameAnimationType.BIG_CHEST.createAnimation();
    private float stateTime = 0f;
    private final Image[] chests = new Image[3];
    private final Skin skin;
    private final Stage stage;
    private boolean opened = false;
    private boolean abilityChosen = false;

    public ChooseAbilityDialog(Stage stage, Skin skin, AbilityType[] abilities) {
        super("", skin);
        this.abilities = abilities;
        this.skin = skin;
        this.stage = stage;
        setModal(true);
        setMovable(false);
        setSize(600, 400);
        setPosition((stage.getWidth() - getWidth()) / 2f, (stage.getHeight() - getHeight()) / 2f);

        Table table = new Table();
        table.center();

        for (int i = 0; i < 3; i++) {
            final int index = i;
            Image chestImage = new Image(new TextureRegionDrawable(chestAnimation.getKeyFrame(0)));
            chests[i] = chestImage;
            chestImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (opened) return;
                    opened = true;
                    showAbilityOptions(index);
                }
            });
            table.add(chestImage).size(96).pad(30);
        }

        add(table).expand().center();
    }

    private void showAbilityOptions(int chosenIndex) {
        clearChildren();

        Table table = new Table();
        table.center();
        SoundManager.getInstance().playSFX(SoundEffectType.DRUM_ROLL_3);
        SoundManager.getInstance().playSFX(SoundEffectType.SPECIAL_AND_POWERUP_8);

        for (int i = 0; i < 3; i++) {
            final AbilityType ability = abilities[i];
            Table abilityTable = new Table(skin);
            Label nameLabel = new Label(ability.getFaName(), skin);
            Label effectLabel = new Label(ability.getEffectText(), skin);
            effectLabel.setWrap(true);
            effectLabel.setAlignment(1); // center

            if (i == chosenIndex) {
                nameLabel.setColor(0, 1, 0, 1);
                effectLabel.setColor(0, 1, 0, 1);
            }

            abilityTable.add(nameLabel).padBottom(5).row();
            abilityTable.add(effectLabel).width(250).padBottom(10).row();

            if (i == chosenIndex) {
                TextButton chooseBtn = new TextButton("Choose", skin);
                chooseBtn.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if (abilityChosen) return;
                        abilityChosen = true;
                        Player player = GameWorld.getInstance().getPlayer();
                        player.setAbility(ability);
                        GameWorld.getInstance().resume();
                        remove();
                    }
                });
                abilityTable.add(chooseBtn).width(100).padBottom(30);
            }

            table.add(abilityTable).pad(30);
        }

        row();
        add(table).expand().center().row();

        TextButton resumeButton = new TextButton("Resume", skin);
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameWorld.getInstance().resume();
                remove();
            }
        });
        add(resumeButton).padTop(20);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (!opened) {
            stateTime += delta;
            TextureRegion frame = chestAnimation.getKeyFrame(stateTime, true);
            for (Image chest : chests) {
                chest.setDrawable(new TextureRegionDrawable(frame));
            }
        }
    }
}
