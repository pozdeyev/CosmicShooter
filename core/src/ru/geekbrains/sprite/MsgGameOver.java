package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.base.Sprite;

public class MsgGameOver extends Sprite {

    //Высота
    private static final float HEIGHT = 0.07f;
    //Растояние
    private static final float BOTTOM_DISTANCE= 0.3f;

    public MsgGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
        setHeightProporsion(HEIGHT);
        setBottom(BOTTOM_DISTANCE);
    }
}
