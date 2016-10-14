package org.nakonechnyi.repository;

import org.nakonechnyi.util.AppProperties;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @autor A_Nakonechnyi
 * @date 14.10.2016.
 */
public class AbstractRepo {

    Connection getDBConnection() throws ConnectException {
        try {
            Class.forName(AppProperties.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver?");
            e.printStackTrace();
            throw new ConnectException();
        }
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(AppProperties.DB_URL, AppProperties.DB_USER, AppProperties.DB_PASS);
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            throw new ConnectException();
        }

        return connection;
    }
}
