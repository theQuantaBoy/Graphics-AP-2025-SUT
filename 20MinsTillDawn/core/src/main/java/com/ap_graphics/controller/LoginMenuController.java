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

        if (!player.getPassword().equals(password))
        {
            return new Result(false, "wrong password");
        }

        App.setCurrentPlayer(player);
        return new Result(true, "logged in successfully");
    }

    public Result validSecurityQuestion(String username, String password, SecurityQuestionOptions answer)
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

        if (player.getPassword().equals(password))
        {
            return new Result(false, "new password can not be the same as the old password");
        }

        if (password.length() < 8)
        {
            return new Result(false, "new password must be at least 8 characters");
        }

        if (!hasCapitalLetter(password))
        {
            return new Result(false, "new password must have at least one capital letter");
        }

        if (!hasDigit(password))
        {
            return new Result(false, "new password must have at least one digit");
        }

        if (!hasSpecialCharacter(password))
        {
            return new Result(false, "new password must have at least one special character");
        }

        player.setPassword(password);
        return new Result(true, "password changed successfully");
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
}
