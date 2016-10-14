package org.nakonechnyi.repository;

import org.nakonechnyi.util.AppProperties;
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
public class TaskRepository extends AbstractRepo{

    //Field names
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String PRIORITY = "priority";
    public static final String STATUS_DONE = "statusDone";

    public static final String TABLE = AppProperties.DB_NAME + ".tasks";
    public static final String _ = ", ";

    public List<Task> getAllCompleted() {
        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + " FROM " + TABLE + " WHERE " + STATUS_DONE + " = 1";
            return readDB(sql, (byte)1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAllCompleted();
        }
    }

    public List<Task> getAll() {
        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + " FROM " + TABLE + " WHERE " + STATUS_DONE + " = 0";
            return readDB(sql, (byte) 0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAll();
        }

    }

    public void updateStatus(boolean statusDone, int taskId) {
        Connection conn = null;
        PreparedStatement stmnt = null;

        String sqlQuery = "UPDATE " + TABLE +
                " SET " +
                STATUS_DONE + " = '"+ (statusDone == true ? 1 : 0) +
                "' WHERE " + ID + " = ?";

        try {
            conn = getDBConnection();
            stmnt = conn.prepareStatement(sqlQuery);
            stmnt.setInt(1, taskId);
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskRepository.updateStatus( statusDone, taskId);
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
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
        Connection conn = null;
        PreparedStatement stmnt = null;
        SimpleDateFormat format1 = new SimpleDateFormat(AppProperties.DATE_FORMAT);
        String sqlQuery = "INSERT INTO " + TABLE + " (" + NAME + _ + DATE + _ + PRIORITY + ") VALUES ( ?, ?, ?);";
        try {
            conn = getDBConnection();
            stmnt = conn.prepareStatement(sqlQuery);
            stmnt.setString(1, task.getName());
            stmnt.setString(2, format1.format(task.getDate()));
            stmnt.setInt(3, task.getPriority());
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskRepository.insert(task);
        } finally {
            try {
                if (stmnt != null) {
                    stmnt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    List<Task> readDB (String sqlQuery, byte statusDone) throws ConnectException {
        List<Task> getList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = getDBConnection();
            stmt = conn.prepareStatement(sqlQuery);
            resultSet = stmt.executeQuery();

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
                resultSet.getInt(ID),
                resultSet.getString(NAME),
                resultSet.getDate(DATE),
                resultSet.getInt(PRIORITY),
                statusDone
        );

    }
}
