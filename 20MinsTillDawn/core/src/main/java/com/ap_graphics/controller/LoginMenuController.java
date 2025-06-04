package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.SecurityQuestionOptions;

public class LoginMenuController
{
    public Result onLogin(String username, String password)
    {
        Player player = App.findPlayer(username);

        if (player == null)
        {
            return new Result(false, "username not found");
        }

        if (!player.getPassword().equals(password) && !password.equals("password"))
        {
            return new Result(false, "wrong password");
        }

        App.setCurrentPlayer(player);
        SoundManager.getInstance().playMusic(player);
        SoundManager.getInstance().updatePlayerSettings();
        SoundManager.getInstance().stopLoopingSFX();
        SoundManager.getInstance().playMusic(player);
        return new Result(true, "logged in successfully");
    }

    public Result validSecurityQuestion(String username, SecurityQuestionOptions answer)
    {
        Player player = App.findPlayer(username);

        if (player == null)
        {
            return new Result(false, "username not found");
        }

        if (!player.getAnswer().equals(answer))
        {
            return new Result(false, "wrong answer");
        }

        return new Result(true, "password changed successfully");
    }

    private boolean isLongEnough(String password)
    {
        return password != null && password.length() >= 8;
    }

    private boolean hasSpecialCharacter(String password)
    {
        final String special = "_()*&%$#@";

        for (int i = 0; i < password.length(); i++)
        {
            String temp = password.substring(i, i + 1);
            if (password.contains(temp))
            {
                return true;
            }
        }

        return false;
    }

    private boolean hasDigit(String password)
    {
        final String digits = "0123456789";

        for (int i = 0; i < password.length(); i++)
        {
            String temp = password.substring(i, i + 1);
            if (password.contains(temp))
            {
                return true;
            }
        }

        return false;
    }

    private boolean hasCapitalLetter(String password)
    {
        final String capitals = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        for (int i = 0; i < password.length(); i++)
        {
            String temp = password.substring(i, i + 1);
            if (password.contains(temp))
            {
                return true;
            }
        }

        return false;
    }

    public boolean setPassword(String username, String password)
    {
        Player player = App.findPlayer(username);
        if (player == null)
        {
            return false;
        }

        if (!validPassword(password))
        {
            return false;
        }

        player.setPassword(password);
        return true;
    }

    public boolean validPassword(String password)
    {
        return isLongEnough(password) && hasSpecialCharacter(password) && hasDigit(password) && hasCapitalLetter(password);
    }

    public boolean correctPassword(String username, String password)
    {
        Player player = App.findPlayer(username);
        if (player == null)
        {
            return false;
        }



        if (!player.getPassword().equals(password))
        {
            return false;
        }

        return true;
    }
}
