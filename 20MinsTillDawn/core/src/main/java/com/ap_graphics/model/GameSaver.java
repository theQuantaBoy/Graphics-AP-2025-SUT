package com.ap_graphics.model;

import com.ap_graphics.model.combat.*;
import com.ap_graphics.model.enums.EnemyType;
import com.ap_graphics.model.enums.WeaponType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.List;

public class GameSaver
{
    private static final String SAVE_DIR = "saves/";

    public static void saveGame(GameWorld world)
    {
        SaveData data = new SaveData();

        // Save player
        Player player = App.getCurrentPlayer();
        PlayerData pd = new PlayerData();
        pd.x = player.getPosX();
        pd.y = player.getPosY();
        pd.xp = player.getXp();
        pd.level = player.getLevel(); // if not auto-calculated
        pd.equippedWeapon = player.getCurrentWeapon().getType().name();
        data.player = pd;

        // Save enemies
        for (Enemy e : world.getEnemies()) {
            EnemyData ed = new EnemyData();
            ed.type = e.getType().name();
            ed.x = e.getPosition().x;
            ed.y = e.getPosition().y;
            ed.hp = e.getHp();
            data.enemies.add(ed);
        }

        // Save general game state
        data.elapsedTime = world.getTotalGameTime();
        data.gameTime = world.getGameTime();

        String username = App.getCurrentPlayer().getUsername(); // or getName()
        FileHandle dir = new FileHandle(SAVE_DIR);
        if (!dir.exists()) dir.mkdirs();

        Json json = new Json();
        FileHandle file = new FileHandle(SAVE_DIR + username + ".json");
        file.writeString(json.prettyPrint(data), false);
    }

    public static GameWorld loadGame(OrthographicCamera camera)
    {
        String username = App.getCurrentPlayer().getUsername();
        FileHandle file = new FileHandle(SAVE_DIR + username + ".json");
        if (!file.exists()) return null;

        Json json = new Json();
        SaveData data = json.fromJson(SaveData.class, file);

        // Restore player
        Player p = App.getCurrentPlayer();

        Texture background = new Texture("images/essential/background.png");

        GameWorld world = new GameWorld(background.getWidth(), background.getHeight(),p,  data.gameTime, camera, background);
        world.setTotalGameTime(data.elapsedTime);

        p.setPosition(data.player.x, data.player.y);
        p.setXp(data.player.xp);
        p.setLevel(data.player.level); // only if not auto-updated
        p.setCurrentWeapon(new Weapon(WeaponType.valueOf(data.player.equippedWeapon)));
        p.resetHP();

        for (EnemyData ed : data.enemies)
        {
            EnemyType type = EnemyType.valueOf(ed.type);
            if (type != EnemyType.ELDER_BRAIN)
            {
                Enemy e = null;

                if (type == EnemyType.TENTACLE_1)
                {
                    e = new TentacleMonster(EnemyType.TENTACLE_1, ed.x, ed.y);
                } else if (type == EnemyType.TENTACLE_2)
                {
                    e = new TentacleMonster(EnemyType.TENTACLE_2, ed.x, ed.y);
                } else if (type == EnemyType.EYE_BAT)
                {
                    e = new Eyebat(ed.x, ed.y);
                } else if (type == EnemyType.TREE)
                {
                    e = new Tree(ed.x, ed.y);
                }

                if (e != null)
                {
                    e.setHp(ed.hp);
                    world.getEnemies().add(e);
                }
            }
        }

        return world;
    }

    public static class SaveData
    {
        public PlayerData player;
        public List<EnemyData> enemies = new ArrayList<>();
        public float elapsedTime;
        public float gameTime;
    }

    public static class PlayerData
    {
        public float x, y;
        public int xp;
        public int level;
        public String equippedWeapon;
    }

    public static class EnemyData
    {
        public String type;
        public float x, y;
        public int hp;
    }

    public static boolean saveExists(String username)
    {
        FileHandle file = new FileHandle(SAVE_DIR + username + ".json");
        return file.exists();
    }

    public static void deleteSave(String username)
    {
        FileHandle file = new FileHandle(SAVE_DIR + username + ".json");
        if (file.exists()) {
            file.delete();
        }
    }
}
