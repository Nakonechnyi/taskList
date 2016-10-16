package org.nakonechnyi.repository;

import org.apache.log4j.Logger;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.util.AppProperties;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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

    final static Logger logger = Logger.getLogger(TaskRepository.class);

    public List<Task> getAllCompleted() {
        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + _ + STATUS_DONE + " FROM " + TABLE + " WHERE " + STATUS_DONE + " = 1";
            return (List<Task>)(List<?>)readDB(sql);
        } catch (Exception e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAllCompleted();
        }
    }

    public List<Task> getAll() {
        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + _ + STATUS_DONE + " FROM " + TABLE + " WHERE " + STATUS_DONE + " = 0";
            return (List<Task>)(List<?>)readDB(sql);
        } catch (Exception e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getAll();
        }

    }

    public boolean updateStatus(boolean statusDone, int taskId) {
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
            return stmnt.executeUpdate() == 1;
        } catch (Exception e) {
            logger.error(e);
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
                logger.error(e);
            }
        }
        return false;
    }

    public boolean insert(Task task) {
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
            return stmnt.executeUpdate() == 1;
        } catch (Exception e) {
            logger.error(e);
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
                logger.error(e);
            }
        }
        return false;
    }

    public Task getById(int taskId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + _ + STATUS_DONE + " FROM " + TABLE + " WHERE " + ID + " = ?";
            conn = getDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            resultSet = stmt.executeQuery();
            resultSet.next();
            return readObj(resultSet);
        } catch (Exception e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskRepository.getById(taskId);
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
    }

    public boolean delete(int taskId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resultSet = null;

        try {
            String sql = "DELETE FROM " + TABLE + " WHERE " + ID + " = ? ;";
            conn = getDBConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, taskId);
            return stmt.executeUpdate() == 1;
        } catch (Exception e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskArchiveRepository.delete(taskId);
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
        return false;
    }

    @Override
    Task readObj(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getInt(ID),
                resultSet.getString(NAME),
                resultSet.getDate(DATE),
                resultSet.getInt(PRIORITY),
                resultSet.getByte(STATUS_DONE)
        );
    }
}
