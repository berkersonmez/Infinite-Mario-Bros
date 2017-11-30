package com.mojang.mario.level;

import java.io.*;
import java.util.Random;

public class FileLevelGenerator {

    public static Level createLevel(String fileName, int type)
    {
        FileLevelGenerator levelGenerator = new FileLevelGenerator(fileName, type);
        return levelGenerator.createLevel();
    }

    private String fileName;
    private int type;
    private int width;
    private int height;
    private Level level;

    private FileLevelGenerator(String fileName, int type)
    {
        this.fileName = fileName;
        this.type = type;
    }

    private Level createLevel()
    {
        try {
            this.width = countRows();
            this.height = countLines();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.level = new Level(this.width, this.height);

        level.xExit = this.width;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(this.fileName)))) {
            String line;
            int y = 0;
            while ((line = br.readLine()) != null) {
                int x = 0;
                for (char ch: line.toCharArray()) {
                    placeBlock(ch, x, y);
                    x++;
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return level;
    }

    private void placeBlock(char ch, int x, int y)
    {
        if (ch == 'X') { // Floor
            level.setBlock(x, y, (byte) (1 + 8 * 16));
        } else if (ch == 'Q') { // Coin Box
            level.setBlock(x, y, (byte) (4 + 1 + 1 * 16));
        } else if (ch == '?') { // Power up
            level.setBlock(x, y, (byte) (4 + 2 + 1 * 16));
        } else if (ch == 'S') { // Brick
            level.setBlock(x, y, (byte) (0 + 1 * 16));
        } else if (ch == '<') { // Pipe top left
            level.setBlock(x, y, (byte) (10 + 0 * 16));
        } else if (ch == '>') { // Pipe top right
            level.setBlock(x, y, (byte) (11 + 0 * 16));
        } else if (ch == '[') { // Pipe body left
            level.setBlock(x, y, (byte) (10 + 1 * 16));
        } else if (ch == ']') { // Pipe body right
            level.setBlock(x, y, (byte) (11 + 1 * 16));
        } else if (ch == 'B') { // Pipe body right
            level.setBlock(x, y, (byte) (14 + 1 * 16));
        } else if (ch == 'b') { // Pipe body right
            level.setBlock(x, y, (byte) (14 + 0 * 16));
        } else if (ch == 'E') { // Enemy
            // TODO: Need to annotate enemy types to get rid of random selection.
            Random random = new Random();
            int type = random.nextInt(4);
            level.setSpriteTemplate(x, y, new SpriteTemplate(type, false));
        } else if (ch == 'o') { // Coin
            level.setBlock(x, y, (byte) (0 + 2 * 16));
        }
    }

    private int countLines() throws IOException {
        try (InputStream is = new BufferedInputStream(getClass().getResourceAsStream(this.fileName))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean empty = true;
            while ((readChars = is.read(c)) != -1) {
                empty = false;
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++count;
                    }
                }
            }
            return (count == 0 && !empty) ? 1 : count;
        }
    }

    private int countRows() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(this.fileName)))) {
            String line;
            line = br.readLine();
            return line.length();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
