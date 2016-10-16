package org.nakonechnyi.service;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class TaskListTableService {

    private JFrame frame = null;
    private JFrame complitedFrame = null;
    private JFrame archivedFrame = null;
    private JTable table = null;
    private DefaultTableModel model = null;
    private static final Object[] COLUMNS = {"Id", "Name", "Date", "Priority", "Comment"};
    private static final Object[] COMPLETED_COLUMNS = {"Id", "Name", "Date", "Priority"};
    private static final Object[] ARCHIVED_COLUMNS = {"Id", "Original Id", "Name", "Date", "Priority", "Status Done", "Archived On"};

    public void createAndShow() {
        frame = new JFrame("Task List");
        Object[][] data = new TaskService().getAllInTableModelFormat();

        model = new DefaultTableModel(data, COLUMNS);
        table = new JTable(model);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JPanel pnlButton = getButtons();

        frame.add(pnlButton, BorderLayout.SOUTH);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
        frame.revalidate();
    }

    public JPanel getButtons() {
        JPanel result = new JPanel();
        JButton mainMenuButton = new JButton("Main menu");
        mainMenuButton.addActionListener(e -> frame.dispose());

        JButton complitedTasksButton = new JButton("Completed tasks");
        complitedTasksButton.addActionListener( e -> createAndShowCompleted());

        JButton archivedTasksButton = new JButton("Archived tasks");
        archivedTasksButton.addActionListener(e -> createAndShowArchived());

        JButton saveAsDoneButton = new JButton("Save as Done");
        saveAsDoneButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int taskId = (Integer) model.getValueAt(row, 0);
                String taskName = (String) model.getValueAt(row, 1);
                int confirm = JOptionPane.showConfirmDialog(null, "Save task: " + taskName + " ( id: " + taskId + ") as Done?");
                if (confirm == 0 ) {
                    new TaskService().saveAsDone(taskId);
                }
                model.removeRow(row);
            }
        });

        JButton deleteAndArchiveButton = getDeleteButton(table, model);

        result.add(mainMenuButton);
        result.add(complitedTasksButton);
        result.add(archivedTasksButton);
        result.add(saveAsDoneButton);
        result.add(deleteAndArchiveButton);
        return result;
    }

    private JButton getDeleteButton(JTable table, DefaultTableModel model) {
        JButton deleteAndArchiveButton = new JButton("Delete and Archive");
        deleteAndArchiveButton.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                int taskId = (Integer) model.getValueAt(row, 0);
                String taskName = (String) model.getValueAt(row, 1);
                int confirm = JOptionPane.showConfirmDialog(null, "Delete and Archive task: " + taskName + " ( id: " + taskId + ")?");
                if (confirm == 0) {
                    new TaskService().sendToArchive(taskId);
                }
                model.removeRow(row);
            }
        });
        return deleteAndArchiveButton;
    }

    public void createAndShowCompleted() {
        complitedFrame = new JFrame("Complited Task List");
        JPanel panel = new JPanel();
        Object[][] data = new TaskService().getAllCompletedInTableModelFormat();

        DefaultTableModel model = new DefaultTableModel(data, COMPLETED_COLUMNS);
        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JButton closeCompletedList = new JButton("Close");
        closeCompletedList.addActionListener(e -> complitedFrame.dispose());

        JButton deleteAndArchiveButton = getDeleteButton(table, model);

        panel.add(deleteAndArchiveButton);
        panel.add(closeCompletedList);

        complitedFrame.add(panel, BorderLayout.SOUTH);
        complitedFrame.add(new JScrollPane(table));
        complitedFrame.pack();
        complitedFrame.setVisible(true);
        complitedFrame.revalidate();
    }

    public void createAndShowArchived() {
        archivedFrame = new JFrame("Archived Task List");
        Object[][] data = new TaskService().getAllArchivedInTableModelFormat();

        DefaultTableModel model = new DefaultTableModel(data, ARCHIVED_COLUMNS);
        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JButton closeArchivedList = new JButton("Close");
        closeArchivedList.addActionListener(e -> archivedFrame.dispose());
        archivedFrame.add(closeArchivedList, BorderLayout.SOUTH);
        archivedFrame.add(new JScrollPane(table));
        archivedFrame.pack();
        archivedFrame.setVisible(true);
        archivedFrame.revalidate();
    }

}
