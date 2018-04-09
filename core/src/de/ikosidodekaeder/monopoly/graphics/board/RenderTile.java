package de.ikosidodekaeder.monopoly.graphics.board;

import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;

/**
 * Created by Sven on 09.04.2018.
 */

public class RenderTile extends UiImage {

    public Field field;

    public RenderTile(float x, float y, float width, float height, String path) {
        super(x, y, width, height, path);
    }
}
