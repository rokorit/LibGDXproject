package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {

    Texture img0 = new Texture("space_ship.png");
    TextureRegion player = new TextureRegion(img0, 0, 0, img0.getWidth()/2, img0.getHeight());
    public static float playerWidth = 3f;
    float playerHeight = 6f* img0.getHeight()/img0.getWidth();

    TextureRegion explosion0 = new TextureRegion(img0, img0.getWidth()/2, 0, img0.getWidth()/4, img0.getHeight()/2);
    TextureRegion explosion1 = new TextureRegion(img0, img0.getWidth()/2, img0.getHeight()/2, img0.getWidth()/2, img0.getHeight()/2);

    Texture img1 = new Texture("space_ship_thrust.png");
    TextureRegion thrust = new TextureRegion(img1, img1.getWidth(), img1.getHeight());
    public float thrustWidth = 0.9f;
    public float thrustHeight = 0.25f * img1.getHeight()/img1.getWidth();

    public float speed = 0;
    public float maxSpeed = 82.5f;
    float previousSpeed = 0;


    int form = 0;

    boolean update = true;

    @Override
    public void draw(Batch batch, float alpha){
        if(form == 0){
            thrustHeight = 1f*img1.getHeight()/img1.getWidth()*(3*speed/4/maxSpeed + 0.25f);

            batch.draw(player, getX()-playerWidth/2, getY()-playerHeight/3, playerWidth/2, playerHeight/3,
                    playerWidth, playerHeight, getScaleX(), getScaleY(), getRotation());
            if(speed > 0){
                batch.draw(thrust, getX()-thrustWidth/2, getY() - playerHeight/3 - thrustHeight, thrustWidth/2f, playerHeight/3 + thrustHeight,
                        thrustWidth, thrustHeight, 1f, 1f, getRotation());
            }
        } else if (form == 1) {
            batch.draw(explosion0, getX()-playerWidth/2, getY()-playerHeight/3, playerWidth/2, playerHeight/3,
                    playerWidth, playerHeight, getScaleX(), getScaleY(), getRotation());
            form = 2;
        } else if (form == 2) {
            batch.draw(explosion1, getX()-1.5f*playerHeight, getY()-1.5f*playerHeight, 3*playerHeight/2, 3*playerHeight/3,
                    3*playerHeight, 3*playerHeight, getScaleX(), getScaleY(), getRotation());
        }

    }
    public void move() {
        float x = getX();
        float y = getY();
        x = x - (float) Math.sin(Math.toRadians(getRotation())) * speed * Gdx.graphics.getDeltaTime();
        y = y + (float) Math.cos(Math.toRadians(getRotation())) * speed * Gdx.graphics.getDeltaTime();
        setX(x);
        setY(y);
    }
    public void accelerate(){
        if(speed < maxSpeed){
            speed = speed + 2;
        }
        if(speed > maxSpeed){
            speed = maxSpeed;
        }
    }
    public void decelerate(){
        if(speed > 0){
            speed = speed - 0.5f;
        }
        if(speed < 0){
            speed = 0;
        }
    }
    public float turnRight(){
        float rotateBy;
        rotateBy = (float)Math.sqrt(speed/13);
        setRotation(getRotation() - rotateBy);
        if(getRotation() < 0){
            setRotation(360 + getRotation());
        }
        return rotateBy;//za kamero
    }
    public float turnLeft(){
        float rotateBy;
        rotateBy = -(float)Math.sqrt(speed/13);
        setRotation(getRotation() - rotateBy);
        if(getRotation()>= 360){
            setRotation(getRotation() - 360);
        }
        return rotateBy;
    }

    public void stopUpdate(){
        previousSpeed = speed;
        speed = 0;
        this.update = false;
    }
    public void restorePreviusSpeed(){
        speed = previousSpeed;
    }

    public int getColumn(){
        if(this.getX() > SaveManager.numOfCol * SaveManager.chunkSize - SaveManager.chunkSize/4){
            setX(SaveManager.numOfCol * SaveManager.chunkSize - SaveManager.chunkSize/4);
        }
        if(this.getX() < SaveManager.chunkSize/4){
            setX(SaveManager.chunkSize/4);
        }
        int column = (int)this.getX() / (int)SaveManager.chunkSize;
        return column;
    }

    public int getRow(){
        if(this.getY() > SaveManager.numOfRow * SaveManager.chunkSize - SaveManager.chunkSize/4){
            setY(SaveManager.numOfRow * SaveManager.chunkSize - SaveManager.chunkSize/4);
        }
        if(this.getY() < SaveManager.chunkSize/4){
            setY(SaveManager.chunkSize/4);
        }
        int row = (int)this.getY() / (int)SaveManager.chunkSize;
        return row;
    }


    public boolean getUpdateStatus(){
        return this.update;
    }

    public void explode(){
        if(form < 1){
            form = 1;
        }
        stopUpdate();
    }
}