package ru.geekbrains;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class CosmicShooter extends ApplicationAdapter {
    SpriteBatch batch;
    Texture background;

    @Override
    public void create() {
        batch = new SpriteBatch();
        background = new Texture("nebulabackground.jpg");

        Vector2 v1=new Vector2();
        Vector2 v2=new Vector2();
        Vector2 v3;
        v1.set(5,5);
        v2.set(2,1);
        v1.add(v2);
        System.out.println("add v.x = "+v1.x+" v.y = "+v1.y);
        v1.set(10,6);
        v2.set(3,11);
        v1.sub(v2);
        v3=v1.cpy().sub(v2);
        System.out.println("sub v.x = "+v1.x+" v.y = "+v1.y);
        System.out.println("sub v.x = "+v3.x+" v.y = "+v3.y);
        System.out.println("v1.len = " + v1.len());
        System.out.println("v3.len = " + v3.len());
        v3.nor();
        System.out.println("sub v.x = "+v3.x+" v.y = "+v3.y);
        System.out.println("v3.len = " + v3.len());

        v1.set(4,5);
        v2.set(5,13);

        v1.nor();
        v2.nor();

        System.out.println(Math.acos((v1.dot(v2))));
    }

    @Override
    public void render() {
        int width = Gdx.app.getGraphics().getWidth();
        int height = Gdx.app.getGraphics().getHeight();
        batch.begin();
        batch.draw(background, 0, 0, width, height);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
    }
}
