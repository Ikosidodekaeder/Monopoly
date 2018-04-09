package de.ikosidodekaeder.util;

/**
 * Created by Johannes Lüke on 09.04.2018.
 */

public interface Delegate {
    public void invoke(Object... args) throws Exception;
}