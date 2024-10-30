import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

class Theme {
    private final Color background;
    private final Color foreground;
    private final Color buttonBackground;
    private final Color buttonForeground;
    private final Color inputBackground;
    private final Color inputForeground;
    private final Color listBackground;
    private final Color listForeground;
    private final Color selectionBackground;
    private final Color selectionForeground;
    
    public Theme(Color background, Color foreground, Color buttonBackground, 
                Color buttonForeground, Color inputBackground, Color inputForeground,
                Color listBackground, Color listForeground, 
                Color selectionBackground, Color selectionForeground) {
        this.background = background;
        this.foreground = foreground;
        this.buttonBackground = buttonBackground;
        this.buttonForeground = buttonForeground;
        this.inputBackground = inputBackground;
        this.inputForeground = inputForeground;
        this.listBackground = listBackground;
        this.listForeground = listForeground;
        this.selectionBackground = selectionBackground;
        this.selectionForeground = selectionForeground;
    }
    
    public Color getBackground() { return background; }
    public Color getForeground() { return foreground; }
    public Color getButtonBackground() { return buttonBackground; }
    public Color getButtonForeground() { return buttonForeground; }
    public Color getInputBackground() { return inputBackground; }
    public Color getInputForeground() { return inputForeground; }
    public Color getListBackground() { return listBackground; }
    public Color getListForeground() { return listForeground; }
    public Color getSelectionBackground() { return selectionBackground; }
    public Color getSelectionForeground() { return selectionForeground; }
}

class ThemeManager {
    private static final Map<String, Theme> themes = new HashMap<>();
    
    static {
        themes.put("Default", new Theme(
            UIManager.getColor("Panel.background"),
            UIManager.getColor("Panel.foreground"),
            UIManager.getColor("Button.background"),
            UIManager.getColor("Button.foreground"),
            UIManager.getColor("TextField.background"),
            UIManager.getColor("TextField.foreground"),
            UIManager.getColor("List.background"),
            UIManager.getColor("List.foreground"),
            UIManager.getColor("List.selectionBackground"),
            UIManager.getColor("List.selectionForeground")
        ));
        
        themes.put("Modern Light", new Theme(
            new Color(240, 240, 240),
            new Color(60, 60, 60),
            new Color(225, 225, 225),
            new Color(60, 60, 60),
            Color.WHITE,
            new Color(60, 60, 60),
            Color.WHITE,
            new Color(60, 60, 60),
            new Color(200, 200, 200),
            new Color(60, 60, 60)
        ));
        
        themes.put("Modern Dark", new Theme(
            new Color(50, 50, 50),
            new Color(230, 230, 230),
            new Color(70, 70, 70),
            new Color(230, 230, 230),
            new Color(60, 60, 60),
            new Color(230, 230, 230),
            new Color(60, 60, 60),
            new Color(230, 230, 230),
            new Color(80, 80, 80),
            new Color(230, 230, 230)
        ));
        
        themes.put("Pink", new Theme(
            new Color(255, 240, 245),
            new Color(199, 21, 133),
            new Color(255, 182, 193),
            new Color(199, 21, 133),
            Color.WHITE,
            new Color(199, 21, 133),
            Color.WHITE,
            new Color(199, 21, 133),
            new Color(255, 182, 193),
            new Color(199, 21, 133)
        ));
        
        themes.put("Green", new Theme(
            new Color(240, 255, 240),
            new Color(34, 139, 34),
            new Color(144, 238, 144),
            new Color(34, 139, 34),
            Color.WHITE,
            new Color(34, 139, 34),
            Color.WHITE,
            new Color(34, 139, 34),
            new Color(144, 238, 144),
            new Color(34, 139, 34)
        ));
        
        themes.put("Dark Blue", new Theme(
            new Color(25, 25, 112),
            Color.WHITE,
            new Color(0, 0, 139),
            Color.WHITE,
            new Color(30, 30, 139),
            Color.WHITE,
            new Color(30, 30, 139),
            Color.WHITE,
            new Color(0, 0, 139),
            Color.WHITE
        ));
    }
    
    public static Theme getTheme(String name) {
        return themes.get(name);
    }
    
    public static String[] getThemeNames() {
        return themes.keySet().toArray(new String[0]);
    }
}

class ToDoListApp extends JFrame {
    private DefaultListModel<TodoItem> listModel;
    private JList<TodoItem> todoList;
    private JTextField inputField;
    private JComboBox<String> themeSelector;
    private JPanel buttonPanel;
    
    public ToDoListApp() {
        setTitle("To-Do List");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(520, 400);
        setLayout(new BorderLayout(10, 10));
        
        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        todoList.setCellRenderer(new TodoListCellRenderer());
        
        inputField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton removeButton = new JButton("Remove");
        JButton clearAllButton = new JButton("Clear All");
        JButton toggleDoneButton = new JButton("Toggle Done");
        JButton saveButton = new JButton("Save");
        JButton saveAsButton = new JButton("Save As");
        JButton openButton = new JButton("Open");
        
        themeSelector = new JComboBox<>(ThemeManager.getThemeNames());
        
        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
        
        JPanel themePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        themePanel.add(new JLabel("Theme: "));
        themePanel.add(themeSelector);
        
        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        
        topPanel.add(themePanel, BorderLayout.NORTH);
        topPanel.add(inputPanel, BorderLayout.SOUTH);
        
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        buttonPanel.add(toggleDoneButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(clearAllButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveAsButton);
        buttonPanel.add(openButton);
        
        add(topPanel, BorderLayout.NORTH);
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
        
        themeSelector.addActionListener(e -> applyTheme((String) themeSelector.getSelectedItem()));
        
        saveButton.addActionListener(e -> saveToFile(false));
        saveAsButton.addActionListener(e -> saveToFile(true));
        openButton.addActionListener(e -> loadFromFile());
        
        applyTheme("Default");
    }
    
    private void applyTheme(String themeName) {
        Theme theme = ThemeManager.getTheme(themeName);
        if (theme == null) return;
        
        getContentPane().setBackground(theme.getBackground());
        getContentPane().setForeground(theme.getForeground());
        
        applyThemeToContainer(getContentPane(), theme);
        
        buttonPanel.setBackground(theme.getBackground());
        for (Component comp : buttonPanel.getComponents()) {
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(theme.getButtonBackground());
                button.setForeground(theme.getButtonForeground());
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.setFocusPainted(false);
            }
        }
        
        todoList.setBackground(theme.getListBackground());
        todoList.setForeground(theme.getListForeground());
        todoList.setSelectionBackground(theme.getSelectionBackground());
        todoList.setSelectionForeground(theme.getSelectionForeground());
        
        SwingUtilities.updateComponentTreeUI(this);
    }
    
    private void applyThemeToContainer(Container container, Theme theme) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel panel = (JPanel) comp;
                panel.setBackground(theme.getBackground());
                panel.setForeground(theme.getForeground());
                applyThemeToContainer(panel, theme);
            } else if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                button.setBackground(theme.getButtonBackground());
                button.setForeground(theme.getButtonForeground());
                button.setOpaque(true);
                button.setBorderPainted(true);
                button.setFocusPainted(false);
            } else if (comp instanceof JTextField) {
                comp.setBackground(theme.getInputBackground());
                comp.setForeground(theme.getInputForeground());
            } else if (comp instanceof JScrollPane) {
                comp.setBackground(theme.getListBackground());
                ((JScrollPane)comp).getViewport().setBackground(theme.getListBackground());
            }
        }
    }
    
    private void addItem() {
        String text = inputField.getText().trim();
        if (!text.isEmpty()) {
            listModel.addElement(new TodoItem(text));
            inputField.setText("");
        }
    }
    
    private void saveToFile(boolean saveAs) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save To-Do List");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("To-Do List Files", "ser");
        fileChooser.setFileFilter(filter);
        
        int userSelection = saveAs ? fileChooser.showSaveDialog(this) : fileChooser.showDialog(this, "Save");
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filename = file.getAbsolutePath();
            if (!filename.endsWith(".ser")) {
                file = new File(filename + ".ser");
            }
            
            try {
                saveToFile(file);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void saveToFile(File file) throws IOException {
        List<TodoItem> items = new ArrayList<>();
        for (int i = 0; i < listModel.size(); i++) {
            items.add(listModel.get(i));
        }
        
        String selectedTheme = (String) themeSelector.getSelectedItem();
        
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(items);
            out.writeObject(selectedTheme);
        }
    }
    
    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Open To-Do List");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("To-Do List Files", "ser");
        fileChooser.setFileFilter(filter);
        
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                loadFromFile(file);
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            List<TodoItem> items = (List<TodoItem>) in.readObject();
            listModel.clear();
            items.forEach(listModel::addElement);
            
            String themeName = (String) in.readObject();
            themeSelector.setSelectedItem(themeName);
            applyTheme(themeName);
        }
    }
}

class TodoItem implements Serializable {
    private String text;
    private boolean isDone;
    
    public TodoItem(String text) {
        this.text = text;
        this.isDone = false;
    }
    
    public String getText() { return text; }
    public boolean isDone() { return isDone; }
    public void toggleDone() { isDone = !isDone; }
    
    @Override
    public String toString() { return text; }
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