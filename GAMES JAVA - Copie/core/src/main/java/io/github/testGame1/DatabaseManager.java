package io.github.testGame1;

import java.sql.*;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:mysql://localhost:3306/zombie_game_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    //Voir si le user est valide
    public boolean validateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Enregistre le score actuel du joueur dans la base
    public void saveScore(String username, int score) {
        String query = "UPDATE users SET score = ? WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, score);
            statement.setString(2, username);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Ne garde que le plus haut score du joueur dans la base
    public int getHighScore(String username) {
        String query = "SELECT score FROM users WHERE username = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("score");
            } else {
                logger.warning("No score found for user: " + username);
            }
        } catch (Exception e) {
            logger.severe("Error while retrieving score for user: " + username);
            e.printStackTrace();
        }
        return 0; //Retourne 0 si aucun score n'est trouvé ou si une erreur se produit
    }
}
