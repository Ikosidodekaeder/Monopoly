package de.ikosidodekaeder.logic.interfaces;


import de.ikosidodekaeder.logic.PlayerFigure;

import java.util.ArrayList;
import java.util.List;

public abstract class Field {

    protected String        Name     = "Nirvana";
    protected PlayerFigure  Owner    = null;
    protected long          Value    = -1;
    protected int           Position = -1;
    protected int           group    = 0;
    protected List<PlayerFigure>  PlayerOnStreet = new ArrayList<>();

    protected Field(String name,int value,int group,int position){
        this.Name = name;
        this.Value = value;
        this.Position = position;
        this.group = group;
    }


    public List<PlayerFigure> currentlyVisitingPlayers() {
        return null;
    }


    public boolean hasOwner() {
        return false;
    }


    public String Name() {
        return null;
    }

    public int boardID() { return Position;}

    public int group() {
        return group;
    }

    public void changeOwner(Player newOwner) {

    }

    public void addPlayer(Player player) {

    }

    public void removePlayer(Player player) {

    }

    public long getValue(){
        return Value;
    }

}