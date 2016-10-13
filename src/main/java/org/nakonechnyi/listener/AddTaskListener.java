package org.nakonechnyi.listener;

import org.nakonechnyi.domain.Task;
import org.nakonechnyi.service.TaskService;

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

    public void actionPerformed(ActionEvent e) {

        TaskService taskService = new TaskService();
        String taskName;
        String dateStr;
        Date date;
        int priority;
        try {
            taskName = JOptionPane.showInputDialog("Enter task name:");
            dateStr = JOptionPane.showInputDialog("Enter date in yyyy-MM-dd format (like \"2016-10-13\"):");

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            date = formatter.parse(dateStr);

            priority = Integer.parseInt(JOptionPane.showInputDialog("Enter priority:"));

            int confirm = JOptionPane.showConfirmDialog(null, "Save new task: " + taskName + ", on: " + date + " with " + priority + " priority?");
            if (confirm == 0 ) {
                taskService.create(new Task(taskName, date, priority));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
