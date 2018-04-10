package de.ikosidodekaeder.monopoly.graphics.board;

import com.badlogic.gdx.graphics.Color;

import de.ikosidodekaeder.logic.Board;
import de.ikosidodekaeder.logic.FieldTypes.CommunityEvent;
import de.ikosidodekaeder.logic.FieldTypes.PrisonEvent;
import de.ikosidodekaeder.logic.FieldTypes.SpecialEvent;
import de.ikosidodekaeder.logic.FieldTypes.StartEvent;
import de.ikosidodekaeder.logic.FieldTypes.Street;
import de.ikosidodekaeder.logic.FieldTypes.TaxEvent;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.monopoly.graphics.elements.ElementContainer;
import de.ikosidodekaeder.monopoly.graphics.elements.UiImage;
import de.ikosidodekaeder.util.Delegate;

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

    private int[] hSizes = new int[]{100, 70};
    private int[] specialSizes = new int[]{100, 100};
    private int[] vSizes = new int[]{70, 100};

    public RenderBoard(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    private RenderTile getImageForType(int type, int group) {
        if (type == 0) {
            return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/loos.png", Color.WHITE);
        }
        if (type == HORIZONTAL_LEFT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_left.png", colors[group]);
        }
        if (type == HORIZONTAL_RIGHT) {
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/horizontal_right.png", colors[group]);
        }
        if (type == VERTICAL_BOTTOM) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_bottom.png", colors[group]);
        }
        if (type == VERTICAL_TOP) {
            return new RenderTile(x, y, vSizes[0], vSizes[1], "board/vertical_top.png", colors[group]);
        }

        return new RenderTile(x, y, 0, 0, "board/vertical_top.png", colors[group]);
    }

    private RenderTile createImageAt(int i, int group) {
        if (i == 0) {
            x = (specialSizes[0] * 2) + (vSizes[0] * (laneLength - 2));
            // start field
            RenderTile image = getImageForType(0, 0);
            x -= vSizes[0];
            return image;
        }

        if (i < firstLane) {
            RenderTile image = getImageForType(VERTICAL_BOTTOM, group);
            if (i < firstLane-1) {
                x -= vSizes[0];
            }
            return image;
        }
        if (i == firstLane) {
            x -= specialSizes[0];
            RenderTile image = getImageForType(0, 0);
            y += specialSizes[1];
            return image;
        }
        if (i < secondLane) {
            RenderTile image = getImageForType(HORIZONTAL_LEFT, group);
            y += hSizes[1];
            return image;
        }
        if (i == secondLane) {
            RenderTile image = getImageForType(0, 0);
            x += specialSizes[0];
            return image;
        }
        if (i < thirdLane) {
            RenderTile image = getImageForType(VERTICAL_TOP, group);
            x += vSizes[0];
            return image;
        }
        if (i == thirdLane) {
            RenderTile image = getImageForType(0, 0);
            y -= hSizes[1];
            return image;
        }

        RenderTile image = getImageForType(HORIZONTAL_RIGHT, group);
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

            //final RenderTile tile = createImageAt(i);
            final RenderTile tile = createImageAt(i, field.getGroup());
            tile.field = field;
            tile.field.setArrivalCallback(new Delegate() {
                @Override
                public void invoke(Object... args) throws Exception {
                    if(args.length == 0)
                        throw new IllegalArgumentException("You must provide at least one Argument as parameter");
                    if(!(args[0] instanceof Player))
                        throw new IllegalArgumentException("First Parameter must be of type Player");

                    Player p = (Player) args[0];

                    if(tile.field.hasOwner()){
                        for(Player player : board.Players)
                        {
                           if(tile.field instanceof Street){
                               Street street = (Street)tile.field;
                               player.ReceiveMoney(street.NumberOfHotels() * 300);
                               p.PayMoney(street.NumberOfHotels() * 300);
                               break;
                           }else if(tile.field instanceof SpecialEvent){
                                if(tile.field instanceof StartEvent){
                                    player.ReceiveMoney(tile.field.getValue());
                                }
                                if(tile.field instanceof TaxEvent){
                                    player.PayMoney(tile.field.getValue());
                                }
                                if(tile.field instanceof CommunityEvent){
                                    //SPECIAL
                                }
                                if(tile.field instanceof PrisonEvent){
                                    player.setPosition(10);
                                }
                           }
                            else if(player.PlayerID() == tile.field.Owner()){
                                player.ReceiveMoney(tile.field.PropertyRent());
                                p.PayMoney(tile.field.PropertyRent());
                                break;
                            }
                        }
                    }
                }
            });

            this.children.add(tile);
        }
    }
}
