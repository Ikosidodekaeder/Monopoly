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
import java.util.List;

/**
 * Created by Johannes Lüke on 08.04.2018.
 */

public class Board {

    List<Field>     actualBoard = new ArrayList<Field>();
    List<Player>    Players = new ArrayList<Player>();

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
        //TODO: URL must be relative, for debug purposes its absolute
        readMap("C:\\Users\\z003PKSW\\Source\\Repos\\Monopoly\\android\\assets\\Map.txt");
    }



    boolean finishedTurnAllPlayer(){
        return false ;//Players.stream().allMatch(p -> p.finishedTurn());
    }




}
