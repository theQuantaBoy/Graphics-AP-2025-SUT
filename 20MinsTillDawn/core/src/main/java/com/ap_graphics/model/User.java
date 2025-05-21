package com.ap_graphics.model;

import com.ap_graphics.model.enums.Avatar;
import com.ap_graphics.model.enums.SecurityQuestionOptions;

public class User
{
    private String nickname;
    private String username;
    private String password;
    private Avatar avatar;
    private SecurityQuestionOptions answer;

    public User(String nickname, String username, String password, SecurityQuestionOptions answer, Avatar avatar)
    {
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.answer = answer;
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

    public void setAnswer(SecurityQuestionOptions answer)
    {
        this.answer = answer;
    }

    public void setAvatar(Avatar avatar)
    {
        this.avatar = avatar;
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

    public Avatar getAvatar()
    {
        return avatar;
    }

    public SecurityQuestionOptions getAnswer()
    {
        return answer;
    }
}
