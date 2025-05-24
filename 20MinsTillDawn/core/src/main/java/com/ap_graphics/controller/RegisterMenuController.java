package com.ap_graphics.controller;

import com.ap_graphics.model.App;
import com.ap_graphics.model.Player;
import com.ap_graphics.model.Result;
import com.ap_graphics.model.enums.SecurityQuestionOptions;

public class RegisterMenuController
{
    public Result register(String username, String password, SecurityQuestionOptions options)
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

        Player player = new Player(username, password, options);
        App.getPlayers().add(player);
        App.setCurrentPlayer(player);
        return new Result(true, "success");
    }

    public boolean isLongEnough(String password)
    {
        return password != null && password.length() >= 8;
    }

    public boolean hasSpecial(String password) {
        final String special = "_()*&%$#@";
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (special.indexOf(c) != -1) {
                return true;
            }
        }
        return false;
    }

    public boolean hasDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCapital(String password) {
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUnique(String username)
    {
        for (Player player : App.getPlayers())
        {
            if (player.getUsername().equals(username))
            {
                return false;
            }
        }
        return true;
    }

    public boolean validUsernameAndPassword(String username, String password)
    {
        if (username.contains(" ") || password.contains(" "))
        {
            return false;
        }

        if (!isUnique(username))
        {
            return false;
        }

        if (!isLongEnough(password))
        {
            return false;
        }

        if (!hasSpecial(password))
        {
            return false;
        }

        if (!hasDigit(password))
        {
            return false;
        }

        if (!hasCapital(password))
        {
            return false;
        }

        return true;
    }
}
