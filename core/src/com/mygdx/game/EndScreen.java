package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EndScreen implements Screen {
    final Drop game;

    Stage stage;
    OrthographicCamera cam;
    FitViewport viewport;

    Texture allButtons = new Texture("menu_buttons.png");
    TextureRegion victoryText;
    TextureRegion defeatText;
    TextureRegion restartButton = new TextureRegion(allButtons, 0, 4*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );
    TextureRegion menuButton = new TextureRegion(allButtons, 0, 3*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );

    SpriteBatch batch;

    ImageButton restart;
    ImageButton menu;

    Planet backgroundPlanet1;
    Planet backgroundPlanet2;
    int file;
    boolean victoryState;

    public EndScreen(Drop game, int file, boolean victoryState){
        this.game = game;
        this.file = file;
        this.victoryState = victoryState;

        cam = new OrthographicCamera();
        cam.position.set(0, 0, 0);
        viewport = new FitViewport(100f * Gdx.graphics.getWidth()/ Gdx.graphics.getHeight(), 100f, cam);
        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);

        backgroundPlanet1 = new Planet();
        backgroundPlanet1.setParameters(0 , viewport.getWorldWidth() + 25f, -18);
        stage.addActor(backgroundPlanet1);
        backgroundPlanet2 = new Planet();
        backgroundPlanet2.setParameters(2,3, viewport.getWorldHeight() - 12f);
        stage.addActor(backgroundPlanet2);

        menu = new ImageButton(new TextureRegionDrawable(menuButton));
        menu.setSize((float) (5.3 * 12.5), 12.5f);
        menu.setPosition(viewport.getWorldWidth()/2 - menu.getWidth()/2, 10);
        stage.addActor(menu);

        restart = new ImageButton(new TextureRegionDrawable(restartButton));
        restart.setSize((float) (5.3 * 12.5), 12.5f);
        restart.setPosition(viewport.getWorldWidth()/2 - restart.getWidth()/2, 25);
        stage.addActor(restart);


        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);
        victoryText = new TextureRegion(allButtons, 0, 6*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );
        defeatText = new TextureRegion(allButtons, 0, 7*allButtons.getHeight()/8, allButtons.getWidth(), allButtons.getHeight()/8 );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0f, 1);
        backgroundPlanet1.setVisible(true);
        backgroundPlanet2.setVisible(true);
        stage.draw();

        if(restart.isPressed()){
            game.setScreen(new Game(game, file));
        }
        if(menu.isPressed()){
            game.setScreen(new MainMenu(game, file));
        }
        batch.begin();
        if(victoryState){
            batch.draw(victoryText, viewport.getWorldWidth()/2 - 53, 47.5f, (float)(5.3*20), 20);
        }else{
            batch.draw(defeatText, viewport.getWorldWidth()/2 - 53, 47.5f, (float)(5.3*20), 20);
        }
        batch.end();
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
        batch.dispose();
    }
}
