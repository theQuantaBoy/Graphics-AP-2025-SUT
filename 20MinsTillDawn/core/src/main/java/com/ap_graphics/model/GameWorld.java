package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class GameWorld
{
    private static GameWorld instance;

    public static GameWorld getInstance()
    {
        return instance;
    }

    private final Player player;
    private final List<WorldObject> obstacles = new ArrayList<>();

    private List<Enemy> enemies = new ArrayList<>();

    private float totalGameTime = 0f;
    private float tentacleSpawnTimer = 0f;
    private float eyebatSpawnTimer = 0f;

    private final float worldWidth, worldHeight;

    private ArrayList<XpOrb> xpOrbs = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    private List<FloatingText> floatingTexts = new ArrayList<>();
    private BitmapFont xpFont;

    public GameWorld(Player player, float w, float h)
    {
        instance = this;
        this.player = player;
        this.worldWidth = w;
        this.worldHeight = h;

        xpFont = new BitmapFont();
        xpFont.getData().setScale(1.5f);
    }

    public void update(float delta)
    {
        totalGameTime += delta;
        tentacleSpawnTimer += delta;
        eyebatSpawnTimer += delta;

        spawnEnemies();
        checkCollisions(delta);

        for (Enemy enemy : enemies)
        {
            enemy.update(delta, player);
        }

        updateOrbs(delta);
        updateFloatingTexts(delta);
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
        enemies.add(new TentacleMonster(EnemyType.TENTACLE_1, spawn.x, spawn.y));
    }

    private void spawnEyebat()
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
                if (enemy.getBounds().overlaps(bulletBounds))
                {
                    enemy.takeDamage(bullet.getDamage());
                    xpOrbs.add(new XpOrb(enemy.getPosition().x, enemy.getPosition().y));
                    enemyCollisionIter.remove();
                    bulletIter.remove();
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
    }

    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }

    public void addXpOrb(XpOrb orb)
    {
        xpOrbs.add(orb);
    }

    public void addFloatingText(String text, Vector2 position)
    {
        floatingTexts.add(new FloatingText(text, position));
    }

    public void renderUI(SpriteBatch batch)
    {
        xpFont.setColor(1, 1, 0, 1); // Reset color before drawing
        floatingTexts.forEach(text -> text.render(batch, xpFont));
    }

    public ArrayList<XpOrb> getXpOrbs()
    {
        return xpOrbs;
    }

    public void spawnEnemies()
    {
        if (tentacleSpawnTimer >= 3f)
        {
            spawnTentacleMonster();
            tentacleSpawnTimer = 0f;
        }

        float eyebatInterval = Math.max(4 * enemies.size() - totalGameTime + 30, 10);
        if (eyebatSpawnTimer >= eyebatInterval) {
            spawnEyebat();
            eyebatSpawnTimer = 0f;
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
                addFloatingText("+3", new Vector2(player.getPosX(), player.getPosY()));
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
}
