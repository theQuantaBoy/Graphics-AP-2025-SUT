package com.ap_graphics.model;

import java.util.ArrayList;

public class App
{
    private static User currentPlayer;
    private static ArrayList<User> players = new ArrayList<>();

    public static User findPlayer(String username)
    {
        for (User player : players)
        {
            if (player.getUsername().equals(username))
            {
                return player;
            }
        }

        return null;
    }

    public static ArrayList<User> getPlayers()
    {
        return players;
    }

    public static void setCurrentPlayer(User currentPlayer)
    {
        App.currentPlayer = currentPlayer;
    }

    public static User getCurrentPlayer()
    {
        return currentPlayer;
    }
}
