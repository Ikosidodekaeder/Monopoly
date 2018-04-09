package de.ikosidodekaeder.eventsystem.annotations;

/**
 * Created by Johannes Lüke on 09.04.2018.
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.ikosidodekaeder.eventsystem.EventPriority;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler{
    EventPriority priority() default EventPriority.NORMAL;
}