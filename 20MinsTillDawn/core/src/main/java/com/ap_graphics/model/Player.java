package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;

public class Player
{
    private String nickname;
    private String username;
    private String password;
    private Avatar avatar;
    private boolean securityQuestionAnswer;

    public Player(String nickname, String username, String password, boolean securityQuestionAnswer, Avatar avatar)
    {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public void setSecurityQuestionAnswer(boolean securityQuestionAnswer)
    {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public String getNickname()
    {
        return nickname;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public boolean isSecurityQuestionAnswer()
    {
        return securityQuestionAnswer;
    }
}
