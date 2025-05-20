package com.ap_graphics.model.enums;

public enum SecurityQuestionOptions
{
    ELEPHANT_SIZED_PUPPIES("images/sequrity_question/elephant_sized_puppy.png", "elephant sized puppies"),
    PUPPY_SIZED_ELEPHANTS("images/sequrity_question/puppy_sized_elephants.png", "puppy sized elephants"),
    ;

    private final String path;
    private final String name;

    SecurityQuestionOptions(String path, String name)
    {
        this.path = path;
        this.name = name;
    }

    public static SecurityQuestionOptions getQuestion(int option)
    {
        switch (option % 2)
        {
            case 0:
                return PUPPY_SIZED_ELEPHANTS;
            case 1:
                return ELEPHANT_SIZED_PUPPIES;
            default:
                return null;
        }
    }

    public String getPath()
    {
        return path;
    }

    public String getName()
    {
        return name;
    }
}
