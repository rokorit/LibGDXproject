package com.mygdx.game;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

public class SaveManager {
    //5x raf, createNew, load,
    public static float chunkSize = 250f;
    public static int numOfRow = 1;
    public static int numOfCol = 1;

    public void createNew(int a) throws IOException{
        System.out.println("create new used");
        Random rand = new Random();
        Planet[][][] planet = new Planet[numOfCol][numOfRow][2];
        int form0 = 0;
        int form1 = 0;

        int mama = 0;
        for(int col = 0; col < numOfCol; col++){
            for(int row = 0; row < numOfRow;  row++){
                planet[col][row][0] = new Planet();
                planet[col][row][1] = new Planet();
                form0 = rand.nextInt(4);
                planet[col][row][0].setForm(form0);//parametri za 1.
                do{
                    form1 = rand.nextInt(4);
                } while (form1 == form0);
                planet[col][row][1].setForm(form1);//parametri za 2.

                do{
                    do {
                        mama++;

                        planet[col][row][0].setParameters(col, row, setRandomPos(form0), setRandomPos(form0));
                        planet[col][row][1].setParameters(col, row, setRandomPos(form1), setRandomPos(form1));

                    }while (planet[col][row][0].isTouchingPlatform() || planet[col][row][1].isTouchingPlatform());
                } while(planet[col][row][0].isTouching( planet[col][row][1] ));
                System.out.println(mama);
                mama = 0;
            }
        }


        write(planet, a);
    }

    public void write(Planet[][][] planet, int a) throws IOException{ //int form, float posX, float posY
        RandomAccessFile raf = new RandomAccessFile("save" + a + ".raf", "rw");
        raf.seek(0);
        for(int col = 0; col < numOfCol; col++){
            for(int row = 0; row < numOfRow;  row++){
                raf.writeInt(planet[col][row][0].getForm());//prvi planet v chunku
                raf.writeFloat(planet[col][row][0].getX());
                raf.writeFloat(planet[col][row][0].getY());

                raf.writeInt(planet[col][row][1].getForm());//drugi planet v chunku
                raf.writeFloat(planet[col][row][1].getX());
                raf.writeFloat(planet[col][row][1].getY());
            }
        }
        raf.close();
    }

    public Planet[][][] read(int a) throws IOException{
        RandomAccessFile raf = new RandomAccessFile("save" + a + ".raf", "rw");
        raf.seek(0);
        Planet[][][] planet = new Planet[numOfCol][numOfRow][2];

        for(int col = 0; col < numOfCol; col++){
            for(int row = 0; row < numOfRow;  row++){
                planet[col][row][0] = new Planet();
                planet[col][row][1] = new Planet();

                planet[col][row][0].setParameters(raf.readInt(), raf.readFloat(), raf.readFloat());
                planet[col][row][1].setParameters(raf.readInt(), raf.readFloat(), raf.readFloat());
            }
        }

        return planet;
    }

    public float setRandomPos(int form){
        float bounds = 0;
        Random rand = new Random();

        if(form == 0){
            bounds = Planet.planet0rad;
        }else if(form == 1){
            bounds = Planet.planet1rad;
        }else if(form == 2){
            bounds = Planet.planet2rad;
        }else if(form == 3){
            bounds = Planet.planet3rad;
        }

        return rand.nextFloat(chunkSize - 2*bounds) + bounds;
    }

    public boolean checkIfExists(int file){
        boolean exit = true;
        Planet[][][] planet;
        try {
            planet = read(file);
            try{
                planet[0][0][0].getForm();
            } catch (NullPointerException n){
                exit = false;
            }
        } catch (IOException e){
            exit = false;
        }


        return exit;
    }
}
