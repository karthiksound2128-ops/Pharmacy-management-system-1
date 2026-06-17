package pharmacy.system;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login extends JFrame {
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    
    public Login() {
        setTitle("Pharmacy Management System - Login");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));
        
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        userPanel.add(new JLabel("Username:"));
        usernameField = new JTextField(15);
        userPanel.add(usernameField);
        add(userPanel);
        
        JPanel passPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passPanel.add(new JLabel("Password:"));
        passwordField = new JTextField(15);
        passPanel.add(passwordField);
        add(passPanel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(loginButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel);
        
        loginButton.addActionListener(e -> login());
        cancelButton.addActionListener(e -> System.exit(0));
        
        setVisible(true);
    }
    
    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String role = rs.getString("role");
                String fullName = rs.getString("full_name");
                int userId = rs.getInt("user_id");
                
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                
                if (role.equals("Admin")) {
                    new AdminDashboard(userId, fullName, role);
                } else {
                    new PharmacistDashboard(userId, fullName, role);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        new Login();
    }
}
