package de.ikosidodekaeder.logic.interfaces;

import de.ikosidodekaeder.util.Delegate;

/**
 * Created by z003pksw on 10.04.2018.
 */

public interface OnArrival {

    void onArrival(Player player);

    void setArrivalCallback(Delegate lambda);
}
