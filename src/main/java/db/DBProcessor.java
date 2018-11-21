package db;

import java.sql.*;

public class DBProcessor {
    private static String URL = "jdbc:mysql://localhost:3306/mysql?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true&allowMultiQueries=true";
    private static String LOGIN = "root";
    private static String PASSWORD = "123456789";

    private static Statement statement;
    private static Connection connection;


    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                //Log.printlog("connection", "successful");
            } catch (SQLException e) {
                //Log.printlog("connection", "unsuccessful");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        if (statement == null) {
            statement = getConnection().createStatement();
        }
        return statement;
    }
}
