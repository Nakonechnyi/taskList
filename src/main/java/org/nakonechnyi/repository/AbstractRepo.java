package org.nakonechnyi.repository;

import org.apache.log4j.Logger;
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
    final static Logger logger = Logger.getLogger(AbstractRepo.class);

    Connection getDBConnection() throws ConnectException {
        try {
            Class.forName(AppProperties.DB_DRIVER);
        } catch (ClassNotFoundException e) {
            logger.error( "JDBC Driver?", e);
            e.printStackTrace();
            throw new ConnectException();
        }
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection(AppProperties.DB_URL, AppProperties.DB_USER, AppProperties.DB_PASS);
        } catch (SQLException e) {

            logger.error("Connection Failed!", e);
            throw new ConnectException();
        }

        return connection;
    }
}
