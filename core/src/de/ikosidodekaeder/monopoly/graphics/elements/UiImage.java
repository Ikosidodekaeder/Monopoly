package de.ikosidodekaeder.monopoly.graphics.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * Created by Sven on 20.12.2017.
 */

public class UiImage extends UiElement {

    private Image image;

    public UiImage(float x, float y, float width, float height, String path) {
        super(x, y, width, height);

        image = new Image(new Texture(Gdx.files.internal(path)));
        Color color = image.getColor();
        color.set(color.r, color.g, color.b, 0.75f);
        setDisplayX(x);
        setDisplayY(y);
    }


    @Override
    public void addToStage(Stage stage) {
        stage.addActor(image);
    }

    @Override
    public void removeFromStage(Stage stage) {
        stage.getActors().removeValue(this.image, false);
    }

    @Override
    public void show(Stage stage) {
        image.setVisible(true);
    }

    @Override
    public void hide(Stage stage) {
        image.setVisible(false);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setDisplayX(float x) {
        image.setX(x);
    }

    public void setDisplayY(float y) {
        image.setY(y);
    }

    public void addClickListener(ClickListener listener) {
        image.addListener(listener);
    }
}
