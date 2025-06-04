package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;

import java.util.ArrayList;
import java.util.Comparator;

public class ScoreBoardMenuController
{
    public ArrayList<Player> getPlayers(int sortOption, boolean ascending)
    {
        ArrayList<Player> players = new ArrayList<>(App.getPlayers());

        Comparator<Player> comparator = null;

        switch (sortOption)
        {
            case 0: // Username
                comparator = Comparator.comparing(Player::getUsername, String.CASE_INSENSITIVE_ORDER);
                break;

            case 1: // Score
                comparator = Comparator.comparingInt(Player::getScore);
                break;

            case 2: // Game time
                comparator = Comparator.comparingDouble(Player::getTotalPlayTime);
                break;

            case 3: // Kill count
                comparator = Comparator.comparingInt(Player::getKillCount);
                break;
        }

        if (comparator != null)
        {
            if (!ascending)
            {
                comparator = comparator.reversed();
            }
            players.sort(comparator);
        }

        return players;
    }
}
