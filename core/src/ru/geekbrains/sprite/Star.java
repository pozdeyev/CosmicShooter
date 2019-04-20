package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;
import ru.geekbrains.math.Rnd;

public class Star extends Sprite {

    private Vector2 speed;
    private Rect worldBounds;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star_superwhite"));
        float vx = Rnd.nextFloat(-0.005f,0.005f);
        float vy= Rnd.nextFloat(-0.5f,-0.1f);
        speed = new Vector2(vx,vy);
        setHeightProporsion(Rnd.nextFloat(0.001f,0.01f));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds=worldBounds;
        float posX = Rnd.nextFloat (worldBounds.getLeft(),worldBounds.getRight());
        float posY = Rnd.nextFloat (worldBounds.getBottom(), worldBounds.getTop());
        this.pos.set(posX,posY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speed,delta);
        if (getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom()>worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}
