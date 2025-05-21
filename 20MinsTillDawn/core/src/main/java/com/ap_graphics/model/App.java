package com.ap_graphics.model;

import java.util.ArrayList;

public class App
{
    private static Player currentPlayer;
    private static ArrayList<Player> players = new ArrayList<>();

    public static Player findPlayer(String username)
    {
        for (Player player : players)
        {
            if (player.getUsername().equals(username))
            {
                return player;
            }
        }

        return null;
    }

    public static ArrayList<Player> getPlayers()
    {
        return players;
    }

    public static void setCurrentPlayer(Player currentPlayer)
    {
        App.currentPlayer = currentPlayer;
    }

    public static Player getCurrentPlayer()
    {
        return currentPlayer;
    }
}
