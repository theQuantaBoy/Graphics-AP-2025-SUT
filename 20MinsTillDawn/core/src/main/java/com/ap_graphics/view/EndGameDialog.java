package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.SoundEffectType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Align;

public class EndGameDialog extends Dialog
{
    public EndGameDialog(Stage stage, Skin skin, BitmapFont font, boolean won)
    {
        super("", skin);
        pad(20);
        setModal(true);
        setMovable(false);

        Player player = App.getCurrentPlayer();

        String titleText = won ? "You Won!" : "You Lost.";

        if (won)
        {
            SoundManager.getInstance().playSFX(SoundEffectType.YOU_WIN_2);
        } else
        {
            SoundManager.getInstance().playSFX(SoundEffectType.YOU_LOSE_4);
        }

        Label title = new Label(titleText, new Label.LabelStyle(font, won ? Color.GREEN : Color.RED));
        title.setAlignment(Align.center);
        getContentTable().add(title).padBottom(30).row();

        Label usernameLabel = new Label("Username: " + player.getUsername(), new Label.LabelStyle(font, Color.WHITE));
        getContentTable().add(usernameLabel).padBottom(15).row();

        Label killLabel = new Label("Kill count: " + player.getKillCount(), new Label.LabelStyle(font, Color.WHITE));
        getContentTable().add(killLabel).padBottom(10).row();

        int score = App.getGame().getScore();
        Label scoreLabel = new Label("Score: " + score, new Label.LabelStyle(font, Color.RED));
        getContentTable().add(scoreLabel).padBottom(20).row();

        TextButton mainMenuButton = new TextButton("go to main menu", skin);
        mainMenuButton.addListener(new ClickListener()
        {
            @Override
            public void clicked(InputEvent event, float x, float y)
            {
                SoundManager.getInstance().playSFX(SoundEffectType.UI_CLICK_36);
                TillDawn.getGame().setScreen(new MainMenuScreen());
                App.setGame(null);
                remove();
            }
        });

        getContentTable().add(mainMenuButton).padTop(20);
        show(stage);
    }
}
