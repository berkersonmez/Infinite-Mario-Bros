package com.mojang.mario.sprites;

import com.mojang.mario.LevelScene;

public class OneUpMushroom extends Mushroom {

    public OneUpMushroom(LevelScene world, int x, int y) {
        super(world, x, y);
        xPic = 2;
    }

    public void collideCheck()
    {
        float xMarioD = world.mario.x - x;
        float yMarioD = world.mario.y - y;
        float w = 16;
        if (xMarioD > -w && xMarioD < w)
        {
            if (yMarioD > -height && yMarioD < world.mario.height)
            {
                Mario.get1Up();
                spriteContext.removeSprite(this);
            }
        }
    }

    public void move()
    {
        if (life<9)
        {
            layer = 0;
            y--;
            life++;
            return;
        }
    }
}
