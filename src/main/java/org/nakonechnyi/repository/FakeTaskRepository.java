package org.nakonechnyi.repository;

import org.nakonechnyi.util.AppProperties;
import org.nakonechnyi.domain.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @autor A_Nakonechnyi
 * @date 13.10.2016.
 */
public class FakeTaskRepository {

    private static List<Task> tasks = fillData();

    private static List<Task> fillData() {
        List<Task> result = new ArrayList<>();

        SimpleDateFormat formatter = new SimpleDateFormat(AppProperties.DATE_FORMAT);
        try {
            result.add(new Task (1, "Build House", formatter.parse("2016-10-10"), 1, (byte)1 ));
            result.add(new Task (2, "Build House2", formatter.parse("2016-10-10"), 2, (byte)0 ));
            result.add(new Task (3, "Plant tree", formatter.parse("2016-10-10"), 2, (byte)1 ));
            result.add(new Task (4, "Grow son", formatter.parse("2025-10-10"), 3, (byte)0 ));
            result.add(new Task (5, "Grow son2", formatter.parse("2027-10-10"), 3, (byte)0 ));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Task> getAllCompleted() {
        return tasks.stream().filter(task -> task.getStatusDone()==(byte)1).collect(Collectors.toList());
    }

    public static List<Task> getAll() {
        return tasks.stream().filter(task -> task.getStatusDone()==(byte)0).collect(Collectors.toList());
    }

    public static void updateStatus(boolean statusDone, int taskId) {
        tasks.stream().filter(task -> task.getId() == taskId).forEach(task -> task.setStatusDone(statusDone == true ? (byte) 1 : (byte) 0 ));
    }

    public static void insert(Task task) {
        int maxId = 0;
        for( Task iter: tasks) {
            if (iter.getId() > maxId) {
                maxId = iter.getId();
            }
        }
        task.setId(++maxId);
        task.setStatusDone((byte)0);
        tasks.add(task);
    }
}
