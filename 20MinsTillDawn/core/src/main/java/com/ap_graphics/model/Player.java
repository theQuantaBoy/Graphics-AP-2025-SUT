package com.ap_graphics.model;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.combat.Weapon;
import com.ap_graphics.model.enums.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
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
    private int maxHp = 50;

    private float sinceInvincibility = 0;
    private boolean isInvincible = false;
    private static final float INVINCIBILITY_DURATION = 1f;

    // Sound settings
    private boolean musicEnabled = true;
    private boolean sfxEnabled = true;
    private float musicVolume = 1.0f;  // Range: 0.0 to 1.0
    private float sfxVolume = 1.0f;    // Range: 0.0 to 1.0
    private int selectedPlaylist = 0; // 0 = TAYLOR_SWIFT, 1 = UNDERTALE

    private boolean autoReloadEnabled = false;
    private boolean blackAndWhiteMode = false;

    private int currentGameDuration = 20;

    private AbilityType abilityType = null;

    private boolean isDead = false;

    private float speedBuffTimer = 0f;
    private float damageBuffTimer = 0f;

    private boolean isSpeedBuffActive = false;
    private boolean isDamageBuffActive = false;

    private ArrayList<AbilityType> abilityTypes = new ArrayList<>();

    private int killCount = 0;
    private float totalPlayTime = 0;

    private int moveUpKey = Input.Keys.W;
    private int moveDownKey = Input.Keys.S;
    private int moveLeftKey = Input.Keys.A;
    private int moveRightKey = Input.Keys.D;

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
        this.maxHp = avatar.getHp() * 10;
        this.speedMultiplier = avatar.getSpeedMultiplier();
    }

    public void update(float delta)
    {
        if (isSpeedBuffActive)
        {
            speedBuffTimer -= delta;
            if (speedBuffTimer <= 0f)
            {
                isSpeedBuffActive = false;
            }
        }

        if (isDamageBuffActive)
        {
            damageBuffTimer -= delta;
            if (damageBuffTimer <= 0f)
            {
                isDamageBuffActive = false;
            }
        }
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
        return isSpeedBuffActive ? speed * 2f : speed;
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
        hp = maxHp;
        SoundManager.getInstance().playSFX(SoundEffectType.SFX_LOWHEALTH_ALARMLOOP1);
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
        return (hp + 9) / 10;
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
        if (!abilityTypes.contains(abilityType))
        {
            abilityTypes.add(abilityType);
        }

        switch (abilityType)
        {
            case VITALITY:
            {
                maxHp += 10;
                break;
            }

            case PROCREASE:
            {
                currentWeapon.addProjectile();
                break;
            }

            case AMOCREASE:
            {
                currentWeapon.addMaxAmmo();
                break;
            }

            case DAMAGER:
            {
                isDamageBuffActive = true;
                damageBuffTimer = 10f;
                break;
            }

            case SPEEDY:
            {
                isSpeedBuffActive = true;
                speedBuffTimer = 10f;
                break;
            }
        }
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
        return maxHp / 10;
    }

    public boolean isDamageBuffActive()
    {
        return isDamageBuffActive;
    }

    public ArrayList<AbilityType> getAbilityTypes()
    {
        return abilityTypes;
    }

    public int getKillCount()
    {
        return killCount;
    }

    public void addToKills()
    {
        killCount += 1;
    }

    public void addScore(int score)
    {
        this.score += score;
    }

    public void resetGameData()
    {
        this.hp = avatar.getHp() * 10;
        this.maxHp = avatar.getHp() * 10;
        this.speedMultiplier = avatar.getSpeedMultiplier();

        this.xp = 0;
        this.level = 1;

        sinceInvincibility = 0;
        isInvincible = false;

        isDead = false;

        speedBuffTimer = 0f;
        damageBuffTimer = 0f;

        isSpeedBuffActive = false;
        isDamageBuffActive = false;

        killCount = 0;

        abilityTypes.clear();
    }

    public int getMoveUpKey() { return moveUpKey; }
    public void setMoveUpKey(int moveUpKey) { this.moveUpKey = moveUpKey; }

    public int getMoveDownKey() { return moveDownKey; }
    public void setMoveDownKey(int moveDownKey) { this.moveDownKey = moveDownKey; }

    public int getMoveLeftKey() { return moveLeftKey; }
    public void setMoveLeftKey(int moveLeftKey) { this.moveLeftKey = moveLeftKey; }

    public int getMoveRightKey() { return moveRightKey; }
    public void setMoveRightKey(int moveRightKey) { this.moveRightKey = moveRightKey; }

    public boolean isValidMovementKey(int keycode)
    {
        // Disallow duplicates
        if (keycode == moveUpKey || keycode == moveDownKey ||
            keycode == moveLeftKey || keycode == moveRightKey)
            return false;

        // Disallow used hotkeys
        if (keycode == Input.Keys.T || keycode == Input.Keys.L ||
            keycode == Input.Keys.H || keycode == Input.Keys.B ||
            keycode == Input.Keys.K || keycode == Input.Keys.P ||
            keycode == Input.Keys.F || keycode == Input.Keys.N ||
            keycode == Input.Keys.C || keycode == Input.Keys.SPACE)
            return false;

        // Disallow modifier and function keys
        if (keycode == Input.Keys.CONTROL_LEFT || keycode == Input.Keys.CONTROL_RIGHT ||
            keycode == Input.Keys.ALT_LEFT || keycode == Input.Keys.ALT_RIGHT ||
            keycode == Input.Keys.SHIFT_LEFT || keycode == Input.Keys.SHIFT_RIGHT ||
            (keycode >= Input.Keys.F1 && keycode <= Input.Keys.F12))
            return false;

        return true;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public void setKillCount(int killCount)
    {
        this.killCount = killCount;
    }

    public void setTotalPlayTime(float totalPlayTime)
    {
        this.totalPlayTime = totalPlayTime;
    }

    public void addToPlayTime(float delta)
    {
        this.totalPlayTime += delta;
    }

    public float getTotalPlayTime()
    {
        return totalPlayTime;
    }
}
