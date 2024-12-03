package io.github.testGame1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class GameOverScreen implements Screen {
    private final ZombieShooterGame game;
    private final SpriteBatch batch;
    private String username;
    private final Texture gameOverImage;
    private final Texture buttonTexture;
    private final BitmapFont gameOverFont;
    private final BitmapFont buttonFont;
    private final Rectangle retryButtonBounds;
    private final Rectangle quitButtonBounds;
    private final GlyphLayout glyphLayout;

    public GameOverScreen(ZombieShooterGame game, String username) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.gameOverImage = new Texture("images/game_over.png");
        this.buttonTexture = new Texture("images/button.png");

        //Police pour "Game Over"
        this.gameOverFont = new BitmapFont();
        gameOverFont.getData().setScale(6);
        gameOverFont.setColor(Color.RED);

        //Police pour les boutons
        this.buttonFont = new BitmapFont();
        this.glyphLayout = new GlyphLayout();

        //Boutons
        retryButtonBounds = new Rectangle(
            Gdx.graphics.getWidth() / 2f - 100,
            Gdx.graphics.getHeight() / 2f - 50,
            200,
            50
        );
        quitButtonBounds = new Rectangle(
            Gdx.graphics.getWidth() / 2f - 100,
            Gdx.graphics.getHeight() / 2f - 120,
            200,
            50
        );
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(
            gameOverImage,
            0,
            0,
            Gdx.graphics.getWidth(),
            Gdx.graphics.getHeight()
        );

        //Afficher le texte game over
        String gameOverText = "Game Over";
        glyphLayout.setText(gameOverFont, gameOverText);
        float textWidth = glyphLayout.width;
        float textX = (Gdx.graphics.getWidth() - textWidth) / 2;
        float textY = Gdx.graphics.getHeight() - 100;
        gameOverFont.draw(batch, gameOverText, textX, textY);

        //Afficher les boutons
        batch.draw(buttonTexture, retryButtonBounds.x, retryButtonBounds.y, retryButtonBounds.width, retryButtonBounds.height);
        batch.draw(buttonTexture, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);
        buttonFont.draw(batch, "Rejouer", retryButtonBounds.x + 50, retryButtonBounds.y + 35);
        buttonFont.draw(batch, "Quitter", quitButtonBounds.x + 50, quitButtonBounds.y + 35);

        batch.end();

        if (Gdx.input.justTouched()) {
            Vector2 touchPos = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
            if (retryButtonBounds.contains(touchPos)) {
                game.setScreen(new GameScreen(game, username)); //Rejouer
            } else if (quitButtonBounds.contains(touchPos)) {
                Gdx.app.exit(); //Quitter
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        gameOverImage.dispose();
        buttonTexture.dispose();
        gameOverFont.dispose();
        buttonFont.dispose();
    }
}
