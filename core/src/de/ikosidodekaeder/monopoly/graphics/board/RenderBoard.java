package de.ikosidodekaeder.monopoly.graphics.board;

import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import de.ikosidodekaeder.logic.Board;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.monopoly.graphics.elements.ElementContainer;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;

/**
 * Created by Sven on 09.04.2018.
 */

public class RenderBoard extends ElementContainer {

    private static final int HORIZONTAL_LEFT    = 1;
    private static final int HORIZONTAL_RIGHT   = 2;
    private static final int VERTICAL_BOTTOM    = 3;
    private static final int VERTICAL_TOP       = 4;

    public Board board;
    int size;
    int laneLength;
    int firstLane;
    int secondLane;
    int thirdLane;
    int fourthLane;

    private int x = 0, y = 0;

    private int[] hSizes = new int[]{100, 70};
    private int[] specialSizes = new int[]{100, 100};
    private int[] vSizes = new int[]{70, 100};

    public RenderBoard(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    private RenderTile getImageForType(int type) {
        if (type == 0) {
            return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/loos.png", Color.WHITE);
        }
        if (type == HORIZONTAL_LEFT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_left.png", new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()), 1));
        }
        if (type == HORIZONTAL_RIGHT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_right.png", new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()), 1));
        }
        if (type == VERTICAL_BOTTOM) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_bottom.png", new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()), 1));
        }
        if (type == VERTICAL_TOP) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_top.png", new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()), 1));
        }

        return new RenderTile(x, y, 0, 0, "board/vertical_top.png", new Color((float) (Math.random()), (float) (Math.random()), (float) (Math.random()), 1));
    }

    private RenderTile createImageAt(int i) {
        if (i == 0) {
            x = (specialSizes[0] * 2) + (vSizes[0] * (laneLength - 2));
            // start field
            RenderTile image = getImageForType(0);
            x -= vSizes[0];
            return image;
        }

        if (i < firstLane) {
            RenderTile image = getImageForType(VERTICAL_BOTTOM);
            if (i < firstLane-1) {
                x -= vSizes[0];
            }
            return image;
        }
        if (i == firstLane) {
            x -= specialSizes[0];
            RenderTile image = getImageForType(0);
            y += specialSizes[1];
            return image;
        }
        if (i < secondLane) {
            RenderTile image = getImageForType(HORIZONTAL_LEFT);
            y += hSizes[1];
            return image;
        }
        if (i == secondLane) {
            RenderTile image = getImageForType(0);
            x += specialSizes[0];
            return image;
        }
        if (i < thirdLane) {
            RenderTile image = getImageForType(VERTICAL_TOP);
            x += vSizes[0];
            return image;
        }
        if (i == thirdLane) {
            RenderTile image = getImageForType(0);
            y -= hSizes[1];
            return image;
        }

        RenderTile image = getImageForType(HORIZONTAL_RIGHT);
        y -= hSizes[1];
        return image;

    }

    public void createBoard() {
        board = new Board("Map.txt");
        size        = board.actualBoard.size();
        laneLength  = size/4;
        firstLane   = laneLength;
        secondLane  = laneLength*2;
        thirdLane   = laneLength*3;
        fourthLane  = laneLength*4;


        for (int i=0; i<size; i++) {
            Field field = board.actualBoard.get(i);

            RenderTile tile = createImageAt(i);
            tile.field = field;

            this.children.add(tile);
        }
    }
}
