package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    Texture img0 = new Texture("missile.png");
    TextureRegion enemy = new TextureRegion(img0, 0, 0, img0.getWidth(), img0.getHeight());

    Texture img1 = new Texture("space_ship_thrust.png");
    TextureRegion thrust = new TextureRegion(img1, img1.getWidth(), img1.getHeight());

    float enemyWidth = 2f;
    float enemyHeight = 2f* img0.getHeight()/img0.getWidth();

    float thrustWidth = 1f;
    float thrustLength = 3f;
    float speed = 80;
    boolean update;

    public Enemy(float x, float y){
        setX(x);
        setY(y);
        update = false;
    }

    @Override
    public void draw(Batch batch, float alpha){

        batch.draw(enemy, getX()-enemyWidth/2, getY()-enemyHeight/3, enemyWidth/2, enemyHeight/3, enemyWidth, enemyHeight, getScaleX(), getScaleY(), getRotation());

        batch.draw(thrust, getX()-thrustWidth/2, getY() - enemyHeight/3 - thrustLength, thrustWidth/2f, enemyHeight/3 + thrustLength, thrustWidth, thrustLength, 1f, 1f, getRotation());
    }

    public void hunt(Player player){
        if(update){
            float targetDistance;
            float relPosX = getX() - player.getX();
            float relPosY = getY() - player.getY();

            targetDistance = (float)Math.sqrt(Math.pow(relPosX, 2)+ Math.pow(relPosY, 2));


            if(getY() >= player.getY()){
                setRotation( -(float)Math.toDegrees( Math.asin( (double)relPosX/targetDistance ))- 180);
            }else{
                setRotation( (float)Math.toDegrees( Math.asin( (double)relPosX/targetDistance )));
            }

            setX( getX() - (float)Math.sin(Math.toRadians(getRotation()))*speed* Gdx.graphics.getDeltaTime());
            setY( getY() + (float)Math.cos(Math.toRadians(getRotation()))*speed*Gdx.graphics.getDeltaTime() );
        }
    }

    public boolean isTouching(Player player){
        boolean exit;
        float relativeX = Math.abs(this.getX() - player.getX());
        float relativeY = Math.abs(this.getY() - player.getY());
        if(Math.pow(relativeX, 2) + Math.pow(relativeY, 2) > Math.pow( player.getHeight()/3 + enemyHeight/2, 2 )){
            exit = false;
        }else{
            exit = true;
            this.update = false;
            this.setVisible(false);
        }
        return exit;
    }
    public void isTouching(Platform platform){
        float relativeX = Math.abs(this.getX() - platform.getX());
        float relativeY = Math.abs(this.getY() - platform.getY());
        if(Math.pow(relativeX, 2) + Math.pow(relativeY, 2) <= Math.pow( platform.getRadius() + enemyHeight/2, 2 )){
            update = false;
            this.setVisible(false);
        }
    }

    public void startUpdate(){ this.update = true;}
}
