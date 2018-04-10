package de.ikosidodekaeder.logic;

import java.util.UUID;

/**
 * Created by z003pksw on 08.04.2018.
 */

public enum PlayerFigure {

    DOG(UUID.fromString("525183d9-1a5a-40e1-a712-e3099282c341")),
    HAT(UUID.fromString("525183d9-1a5a-40e1-a712-e3099282c342")),
    CAR(UUID.fromString("525183d9-1a5a-40e1-a712-e3099282c343")),
    GUITAR(UUID.fromString("525183d9-1a5a-40e1-a712-e3099282c344"));


    UUID id;

    PlayerFigure(UUID uuid) {
    }

}
