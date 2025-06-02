package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;

import java.sql.*;
import java.util.ArrayList;

public class DataBaseManager {
    private static final String DB_URL = "jdbc:sqlite:players.db";

    public static void initialize() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql =
                "CREATE TABLE IF NOT EXISTS players (" +
                    "username TEXT PRIMARY KEY," +
                    "password TEXT," +
                    "answer TEXT," +
                    "avatar TEXT," +
                    "moveUp INTEGER," +
                    "moveDown INTEGER," +
                    "moveLeft INTEGER," +
                    "moveRight INTEGER," +
                    "musicVolume REAL," +
                    "sfxEnabled INTEGER," +
                    "playlist INTEGER," +
                    "autoReload INTEGER," +
                    "bwMode INTEGER," +
                    "score INTEGER" +
                    ");";

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void savePlayers(ArrayList<Player> players) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String insertSQL =
                "INSERT OR REPLACE INTO players " +
                    "(username, password, answer, avatar, moveUp, moveDown, moveLeft, moveRight, musicVolume, sfxEnabled, playlist, autoReload, bwMode, score) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(insertSQL);

            for (Player p : players) {
                pstmt.setString(1, p.getUsername());
                pstmt.setString(2, p.getPassword());
                pstmt.setString(3, p.getAnswer().name());
                pstmt.setString(4, p.getAvatar().name());
                pstmt.setInt(5, p.getMoveUpKey());
                pstmt.setInt(6, p.getMoveDownKey());
                pstmt.setInt(7, p.getMoveLeftKey());
                pstmt.setInt(8, p.getMoveRightKey());
                pstmt.setFloat(9, p.getMusicVolume());
                pstmt.setBoolean(10, p.isSfxEnabled());
                pstmt.setInt(11, p.getSelectedPlaylist());
                pstmt.setBoolean(12, p.isAutoReloadEnabled());
                pstmt.setBoolean(13, p.isBlackAndWhiteMode());
                pstmt.setInt(14, p.getScore());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Player> loadPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery("SELECT * FROM players");
            while (rs.next()) {
                Player p = new Player(
                    rs.getString("username"),
                    rs.getString("password"),
                    SecurityQuestionOptions.valueOf(rs.getString("answer"))
                );
                p.setAvatar(Avatar.valueOf(rs.getString("avatar")));
                p.setMoveUpKey(rs.getInt("moveUp"));
                p.setMoveDownKey(rs.getInt("moveDown"));
                p.setMoveLeftKey(rs.getInt("moveLeft"));
                p.setMoveRightKey(rs.getInt("moveRight"));
                p.setMusicVolume(rs.getFloat("musicVolume"));
                p.setSfxEnabled(rs.getBoolean("sfxEnabled"));
                p.setSelectedPlaylist(rs.getInt("playlist"));
                p.setAutoReloadEnabled(rs.getBoolean("autoReload"));
                p.setBlackAndWhiteMode(rs.getBoolean("bwMode"));
                p.setScore(rs.getInt("score"));

                players.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }
}
