package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Ship extends Sprite {

    private Vector2 speed;
    private Rect worldBounds;

    public Ship(TextureAtlas gameAtlas) {
        super(gameAtlas.findRegion("main_ship"));
        setHeightProporsion(0.1f);

    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
        setBottom(worldBounds.getBottom());
    }

}
