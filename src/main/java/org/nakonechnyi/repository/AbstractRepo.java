package org.nakonechnyi.repository;

import org.apache.log4j.Logger;
import org.nakonechnyi.util.AppProperties;

import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 14.10.2016.
 */
public abstract class AbstractRepo {
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

    List<Object> readDB (String sqlQuery) throws ConnectException {
        List<Object> getList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sqlQuery);

            while (resultSet.next()) {
                getList.add(readObj(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                logger.error(e);
            }
        }
        return getList;
    }

    abstract Object readObj(ResultSet resultSet) throws SQLException;
}
