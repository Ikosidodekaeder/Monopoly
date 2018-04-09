package de.ikosidodekaeder.eventsystem.interfaces;

/**
 * Created by Johannes Lüke on 09.04.2018.
 */

public interface EventFire<E,L> {

    void fire(E event, L listener);

}