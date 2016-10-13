package org.nakonechnyi.repository;

import org.nakonechnyi.domain.Task;

import javax.swing.*;
import java.net.ConnectException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class TaskRepository {

    public List<Task> getAllCompleted() {
        try {
            String sql = "SELECT id, name, date, priority FROM Tasks WHERE statusDone = 1";
            return readDB(sql, (byte)1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAllCompleted();
        }
    }

    public List<Task> getAll() {
        try {
            String sql = "SELECT id, name, date, priority FROM Tasks WHERE statusDone = 0";
            return readDB(sql, (byte) 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAll();
        }

    }

    private Connection getDBConnection() throws ConnectException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver?");
            e.printStackTrace();
            throw new ConnectException();
        }
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/console_task_list", "roott", "polipol11");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            throw new ConnectException();
        }

        return connection;
    }

    public void updateStatus(boolean statusDone, int taskId) {
        String sqlQuery = "UPDATE console_task_list.tasks SET " +
                "statusDone = '"+ (statusDone == true ? (byte)1 : (byte)0) +
                "' WHERE id = "+ taskId;
        try {
            updateDB(sqlQuery);
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskRepository.updateStatus( statusDone, taskId);
        }
    }

    private void updateDB(String sqlQuery) throws ConnectException {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ConnectException();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(Task task) {

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        String sqlQuery = "INSERT INTO console_task_list.tasks (name, date, priority ) VALUES ('"+
                task.getName() + "', '" +
                format1.format(task.getDate()) + "', '" +
                task.getPriority() + "');";
        try {
            updateDB(sqlQuery);
        } catch (ConnectException e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskRepository.insert(task);
        }
    }


    List<Task> readDB (String sqlQuery, byte statusDone) throws ConnectException {
        List<Task> getList = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sqlQuery);

            while (resultSet.next()) {
                getList.add(readTask(resultSet, statusDone));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
        return getList;
    }

    private Task readTask(ResultSet resultSet, byte statusDone) throws SQLException {
        return new Task(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getDate("date"),
                resultSet.getInt("priority"),
                statusDone
        );

    }
}
