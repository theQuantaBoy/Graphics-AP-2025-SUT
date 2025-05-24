package com.ap_graphics.model;

import com.ap_graphics.model.combat.Weapon;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.ap_graphics.model.enums.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.Random;

public class Player
{
    private String username;
    private String password;
    private Avatar avatar;
    private SecurityQuestionOptions answer;
    private TextureRegion currentFrame;

    private float width = (float) Gdx.graphics.getWidth() / 2;
    private float height = (float) Gdx.graphics.getHeight() / 2;
    private float speed = 1.25f;
    private Sprite playerSprite;

    private boolean isHeadedRight = true;
    private Weapon currentWeapon;

    private int xp = 0;
    private int score = 0;

    private float sinceInvincibility = 0;
    private boolean isInvincible = false;
    private static final float INVINCIBILITY_DURATION = 1f;

    public Player(String username, String password, SecurityQuestionOptions answer)
    {
        this.username = username;
        this.password = password;
        this.avatar = Avatar.getAvatar(new Random().nextInt(Avatar.values().length));
        this.answer = answer;

        TextureRegion firstFrame = avatar.getIdleAnimation().getKeyFrames()[0];
        this.playerSprite = new Sprite(firstFrame);
        this.playerSprite.setPosition(width, height);
        setCurrentWeapon(new Weapon(WeaponType.REVOLVER));
    }

    public void updateInvincibility(float delta)
    {
        if (sinceInvincibility >= INVINCIBILITY_DURATION)
        {
            sinceInvincibility = 0;
            isInvincible = false;
            return;
        }

        if (isInvincible)
        {
            sinceInvincibility += delta;
        }
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

    public TextureRegion getCurrentFrame()
    {
        return currentFrame;
    }

    public void setCurrentFrame(TextureRegion frame)
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

    public boolean isHeadedRight()
    {
        return isHeadedRight;
    }

    public void setHeadedRight(boolean headedRight)
    {
        isHeadedRight = headedRight;
    }

    public Weapon getCurrentWeapon()
    {
        return currentWeapon;
    }

    public void setCurrentWeapon(Weapon currentWeapon)
    {
        this.currentWeapon = currentWeapon;
    }

    public void gainXP(int xp)
    {
        this.xp += xp;
    }

    public Vector2 getPosition()
    {
        return new Vector2(width, height);
    }

    public Rectangle getBounds() {
        // Get dimensions from player's current animation frame
        TextureRegion frame = currentFrame;

        // Calculate centered bounding box
        float width = frame.getRegionWidth();
        float height = frame.getRegionHeight();

        return new Rectangle(
            getPosX() - width/2f,  // Center X
            getPosY() - height/2f, // Center Y
            width,                  // Actual width
            height                  // Actual height
        );
    }

    public boolean takeDamage(int dmg)
    {
        if (isInvincible)
        {
            return false;
        }

        this.xp -= dmg;
        isInvincible = true;
        return true;
    }

    public int getScore()
    {
        return score;
    }

    public Texture getImage()
    {
        return  new Texture(Gdx.files.internal(avatar.getPortraitPath()));
    }
}
