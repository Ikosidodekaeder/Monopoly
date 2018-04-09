package de.ikosidodekaeder.monopoly.graphics.elements;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sven on 09.04.2018.
 */

public class ElementContainer extends UiElement {

    public List<UiElement> children = new ArrayList<>();

    public ElementContainer(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void addToStage(Stage stage) {
        for (UiElement child : children) {
            child.addToStage(stage);
        }
    }

    @Override
    public void removeFromStage(Stage stage) {
        for (UiElement child : children) {
            child.removeFromStage(stage);
        }
    }

    @Override
    public void show(Stage stage) {
        for (UiElement child : children) {
            child.show(stage);
        }
    }

    @Override
    public void hide(Stage stage) {
        for (UiElement child : children) {
            child.hide(stage);
        }
    }
}
