package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.Result;

public class LoginMenuController
{
    public Result onLogin(String username, String password)
    {
        Player player = App.findPlayer(username);

        if (player == null)
        {
            return new Result(false, "username not found");
        }

        if (!player.getPassword().equals(password))
        {
            return new Result(false, "wrong password");
        }

        App.setCurrentPlayer(player);
        return new Result(true, "logged in successfully");
    }
}
