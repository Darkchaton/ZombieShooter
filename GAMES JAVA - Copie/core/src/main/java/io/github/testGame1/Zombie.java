package io.github.testGame1;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Zombie {
    private Texture texture;
    private TextureRegion textureRegion;
    private float x;
    private float y;
    private float speed = 150f;
    private boolean isHit = false;
    private float hitTimer = 0;
    private final float hitDuration = 0.3f;
    private int hitCount = 0;
    private Player player;

    public Zombie(float startX, float startY, Player player) {
        texture = new Texture("images/zombie.png");
        textureRegion = new TextureRegion(texture);
        this.x = startX;
        this.y = startY;
        this.player = player;
    }

    public void update(float deltaTime) {
        x -= speed * deltaTime;

        //Clignotement après avoir été touché
        if (isHit) {
            hitTimer -= deltaTime;
            if (hitTimer <= 0) {
                isHit = false;
                hitTimer = 0;
            }
        }
    }

    //Inverser le render afin qu'il fasse face au joueur et qu'il soit réduit de moitié
    public void render(SpriteBatch batch) {
        if (!isHit || ((int)(hitTimer * 10) % 2 == 0)) {
            float dx = player.getX() - x;
            float dy = player.getY() - y;
            float angle = (float) Math.atan2(dy, dx) * 180 / (float) Math.PI;
            boolean flipX = dx < 0;

            textureRegion.flip(flipX, false);

            float scaleFactor = 0.5f;
            batch.draw(textureRegion, x, y, textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2,
                textureRegion.getRegionWidth(), textureRegion.getRegionHeight(), scaleFactor, scaleFactor, angle);

            textureRegion.flip(flipX, false);
        }
    }

    public void dispose() {
        texture.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return textureRegion.getRegionWidth();
    }

    public float getHeight() {
        return textureRegion.getRegionHeight();
    }

    //Quand le zombie est touché
    public void hit() {
        isHit = true;
        hitTimer = hitDuration;
        hitCount++;
    }
    //Le zombie prend 2 balles pour mourrir
    public boolean isDead() {
        return hitCount >= 2;
    }
}
