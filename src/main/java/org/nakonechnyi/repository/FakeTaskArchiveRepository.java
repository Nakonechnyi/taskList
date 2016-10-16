package org.nakonechnyi.repository;

import org.apache.log4j.Logger;
import org.nakonechnyi.domain.ArchivedTask;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.util.AppProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 16.10.2016.
 */
@Deprecated
public class FakeTaskArchiveRepository {
    private static List<ArchivedTask> archivedTasks = fillData();
    final static Logger logger = Logger.getLogger(FakeTaskArchiveRepository.class);

        private static List<ArchivedTask> fillData() {
            List<ArchivedTask> result = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat(AppProperties.DATE_FORMAT);
            try {
                result.add(new ArchivedTask ( "Work", formatter.parse("2016-10-10"), 1, (byte)1, new java.sql.Date(Calendar.getInstance().getTime().getTime()), 55));
                result.add(new ArchivedTask ( "Sleep", formatter.parse("2016-10-10"), 2, (byte)0 , new java.sql.Date(0L), 54));

            } catch (ParseException e) {
                logger.error(e);
            }
            return result;
        }

        public static List<ArchivedTask> getAll() {
            return archivedTasks;
        }


    public static void insert(Task task) {
        int maxId = 0;
        for( ArchivedTask iter: archivedTasks) {
            if (iter.getId() > maxId) {
                maxId = iter.getId();
            }
        }
        task.setId(++maxId);
        ArchivedTask prepsred = new ArchivedTask(
                task.getName(),
                task.getDate(),
                task.getPriority(),
                task.getStatusDone(),
                new java.sql.Date(Calendar.getInstance().getTime().getTime()),
                task.getId());
        archivedTasks.add(prepsred);
    }

    public static boolean delete(int taskId) {
        archivedTasks.removeIf(task -> task.getId() == taskId);
        return true;
    }
}
