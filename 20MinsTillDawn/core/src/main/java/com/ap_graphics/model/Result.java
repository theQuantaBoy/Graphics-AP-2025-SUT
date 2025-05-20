package com.ap_graphics.model;

public class Result
{
    private final boolean success;
    private final String message;

    public Result(boolean success, String message)
    {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString()
    {
        return message;
    }

    public boolean isSuccessful()
    {
        return success;
    }
}
