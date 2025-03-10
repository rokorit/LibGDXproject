package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Planet extends Actor {
    public static float planet0rad = 75f;
    public static float planet1rad = 62.5f;
    public static float planet2rad = 50f;
    public static float planet3rad = 37.5f;

    Texture img = new Texture(Gdx.files.internal("planet.png"));

    TextureRegion planet0 = new TextureRegion(img, 2*img.getWidth()/4, 0, img.getWidth()/4, img.getHeight()); //zemlja
    TextureRegion planet1 = new TextureRegion(img, 0, 0, img.getWidth()/4, img.getHeight()); //plinasti
    TextureRegion planet2 = new TextureRegion(img, img.getWidth()/4, 0, img.getWidth()/4, img.getHeight());  //lava
    TextureRegion planet3 = new TextureRegion(img, 3*img.getWidth()/4, 0, img.getWidth()/4, img.getHeight());  //luna

    int form; // pomenbn
    float radius;


    @Override
    public void draw(Batch batch, float alpha){
        if(form == 0){
            batch.draw(planet0,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
            setRotation(getRotation() + 0.020f);
        } else if(form == 1){
            batch.draw(planet1,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
            setRotation(getRotation() + 0.025f);
        } else if (form == 2){
            batch.draw(planet2,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
            setRotation(getRotation() + 0.030f);
        }else if (form == 3){
            batch.draw(planet3,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
            setRotation(getRotation() + 0.035f);
        }
        this.setVisible(false);

    }

    public void setParameters(int column, int row, float x, float y) {//za create new
        setX(x + column * SaveManager.chunkSize);
        setY(y + row * SaveManager.chunkSize);

    }
    public void setParameters(int form, float x, float y){//za read
        setX(x);
        setY(y);
        setForm(form);

    }

    public void setForm(int form){
        this.form = form;
        if(form == 0){
            radius = planet0rad;
        }else if(form == 1){
            radius = planet1rad;
        }else if(form == 2){
            radius = planet2rad;
        }else if(form == 3){
            radius = planet3rad;
        }

    }

    public void setRadius(float radius){
        this.radius = radius;
    }
    public float getRadius(){
        return radius;
    }

    //za write
    public int getForm(){
        return form;
    }

    public boolean isTouching(Planet planet){

        boolean exit;
        float relativeX = Math.abs(this.getX() - planet.getX());
        float relativeY = Math.abs(this.getY() - planet.getY());
        if(Math.pow(relativeX, 2) + Math.pow(relativeY, 2) > Math.pow(this.getRadius() + planet.getRadius() + 2*Player.playerWidth, 2)){
            exit = false;
        }else{
            exit = true;
        }
        return exit;
    }

    public boolean isTouchingPlatform(){
        if(Math.pow( Math.abs(this.getX() - SaveManager.chunkSize/4) , 2) + Math.pow(Math.abs(this.getY() - SaveManager.chunkSize/4), 2) < Math.pow(this.getRadius() + Platform.radius*2, 2)){
            return true;
        } else if (Math.pow( Math.abs(this.getX() - SaveManager.chunkSize * SaveManager.numOfCol - SaveManager.chunkSize/4) , 2) + Math.pow(Math.abs(this.getY() - SaveManager.chunkSize * SaveManager.numOfCol - SaveManager.chunkSize/4), 2) < Math.pow(this.getRadius() + 2*Platform.radius, 2)) {
            System.out.println("se ne dotika end");
            return true;
        } else {
            return false;
        }
    }

    public int getColumn(){
        if(this.getX() > SaveManager.numOfCol * SaveManager.chunkSize - 0.1){
            setX(SaveManager.numOfCol * SaveManager.chunkSize - 0.1f);
        }
        if(this.getX() < 0){
            setX(0);
        }
        int column = (int)this.getX() / (int)SaveManager.chunkSize;
        return column;
    }

    public int getRow(){
        if(this.getY() > SaveManager.numOfRow * SaveManager.chunkSize - 0.1){
            setY(SaveManager.numOfRow * SaveManager.chunkSize - 0.1f);
        }
        if(this.getY() < 0){
            setY(0);
        }
        int row = (int)this.getY() / (int)SaveManager.chunkSize;
        return row;
    }

    public boolean isTouching(Player player){
        boolean exit;
        float relativeX = Math.abs(this.getX() - player.getX());
        float relativeY = Math.abs(this.getY() - player.getY());
        if(Math.pow(relativeX, 2) + Math.pow(relativeY, 2) > Math.pow( radius + player.getWidth()/2, 2 )){
            exit = false;
        }else{
            exit = true;
        }
        return exit;
    }
}

