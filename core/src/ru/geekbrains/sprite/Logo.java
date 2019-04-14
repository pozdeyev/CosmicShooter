package ru.geekbrains.sprite;



import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.math.Vector2;


import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.base.Sprite;
import ru.geekbrains.math.Rect;

public class Logo extends Sprite {

    private static final float SIZELOGO = 0.35f; //Масштабный коэффицент
    private static final float SPEEDCONST = 0.005f; //Скорость logo
    private Vector2 speed = new Vector2();
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
        if (buf.cpy().sub(pos).len()>SPEEDCONST) {
            pos.add(speed);
        } else pos.set (buf);

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        speed = touch.cpy().sub(pos); //определяем вектор скорости
        speed.setLength(SPEEDCONST); //задаем длину вектора скорости до заданного значения
        buf.set(touch);

        return false;
    }


    private void stop() {
      //  speed.setZero();
    }


}
