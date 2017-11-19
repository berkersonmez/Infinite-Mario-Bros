package com.mojang.mario.level;

public class FileLevelGenerator {

    public static Level createLevel(String fileName, int type)
    {
        FileLevelGenerator levelGenerator = new FileLevelGenerator(fileName, type);
        return levelGenerator.createLevel();
    }

    private String fileName;
    private int type;

    private FileLevelGenerator(String fileName, int type)
    {
        this.fileName = fileName;
        this.type = type;
    }

    private Level createLevel()
    {

        return null;
    }
}
