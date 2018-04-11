package de.ikosidodekaeder.logic;

import de.ikosidodekaeder.logic.FieldTypes.Street;
import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.logic.interfaces.UpdateData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by z003pksw on 08.04.2018.
 */

public class HumanPlayer implements Player {

    List<Field>     propeties = new ArrayList<>();
    PlayerFigure    figure = null;
    String          name;
    int             Money;
    boolean         turnFinished = true;
    int             position = -1;


    @Override
    public String toString(){

        StringBuilder builder = new StringBuilder();

        builder
                .append(figure)
                .append(";")
                .append(name)
                .append(";")
                .append(position)
                .append(";")
                .append(Money)
                .append(";")
                .append(turnFinished)
                .append(";");

        for(Field f : propeties){
            builder.append(f.toString())
                    .append(",");
        }

        return builder.toString();
    }


    @Override
    public UpdateData UpdateAttributes(String data){
        String[] input = data.split(";");
        if(input.length < 4)
            throw new IllegalArgumentException("Data Argument has not enough data inside. (needs at least 4 different items)");

        this.figure = PlayerFigure.valueOf(input[0]);
        this.name = input[1];
        this.position = Integer.parseInt(input[1]);
        this.Money = Integer.parseInt(input[2]);
        this.turnFinished = Boolean.parseBoolean(input[3]);

        String[] fields = input[4].split(",");

        for (int i = 0; i < fields.length; i++){
            this.propeties.get(i).UpdateAttributes(fields[i]);
        }



        return this;

    }

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
    public int setPosition(int position) {
        //TODO: This is not correct... but for the start it suffice
        return (this.position=position);
    }
    @Override
    public int getPosition() {
        //TODO: This is not correct... but for the start it suffice
        return (this.position);
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
    public void PayMoney(long amount) {
        Money += amount;
    }

    @Override
    public void ReceiveMoney(long amount) {
        Money -= amount;
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
