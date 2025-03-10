package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.io.IOException;

public class SaveMenu implements Screen {
    SaveManager saveManager;
    final Drop game;
    Stage stage;
    OrthographicCamera cam;
    FitViewport viewport;
    Texture allSaves = new Texture("save_buttons.png");
    Texture allButtons = new Texture("menu_buttons.png");
    TextureRegion createNewButton = new TextureRegion(allButtons, 0, 3+5*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8);

    TextureRegion saveButton0 = new TextureRegion(allSaves, 0, 0, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton1 = new TextureRegion(allSaves, 0, allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton2 = new TextureRegion(allSaves, 0, 2*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton3 = new TextureRegion(allSaves, 0, 3*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton4 = new TextureRegion(allSaves, 0, 4*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    ImageButton[] save = new ImageButton[5];
    ImageButton createNew;
    Planet planet;

    Planet backgroundPlanet1;
    Planet backgroundPlanet2;

    boolean returnToMenu;
    boolean override;
    boolean overrideSave;
    int buttonPointer;
    public SaveMenu(final Drop game){
        this.game = game;


        override = false;
        saveManager = new SaveManager();

        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        viewport = new FitViewport(100f * Gdx.graphics.getWidth()/ Gdx.graphics.getHeight(), 100f, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        backgroundPlanet1 = new Planet();
        backgroundPlanet1.setParameters(1 , viewport.getWorldWidth() + 12f, viewport.getWorldHeight() + 10f);
        stage.addActor(backgroundPlanet1);
        backgroundPlanet2 = new Planet();
        backgroundPlanet2.setParameters(2,2, 2);
        stage.addActor(backgroundPlanet2);

        save[0] = new ImageButton(new TextureRegionDrawable(saveButton0));
        save[1] = new ImageButton(new TextureRegionDrawable(saveButton1));
        save[2] = new ImageButton(new TextureRegionDrawable(saveButton2));
        save[3] = new ImageButton(new TextureRegionDrawable(saveButton3));
        save[4] = new ImageButton(new TextureRegionDrawable(saveButton4));
        for(int i = 0; i < 5; i++){
            save[i].setSize((float) (5.3*12.5), 12.5f);
            save[i].setPosition(viewport.getWorldWidth()/2 - save[i].getWidth()/2, 10 + 12.5f*i);
            stage.addActor(save[i]);
        }

        createNew = new ImageButton(new TextureRegionDrawable(createNewButton));
        createNew.setSize((float) (5.3*12.5), 12.5f);
        createNew.setPosition(viewport.getWorldWidth()/2 - createNew.getWidth()/2, 75f);
        stage.addActor(createNew);

        planet = new Planet();
        planet.setParameters(0, 130 , 0);
        planet.setRadius(2.5f);
        stage.addActor(planet);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0f, 1);

        backgroundPlanet1.setVisible(true);
        backgroundPlanet2.setVisible(true);

        overrideSave = false;
        buttonPointer = 0;
        returnToMenu = false;

        if(createNew.isPressed()){
            override = true;
        }
        for(int i = 0; i < 5; i++){
            if (save[i].isPressed()) {
                planet.setY(16.25f + 12.5f*i);
                planet.setVisible(true);
                buttonPointer = i;
                if(override){
                    overrideSave = true;
                }else{
                    returnToMenu = true;
                }
            }
        }

        stage.draw();
        if(returnToMenu){
            if (!saveManager.checkIfExists(buttonPointer)) {
                try {
                    saveManager.createNew(buttonPointer);
                } catch (IOException e) {
                    System.out.println("nea dela");
                }
            }
            game.setScreen(new MainMenu(game, buttonPointer));
        }
        if(overrideSave){
            try{
                saveManager.createNew(buttonPointer);
            }catch(IOException e){
                System.out.println("nea dela");
            }
            override = false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
