package io.github.testGame1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private Texture texture;
    private float x;
    private float y;
    private float speed = 400f;

    public Bullet(String texturePath, float startX, float startY) {
        texture = new Texture(texturePath);
        x = startX;
        y = startY;
    }

    public void update(float deltaTime) {
        x += speed * deltaTime;
    }

    public void render(SpriteBatch batch) {
        float targetWidth = texture.getWidth() * 0.5f;
        float targetHeight = texture.getHeight() * 0.5f;
        batch.draw(texture, x, y, targetWidth, targetHeight);
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {  // Ajout de la méthode getY()
        return y;
    }

    public float getWidth() {
        return texture.getWidth();
    }

    public float getHeight() {
        return texture.getHeight();
    }
}
