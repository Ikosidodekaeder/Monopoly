package de.ikosidodekaeder.logic;

import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.util.ClassLoader_Fields;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Johannes Lüke on 08.04.2018.
 */

public class Board {

    public List<Field>     actualBoard = new ArrayList<Field>();
    public List<Player>    Players = new ArrayList<Player>();

    void readMap(String filename){

        String currentline = new String();
        try{
            BufferedReader reader = new BufferedReader(
                    new FileReader(filename)
            );
            try{
                //skip first line
                reader.readLine();
                while((currentline = reader.readLine()) != null){
                    if(currentline.length() == 0
                            || currentline.charAt(0) == '#')
                        continue;

                    String[] FieldData = currentline.split(";");

                    this.actualBoard.add(
                            (Field) ClassLoader_Fields.InstanceFromMeta(
                                    FieldData[0],
                                    new Class<?>[]{
                                            String.class,
                                            int.class,
                                            int.class,
                                            int.class
                                    },
                                    FieldData[1],
                                    Integer.parseInt(FieldData[2]),
                                    Integer.parseInt(FieldData[3]),
                                    this.actualBoard.size()
                            )
                    );

                }
            }catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                System.err.println();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public Board(String Filename){
        readMap(Filename);
    }

    @Override
    public String toString(){
       StringBuilder builder = new StringBuilder();

       for(int i = 0; i < actualBoard.size(); i++){
           builder.append(actualBoard.get(i)).append("\n");
       }
       return builder.toString();
    }

    void AddPlayer(Player player){
        Players.add(player);
    }

    void removePlayer(PlayerFigure figure) {
        Iterator<Player> iter = Players.iterator();

        while(iter.hasNext())
            if(iter.next().PlayerID() == figure)
            {
                iter.remove();
                break;
            }
    }

    boolean finishedTurnAllPlayer() {
        Iterator<Player> iter = Players.iterator();
        if(iter.hasNext()){
            if(iter.next().finishedTurn() == false)
                return false;
        }
        return true ;
    }




}
