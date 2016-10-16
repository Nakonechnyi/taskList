package org.nakonechnyi.repository;

import org.nakonechnyi.domain.ArchivedTask;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.util.AppProperties;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 16.10.2016.
 */
public class TaskArchiveRepository extends AbstractRepo {

    public static final String ID = "id";
    public static final String ORIGINAL_ID = "originalId";
    public static final String NAME = "name";
    public static final String DATE = "date";
    public static final String PRIORITY = "priority";
    public static final String STATUS_DONE = "statusDone";
    public static final String ARCHIVED_ON = "archivedOn";
    private static final String ARCHIVE_TABLE = AppProperties.DB_NAME + ".tasks_archive";
    public static final String _ = ", ";

    public void insert(Task task) {
        Connection conn = null;
        PreparedStatement stmnt = null;
        SimpleDateFormat format = new SimpleDateFormat(AppProperties.DATE_FORMAT);
        String sqlQuery = "INSERT INTO " + ARCHIVE_TABLE + " (" + ORIGINAL_ID + _ + NAME + _ + DATE + _ + PRIORITY + _ + STATUS_DONE + _ + ARCHIVED_ON + ") VALUES ( ?, ?, ?, ?, ?, ?);";
        try {
            conn = getDBConnection();
            stmnt = conn.prepareStatement(sqlQuery);
            stmnt.setInt(1, task.getId());
            stmnt.setString(2, task.getName());
            stmnt.setString(3, format.format(task.getDate()));
            stmnt.setInt(4, task.getPriority());
            stmnt.setInt(5, task.getStatusDone());
            stmnt.setDate(6, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            stmnt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeArchivedTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            FakeTaskArchiveRepository.insert(task);
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
    public List<ArchivedTask> getAll() {
        try {
            String sql = "SELECT " + ID + _ + NAME + _ + DATE + _ + PRIORITY + _ + STATUS_DONE + _ + ARCHIVED_ON + _ + ORIGINAL_ID + " FROM " + ARCHIVE_TABLE ;
            return (List<ArchivedTask>)(List<?>)readDB(sql);
        } catch (Exception e) {
            logger.error(e);
            JOptionPane.showMessageDialog(null, "Original DB not connected! Will be used FakeTaskRepository.","Warning", JOptionPane.WARNING_MESSAGE);
            return FakeTaskArchiveRepository.getAll();
        }

    }

    @Override
    ArchivedTask readObj(ResultSet resultSet) throws SQLException {
        return new ArchivedTask(
                resultSet.getInt(ID),
                resultSet.getString(NAME),
                resultSet.getDate(DATE),
                resultSet.getInt(PRIORITY),
                resultSet.getByte(STATUS_DONE),
                resultSet.getDate(ARCHIVED_ON),
                resultSet.getInt(ORIGINAL_ID)
        );
    }
}
