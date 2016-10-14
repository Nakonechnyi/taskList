package org.nakonechnyi.listener;

import org.apache.log4j.Logger;
import org.nakonechnyi.util.AppProperties;
import org.nakonechnyi.domain.Task;
import org.nakonechnyi.service.TaskService;
import org.nakonechnyi.util.Validator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class AddTaskListener implements ActionListener {

    final static Logger logger = Logger.getLogger(AddTaskListener.class);

    public void actionPerformed(ActionEvent e) {

        TaskService taskService = new TaskService();
        String taskName = null;
        String dateStr = null;
        String priorityStr = null;
        Date date;
        int priority = 0;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(AppProperties.DATE_FORMAT);
            while (!Validator.isTaskNameValid(taskName)) {
                taskName = JOptionPane.showInputDialog("Enter task name:");
            }
            while (!Validator.isDateStrValid(dateStr)) {
                dateStr = JOptionPane.showInputDialog("Enter date in " + AppProperties.DATE_FORMAT +
                        " format (like " + formatter.format(new Date()) + "):");
            }
            date = formatter.parse(dateStr);

            while (!Validator.isPriorityValid(priorityStr)) {
                priorityStr = JOptionPane.showInputDialog("Enter priority:");
            }
            priority = Integer.parseInt(priorityStr);
            int confirm = JOptionPane.showConfirmDialog(null, "Save new task: " + taskName + ", on: " + date + " with " + priority + " priority?");
            if (confirm == 0 ) {
                taskService.create(new Task(taskName, date, priority));
            }
        } catch (ParseException ex) {
            logger.info("ParseException.", ex);
        }
    }
}
