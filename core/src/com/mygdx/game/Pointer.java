package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Pointer extends Actor {
    Texture img0 = new Texture("pointer.png");
    TextureRegion pointer1 = new TextureRegion(img0, 0, 0, img0.getWidth()/2, img0.getHeight());
    TextureRegion pointer2 = new TextureRegion(img0, img0.getWidth()/2, 0, img0.getWidth()/2, img0.getHeight());
    int form;
    float offset;
    float targetDistance;
    boolean draw = false;

    public Pointer(int form){
        this.form = form;
    }

    @Override
    public void draw(Batch batch, float alpha){
        if(draw){
            if(form == 0){
                batch.draw(pointer1, getX()-0.75f, getY() + offset, 0.75f, -offset,
                        1.5f,1.5f*2*img0.getHeight()/img0.getWidth(), getScaleX(), getScaleY(),getRotation());
            }
            if(form == 1){
                batch.draw(pointer2, getX()-0.75f, getY() + offset, 0.75f, -offset,
                        1.5f,1.5f*2*img0.getHeight()/img0.getWidth(), getScaleX(), getScaleY(),getRotation());
            }
        }

    }

    public void update(float x, float y, float targetX, float targetY, float offset){
        setX(x);
        setY(y);

        this.offset = offset;

        float relPosX = x - targetX;
        float relPosY = y - targetY;
        targetDistance = (float)Math.sqrt(Math.pow(relPosX, 2)+ Math.pow(relPosY, 2));

        if(targetDistance != 0){
            draw = true;
        }else{
            draw = false;
        }

        if(draw){
            if(y >= targetY){
                setRotation( -(float)Math.toDegrees( Math.asin( (double)relPosX/targetDistance ))- 180);
            }else{
                setRotation( (float)Math.toDegrees( Math.asin( (double)relPosX/targetDistance )));
            }
        }
        if(getRotation() >= 360){
            setRotation(getRotation()-360);
        }
        if(getRotation() <= 0){
            setRotation(getRotation() + 360);
        }
    }
}

