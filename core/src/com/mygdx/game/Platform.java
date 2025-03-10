package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Platform extends Actor {
    Texture img = new Texture(Gdx.files.internal("shield.png"));
    TextureRegion shieldWaiting = new TextureRegion(img, 0, 0, img.getWidth()/2, img.getHeight());
    TextureRegion shieldActive = new TextureRegion(img, img.getWidth()/2, 0, img.getWidth()/2, img.getHeight());
    static float radius = 20;
    boolean wait;
    public Platform(float x, float y){
        setX(x);
        setY(y);
    }

    @Override
    public void draw(Batch batch, float alpha){
        if(wait){
            batch.draw(shieldWaiting,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
        }else {
            batch.draw(shieldActive,this.getX()-radius,this.getY()-radius, radius, radius, 2*radius, 2*radius, getScaleX(), getScaleY(), getRotation());
        }

    }

    public boolean isTouching(Player player){
        boolean exit;
        float relativeX = Math.abs(this.getX() - player.getX());
        float relativeY = Math.abs(this.getY() - player.getY());
        if(Math.pow(relativeX, 2) + Math.pow(relativeY, 2) > Math.pow( radius/2, 2 )){
            exit = false;
            wait = true;
        }else{
            exit = true;
            wait = false;
        }
        return exit;
    }

    public float getRadius(){
        return this.radius;
    }
}
