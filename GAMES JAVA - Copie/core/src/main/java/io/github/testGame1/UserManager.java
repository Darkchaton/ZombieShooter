package io.github.testGame1;

public class UserManager {
    private final DatabaseManager databaseManager;

    public UserManager(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    /**
     * Authentifie un utilisateur en vérifiant les informations dans la base de données.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return true si les informations sont valides, sinon false.
     */
    public boolean authenticateUser(String username, String password) {
        return databaseManager.validateUser(username, password);
    }

    /**
     * Enregistre un nouvel utilisateur dans la base de données.
     *
     * @param username Le nom d'utilisateur.
     * @param password Le mot de passe.
     * @return true si l'utilisateur est enregistré avec succès, sinon false.
     */
    public boolean registerUser(String username, String password) {
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (var connection = databaseManager.getConnection();
             var statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, password);
            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.err.println("Error while registering user: " + e.getMessage());
            return false;
        }
    }
}
