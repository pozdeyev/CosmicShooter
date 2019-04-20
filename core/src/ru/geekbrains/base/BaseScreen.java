package ru.geekbrains.base;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.math.Rect;
import ru.geekbrains.math.MatrixUtils;
//import ru.geekbrains.sprite.Logo;

public abstract class BaseScreen implements Screen, InputProcessor {


    protected SpriteBatch batch;

    protected Rect worldBounds; //рабочая система координат
    private Rect screenBounds;  //в пикселях
    private Rect glBounds; //система координать gl

    private Matrix4 worldtoGl;//матрица для батчера
    private Matrix3 screentoWorld; //матрица для событий
    private Vector2 touch;

    @Override
    public void show() {
        System.out.println("show");
        Gdx.input.setInputProcessor(this);
        batch=new SpriteBatch();
        worldBounds = new Rect();
        screenBounds = new Rect();
        glBounds = new Rect (0,0,1f,1f);
        worldtoGl=new Matrix4();
        screentoWorld=new Matrix3();
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.8f,0.8f,0.8f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height = " + height);
        screenBounds.setSize(width,height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        float aspect = width/(float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f*aspect);
        MatrixUtils.calcTransitionMatrix(worldtoGl,worldBounds,glBounds);
        batch.setProjectionMatrix(worldtoGl);
        MatrixUtils.calcTransitionMatrix(screentoWorld, screenBounds,worldBounds);
        resize(worldBounds);
    }


    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }
    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped char = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY = " +
                screenY+ " pointer = " + pointer +" button = " + button);
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screentoWorld);
        touchDown(touch, pointer);
        return false;
    }


    public boolean touchDown(Vector2 touch, int pointer) {
        System.out.println("touchDown touchX = " + touch.x + " touchY = " +
                touch.y);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY = " +
                screenY+ " pointer = " + pointer +" button = " + button);
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screentoWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println("touchUp touchX = " + touch.x + " touchY = " +
                touch.y);
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY = " +
                screenY+ " pointer = " + pointer);
        touch.set(screenX,screenBounds.getHeight()-screenY).mul(screentoWorld);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touchX = " + touch.x + " touchY = " +
                touch.y);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
