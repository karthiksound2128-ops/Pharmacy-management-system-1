package pharmacy.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewAllMedicines extends JFrame {
    private int userId;
    private JTable medicinesTable;
    
    public ViewAllMedicines(int userId) {
        this.userId = userId;
        setTitle("View All Medicines - Pharmacy Management System");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JButton backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(e -> {
            this.dispose();
            new AdminDashboard(userId, "Admin", "Admin");
        });
        
        Object[] columnNames = {"Medicine ID", "Name", "Company", "Category", "Quantity", "Price", "Expiry Date", "Batch Number"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        medicinesTable = new JTable(model);
        
        JScrollPane tableScrollPane = new JScrollPane(medicinesTable);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(backButton, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        
        add(mainPanel);
        loadMedicines();
        setVisible(true);
    }
    
    private void loadMedicines() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM medicines";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            DefaultTableModel model = (DefaultTableModel) medicinesTable.getModel();
            model.setRowCount(0);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("medicine_id"),
                    rs.getString("medicine_name"),
                    rs.getString("company_name"),
                    rs.getString("category"),
                    rs.getInt("quantity"),
                    rs.getDouble("price_per_unit"),
                    rs.getDate("expiry_date"),
                    rs.getString("batch_number")
                };
                model.addRow(row);
            }
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
