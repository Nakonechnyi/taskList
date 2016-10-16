package test.org.nakonechnyi.repository;

import org.junit.Assert;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.repository.TaskRepository;
import org.nakonechnyi.util.AppProperties;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @autor A_Nakonechnyi
 * @date 17.10.2016.
 */
public class TaskRepositoryTest {

    private static final String NAME = "Unit test";
    private static final int PRIORITY = 1;
    private static final java.util.Date DATE = new java.util.Date();
    private static final TaskRepository TASK_REPOSITORY = new TaskRepository();
    private Task task = null;

    @org.junit.Test
    public void testGetAllCompleted() throws Exception {
        List<Task> listToTest  = TASK_REPOSITORY.getAllCompleted();
        Assert.assertEquals(listToTest.size() > 0, true);
    }

    @org.junit.Test
    public void testGetAll() throws Exception {
        List<Task> listToTest  = TASK_REPOSITORY.getAll();
        Assert.assertEquals(listToTest.size() > 0, true);
    }

    @org.junit.Test
    public void testInsertGetUpdateDelete() throws Exception {
        Assert.assertEquals(TASK_REPOSITORY.insert(new Task(NAME, DATE, PRIORITY)), true);

        SimpleDateFormat formatter = new SimpleDateFormat(AppProperties.DATE_FORMAT);
        List<Task> taskList = TASK_REPOSITORY.getAll();
        task = taskList.stream().filter(taskInStream ->{
                    try {
                        return taskInStream.getName().equals(NAME) &&
                                    taskInStream.getDate().compareTo(formatter.parse(formatter.format(DATE))) == 0 &&
                                    taskInStream.getPriority() == PRIORITY &&
                                    taskInStream.getStatusDone() == (byte)0;
                    } catch (ParseException e) {
                        return false;
                    }
                }
        ).findFirst().get();

        Assert.assertEquals(TASK_REPOSITORY.getById(task.getId()).getName(), NAME);

        Assert.assertEquals(TASK_REPOSITORY.updateStatus(true, task.getId()), true);

        Assert.assertEquals(new TaskRepository().delete(task.getId()), true);    }
}