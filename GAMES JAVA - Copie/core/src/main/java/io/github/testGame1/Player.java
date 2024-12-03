package io.github.testGame1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    private Texture texture;
    private float x, y;
    private float speed = 200f;
    private int healthPoints;
    private float invincibilityTimer = 0;
    private final float invincibilityDuration = 1.0f;

    //Initialiser le joueur
    public Player(String texturePath, float startX, float startY, int initialHealth) {
        this.texture = new Texture(texturePath);
        this.x = startX;
        this.y = startY;
        this.healthPoints = initialHealth;
    }

    //Le faire déplacer avec les touches du clavier
    public void update(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * deltaTime;
        }

        if (invincibilityTimer > 0) {
            invincibilityTimer -= deltaTime;
        }

        //Limites de la fenêtre
        float screenWidth = Gdx.graphics.getWidth();
        x = Math.max(0, Math.min(x, screenWidth - texture.getWidth()));

        float screenHeight = Gdx.graphics.getHeight();
        y = Math.max(0, Math.min(y, screenHeight - texture.getHeight()));
    }

    public void takeDamage(int damage) {
        if (invincibilityTimer <= 0) {
            healthPoints = Math.max(0, healthPoints - damage);
            invincibilityTimer = invincibilityDuration;
            System.out.println("Player took damage! HP: " + healthPoints);
        }
    }

    public void render(SpriteBatch batch) {
        float scaleFactor = 0.5f;
        batch.draw(texture, x, y, texture.getWidth() * scaleFactor, texture.getHeight() * scaleFactor);
    }

    public void dispose() {
        texture.dispose();
    }

    //Obtenir le x et y du joueurr
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

    //Réduire la texture de 2
    public float getWidth() {
        return texture.getWidth() * 0.5f;
    }
    public float getHeight() {
        return texture.getHeight() * 0.5f;
    }

        //Vérifie si le joueur est mort
        public boolean isDead() {
            return healthPoints <= 0;
        }
        public int getHealthPoints() {
            return healthPoints;
        }
}
