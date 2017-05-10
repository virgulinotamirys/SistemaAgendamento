package sistemaagendamento.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String database = "sistema_agendamento";
    private static String url = "jdbc:mysql://localhost:3306/" + database;
    private static String username = "root";
    private static String password = "root";

    public static Connection getConnection() {
        Connection connection = null;

        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return connection;
    }
}
