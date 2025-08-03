import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class TaskManager extends JFrame {

    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskTitleField;
    private JTextArea taskDescriptionArea;
    private ArrayList<String> taskTitles;
    private ArrayList<String> taskDescriptions;

    private JTextArea showTasksArea;

    public TaskManager() {
        setTitle("Task Manager");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        taskTitles = new ArrayList<>();
        taskDescriptions = new ArrayList<>();
        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);

        
        JTabbedPane tabbedPane = new JTabbedPane();

        
        JPanel managePanel = new JPanel(new BorderLayout());

        JScrollPane taskListScrollPane = new JScrollPane(taskList);

        JLabel titleLabel = new JLabel("Task Title:");
        taskTitleField = new JTextField(20);

        JLabel descriptionLabel = new JLabel("Description:");
        taskDescriptionArea = new JTextArea(5, 20);
        JScrollPane descriptionScrollPane = new JScrollPane(taskDescriptionArea);

        JButton addButton = new JButton("Add Task");
        JButton deleteButton = new JButton("Delete Task");

        addButton.addActionListener(e -> addTask());
        deleteButton.addActionListener(e -> deleteTask());

        JPanel inputPanel = new JPanel(new GridLayout(4, 1));
        inputPanel.add(titleLabel);
        inputPanel.add(taskTitleField);
        inputPanel.add(descriptionLabel);
        inputPanel.add(descriptionScrollPane);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(inputPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        managePanel.add(taskListScrollPane, BorderLayout.CENTER);
        managePanel.add(southPanel, BorderLayout.SOUTH);

        
        JPanel showPanel = new JPanel(new BorderLayout());
        showTasksArea = new JTextArea();
        showTasksArea.setEditable(false);
        showTasksArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane showScrollPane = new JScrollPane(showTasksArea);
        showPanel.add(showScrollPane, BorderLayout.CENTER);

        
        tabbedPane.addTab("Manage Tasks", managePanel);
        tabbedPane.addTab("Show Tasks", showPanel);

       
        tabbedPane.addChangeListener(e -> {
            if (tabbedPane.getSelectedIndex() == 1) {
                updateShowTasksArea();
            }
        });

        add(tabbedPane);
        setVisible(true);
    }

    private void addTask() {
        String title = taskTitleField.getText().trim();
        String description = taskDescriptionArea.getText().trim();

        if (!title.isEmpty()) {
            taskTitles.add(title);
            taskDescriptions.add(description);
            taskListModel.addElement(title);

            taskTitleField.setText("");
            taskDescriptionArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Task title cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            taskListModel.remove(selectedIndex);
            taskTitles.remove(selectedIndex);
            taskDescriptions.remove(selectedIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateShowTasksArea() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < taskTitles.size(); i++) {
            sb.append("Task ").append(i + 1).append(":\n");
            sb.append("Title: ").append(taskTitles.get(i)).append("\n");
            sb.append("Description: ").append(taskDescriptions.get(i)).append("\n");
            sb.append("--------------------------------------------------\n");
        }

        if (taskTitles.isEmpty()) {
            sb.append("No tasks added yet.");
        }

        showTasksArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskManager::new);
    }
}
