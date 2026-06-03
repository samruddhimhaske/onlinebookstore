package com.bookstore.ui;

import com.bookstore.service.AuthService;
import com.bookstore.dao.OrderDAO;
import com.bookstore.model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminDashboardFrame extends BaseFrame {

    private OrderDAO orderDAO;
    private com.bookstore.dao.UserDAO userDAO;

    public AdminDashboardFrame() {
        super("Admin Dashboard - Online Book Store");
        orderDAO = new OrderDAO();
        userDAO = new com.bookstore.dao.UserDAO();
        
        setCenterScreen(800, 500);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel welcomeLabel = new JLabel("Admin Control Panel");
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(TITLE_FONT);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JButton logoutBtn = createStyledButton("Logout", Color.RED);
        logoutBtn.addActionListener(e -> {
            new AuthService().logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        topPanel.add(logoutBtn, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 20, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JButton viewUsersBtn = createStyledButton("User Management", SECONDARY_COLOR);
        JButton viewBooksBtn = createStyledButton("Inventory Management", SECONDARY_COLOR);
        JButton manageCatBtn = createStyledButton("Category Management", SECONDARY_COLOR);
        JButton viewOrdersBtn = createStyledButton("View All Orders", SECONDARY_COLOR);
        JButton reportsBtn = createStyledButton("Generate Reports", ACCENT_COLOR);

        viewUsersBtn.addActionListener(e -> new UserManagementDialog(this).setVisible(true));
        viewBooksBtn.addActionListener(e -> new BookManagementDialog(this).setVisible(true));
        manageCatBtn.addActionListener(e -> new CategoryManagementDialog(this).setVisible(true));
        viewOrdersBtn.addActionListener(e -> showOrdersDialog());
        reportsBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Module stub: Advanced Reports (Phase 4/5)"));

        centerPanel.add(viewUsersBtn);
        centerPanel.add(viewBooksBtn);
        centerPanel.add(manageCatBtn);
        centerPanel.add(viewOrdersBtn);
        centerPanel.add(reportsBtn);

        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void showOrdersDialog() {
        JDialog dialog = new JDialog(this, "All Orders History", true);
        dialog.setSize(800, 450);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        
        String[] columns = {"Order ID", "User ID", "Date", "Tax", "Total Amount", "Status"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        
        List<Order> orders = orderDAO.getAllOrders();
        for (Order o : orders) {
             model.addRow(new Object[]{o.getOrderId(), o.getUserId(), o.getOrderDate(), o.getTaxAmount(), o.getTotalAmount(), o.getStatus() + (o.getDeliveryDays() > 0 ? " (Delivery: " + o.getDeliveryDays() + " days)" : "")});
        }
        
        JTable table = new JTable(model);
        dialog.add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton viewOrderBtn = new JButton("View Order & Dispatch (Assign Delivery)");
        viewOrderBtn.setBackground(new Color(41, 128, 185));
        viewOrderBtn.setForeground(Color.WHITE);
        
        viewOrderBtn.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(dialog, "Please select an Order from the list!");
                return;
            }
            int orderId = (int) model.getValueAt(selectedRow, 0);
            int userId = (int) model.getValueAt(selectedRow, 1);
            
            com.bookstore.model.User user = userDAO.getAllUsers().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);
            
            if (user != null) {
                JPanel detailPanel = new JPanel(new GridLayout(0, 1, 5, 5));
                detailPanel.add(new JLabel("<html><b>Customer Name:</b> " + user.getName() + "</html>"));
                detailPanel.add(new JLabel("<html><b>Contact:</b> " + user.getPhone() + " | " + user.getEmail() + "</html>"));
                detailPanel.add(new JLabel("<html><b>Shipping Address:</b> " + user.getAddress() + "</html>"));
                detailPanel.add(new JLabel(" "));
                detailPanel.add(new JLabel("Assign Delivery Time (in days):"));
                JTextField daysField = new JTextField();
                detailPanel.add(daysField);
                
                int option = JOptionPane.showConfirmDialog(dialog, detailPanel, "Dispatch Order #" + orderId, JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        int days = Integer.parseInt(daysField.getText().trim());
                        if (orderDAO.updateDeliveryDays(orderId, days)) {
                             JOptionPane.showMessageDialog(dialog, "Order dispatched! Delivery set to " + days + " days.");
                             dialog.dispose();
                             showOrdersDialog(); // Re-open refreshing data
                        } else {
                             JOptionPane.showMessageDialog(dialog, "Database update failed.");
                        }
                    } catch(Exception ex) {
                        JOptionPane.showMessageDialog(dialog, "Valid number required.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Error: Customer data not found.");
            }
        });

        bottomPanel.add(viewOrderBtn);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}
