package edu.lfa.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author RAJU
 */
public class Database {

    private String driver, url, username, password, sql;
    private Connection connection = null;
    private PreparedStatement statement = null;
    private ResultSet result;

    public Database() {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost/college";
        username = "root";
        password = "";
    }

    public void connectToDatabase() throws ClassNotFoundException, SQLException {

        Class.forName(driver);
        connection = DriverManager.getConnection(url, username, password);

    }

    public PreparedStatement initStatement(String sql) throws SQLException {
        statement = connection.prepareStatement(sql);
        return statement;
    }

    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();

    }

    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    public void disconnectDatabase() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();

        }
    }

}
