package pharmacy.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ManageUsers extends JFrame {
    private int userId;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField nameField;
    private JComboBox<String> roleComboBox;
    private JTextField phoneField;
    private JButton addButton;
    private JButton refreshButton;
    private JButton backButton;
    private JTable usersTable;
    
    public ManageUsers(int userId) {
        this.userId = userId;
        setTitle("Manage Users - Pharmacy Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        inputPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        inputPanel.add(usernameField);
        
        inputPanel.add(new JLabel("Password:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);
        
        inputPanel.add(new JLabel("Full Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);
        
        inputPanel.add(new JLabel("Role:"));
        roleComboBox = new JComboBox<>(new String[]{"Admin", "Pharmacist"});
        inputPanel.add(roleComboBox);
        
        inputPanel.add(new JLabel("Phone:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addButton = new JButton("Add User");
        refreshButton = new JButton("Refresh");
        backButton = new JButton("Back");
        
        buttonPanel.add(addButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        
        Object[] columnNames = {"User ID", "Username", "Password", "Full Name", "Role", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(model);
        
        JScrollPane tableScrollPane = new JScrollPane(usersTable);
        
        addButton.addActionListener(e -> addUser());
        refreshButton.addActionListener(e -> loadUsers());
        backButton.addActionListener(e -> {
            this.dispose();
            new AdminDashboard(userId, "Admin", "Admin");
        });
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        add(mainPanel);
        loadUsers();
        setVisible(true);
    }
    
    private void addUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = nameField.getText();
        String role = roleComboBox.getSelectedItem().toString();
        String phone = phoneField.getText();
        
        if (username.isEmpty() || password.isEmpty() || fullName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO users (username, password, full_name, role, phone) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.setString(3, fullName);
            pst.setString(4, role);
            pst.setString(5, phone);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "User added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
                nameField.setText("");
                phoneField.setText("");
                loadUsers();
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadUsers() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("full_name"),
                    rs.getString("role"),
                    rs.getString("phone")
                };
                model.addRow(row);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
