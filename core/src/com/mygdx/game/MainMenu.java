package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class MainMenu implements Screen {
    final Drop game;

    SaveManager saveManager;
    OrthographicCamera cam;
    FitViewport viewport;

    Texture allButtons = new Texture("menu_buttons.png");
    Texture allSaves = new Texture("save_buttons.png");
    TextureRegion playButton = new TextureRegion(allButtons, 0, 0, allButtons.getWidth(), allButtons.getHeight()/8 );
    TextureRegion exitButton = new TextureRegion(allButtons, 0, allButtons.getHeight()/8+3, allButtons.getWidth(), allButtons.getHeight()/8);

    TextureRegion saveButton0 = new TextureRegion(allSaves, 0, 0, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton1 = new TextureRegion(allSaves, 0, allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton2 = new TextureRegion(allSaves, 0, 2*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton3 = new TextureRegion(allSaves, 0, 3*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);
    TextureRegion saveButton4 = new TextureRegion(allSaves, 0, 4*allSaves.getHeight()/5, allSaves.getWidth(), allSaves.getHeight()/5);

    Planet backgroundPlanet1;
    Planet backgroundPlanet2;



    Stage stage;

    ImageButton play;
    ImageButton exit;

    ImageButton[] save = new ImageButton[5];

    int file;

    public MainMenu(final Drop game, int file) {
        this.game = game;
        saveManager = new SaveManager();
        this.file = file;

        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        viewport = new FitViewport(100f * Gdx.graphics.getWidth()/ Gdx.graphics.getHeight(), 100f, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        backgroundPlanet1 = new Planet();
        backgroundPlanet1.setParameters(0 , 3, viewport.getWorldHeight() - 12f);
        stage.addActor(backgroundPlanet1);
        backgroundPlanet2 = new Planet();
        backgroundPlanet2.setParameters(3,viewport.getWorldWidth() - 25f, 8);
        stage.addActor(backgroundPlanet2);

        play = new ImageButton(new TextureRegionDrawable(playButton));
        play.setSize(15f * 8 * allButtons.getWidth() / allButtons.getHeight(), 15);
        play.setPosition(10, 40);
        play.setOrigin(0,0);
        stage.addActor(play);


        exit = new ImageButton(new TextureRegionDrawable(exitButton));
        exit.setPosition(10, 10);
        exit.setSize((float) (5.3 * 12.5), 12.5f);
        stage.addActor(exit);



        save[0] = new ImageButton(new TextureRegionDrawable(saveButton0));
        save[1] = new ImageButton(new TextureRegionDrawable(saveButton1));
        save[2] = new ImageButton(new TextureRegionDrawable(saveButton2));
        save[3] = new ImageButton(new TextureRegionDrawable(saveButton3));
        save[4] = new ImageButton(new TextureRegionDrawable(saveButton4));

        for(int i = 0; i < 5; i++){
            save[4-i].setPosition(10, 25f);
            save[4-i].setSize((float) (5.3*12.5), 12.5f);
            stage.addActor(save[4-i]);
            save[4-i].setVisible(false);
        }

    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0f, 1);
        backgroundPlanet1.setVisible(true);
        backgroundPlanet2.setVisible(true);
        save[file].setVisible(true);


        stage.draw();

        if(Gdx.input.isKeyPressed(Input.Keys.NUM_1)){
            try{
                saveManager.createNew(1);
            }catch (IOException e){
                System.out.println("nea dela");
            }

            game.setScreen(new Game(game, file));
            dispose();
        }

        if(play.isPressed()){
            game.setScreen(new Game(game, file));
            dispose();
        }
        if(exit.isPressed()){
            Gdx.app.exit();
            dispose();
        }
        if(save[file].isPressed()){
            game.setScreen(new SaveMenu(game));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.setScreen(new EndScreen(game, file, false));
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
