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

public class Game implements Screen {
    final Drop game;
    int curentFile;
    public Stage stage;
    FitViewport viewport;
    OrthographicCamera cam;

    Player player;
    Enemy enemy0;
    Enemy enemy1;
    Enemy enemy2;

    SaveManager saveManager;
    Planet[][][] planet;

    Pointer pointerFinish;
    Pointer pointerEnemy0;
    Pointer pointerEnemy1;
    Pointer pointerEnemy2;

    Platform startPlatform;
    Platform goalPlatform;

    Texture allButtons = new Texture("menu_buttons.png");
    TextureRegion resumeButton = new TextureRegion(allButtons, 0, 2*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );
    ImageButton resume;
    TextureRegion menuButton = new TextureRegion(allButtons, 0, 3*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );
    ImageButton menu;

    float endTimer;
    boolean pause;
    public Game(Drop game, int file){
        this.game = game;
        pause = false;

        curentFile = file;
        cam = new OrthographicCamera();
        cam.translate(cam.viewportWidth / 2f, cam.viewportHeight / 2f);
        viewport = new FitViewport(100f* Gdx.graphics.getWidth()/Gdx.graphics.getHeight(), 100f, cam);
        cam.zoom = 1.1f;
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        player = new Player();

        pointerFinish = new Pointer(0);
        pointerEnemy0 = new Pointer(1);
        pointerEnemy1 = new Pointer(1);
        pointerEnemy2 = new Pointer(1);

        startPlatform = new Platform(SaveManager.chunkSize/4, SaveManager.chunkSize/4);
        goalPlatform = new Platform(SaveManager.chunkSize * SaveManager.numOfCol - SaveManager.chunkSize/4, SaveManager.chunkSize * SaveManager.numOfRow - SaveManager.chunkSize/4);

        saveManager = new SaveManager();
        try {
            planet = saveManager.read(curentFile);
            System.out.println("read dela");

            System.out.println( planet[0][0][0].getX() + "   " + planet[0][0][0].getY() + "  " + planet[0][0][0].getForm());
        }catch (IOException e){
            System.out.println("nea dela");
        }

        for(int col = 0; col < SaveManager.numOfCol; col++){
            for(int row = 0; row < SaveManager.numOfRow; row++){
                stage.addActor(planet[col][row][0]);
                planet[col][row][0].setVisible(false);
                stage.addActor(planet[col][row][1]);
                planet[col][row][1].setVisible(false);
            }
        }


        enemy0 = new Enemy(-50, -50);
        enemy1 = new Enemy(0, -50);
        enemy2 = new Enemy(-50, 0);
        stage.addActor(enemy0);
        stage.addActor(enemy1);
        stage.addActor(enemy2);

        stage.addActor(player);
        stage.addActor(pointerFinish);
        stage.addActor(pointerEnemy0);
        stage.addActor(pointerEnemy1);
        stage.addActor(pointerEnemy2);
        stage.addActor(startPlatform);
        stage.addActor(goalPlatform);

        resume = new ImageButton(new TextureRegionDrawable(resumeButton));
        resume.setSize((float) (5.3 * 12.5), 12.5f);
        stage.addActor(resume);
        resume.setVisible(false);

        menu = new ImageButton(new TextureRegionDrawable(menuButton));
        menu.setSize((float) (5.3 * 12.5), 12.5f);
        stage.addActor(menu);
        menu.setVisible(false);

        endTimer = 1.5f;
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0, 0, 0, 1);

        loadChunks();

        cam.position.set(player.getX(), player.getY(),0);

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !pause && player.getUpdateStatus()){
            pause = true;
            cam.zoom = 1;
            cam.rotate(player.getRotation());
            player.stopUpdate();
        }
        if(pause){

            resume.setPosition(player.getX() - resume.getWidth()/2, player.getY() - resume.getHeight());
            resume.setVisible(true);

            menu.setPosition(player.getX() - menu.getWidth()/2, player.getY() - 27.5f);
            menu.setVisible(true);

            if(resume.isPressed()){
                cam.zoom = 1.1f;
                cam.rotate(-player.getRotation());

                resume.setVisible(false);
                menu.setVisible(false);
                pause = false;

                player.restorePreviusSpeed();
                player.update = true;
            }
            if(menu.isPressed()){
                game.setScreen(new MainMenu(game, curentFile));
            }
        }

        stage.draw();//pomenbno

        if(player.getUpdateStatus()){
            if(Gdx.input.isKeyPressed(Input.Keys.A)){
                cam.rotate(player.turnLeft());

            }
            if(Gdx.input.isKeyPressed(Input.Keys.D)){
                cam.rotate(player.turnRight());

            }
            if(Gdx.input.isKeyPressed(Input.Keys.W)){
                player.accelerate();
            }else{
                player.decelerate();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.S)){
                player.decelerate();
            }
            if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
                player.accelerate();
                player.accelerate();
                player.accelerate();
            }
            player.move();
        }



        if (!startPlatform.isTouching(player)) {
            startPlatform.setVisible(false);
            enemy0.startUpdate();
            enemy1.startUpdate();
            enemy2.startUpdate();
        }
        if(goalPlatform.isTouching(player)){
            player.stopUpdate();
            endTimer -= Gdx.graphics.getDeltaTime();
            enemy0.isTouching(goalPlatform);
            enemy1.isTouching(goalPlatform);
            enemy2.isTouching(goalPlatform);
        }

        if(enemy0.isTouching(player) || enemy1.isTouching(player) || enemy2.isTouching(player) || planet[player.getColumn()][player.getRow()][0].isTouching(player) || planet[player.getColumn()][player.getRow()][1].isTouching(player)){
            player.explode();
            endTimer -= Gdx.graphics.getDeltaTime();
        }

        if(!pause){
            enemy0.isTouching(player);
            enemy1.isTouching(player);
            enemy2.isTouching(player);
            enemy0.hunt(player);
            enemy1.hunt(player);
            enemy2.hunt(player);
        }

        pointerFinish.update(player.getX(), player.getY(), goalPlatform.getX(), goalPlatform.getY(), 6);
        pointerEnemy0.update(player.getX(), player.getY(), enemy0.getX(), enemy0.getY(),7);
        pointerEnemy1.update(player.getX(), player.getY(), enemy1.getX(), enemy1.getY(),7);
        pointerEnemy2.update(player.getX(), player.getY(), enemy2.getX(), enemy2.getY(),7);

        if(endTimer <= 0){
            if(player.form == 2){
                game.setScreen(new EndScreen(game, curentFile, false));
            } else if(goalPlatform.isTouching(player)){
                game.setScreen(new EndScreen(game, curentFile, true));
            }
        }

    }

    @Override
    public void show(){

    }

    @Override
    public void hide(){

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    public void loadChunks(){
        for(int col = 0; col < SaveManager.numOfCol; col++){
            for(int row = 0; row < SaveManager.numOfRow; row++){
                if(planet[col][row][0].getColumn() == player.getColumn() + 1 || planet[col][row][0].getColumn() == player.getColumn() || planet[col][row][0].getColumn() == player.getColumn()-1){
                    if(planet[col][row][0].getRow() == player.getRow() + 1 || planet[col][row][0].getRow() == player.getRow() || planet[col][row][0].getRow() == player.getRow()-1){
                        planet[col][row][0].setVisible(true);
                        planet[col][row][1].setVisible(true);
                    }
                }
            }
        }
    }
}
