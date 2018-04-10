package de.ikosidodekaeder.monopoly.graphics.board;

import com.badlogic.gdx.graphics.Color;

import de.ikosidodekaeder.logic.Board;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.monopoly.graphics.elements.ElementContainer;

/**
 * Created by Sven on 09.04.2018.
 */

public class RenderBoard extends ElementContainer {

    private static final int HORIZONTAL_LEFT    = 1;
    private static final int HORIZONTAL_RIGHT   = 2;
    private static final int VERTICAL_BOTTOM    = 3;
    private static final int VERTICAL_TOP       = 4;

    private static final Color[] colors = new Color[]{
            Color.BLACK,    // 0: Special
            Color.PURPLE,   // 1
            Color.WHITE,    // 2: Bahnhof
            Color.SKY,      // 3
            Color.PINK,     // 4
            Color.WHITE,    // 5: Wasser/Elektrizitätswerk
            Color.GOLD,     // 6
            Color.RED,      // 7
            Color.YELLOW,   // 8
            new Color(0x1C8C0CFF),    // 9
            new Color(0.1f, 0.1f, 0.8f, 1.0f),  // 10
            Color.WHITE     // 11: Steuer
    };

    public Board board;
    int size;
    int laneLength;
    int firstLane;
    int secondLane;
    int thirdLane;
    int fourthLane;

    private int x = 0, y = 0;

    private int[] hSizes = new int[]{200, 140};
    private int[] specialSizes = new int[]{200, 200};
    private int[] vSizes = new int[]{140, 200};

    public RenderBoard(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    private RenderTile getImageForType(int type, int group, String name) {
        if (type == 0) {
            if (group == 0) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/loos.png", Color.WHITE, null);
            if (group == 1) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/prison.png", Color.WHITE, null);
            if (group == 2) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/parking.png", Color.WHITE, null);
            if (group == 3) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/gotoprison.png", Color.WHITE, null);
        }
        if (type == HORIZONTAL_LEFT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_left.png", colors[group], name);
        }
        if (type == HORIZONTAL_RIGHT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_right.png", colors[group], name);
        }
        if (type == VERTICAL_BOTTOM) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_bottom.png", colors[group], name);
        }
        if (type == VERTICAL_TOP) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_top.png", colors[group], name);
        }

        return new RenderTile(x, y, 0, 0, "board/vertical_top.png", colors[group], name);
    }

    private RenderTile createImageAt(int i, int group, String name) {
        if (i == 0) {
            x = (specialSizes[0] * 2) + (vSizes[0] * (laneLength - 2));
            // start field
            RenderTile image = getImageForType(0, 0, name);
            x -= vSizes[0];
            return image;
        }

        if (i < firstLane) {
            RenderTile image = getImageForType(VERTICAL_BOTTOM, group, name);
            if (i < firstLane-1) {
                x -= vSizes[0];
            }
            return image;
        }
        if (i == firstLane) {
            x -= specialSizes[0];
            RenderTile image = getImageForType(0, 1, name);
            y += specialSizes[1];
            return image;
        }
        if (i < secondLane) {
            RenderTile image = getImageForType(HORIZONTAL_LEFT, group, name);
            y += hSizes[1];
            return image;
        }
        if (i == secondLane) {
            RenderTile image = getImageForType(0, 2, name);
            x += specialSizes[0];
            return image;
        }
        if (i < thirdLane) {
            RenderTile image = getImageForType(VERTICAL_TOP, group, name);
            x += vSizes[0];
            return image;
        }
        if (i == thirdLane) {
            RenderTile image = getImageForType(0, 3, name);
            y -= hSizes[1];
            return image;
        }

        RenderTile image = getImageForType(HORIZONTAL_RIGHT, group, name);
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

            RenderTile tile = createImageAt(i, field.getGroup(), field.getName());
            tile.field = field;

            this.children.add(tile);
        }
    }
}
