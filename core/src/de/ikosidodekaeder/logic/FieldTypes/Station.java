package de.ikosidodekaeder.logic.FieldTypes;

import de.ikosidodekaeder.logic.PlayerFigure;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.util.Delegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Johannes Lüke on 08.04.2018.
 *
 * Represents one of four TrainStations on our board. You can
 * buy a station but never build houses on.
 */
public class Station extends Field {

    Delegate Lambda = null;

    public Station(String name,int value,int group,int id_on_map){
        super(name,value,group,id_on_map);
    }

    @Override
    public void setArrivalCallback(Delegate runnable){
        Lambda = runnable;
    }

    @Override
    public void onArrival(Player player){
        if(Lambda != null)
            try {
                Lambda.invoke(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public String toString(){
        return "Station: " + this.Name + " Worth: " + getValue();
    }
}
