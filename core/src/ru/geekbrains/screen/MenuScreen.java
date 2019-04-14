package ru.geekbrains.screen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.geekbrains.base.BaseScreen;
import ru.geekbrains.math.Rect;
import ru.geekbrains.sprite.Background;
import ru.geekbrains.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture bg;
    private Texture lg;
    private Background background;
    private Logo logo; //

    @Override
    public void show() {
        super.show();
        lg = new Texture("badlogic.jpg"); //инициализируем lg
        bg = new Texture("nebulabg.jpg");
        background = new Background(new TextureRegion(bg));
        logo = new Logo (new TextureRegion(lg)); //создаем экземпляр Logo
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
      // super.render(delta);
        update(delta);


        batch.begin();
        background.draw(batch); //батчер фона
        logo.draw(batch); //батчер лого
        batch.end();
    }

    public void update(float delta){
    logo.update(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
        bg.dispose();
        lg.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        logo.touchDown(touch, pointer); //передаем координаты в Logo
        return false;
    }

}
