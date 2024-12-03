package io.github.testGame1;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ZombieShooterGame extends Game {
    public BitmapFont font;
    public SpriteBatch batch;
    private FitViewport viewport;

    @Override
    public void create() {
        font = new BitmapFont();
        font.setColor(Color.YELLOW);
        batch = new SpriteBatch();

        //Viewport
        viewport = new FitViewport(800, 600);

        //Définir l'écran de connexion au démarrage
        this.setScreen(new LoginScreen(this, viewport));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        font.dispose();
        batch.dispose();

        if (getScreen() != null) {
            getScreen().dispose();
        }
    }
}
