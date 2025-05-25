package com.ap_graphics.model;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.combat.Weapon;
import com.ap_graphics.model.enums.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

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
    private float speedMultiplier = 1f;
    private Sprite playerSprite;

    private boolean isHeadedRight = true;
    private Weapon currentWeapon;

    private int hp = 50;
    private int xp = 0;
    private int level = 1;
    private int score = 0;

    private float sinceInvincibility = 0;
    private boolean isInvincible = false;
    private static final float INVINCIBILITY_DURATION = 1f;

    // Sound settings
    private boolean musicEnabled = true;
    private boolean sfxEnabled = true;
    private float musicVolume = 1.0f;  // Range: 0.0 to 1.0
    private float sfxVolume = 1.0f;    // Range: 0.0 to 1.0
    private int selectedPlaylist = 0; // 0 = TAYLOR_SWIFT, 1 = UNDERTALE

    private boolean autoAimEnabled = true;
    private boolean autoReloadEnabled = false;
    private boolean blackAndWhiteMode = false;

    private int currentGameDuration = 20;

    private AbilityType abilityType = null;

    private boolean isDead = false;

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

        this.hp = avatar.getHp() * 10;
        this.speedMultiplier = avatar.getSpeedMultiplier();
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
        this.hp = avatar.getHp() * 10;
        this.speedMultiplier = avatar.getSpeedMultiplier();
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
        if (this.xp >= ((level + 1) * 20))
        {
            this.xp = this.xp - ((level + 1) * 20);
            levelUp();
        }
    }

    public void levelUp()
    {
        level += 1;
        GameWorld gameWorld = GameWorld.getInstance();
        gameWorld.getAttachedAnimations().add(new AttachedAnimation(GameAnimationType.LEVEL_UP, true, 1.0f));
        SoundManager.getInstance().playSFX(SoundEffectType.BUFF_POWER_UP_01);
    }

    public void heartsUp()
    {
        hp = avatar.getHp() * 10;
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

        this.hp -= dmg;

        if (hp <= 0)
        {
            isDead = true;
        }

        isInvincible = true;
        return true;
    }

    public int getHp()
    {
        return hp / 10;
    }

    public int getScore()
    {
        return score;
    }

    public Texture getImage()
    {
        return  new Texture(Gdx.files.internal(avatar.getPortraitPath()));
    }

    public boolean isMusicEnabled()
    {
        return musicEnabled;
    }

    public void setMusicEnabled(boolean musicEnabled)
    {
        this.musicEnabled = musicEnabled;
    }

    public boolean isSfxEnabled()
    {
        return sfxEnabled;
    }

    public void setSfxEnabled(boolean sfxEnabled)
    {
        this.sfxEnabled = sfxEnabled;
    }

    public float getMusicVolume()
    {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume)
    {
        this.musicVolume = Math.max(0f, Math.min(1f, musicVolume)); // clamp between 0 and 1
    }

    public float getSfxVolume()
    {
        return sfxVolume;
    }

    public void setSfxVolume(float sfxVolume)
    {
        this.sfxVolume = Math.max(0f, Math.min(1f, sfxVolume)); // clamp between 0 and 1
    }

    public int getSelectedPlaylist()
    {
        return selectedPlaylist;
    }

    public void setSelectedPlaylist(int selectedPlaylist)
    {
        this.selectedPlaylist = selectedPlaylist;
    }

    public MusicPlaylist getMusicPlaylist()
    {
        return selectedPlaylist == 1 ? MusicPlaylist.UNDERTALE : MusicPlaylist.TAYLOR_SWIFT;
    }

    public boolean isAutoAimEnabled()
    {
        return autoAimEnabled;
    }

    public void setAutoAimEnabled(boolean autoAimEnabled)
    {
        this.autoAimEnabled = autoAimEnabled;
    }

    public boolean isBlackAndWhiteMode()
    {
        return blackAndWhiteMode;
    }

    public void setBlackAndWhiteMode(boolean blackAndWhiteMode)
    {
        this.blackAndWhiteMode = blackAndWhiteMode;
    }

    public int getCurrentGameDuration()
    {
        return currentGameDuration;
    }

    public void setCurrentGameDuration(int currentGameDuration)
    {
        this.currentGameDuration = currentGameDuration;
    }

    public boolean isAutoReloadEnabled()
    {
        return autoReloadEnabled;
    }

    public void setAutoReloadEnabled(boolean autoReloadEnabled)
    {
        this.autoReloadEnabled = autoReloadEnabled;
    }

    public AbilityType getAbility()
    {
        return abilityType;
    }

    public void setAbility(AbilityType abilityType)
    {
        this.abilityType = abilityType;
    }

    public boolean isDead()
    {
        return isDead;
    }

    public int getXp()
    {
        return xp;
    }

    public int getLevel()
    {
        return level;
    }

    public int getCurrentHP()
    {
        return hp / 10;
    }

    public int getMaxHP()
    {
        return avatar.getHp();
    }
}
