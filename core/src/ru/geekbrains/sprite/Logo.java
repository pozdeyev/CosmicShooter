package ru.geekbrains.sprite;



import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float SIZELOGO = 0.35f; //Масштабный коэффицент
    private static final float SPEEDCONST = 0.15f; //Скорость logo

    private Vector2 touch = new Vector2();
    private Vector2 speed = new Vector2();
    private Vector2 bufV = new Vector2();
    private Vector2 buf = new Vector2();
    public Logo(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProporsion(SIZELOGO);
        pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buf.set(touch);
        bufV.set(speed);
        if (buf.sub(pos).len()<bufV.scl(delta).len()) {
            pos.set (touch);
        } else pos.mulAdd (speed,delta);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch=touch;
        speed = touch.cpy().sub(pos); //определяем вектор скорости
        speed.setLength(SPEEDCONST); //задаем длину вектора скорости до заданного значения
        return false;
    }


    private void stop() {
      //  speed.setZero();
    }


}
