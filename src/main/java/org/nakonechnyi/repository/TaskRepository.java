package org.nakonechnyi.repository;

import org.nakonechnyi.domain.Task;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class TaskRepository {
//
//    Connection connection = null;
//    Statement stmt = null;


//    public TaskRepository() {
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            System.out.println("Where is your MySQL JDBC Driver?");
//            e.printStackTrace();
//            return;
//        }
//
//        System.out.println("MySQL JDBC Driver Registered!");
//
//        try {
//            connection = DriverManager
//                    .getConnection("jdbc:mysql://localhost:3306/console_task_list","root", "polipoli");
//
//        } catch (SQLException e) {
//            System.out.println("Connection Failed! Check output console");
//            e.printStackTrace();
//            return;
//        }
//
//        if (connection != null) {
//            System.out.println("You made it, take control your database now!");
//        } else {
//            System.out.println("Failed to make connection!");
//        }
//    }


    public List<Task> getAll() {
        List<Task> result = new ArrayList<Task>();
        String sql = "SELECT id, name, date, priority, statusDone FROM Tasks";

        Connection conn = null;
        Statement stmt = null;
        ResultSet resultSet = null;

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            while(resultSet.next()){
                result.add(
                        new Task(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDate("date"),
                                resultSet.getInt("priority"),
                                resultSet.getByte("statusDone")
                        )
                );
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

        return result;
    }

    private Connection getDBConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver?");
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/console_task_list", "root", "polipol11");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
        }

        return connection;
    }

    public void updateStatus(boolean statusDone, int taskId) {
        String sqlQuery = "UPDATE console_task_list.tasks SET " +
                "statusDone = '"+ (statusDone == true ? (byte)1 : (byte)0) +
                "' WHERE id = "+ taskId;
        updateDB(sqlQuery);
    }

    private void updateDB(String sqlQuery) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getDBConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
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
        updateDB(sqlQuery);
    }
}
