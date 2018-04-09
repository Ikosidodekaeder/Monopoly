package de.ikosidodekaeder.logic.FieldTypes;

import de.ikosidodekaeder.logic.PlayerFigure;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Lüke on 08.04.2018.
 *
 * Represents 4 different kind of fields on the board.
 * The first two kinds are the event fields. when ever
 * on player stops on a field of this kind an randomly chosen
 * event is selected and executed for the player.
 *
 * The JailField is the next special field. when ever a player
 * recievies an event that sends him/her to prison the player is
 * sent here.
 *
 * The StartField when ever a player crosses this field he/she will
 * receive funds.
 */

public class SpecialEvent extends Field {

    Runnable    Lambda = null;

    public SpecialEvent(String name,int value,int group,int id_on_map){
        super(name,value,group,id_on_map);
    }

    @Override
    public String toString(){
        return "SpecialEvent: " + this.Name ;
    }

    void setLambda(Runnable runnable){
        Lambda = runnable;
    }

    void onArrival() {
        if(Lambda != null)
            Lambda.run();
    }


}
