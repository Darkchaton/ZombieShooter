package io.github.testGame1;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private ScoreManager scoreManager;
    private String username;
    private ZombieShooterGame game;
    private Player player;
    private List<Bullet> bullets;
    private List<Zombie> zombies;
    private double bulletPosition = 6;

    //Timer pour les zombies
    private float spawnTimer = 0;
    private float spawnInterval = 2.0f;

    private OrthographicCamera camera;

    private Texture background;

    private float saveTimer = 0;
    private float saveInterval = 1.0f;

    public GameScreen(ZombieShooterGame game, String username) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        //Initialisation des objets
        this.scoreManager = new ScoreManager(username);
        bullets = new ArrayList<>();
        zombies = new ArrayList<>();
        player = new Player("images/player.png", 100, 100, 10);
        background = new Texture("images/BG.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        //Mise à jour de la caméra
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Mise à jour du joueur
        player.update(delta);

        //Gestion des zombies
        Iterator<Zombie> zombieIterator = zombies.iterator();
        while (zombieIterator.hasNext()) {
            Zombie zombie = zombieIterator.next();
            zombie.update(delta);

            //Vérifier si le zombie dépasse le bord gauche de l'écran
            if (zombie.getX() <= 0) {
                player.takeDamage(1);
                zombieIterator.remove();

                //Vérifier si le joueur est mort après avoir perdu une vie
                if (player.isDead()) {
                    gameOver();
                    return;
                }
            }
        }

        //Gestion du spawn des zombies
        spawnTimer += delta;
        if (spawnTimer >= spawnInterval) {
            spawnZombie();
            spawnTimer = 0;
        }

        //Gestion des tirs du joueur
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet bullet = new Bullet("images/bullets.png", player.getX() + player.getWidth(), (float) (player.getY() + player.getHeight() / bulletPosition));
            bullets.add(bullet);
        }

        //Mettre à jour les balles et vérifier les collisions avec les zombies
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.update(delta);

            //Vérifier les collisions avec tous les zombies
            zombieIterator = zombies.iterator();
            while (zombieIterator.hasNext()) {
                Zombie zombie = zombieIterator.next();

                //Collisions entre zombie et balle
                if (bullet.getX() < zombie.getX() + zombie.getWidth() &&
                    bullet.getX() + bullet.getWidth() > zombie.getX() &&
                    bullet.getY() < zombie.getY() + zombie.getHeight() &&
                    bullet.getY() + bullet.getHeight() > zombie.getY()) {

                    zombie.hit();
                    scoreManager.addScore(10);
                    bulletIterator.remove();
                    if (zombie.isDead()) {
                        zombieIterator.remove();
                    }
                }
            }
            if (bullet.getX() > Gdx.graphics.getWidth()) {
                bulletIterator.remove();
            }
        }

        //Effacer l'écran et afficher les éléments du jeu
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        //Begin
        game.batch.begin();
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.render(game.batch);

        //Affichage des balles et des zombies
        for (Bullet bullet : bullets) {
            bullet.render(game.batch);
        }
        for (Zombie zombie : zombies) {
            zombie.render(game.batch);
        }

        //Sauvegarder le score dans la base de donn.es
        saveTimer += delta;
        if (saveTimer >= saveInterval) {
            saveTimer = 0; // Réinitialiser le timer
            scoreManager.saveCurrentScore(); // Sauvegarde le score actuel dans la base de données
        }

        //Afficher le score et la vie
        game.font.setColor(1, 1, 0, 1);
        game.font.draw(game.batch, "Score: " + scoreManager.getCurrentScore(), 50, 550);
        game.font.draw(game.batch, "HP: " + player.getHealthPoints(), 50, 520);
        game.batch.end();
    }

    //Générer une position aléatoire pour les zombies
    private void spawnZombie() {
        float minY = 10;
        float maxY = 480;

        float startY = minY + (float) Math.random() * (maxY - minY);
        float startX = 800;

        zombies.add(new Zombie(startX, startY, player));
    }


    public void gameOver() {
        scoreManager.saveHighScore();
        game.setScreen(new GameOverScreen(game, username));
    }

    //Inutilisé
    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        scoreManager.saveHighScore();
        dispose(); }

    @Override
    public void dispose() {
        player.dispose();
        background.dispose();
        for (Bullet bullet : bullets) {
            bullet.dispose();
        }
        for (Zombie zombie : zombies) {
            zombie.dispose();
        }
    }
}
