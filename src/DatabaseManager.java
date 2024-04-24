import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    public static Connection openDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:tasks.db");
    }

    public static void createDatabase() {
        // Abrir uma conexão com banco de dados
        try(Connection connection = openDatabaseConnection();
            Statement statement = connection.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS tasks ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "description TEXT NOT NULL,"
                    + "is_done INTEGER DEFAULT 0"
                    + ");";
            statement.execute(sql);


        } catch (SQLException err) {
            err.printStackTrace();
        }
    }
}
