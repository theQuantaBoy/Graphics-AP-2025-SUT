package com.ap_graphics.view;

import com.ap_graphics.TillDawn;
import com.ap_graphics.controller.ScoreBoardMenuController;
import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.enums.MenuTexts;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;
import java.util.ArrayList;

public class ScoreBoardMenuScreen implements Screen
{
    private final TillDawn app = TillDawn.getGame();
    private Stage stage;
    private Texture leavesTex;
    private Image leftLeavesImage, rightLeavesImage;
    private TextButton usernameBtn, scoreBtn, timeBtn, killBtn, goBackBtn;
    private Table rowsTable;

    private Skin skin = new Skin(Gdx.files.internal("skins/quantum-horizon/skin/quantum-horizon-ui.json"));

    private int whichSort = 1; // 0: username, 1: score, 2: time, 3: kill
    private boolean ascending = false;

    private final ScoreBoardMenuController controller = new ScoreBoardMenuController();

    private ArrayList<Player> sortedPlayers = controller.getPlayers(whichSort, ascending);
    private int number = Math.min(5, sortedPlayers.size());

    @Override
    public void show()
    {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        leavesTex = new Texture("images/visual/T_TitleLeaves.png");
        leftLeavesImage = new Image(leavesTex);
        TextureRegion flippedRegion = new TextureRegion(leavesTex);
        flippedRegion.flip(true, false);
        rightLeavesImage = new Image(flippedRegion);
        stage.addActor(leftLeavesImage);
        stage.addActor(rightLeavesImage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = App.getCurrentPlayer().getFont();

        TextButton.TextButtonStyle buttonStyle = skin.get(TextButton.TextButtonStyle.class);
        buttonStyle.font = App.getCurrentPlayer().getFont();

        Table table = new Table();
        table.setFillParent(true);

        usernameBtn = new TextButton(MenuTexts.USERNAME.getText(), buttonStyle);
        scoreBtn = new TextButton(MenuTexts.SCORE_HEADER.getText(), buttonStyle);
        timeBtn = new TextButton(MenuTexts.GAME_TIME.getText(), buttonStyle);
        killBtn = new TextButton(MenuTexts.KILL_COUNT.getText(), buttonStyle);
        goBackBtn = new TextButton(MenuTexts.GO_BACK.getText(), buttonStyle);

        float basePadding = 40f;
        float colWidth;

        usernameBtn.pack();
        scoreBtn.pack();
        timeBtn.pack();
        killBtn.pack();

        float usernameWidth = usernameBtn.getWidth() + basePadding;
        float scoreWidth = scoreBtn.getWidth() + basePadding;
        float timeWidth = timeBtn.getWidth() + basePadding;
        float killWidth = killBtn.getWidth() + basePadding;

        colWidth = Math.max(Math.max(usernameWidth, scoreWidth), Math.max(timeWidth, killWidth));

        table.add(usernameBtn).width(colWidth).pad(10).spaceRight(40);
        table.add(scoreBtn).width(colWidth).pad(10).spaceRight(40);
        table.add(timeBtn).width(colWidth).pad(10).spaceRight(40);
        table.add(killBtn).width(colWidth).pad(10);
        table.row();

        rowsTable = new Table();
        table.row();
        table.add(rowsTable).colspan(4);

        updateButtonLabels();
        populateRows(colWidth, labelStyle);

        usernameBtn.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (whichSort == 0) ascending = !ascending;
                whichSort = 0;
                sortedPlayers = controller.getPlayers(whichSort, ascending);
                updateButtonLabels();
                populateRows(colWidth, labelStyle);
            }
        });

        scoreBtn.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (whichSort == 1) ascending = !ascending;
                whichSort = 1;
                sortedPlayers = controller.getPlayers(whichSort, ascending);
                updateButtonLabels();
                populateRows(colWidth, labelStyle);
            }
        });

        timeBtn.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (whichSort == 2) ascending = !ascending;
                whichSort = 2;
                sortedPlayers = controller.getPlayers(whichSort, ascending);
                updateButtonLabels();
                populateRows(colWidth, labelStyle);
            }
        });

        killBtn.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                if (whichSort == 3) ascending = !ascending;
                whichSort = 3;
                sortedPlayers = controller.getPlayers(whichSort, ascending);
                updateButtonLabels();
                populateRows(colWidth, labelStyle);
            }
        });

        goBackBtn.addListener(new ClickListener()
        {
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

        table.row().padTop(20);
        goBackBtn.pack();
        float buttonWidth = goBackBtn.getWidth() + 40;
        table.add(goBackBtn).padTop(40).colspan(4).center().width(buttonWidth).height(50).pad(10);

        stage.addActor(table);
    }

    private void updateButtonLabels()
    {
        usernameBtn.setText(MenuTexts.USERNAME.getText());
        scoreBtn.setText(MenuTexts.SCORE_HEADER.getText());
        timeBtn.setText(MenuTexts.GAME_TIME.getText());
        killBtn.setText(MenuTexts.KILL_COUNT.getText());
    }

    private void populateRows(float colWidth, Label.LabelStyle baseStyle)
    {
        rowsTable.clear();
        Player currentPlayer = App.getCurrentPlayer();

        for (int i = 0; i < number && i < sortedPlayers.size(); i++)
        {
            Player player = sortedPlayers.get(i);

            Label.LabelStyle style = new Label.LabelStyle();
            style.font = baseStyle.font;

            if (i == 0)
            {
                style.fontColor = Color.GOLD;
            } else if (i == 1)
            {
                style.fontColor = Color.BLUE;
            } else if (i == 2)
            {
                style.fontColor = Color.ORANGE;
            }

            Label username = new Label(player.getUsername(), style);

            if (player.equals(currentPlayer))
            {
                username = new Label("> " + player.getUsername(), style);
            }

            Label score = new Label(String.valueOf(player.getScore()), style);
            Label time = new Label(formatTime(player.getTotalPlayTime()), style);
            Label kill = new Label(String.valueOf(player.getKillCount()), style);

            rowsTable.add(username).width(colWidth).pad(10).spaceRight(40).left();
            rowsTable.add(score).width(colWidth).pad(10).spaceRight(40).center();
            rowsTable.add(time).width(colWidth).pad(10).spaceRight(40).center();
            rowsTable.add(kill).width(colWidth).pad(10).center();
            rowsTable.row();
        }
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0.1529f, 0.1255f, 0.1882f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height)
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
    }

    private String formatTime(float timeInSeconds)
    {
        int totalSeconds = (int) timeInSeconds;
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%d:%02d", minutes, seconds);
    }
}
