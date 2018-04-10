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
 * Represent any regular street on the board.
 */
public class Street extends Field {

    int Hotels = 0;
    Delegate Lambda = null;

    public Street(String name,int value,int group,int id_on_map){
        super(name,value,group,id_on_map);
    }

    @Override
    public void onArrival(Player player)  {
        if(Lambda != null)
            try {
                Lambda.invoke(player);
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void setArrivalCallback(Delegate runnable){
        Lambda = runnable;
    }

    @Override
    public String toString(){
        return "Street: " + this.Name + " Worth: " + getValue();
    }

    public int NumberOfHotels(){
        return Hotels;
    }

    public void addHotel(){
        this.Hotels++;
    }

    public void removeHotel(){
        this.Hotels--;
    }
}
