package org.nakonechnyi;

import org.nakonechnyi.listener.AddTaskListener;
import org.nakonechnyi.listener.GetTaskListListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class MainMenu extends JFrame implements ActionListener{
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
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        panel.add(addTaskButton);
        panel.add(getTaskListButton);
        panel.add(exitButton);

        setContentPane(panel);
    }

    public void actionPerformed(ActionEvent e) {
//        String name = e.getActionCommand();
//        if (name.equals("Add Task")) {
//            System.out.println("a");
//        } else if (name.equals("Get Task List")) {
//            System.out.println("Get Task List");
//        } else if (name.equals("Exit")) {
//            System.out.println("Exit");
//        }
    }
}
