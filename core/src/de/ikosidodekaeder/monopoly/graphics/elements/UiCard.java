package de.ikosidodekaeder.monopoly.graphics.elements;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import de.ikosidodekaeder.monopoly.graphics.ClickEvent;

/**
 * Created by Sven on 11.04.2018.
 */

public class UiCard extends UiImage {

    private UiButton button;

    public UiCard(float x, float y, float width, float height, String text, int fontSize) {
        super(x, y, width, height, "board/empty.png");
        button = new UiButton("Straße\nkaufen", image.getX(), 60, 0, 0, fontSize);
        button.setDisplayX(button.getX() + image.getWidth()/2 - button.getWidth()/2);
        button.getTextButton().getStyle().fontColor = Color.BLACK;
    }

    public UiButton getButton() {
        return button;
    }

    public void setButton(UiButton button) {
        this.button = button;
    }

    @Override
    public void addToStage(Stage stage) {
        super.addToStage(stage);
        button.addToStage(stage);
    }

    @Override
    public void removeFromStage(Stage stage) {
        super.removeFromStage(stage);
        button.removeFromStage(stage);
    }

    @Override
    public void addClickEvent(final ClickEvent e) {
        super.addClickEvent(e);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                e.click();
            }
        });
    }
}
