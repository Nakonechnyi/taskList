package org.nakonechnyi.service;

import org.nakonechnyi.domain.ArchivedTask;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.repository.TaskArchiveRepository;
import org.nakonechnyi.repository.TaskRepository;

import java.util.Date;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class TaskService {

    TaskRepository taskRepository = new TaskRepository();
    public void create(Task task) {
        taskRepository.insert(task);
    }

    public Object[][] getAllInTableModelFormat() {
        List<Task> taskList = new TaskRepository().getAll();
        Object[][] result = new Object[taskList.size()][6];
        int rowIterator = 0;
        for (Task task: taskList) {
            result[rowIterator][0] = task.getId();
            result[rowIterator][1] = task.getName();
            result[rowIterator][2] = task.getDate();
            result[rowIterator][3] = task.getPriority();
            result[rowIterator][4] = (task.getDate().compareTo(new Date()) < 0 ? "Overdue!" : "" );
            rowIterator++;
        }
        return result;
    }

    public void saveAsDone(int taskId) {
        taskRepository.updateStatus(true, taskId);
    }

    public Object[][] getAllCompletedInTableModelFormat() {
        List<Task> taskList = new TaskRepository().getAllCompleted();
        Object[][] result = new Object[taskList.size()][4];
        int rowIterator = 0;
        for (Task task: taskList) {
            result[rowIterator][0] = task.getId();
            result[rowIterator][1] = task.getName();
            result[rowIterator][2] = task.getDate();
            result[rowIterator][3] = task.getPriority();
            rowIterator++;
        }
        return result;
    }

    public void sendToArchive(int taskId) {
        Task task = taskRepository.getById(taskId);
        new TaskArchiveRepository().insert(task);
        taskRepository.delete(taskId);
    }

    public Object[][] getAllArchivedInTableModelFormat() {
        List<ArchivedTask> taskList = new TaskArchiveRepository().getAll();
        Object[][] result = new Object[taskList.size()][7];
        int rowIterator = 0;
        for (ArchivedTask task: taskList) {
            result[rowIterator][0] = task.getId();
            result[rowIterator][1] = task.getOriginalId();
            result[rowIterator][2] = task.getName();
            result[rowIterator][3] = task.getDate();
            result[rowIterator][4] = task.getPriority();
            result[rowIterator][5] = (task.getStatusDone() == 1);
            result[rowIterator][6] = task.getArchivedOn();
            rowIterator++;
        }
        return result;
    }
}
