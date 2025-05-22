package com.ap_graphics.model;

import com.ap_graphics.model.enums.EnemyType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private float tentacleTimer = 0f;
    private final float tentacleInterval = 5f;

    private ArrayList<XPOrb> xpOrbs = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public GameWorld(Player player, float w, float h)
    {
        instance = this;
        this.player = player;
        this.worldWidth = w;
        this.worldHeight = h;

        // add static trees
//        obstacles.add(new Tree(new Texture("tree.png"), 200, 200));
    }

    public void update(float delta)
    {
        totalGameTime += delta;
        tentacleSpawnTimer += delta;
        eyebatSpawnTimer += delta;

        if (tentacleSpawnTimer >= 3f)
        {
            spawnTentacleMonster();
            tentacleSpawnTimer = 0f;
        }

        float eyebatInterval = Math.max(4 * enemies.size() - totalGameTime + 30, 10);
        if (eyebatSpawnTimer >= eyebatInterval)
        {
            spawnEyebat();
            eyebatSpawnTimer = 0f;
        }

        for (Enemy enemy : enemies)
        {
            enemy.update(delta, player);
        }

        Iterator<Enemy> iterator = enemies.iterator();
        while (iterator.hasNext())
        {
            Enemy enemy = iterator.next();
            if (enemy.isDead()) {
                iterator.remove();
                xpOrbs.add(new XPOrb(enemy.getPosition().x, enemy.getPosition().y));
            }
        }
    }

    public void render(SpriteBatch batch, float delta)
    {
        obstacles.forEach(o -> o.render(batch, delta));
        enemies.forEach(e -> e.render(batch, delta));
        player.getPlayerSprite().draw(batch);
    }

    private TentacleMonster spawnTentacle()
    {
        float x, y;
        switch (MathUtils.random(3))
        {
            case 0:
                x = 0;
                y = MathUtils.random(worldHeight);
                break;
            case 1:
                x = worldWidth;
                y = MathUtils.random(worldHeight);
                break;
            case 2:
                x = MathUtils.random(worldWidth);
                y = 0;
                break;
            default:
                x = MathUtils.random(worldWidth);
                y = worldHeight;
                break;
        }
        Animation<TextureRegion> idle = player.getAvatar().getIdleAnimation();
        Animation<TextureRegion> run = player.getAvatar().getRunAnimation();
        return new TentacleMonster(EnemyType.TENTACLE_1, x, y);
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

    public void checkCollisions()
    {
        Iterator<Bullet> bulletIter = bullets.iterator();
        while (bulletIter.hasNext())
        {
            Bullet bullet = bulletIter.next();
            for (Enemy enemy : enemies)
            {
                if (bullet.getBounds().overlaps(enemy.getBounds())) {
                    enemy.takeDamage(bullet.getDamage());
                    bulletIter.remove();
                    break;
                }
            }
        }
    }
}
