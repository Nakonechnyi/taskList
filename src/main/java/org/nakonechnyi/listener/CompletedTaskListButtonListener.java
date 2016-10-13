package org.nakonechnyi.listener;

import org.nakonechnyi.service.TaskListTableService;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class CompletedTaskListButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent e) {
        new TaskListTableService().createAndShowCompleted();
    }
}
