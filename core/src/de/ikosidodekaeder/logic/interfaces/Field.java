package de.ikosidodekaeder.logic.interfaces;


import de.ikosidodekaeder.logic.PlayerFigure;

import java.util.ArrayList;
import java.util.List;

public abstract class Field implements OnArrival {

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


    public String getName() {
        return Name;
    }

    public int boardID() { return Position;}

    public int getGroup() {
        return group;
    }

    public void changeOwner(Player newOwner) {
        Owner = newOwner.PlayerID();
    }

    public void addPlayer(Player player) {
        PlayerOnStreet.add(player.PlayerID());
    }

    public void removePlayer(Player player) {
        PlayerOnStreet.remove(player.PlayerID());
    }

    public long getValue(){
        return Value;
    }

    public long PropertyRent(){
        return (long) (Value * 0.7);
    }

    public PlayerFigure Owner(){
        return Owner;
    }

    public abstract void onArrival(Player player);

}