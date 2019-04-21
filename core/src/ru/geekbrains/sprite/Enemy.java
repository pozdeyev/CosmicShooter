package ru.geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Enemy extends Sprite {

    private Rect worldBounds;
    private Vector2 speed = new Vector2();
    private int damage;
    private Object owner;

    public Enemy() {
        regions = new TextureRegion[1];
    }

    public void sets(
            Object owner,
            TextureRegion[] region,
            Vector2 speed0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions = region;
        this.speed.set(speed0);
        setHeightProporsion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }


}