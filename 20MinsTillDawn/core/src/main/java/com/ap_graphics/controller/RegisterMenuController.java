package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;
import com.ap_graphics.view.RegisterMenuScreen;

public class RegisterMenuController
{
    private RegisterMenuScreen view;

    public void setView(RegisterMenuScreen view)
    {
        this.view = view;
    }

//    public Result register(String username, String password, int avatarId, SecurityQuestionOptions option)

    public Result register(String username, String password)
    {
        if (username.isEmpty())
        {
            return new Result(false, "please enter a username");
        }

        if (password.isEmpty())
        {
            return new Result(false, "password can not be empty");
        }

        Player other = App.findPlayer(username);
        if (other != null)
        {
            return new Result(false, "this username is already taken");
        }

        if (password.length() < 8)
        {
            return new Result(false, "password must be at least 8 characters");
        }

        if (!hasCapital(password))
        {
            return new Result(false, "password must have at least one capital letter");
        }

        if (!hasDigit(password))
        {
            return new Result(false, "password must have at least one digit");
        }

        if (!hasSpecial(password))
        {
            return new Result(false, "password must have at least one special character");
        }

//        Player player = new Player(username, password, option, Avatar.getAvatar(avatarId));
        Player player = new Player(username, password);
        App.getPlayers().add(player);
        return new Result(true, "success");
    }

    public static boolean isLongEnough(String password)
    {
        return password != null && password.length() >= 8;
    }

    public static boolean hasSpecial(String password)
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

    public static boolean hasDigit(String password)
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

    public static boolean hasCapital(String password)
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

    public boolean isNameValid(String name)
    {
        return name.matches("[a-zA-Z]+");
    }
}
