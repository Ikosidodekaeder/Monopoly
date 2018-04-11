package de.ikosidodekaeder.logic;


import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import de.ikosidodekaeder.logic.interfaces.Field;
import de.ikosidodekaeder.logic.interfaces.Player;
import de.ikosidodekaeder.network.HexaServer;
import de.ikosidodekaeder.util.ClassLoader_Fields;
import de.ikosidodekaeder.util.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.FileHandler;

/**
 * Created by Johannes Lüke on 08.04.2018.
 */

public class Board {

    public List<Field>     actualBoard = new ArrayList<Field>();
    public List<Player>    Players = new ArrayList<Player>();

    PlayerFigure           ClientFigure = PlayerFigure.CAR;

    public PlayerFigure getClientFigure(){
        return ClientFigure;
    }

    void readMap(String filename){

        String currentline;
        //try{
            //File f = new File();
            FileHandle handle = Gdx.files.getFileHandle(filename, Files.FileType.Internal);
            /*BufferedReader reader = new BufferedReader(
                    new FileReader(handle.file())
            );*/
            BufferedReader reader = new BufferedReader(handle.reader());
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

        //}catch (FileNotFoundException e){
           /* e.printStackTrace();
        }*/

    }

    public Board(String Filename){
        readMap(Filename);



    }

    public Pair<Integer,Integer> ThrowDices(){
        return new Pair<>(
                (((byte)(Math.random()*10))%6),
                ((byte)(Math.random()*10))%6
        );
    }

    @Override
    public String toString(){
       StringBuilder builder = new StringBuilder();

       for(int i = 0; i < actualBoard.size(); i++){
           builder.append(actualBoard.get(i)).append("\n");
       }
       return builder.toString();
    }

    public int wrapPos(int Pos){
        if(Pos < actualBoard.size())
            return Pos;
        else {
            Pos -= actualBoard.size();
            return Pos;
        }
    }

    /**
     * Moves the player from one tile to another.
     * Thereby removes and adds a player respectively
     * from a tile to a tile.
     *
     * Triggers the onArrival Method
     *
     * @param figure
     * @param steps
     */
    public void movePlayer(PlayerFigure figure, int steps){
        for( Player p : Players){
            if(p.PlayerID() == figure){

                actualBoard.get(p.getPosition()).removePlayer(p);

                int pos = wrapPos(steps);
                p.setPosition(pos);

                actualBoard.get(pos).addPlayer(p);
                actualBoard.get(pos).onArrival(p);
            }
        }
    }

    public void AddPlayer(Player player){
        Players.add(player);
    }

    public  void removePlayer(PlayerFigure figure) {
        Iterator<Player> iter = Players.iterator();

        while(iter.hasNext())
            if(iter.next().PlayerID() == figure)
            {
                iter.remove();
                break;
            }
    }

    public boolean haveFinishedTurnAllPlayer() {
        Iterator<Player> iter = Players.iterator();
        if(iter.hasNext()){
            if(iter.next().finishedTurn() == false)
                return false;
        }
        return true ;
    }




}
