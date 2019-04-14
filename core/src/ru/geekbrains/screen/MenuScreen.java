package ru.geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float SPEEDCONST = 2;
    private static final float SIZEFACTOR = 0.5f;
//    private static final float TOLERANCE = 1;
    private Vector2 touch;
    private Vector2 position;
    private Vector2 buf;
    private Vector2 speed;
    private Texture img;
    private float sizeX;
    private float sizeY;

    @Override
    public void show() {
        super.show();
        touch = new Vector2();    //позиция мыши
        position = new Vector2(5, 5); //позиция
        speed = new Vector2(); //вектор скорости
        img = new Texture("badlogic.jpg");
        buf = new Vector2();
        sizeX = img.getWidth() * SIZEFACTOR;
        sizeY = img.getHeight() * SIZEFACTOR;

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        buf.set (touch);

        if (buf.sub(position).len()>SPEEDCONST) {
            position.add(speed);
        } else position.set (touch);

        batch.begin();
        batch.draw(img, position.x, position.y, sizeX, sizeY);
        batch.end();

        //Остановка по достижении границ экрана
//        if ((position.y >= Gdx.graphics.getHeight() - sizeY) || (position.y <= 0) ||
//                (position.x >= Gdx.graphics.getWidth() - sizeX) || (position.x <= 0)) {
//            speed.setZero();
//        }
        //Остановка по достижении координат
//        if ((Math.abs(touch.y - position.y) < TOLERANCE) &&
//                (Math.abs(touch.x - position.x) <TOLERANCE)) speed.setZero();
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        System.out.println("touch.x = " + touch.x + " touch.y = " + touch.y);
        speed = touch.cpy().sub(position); //определяем вектор скорости
        speed.setLength(SPEEDCONST); //задаем длину вектора скорости до заданного значения
        return false;
    }
}
