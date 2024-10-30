import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ToDoListApp extends JFrame {
    private DefaultListModel<TodoItem> listModel;
    private JList<TodoItem> todoList;
    private JTextField inputField;
    
    public ToDoListApp() {
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);
        setLayout(new BorderLayout(10, 10));
        
        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        todoList.setCellRenderer(new TodoListCellRenderer());
        
        inputField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton clearAllButton = new JButton("Clear All");
        JButton toggleDoneButton = new JButton("Toggle Done");
        
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(toggleDoneButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearAllButton);
        
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(todoList), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        
        addButton.addActionListener(e -> addItem());
        
        inputField.addActionListener(e -> addItem());
        
        removeButton.addActionListener(e -> {
            int selectedIndex = todoList.getSelectedIndex();
            if (selectedIndex != -1) {
                listModel.remove(selectedIndex);
            }
        });
        
        clearAllButton.addActionListener(e -> {
            listModel.clear();
        });
        
        toggleDoneButton.addActionListener(e -> {
            int selectedIndex = todoList.getSelectedIndex();
            if (selectedIndex != -1) {
                TodoItem item = listModel.getElementAt(selectedIndex);
                item.toggleDone();
                todoList.repaint();
            }
        });
    }
    
    private void addItem() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            listModel.addElement(new TodoItem(text));
            inputField.setText("");
        }
    }
}

class TodoItem {
    private String text;
    private boolean isDone;
    
    public TodoItem(String text) {
        this.text = text;
        this.isDone = false;
    }
    
    public String getText() {
        return text;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public void toggleDone() {
        isDone = !isDone;
    }
    
    @Override
    public String toString() {
        return text;
    }
}

class TodoListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, 
            int index, boolean isSelected, boolean cellHasFocus) {
        
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus);
        
        TodoItem item = (TodoItem) value;
        if (item.isDone()) {
            label.setText("<html><strike>" + item.getText() + "</strike></html>");
        } else {
            label.setText(item.getText());
        }
        
        return label;
    }
}

class program {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ToDoListApp app = new ToDoListApp();
            app.setLocationRelativeTo(null);
            app.setVisible(true);
        });
    }
}