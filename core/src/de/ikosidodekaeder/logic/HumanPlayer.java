package de.ikosidodekaeder.logic;

import de.ikosidodekaeder.logic.FieldTypes.Street;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Created by z003pksw on 08.04.2018.
 */

public class HumanPlayer implements Player {

    List<Field>     propeties = new ArrayList<Field>();
    PlayerFigure    figure = null;
    String          name;
    int             Money;
    boolean         turnFinished = true;
    int             position = -1;

    public HumanPlayer(PlayerFigure ID, String name){

        this.figure = ID;
        this.name = name;
    }

    @Override
    public String NameOfPlayer() {
        return name;
    }

    @Override
    public PlayerFigure PlayerID() {
        return figure;
    }

    @Override
    public List<Field> OwnedProperties() {
        return propeties;
    }

    @Override
    public int move(int steps) {
        //TODO: This is not correct... but for the start it suffice
        return (position+=steps);
    }

    @Override
    public boolean canBuyProperty(Field street) {
        if(street.hasOwner())
            return false;
        return true;
    }

    @Override
    public void startTurn(){
        this.turnFinished = false;
    }

    @Override
    public void endTurn(){
        this.turnFinished = true;
    }

    @Override
    public boolean finishedTurn() {
        return true;
    }

    @Override
    public void buyProperty(Field street) {
        street.changeOwner(this);
    }

    @Override
    public void sellProperty(Field street) {
        street.removePlayer(this);
    }

    @Override
    public void buyHouse(Field street) {
        if(street instanceof Street)
            ((Street)street).addHotel();
    }

    @Override
    public void sellHouse(Field street) {
        if(street instanceof Street)
            ((Street)street).removeHotel();
    }

    @Override
    public long Balance() {
        return Money;
    }

    @Override
    public long NettoValue() {
        int sum =0;
        Iterator<Field> iter = propeties.iterator();
        while(iter.hasNext())
            sum += iter.next().getValue();
        return sum + Balance();
    }
}
