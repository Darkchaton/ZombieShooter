package io.github.testGame1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class LoginScreen implements Screen {

    // Constants for UI layout
    private final int PIXEL_PADDING = 5;
    private final int SPACE_BETWEEN = 20;

    private Stage stage;
    private final ZombieShooterGame zombieShooter;
    private final FitViewport viewport;

    private Label loginLabel;
    private Label passwordLabel;
    private TextField loginField;
    private TextField passwordField;
    private TextButton connectButton;
    private TextButton registerButton;

    public LoginScreen(ZombieShooterGame zombieShooter, FitViewport viewport) {
        this.zombieShooter = zombieShooter;
        this.viewport = viewport;
        this.stage = new Stage(viewport);

        Gdx.input.setInputProcessor(stage);

        // Charger le skin
        Skin glassy = new Skin(Gdx.files.internal("ui/uiskin.json"));
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui/uiskin.atlas"));
        glassy.addRegions(atlas);

        // Initialiser les éléments UI
        loginLabel = new Label("Login: ", glassy);
        loginField = new TextField("", glassy);
        passwordLabel = new Label("Password: ", glassy);
        passwordField = new TextField("", glassy);
        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('*');

        connectButton = new TextButton("Connect", glassy);
        registerButton = new TextButton("Register", glassy);
    }

    @Override
    public void show() {
        // Positionner les labels et champs de texte
        float screenHeight = viewport.getScreenHeight();
        float centerX = stage.getWidth() / 2;

        loginLabel.setPosition(centerX + 80, screenHeight - 320);
        loginField.setPosition(centerX + 40, screenHeight - 350);

        passwordLabel.setPosition(centerX + 80, screenHeight - 420);
        passwordField.setPosition(centerX + 40, screenHeight - 450);

        // Ajouter les labels et champs à la scène
        stage.addActor(loginLabel);
        stage.addActor(loginField);
        stage.addActor(passwordLabel);
        stage.addActor(passwordField);

        // Configurer les boutons
        connectButton.setSize(200, 50);
        connectButton.setPosition(centerX - 250, screenHeight - 360);
        connectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleConnect();
            }
        });

        registerButton.setSize(200, 50);
        registerButton.setPosition(centerX - 250, screenHeight - 460);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleRegister();
            }
        });

        // Ajouter les boutons à la scène
        stage.addActor(connectButton);
        stage.addActor(registerButton);
    }

    private void handleConnect() {
        // Instancier DatabaseManager
        DatabaseManager databaseManager = new DatabaseManager();
        String username = loginField.getText();
        String password = passwordField.getText();

        // Vérifier si les champs sont vides
        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username or password cannot be empty.");
            return;
        }

        // Vérifier si l'utilisateur existe et si le mot de passe est correct
        if (databaseManager.validateUser(username, password)) {
            System.out.println("Authentication successful!");
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            zombieShooter.setScreen(new GameScreen(zombieShooter, username));
        } else {
            System.out.println("Authentication failed. Please check your credentials.");
        }
    }

    private void handleRegister() {
        // Instancier UserManager avec une instance de DatabaseManager
        UserManager userManager = new UserManager(new DatabaseManager());

        String username = loginField.getText();
        String password = passwordField.getText();

        if (userManager.registerUser(username, password)) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed. Username might already exist.");
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.68f, 0.85f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
