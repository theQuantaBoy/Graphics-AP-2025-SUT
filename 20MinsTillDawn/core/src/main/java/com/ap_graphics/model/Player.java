package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player
{
    private String nickname;
    private String username;
    private String password;
    private Avatar avatar;
    private SecurityQuestionOptions answer;
    private Texture currentFrame;

    private float width = (float) Gdx.graphics.getWidth() / 2;
    private float height = (float) Gdx.graphics.getHeight() / 2;
    private float speed = 5;
    private Sprite playerSprite;

    public Player(String nickname, String username, String password, SecurityQuestionOptions answer, Avatar avatar)
    {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.answer = answer;

        Texture firstFrame = avatar.getIdleAnimation().getKeyFrames()[0];
        this.playerSprite = new Sprite(firstFrame);
        this.playerSprite.setPosition(width, height);
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setAnswer(SecurityQuestionOptions answer)
    {
        this.answer = answer;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
    }

    public String getNickname()
    {
        return nickname;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public Avatar getAvatar()
    {
        return avatar;
    }

    public SecurityQuestionOptions getAnswer()
    {
        return answer;
    }

    public Texture getCurrentFrame()
    {
        return currentFrame;
    }

    public void setCurrentFrame(Texture frame)
    {
        this.currentFrame = frame;
        if (playerSprite != null)
        {
            this.playerSprite.setRegion(frame);
        }
    }

    public float getWidth()
    {
        return width;
    }

    public float getHeight()
    {
        return height;
    }

    public void updateLocation(float dx, float dy)
    {
        width += dx;
        height += dy;
    }

    public float getSpeed()
    {
        return speed;
    }

    public float getPosX() {
        return width;
    }

    public void setPosX(float x) {
        this.width = x;
    }

    public float getPosY() {
        return height;
    }

    public void setPosY(float y) {
        this.height = y;
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setPlayerSprite(Sprite sprite) {
        this.playerSprite = sprite;
    }

}
