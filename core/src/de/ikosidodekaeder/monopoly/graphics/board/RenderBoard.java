package de.ikosidodekaeder.monopoly.graphics.board;

import com.badlogic.gdx.Gdx;
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

    public static final int HORIZONTAL_LEFT    = 1;
    public static final int HORIZONTAL_RIGHT   = 2;
    public static final int VERTICAL_BOTTOM    = 3;
    public static final int VERTICAL_TOP       = 4;

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
            if (group == 0) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/loos.png", Color.WHITE, null, -1);
            if (group == 1) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/prison.png", Color.WHITE, null, -1);
            if (group == 2) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/parking.png", Color.WHITE, null, -1);
            if (group == 3) return new RenderTile(x, y, specialSizes[0], specialSizes[1], "board/gotoprison.png", Color.WHITE, null, -1);
        }

        String path = "street";
        String direction = "";
        Color color = colors[group];

        if (group == 1) {
            // Ereignis oder Gemeinschaftskarte
            if (name.equals("Ereigniskarte")) {
                color = Color.RED;
                path = "questionmark";
            } else if (name.equals("Gemeinschaftskarte")) {
                color = Color.BLUE;
                path = "questionmark";
            }
            //return new RenderTile(x, y, hSizes[0], hSizes[1], "board/questionmark.png", color, name, type);
        } else if (group == 2) {
            path = "train";
        }
        if (type == HORIZONTAL_LEFT) {
            direction = "left";
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/" + path + "_" + direction + ".png", color, name, type);
        }
        if (type == HORIZONTAL_RIGHT) {
            direction = "right";
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/" + path + "_" + direction + ".png", color, name, type);
        }
        if (type == VERTICAL_BOTTOM) {
            direction = "bottom";
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/" + path + "_" + direction + ".png", color, name, type);
        }
        if (type == VERTICAL_TOP) {
            direction = "top";
            return new RenderTile(x, y, hSizes[0], hSizes[1], "board/" + path + "_" + direction + ".png", color, name, type);
        }

        return new RenderTile(x, y, 0, 0, "board/street_top.png", colors[group], name, type);
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

            final RenderTile tile = createImageAt(i, field.getGroup(), field.getName());
            tile.field = field;
            for (int j=0; j<(int) (Math.random()*8); j++) {
                tile.addHouse();
            }

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
