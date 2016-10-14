package org.nakonechnyi.service;

import org.nakonechnyi.listener.CompletedTaskListButtonListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Date;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class TaskListTableService implements TableModelListener {

    JFrame frame = new JFrame("Task List");
    JFrame complitedFrame = new JFrame("Complited Task List");
    private static final Object[] COLUMNS = {"Id", "Name", "Date", "Priority", "Status", "Comment"};
    private static final Object[] COMPLITED_COLUMNS = {"Id", "Name", "Date", "Priority"};

    public void createAndShow() {


        Object[][] data = new TaskService().getAllInTableModelFormat();

        DefaultTableModel model = new DefaultTableModel(data, COLUMNS);
        model.addTableModelListener(this);
        JTable table = new JTable(model) {

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return String.class;
                    case 2:
                        return Date.class;
                    case 3:
                        return Integer.class;
                    case 5:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == 4 ? true : false);
            }
        };

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JPanel pnlButton = getButtons();

        frame.add(pnlButton, BorderLayout.SOUTH);
        frame.add(new JScrollPane(table));
        frame.pack();
        frame.setVisible(true);
        frame.revalidate();
    }

    public void tableChanged(TableModelEvent e) {
        int row = e.getFirstRow();
        TableModel model = (TableModel)e.getSource();
        String taskName = (String) model.getValueAt(row, 1);
        int taskId = (Integer) model.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(null, "Save task: " + taskName + " ( id: " + taskId + ") as Done?");
        if (confirm == 0 ) {
            new TaskService().saveAsDone(taskId);
        }
    }

    public JPanel getButtons() {
        JPanel result = new JPanel();
        JButton mainMenuButton = new JButton("Main menu");
        mainMenuButton.addActionListener(e -> frame.dispose());

        JButton complitedTasksButton = new JButton("Completed tasks");
        complitedTasksButton.addActionListener(new CompletedTaskListButtonListener());

        result.add(mainMenuButton);
        result.add(complitedTasksButton);
        return result;
    }

    public void createAndShowCompleted() {
        Object[][] data = new TaskService().getAllCompletedInTableModelFormat();

        DefaultTableModel model = new DefaultTableModel(data, COMPLITED_COLUMNS);
        model.addTableModelListener(this);
        JTable table = new JTable(model);

        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        JButton closeCompletedList = new JButton("Close");
        closeCompletedList.addActionListener(e -> complitedFrame.dispose());
        complitedFrame.add(closeCompletedList, BorderLayout.SOUTH);
        complitedFrame.add(new JScrollPane(table));
        complitedFrame.pack();
        complitedFrame.setVisible(true);
        complitedFrame.revalidate();
    }
}
