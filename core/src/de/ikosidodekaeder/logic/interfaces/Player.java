package de.ikosidodekaeder.logic.interfaces;


import de.ikosidodekaeder.logic.PlayerFigure;

import java.util.List;

public interface Player extends UpdateData {

    String NameOfPlayer();
    PlayerFigure PlayerID();

    List<Field> OwnedProperties();

    /**
     *
     */
    void startTurn();

    /**
     *
     */
    void endTurn();

    /**
     * Moves The player around the board.
     * returns the position on the board after it has been moved
     * @param pos
     * @return
     */
    int setPosition(int pos);

    /**
     * Moves The player around the board.
     * returns the position on the board after it has been moved
     *
     * @return
     */
    int getPosition();

    /**
     * Check if a Player can buy a Field.
     * Field must not be owned by someone else
     * @param street
     * @return
     */
    boolean canBuyProperty(Field street);

    /**
     * Returns if the Player has finished his turn or not
     * @return
     */
    boolean finishedTurn();

    /**
     * Changes the Owner of a Field
     */
    void buyProperty(Field street);

    /**
     * Removes Ownership from the Field
     * @param street
     */
    void sellProperty(Field street);

    /**
     * Changes the amount of Hotels on a Street
     * @param street
     */
    void buyHouse(Field street);

    /**
     * Changes the amount of Hotels on a Street
     * @param street
     */
    void sellHouse(Field street);


    void PayMoney(long amount);

    void ReceiveMoney(long amount);

    /**
     * Returns the player's "liquid capital"
     * @return
     */
    long Balance();

    /**
     * returns the player's totatl worth containing liquid capital
     * as well as non-liquid captial such as streets and houses
     * @return
     */
    long NettoValue();



}

