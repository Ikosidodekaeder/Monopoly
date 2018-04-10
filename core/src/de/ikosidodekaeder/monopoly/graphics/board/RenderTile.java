package de.ikosidodekaeder.monopoly.graphics.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;
import java.util.List;

import de.ikosidodekaeder.logic.FieldTypes.Street;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.monopoly.graphics.elements.ElementContainer;
import de.ikosidodekaeder.monopoly.graphics.elements.UILabel;
import de.ikosidodekaeder.monopoly.graphics.elements.UiElement;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;

/**
 * Created by Sven on 09.04.2018.
 */

public class RenderTile extends ElementContainer {

    public Field field;

    private List<UiImage> buildings = new ArrayList<>();

    public RenderTile(float x, float y, float width, float height, String path, Color color, String name, int lane) {
        super(x, y, width, height);

        Image image = new Image(setColor(path, color));

        setWidth(image.getWidth());
        setHeight(image.getHeight());

        UiImage uiImage = new UiImage(x, y, image);
        children.add(uiImage);

        if (name != null) {
            if (name.contains("%N")) {
                name = name.replace("%N", "\n");
            }
            UILabel label = new UILabel(x, y+image.getHeight(), 0, 0, 17, name, Color.DARK_GRAY, Color.GRAY);
            label.setDisplayX(x + image.getWidth()/2 - label.getWidth()/2);
            label.setDisplayY(y + image.getHeight()/2 - label.getHeight()/2);
            children.add(label);
        }
    }

    public Texture setColor(String path, Color color) {
        Texture texture = new Texture(Gdx.files.internal(path));

        if (!texture.getTextureData().isPrepared()) {
            texture.getTextureData().prepare();
        }
        Pixmap pixmap = texture.getTextureData().consumePixmap();


        for (int x=0; x<getWidth(); x++) {
            for (int y=0; y<getHeight(); y++) {

                Color c = new Color(pixmap.getPixel(x, y));
                if (c.r*255 == 255
                        && c.g*255 == 0
                        && c.b*255 == 233) {
                    //spriteBatch.setColor(color);
                    //spriteBatch.draw(texture, x, y);
                    pixmap.setColor(color);
                    pixmap.fillRectangle(x, y, 1, 1);
                    //texture2.draw(pixmap, x, y);
                }
            }
        }

        Texture tex = new Texture(pixmap);
        texture.getTextureData().disposePixmap();
        pixmap.dispose();
        return tex;
    }

    public boolean addHouse() {
        if (!(field instanceof Street)) {
            return false;
        }
        Street street = (Street) field;
        if (street.Hotels > 0) {
            return false;
        }

        street.Houses++;
        if (street.Houses >= 4) {
            street.Houses = 0;
            street.Hotels = 1;
        }

        children.removeAll(buildings);
        buildings.clear();

        float x = getX();
        float y = getY();
        float addX = 0;
        float addY = 0;
        if (getWidth() > getHeight()) {
            y += 5;
            addY = 30;
        } else {
            x += 5;
            addX = 30;
        }

        for (int i=0; i<street.Houses; i++) {
            UiImage image = new UiImage(x, y, 0, 0, "board/house.png");
            buildings.add(image);
            children.add(image);

            x += addX;
            y += addY;
        }
        for (int i=0; i<street.Hotels; i++) {
            UiImage image = new UiImage(x, y, 0, 0, "board/hotel.png");
            buildings.add(image);
            children.add(image);

            x += addX;
            y += addY;
        }

        return true;
    }
}
