package com.ap_graphics.model;

import com.ap_graphics.controller.SoundManager;
import com.ap_graphics.model.combat.*;
import com.ap_graphics.model.enums.AbilityType;
import com.ap_graphics.model.enums.EnemyType;
import com.ap_graphics.model.enums.GameAnimationType;
import com.ap_graphics.model.enums.SoundEffectType;
import com.ap_graphics.view.ChooseAbilityDialog;
import com.ap_graphics.view.EndGameDialog;
import com.ap_graphics.view.PauseMenuDialog;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.*;

public class GameWorld
{
    private static GameWorld instance;

    public static GameWorld getInstance()
    {
        return instance;
    }

    private final float gameTime;

    private final Player player;
    private final List<WorldObject> obstacles = new ArrayList<>();

    private List<Enemy> enemies = new ArrayList<>();

    private float totalGameTime = 0f;
    private float tentacleSpawnTimer = 0f;
    private float eyebatSpawnTimer = 0f;
    private float elderSpawnTimer = 0f;

    private boolean elderExists = false;

    private final float worldWidth, worldHeight;

    private ArrayList<XpOrb> xpOrbs = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();
    private ArrayList<EnemyBullet> enemyBullets = new ArrayList<>();

    private List<FloatingText> floatingTexts = new ArrayList<>();
    private BitmapFont xpFont;

    List<GameAnimation> animations = new ArrayList<>();
    GameAnimation reloadAnimation = null;

    private boolean paused = false;

    private Stage uiStage;
    private Skin uiSkin;

    private ArrayList<AttachedAnimation> attachedAnimations = new ArrayList<>();

    private boolean levelUpAnimationOver = false;
    private boolean pauseIsSelected = false;

    public GameWorld(Player player, float w, float h, float gameTime)
    {
        instance = this;
        this.player = player;
        this.worldWidth = w;
        this.worldHeight = h;
        this.gameTime = gameTime;

        spawnTrees(10);

        xpFont = new BitmapFont();
        xpFont.getData().setScale(1.5f);
    }

    public void setUIContext(Stage stage, Skin skin)
    {
        this.uiStage = stage;
        this.uiSkin = skin;
    }

    public void update(float delta)
    {
        if (paused) return;

        totalGameTime += delta;
        tentacleSpawnTimer += delta;
        eyebatSpawnTimer += delta;
        elderSpawnTimer += delta;

        if (shouldPause())
        {
            if (pauseIsSelected)
            {
                pause();
                PauseMenuDialog dialog = new PauseMenuDialog(uiStage, uiSkin, xpFont);

                uiStage.addActor(dialog);
                pauseIsSelected = false;
            } else if (player.getHp() == 0)
            {
                pause();
                EndGameDialog dialog = new EndGameDialog(uiStage, uiSkin, xpFont, false);
                uiStage.addActor(dialog);
            } else
            {
                pause();
                EndGameDialog dialog = new EndGameDialog(uiStage, uiSkin, xpFont, true);
                uiStage.addActor(dialog);
            }
        }

        if (levelUpAnimationOver)
        {
            pause();
            AbilityType[] abilities = AbilityType.randomThree();

            ChooseAbilityDialog dialog = new ChooseAbilityDialog(uiStage, uiSkin, abilities);

            uiStage.addActor(dialog);
            levelUpAnimationOver = false;
        }

        spawnEnemies(delta);
        checkCollisions(delta);
        checkEnemyBulletCollisions(delta);
        checkEnemyCollisions(delta);
        player.update(delta);

        for (Enemy enemy : enemies)
        {
            enemy.update(delta, player);
        }

        updateOrbs(delta);
        updateFloatingTexts(delta);
        player.updateInvincibility(delta);
        player.getCurrentWeapon().update(delta);
        updateAnimations(delta);
        updateAttachedAnimations(delta);
    }

    public void render(SpriteBatch batch, float delta)
    {
        obstacles.forEach(o -> o.render(batch, delta));
        enemies.forEach(e -> e.render(batch, delta));
        player.getPlayerSprite().draw(batch);
    }

    public Player getPlayer()
    {
        return player;
    }

    private Vector2 getRandomSpawnPositionOutsideCamera()
    {
        float margin = 200f;
        float side = MathUtils.random(0, 3);
        float x = 0, y = 0;

        if (side == 0) y = worldHeight + margin;         // top
        if (side == 1) x = worldWidth + margin;          // right
        if (side == 2) y = -margin;                      // bottom
        if (side == 3) x = -margin;                      // left

        if (side % 2 == 0) x = MathUtils.random(0, worldWidth);
        else y = MathUtils.random(0, worldHeight);

        return new Vector2(x, y);
    }

    public List<Enemy> getEnemies()
    {
        return enemies;
    }

    private void spawnTentacleMonster()
    {
        Vector2 spawn = getRandomSpawnPositionOutsideCamera();
        if (Math.random() < 0.7f)
        {
            enemies.add(new TentacleMonster(EnemyType.TENTACLE_1, spawn.x, spawn.y));
        } else
        {
            enemies.add(new TentacleMonster(EnemyType.TENTACLE_2, spawn.x, spawn.y));
        }
    }

    public void spawnEyebat()
    {
        Vector2 spawn = getRandomSpawnPositionOutsideCamera();
        enemies.add(new Eyebat(spawn.x, spawn.y));
    }

    public float getTotalGameTime()
    {
        return totalGameTime;
    }

    public void checkCollisions(float delta)
    {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext())
        {
            Bullet bullet = bulletIter.next();
            bullet.update(delta);

            if (isOffScreen(bullet.getPosition()))
            {
                bulletIter.remove();
                continue;
            }

            Rectangle bulletBounds = bullet.getBounds();
            Iterator<Enemy> enemyCollisionIter = enemies.iterator();

            while (enemyCollisionIter.hasNext())
            {
                Enemy enemy = enemyCollisionIter.next();
                if (!(enemy instanceof Tree))
                {
                    if (enemy.getBounds().overlaps(bulletBounds))
                    {
                        enemy.takeDamage(player.getCurrentWeapon().getDamage());
                        SoundManager.getInstance().playSFX(SoundEffectType.BLOOD_SPLASH_QUICK_01);
                        enemy.moveAwayFromPlayer(delta, player);
                        addFloatingText("-" + player.getCurrentWeapon().getDamage(), enemy.getPosition(), Color.YELLOW);
                        if (enemy.isDead())
                        {
                            if (enemy instanceof Elder)
                            {
                                elderExists = false;
                            }
                            SoundManager.getInstance().playSFX(SoundEffectType.EXPLOSION_BLOOD_01);
                            xpOrbs.add(new XpOrb(enemy.getPosition().x, enemy.getPosition().y));
                            animations.add(new GameAnimation(GameAnimationType.TENTACLE_DEATH, enemy.getPosition()));
                            enemyCollisionIter.remove();
                            player.addToKills();
                        }
                        bulletIter.remove();
                        break;
                    }
                }
            }
        }
    }

    public void checkEnemyBulletCollisions(float delta)
    {
        Iterator<EnemyBullet> bulletIter = enemyBullets.iterator();
        while (bulletIter.hasNext())
        {
            Bullet bullet = bulletIter.next();
            bullet.update(delta);

            if (isOffScreen(bullet.getPosition()))
            {
                bulletIter.remove();
                continue;
            }

            Rectangle bulletBounds = bullet.getBounds();
            if (player.getBounds().overlaps(bulletBounds))
            {
                if (player.takeDamage(player.getCurrentWeapon().getDamage()))
                {
                    SoundManager.getInstance().playSFX(SoundEffectType.DEBUFF_SPEED);
                    addFloatingText("-" + player.getCurrentWeapon().getDamage(), player.getPosition(), Color.RED);
                    attachedAnimations.add(new AttachedAnimation(GameAnimationType.HERO_DAMAGE, true, 1.2f));
                }

                bulletIter.remove();
            }
        }
    }

    public void checkEnemyCollisions(float delta)
    {
        for (Enemy e : enemies)
        {
            if (e.getBounds().overlaps(player.getBounds()))
            {
                if (player.takeDamage(2))
                {
                    SoundManager.getInstance().playSFX(SoundEffectType.DEBUFF_SPEED);
                    addFloatingText("-2", player.getPosition(), Color.RED);
                }
            }
        }
    }

    private boolean isOffScreen(Vector2 position)
    {
        return position.x < 0 || position.x > worldWidth ||
            position.y < 0 || position.y > worldHeight;
    }

    public void addBullet(Bullet bullet)
    {
        bullets.add(bullet);
        SoundManager.getInstance().playSFX(SoundEffectType.STANDARD_WEAPON_WHOOSH_01);
    }

    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }

    public void addXpOrb(XpOrb orb)
    {
        xpOrbs.add(orb);
    }

    public void addFloatingText(String text, Vector2 position, Color color)
    {
        floatingTexts.add(new FloatingText(text, position, color));
    }

    public void renderUI(SpriteBatch batch)
    {
        xpFont.setColor(1, 1, 0, 1); // Reset color before drawing
        floatingTexts.forEach(text -> text.render(batch, xpFont));
        drawAnimations(batch);
        drawAttachedAnimations(batch);
        drawWeapon(batch);
    }

    public ArrayList<XpOrb> getXpOrbs()
    {
        return xpOrbs;
    }

    public void spawnEnemies(float delta)
    {
        if (tentacleSpawnTimer >= 3f)
        {
            for (int i = 0; i < ((delta / 30f) + 1); i++)
            {
                spawnTentacleMonster();
            }
            tentacleSpawnTimer = 0f;
        }

        if (delta > (gameTime / 4f))
        {
            if (eyebatSpawnTimer >= 10f)
            {
                for (int i = 0; i < (((4 * delta - gameTime + 30)) / 30f); i++)
                {
                    spawnEyebat();
                }
            }
        }

        if (delta > (gameTime / 2f) - 90f)
        {
            if (!elderExists && elderSpawnTimer > 90f)
            {
                spawnElder();
            }
        }
    }

    public void updateOrbs(float delta)
    {
        Iterator<XpOrb> orbIter = xpOrbs.iterator();
        while (orbIter.hasNext())
        {
            XpOrb orb = orbIter.next();
            orb.update(delta);

            if (orb.getBounds().overlaps(player.getBounds()))
            {
                player.gainXP(3);
                SoundManager.getInstance().playSFX(SoundEffectType.COINS_10);
                addFloatingText("+3", new Vector2(player.getPosX(), player.getPosY()), Color.GREEN);
                orbIter.remove();
            }

            if (orb.shouldRemove())
            {
                orbIter.remove();
            }
        }
    }

    public void updateFloatingTexts(float delta)
    {
        Iterator<FloatingText> textIter = floatingTexts.iterator();
        while (textIter.hasNext())
        {
            if (textIter.next().update(delta))
            {
                textIter.remove();
            }
        }
    }

    public ArrayList<EnemyBullet> getEnemyBullets()
    {
        return enemyBullets;
    }

    public void spawnElder()
    {
        Vector2 spawn = getRandomSpawnPositionOutsideCamera();
        enemies.add(new Elder(spawn.x, spawn.y));
        elderExists = true;
        SoundManager.getInstance().playSFX(SoundEffectType.SPELL_EXPLOSION_MAGIC_02);
    }

    private void spawnTrees(int count)
    {
        for (int i = 0; i < count; i++)
        {
            float x = MathUtils.random(0, worldWidth - EnemyType.TREE.getIdleAnimation().getKeyFrame(0).getRegionWidth());
            float y = MathUtils.random(0, worldHeight - EnemyType.TREE.getIdleAnimation().getKeyFrame(0).getRegionHeight());
            Tree tree = new Tree(x, y);
            enemies.add(tree);
        }
    }

    public int getRemainingTime()
    {
        return Math.max(0, (int) gameTime - (int) totalGameTime);
    }

    public void updateAnimations(float delta)
    {
        for (Iterator<GameAnimation> it = animations.iterator(); it.hasNext(); ) {
            GameAnimation a = it.next();
            a.update(delta);
            if (a.isFinished()) it.remove();
        }
    }

    public void drawAnimations(SpriteBatch batch)
    {
        for (GameAnimation a : animations)
        {
            a.render(batch);
        }
    }

    public void drawWeapon(SpriteBatch batch)
    {
        Weapon weapon = player.getCurrentWeapon();

        if (!weapon.isReloading())
        {
            TextureRegion weaponTex = player.getCurrentWeapon().getType().getTextureRegion();
            Vector2 weaponPos = player.getCurrentWeapon().getPosition();
            batch.draw(
                weaponTex,
                weaponPos.x,
                weaponPos.y,
                weaponTex.getRegionWidth() / 2f,
                weaponTex.getRegionHeight() / 2f,
                weaponTex.getRegionWidth(),
                weaponTex.getRegionHeight(),
                1f,
                1f,
                player.getCurrentWeapon().getRotation()
            );
        }
    }

    public void pause() { paused = true; }

    public void resume() { paused = false; }

    public boolean isPaused() { return paused; }

    public ArrayList<AttachedAnimation> getAttachedAnimations()
    {
        return attachedAnimations;
    }

    public void drawAttachedAnimations(SpriteBatch batch)
    {
        for (AttachedAnimation a : attachedAnimations)
        {
            a.render(batch);
        }
    }

    public void updateAttachedAnimations(float delta)
    {
        for (Iterator<AttachedAnimation> it = attachedAnimations.iterator(); it.hasNext(); ) {
            AttachedAnimation a = it.next();
            a.update(delta);
            if (a.isFinished())
            {
                if (a.getAnimationType() == GameAnimationType.LEVEL_UP)
                {
                    levelUpAnimationOver = true;
                }
                it.remove();
            }
        }
    }

    public void cheatAdvanceTime()
    {
        totalGameTime += 60;
    }

    public void killAllEnemies()
    {
        for (Iterator<Enemy> it = enemies.iterator(); it.hasNext(); ) {
            Enemy a = it.next();
            if (!(a instanceof Tree))
            {
                it.remove();
            }
        }
        SoundManager.getInstance().playSFX(SoundEffectType.EXPLOSION_BLOOD_01);
    }

    private boolean shouldPause()
    {
        return pauseIsSelected || (totalGameTime >= gameTime) || (player.getHp() == 0);
    }

    public void hitPause()
    {
        pauseIsSelected = true;
    }

    public int getScore()
    {
        int score = (int) totalGameTime * player.getKillCount();
        player.addScore(score);
        return score;
    }
}
