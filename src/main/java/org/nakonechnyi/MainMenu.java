package org.nakonechnyi;

import org.nakonechnyi.listener.AddTaskListener;
import org.nakonechnyi.listener.GetTaskListListener;

import javax.swing.*;
import java.awt.*;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class MainMenu extends JFrame {
    public MainMenu() throws HeadlessException {
        super("Console Task List");
        setSize(300, 100);
        setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(new AddTaskListener());
        JButton getTaskListButton = new JButton("Get Task List");
        getTaskListButton.addActionListener(new GetTaskListListener());
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        panel.add(addTaskButton);
        panel.add(getTaskListButton);
        panel.add(exitButton);

        setContentPane(panel);
    }

}
