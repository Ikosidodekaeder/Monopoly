package de.ikosidodekaeder.eventsystem;


import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import de.ikosidodekaeder.eventsystem.annotations.EventHandler;

public class ListenerProcessor<E,L> {

    /**
     * Stores Listeners
     */
    final Set<GenericListener<E,L>>     concreteHandler = Collections.synchronizedSet(new HashSet<GenericListener<E, L>>());

    public ListenerProcessor(){

    }
    public ListenerProcessor(E event,  L listener){

        for (Method handler : listener.getClass().getDeclaredMethods()) {
            if (handler.isAnnotationPresent(EventHandler.class)) {
                concreteHandler.add(new GenericListener<>(
                        listener,
                        handler,
                        handler.getAnnotation(EventHandler.class)
                ));
            }
        }
    }


    public void remove(GenericListener<E,L> listener){
        concreteHandler.remove(listener);
    }

    public void register(GenericListener<E,L> listener){
        concreteHandler.add(listener);
    }

    public void invoke(E event){
        if(!concreteHandler.isEmpty()) {//.filter(handler -> handler.getEventType() .equals (event))
            for (GenericListener<E, L> handler : concreteHandler) {
                handler.fire(event);
            }
        }
    }
}