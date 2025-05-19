package com.ap_graphics.model;

import java.util.ArrayList;

public class App
{
    public static ArrayList<Player> players = new ArrayList<>();

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
}
