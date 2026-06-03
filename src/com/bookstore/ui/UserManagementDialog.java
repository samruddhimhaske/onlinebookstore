package com.bookstore.ui;

import com.bookstore.dao.UserDAO;
import com.bookstore.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserManagementDialog extends JDialog {

    private UserDAO userDAO;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public UserManagementDialog(JFrame parent) {
        super(parent, "Manage Users", true);
        setSize(700, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        userDAO = new UserDAO();

        String[] cols = {"User ID", "Name", "Email", "Phone", "Role", "Active Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        userTable = new JTable(tableModel);
        add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnViewDetails = new JButton("View User Details");
        JButton btnToggleStatus = new JButton("Block / Unblock User");
        JButton btnDelete = new JButton("Delete User Account");

        btnViewDetails.addActionListener(e -> viewUserDetails());
        btnToggleStatus.addActionListener(e -> toggleUserStatus());
        btnDelete.addActionListener(e -> deleteUser());

        btnPanel.add(btnViewDetails);
        btnPanel.add(btnToggleStatus);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<User> users = userDAO.getAllUsers();
        for (User u : users) {
             tableModel.addRow(new Object[]{u.getId(), u.getName(), u.getEmail(), u.getPhone(), u.getRole(), u.isActive() ? "Active" : "Blocked"});
        }
    }

    private void viewUserDetails() {
        int selected = userTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }

        int userId = (int) tableModel.getValueAt(selected, 0);
        User user = userDAO.getAllUsers().stream().filter(u -> u.getId() == userId).findFirst().orElse(null);

        if (user != null) {
            String details = "<html><table border='1' cellspacing='0' cellpadding='5'>" +
                    "<tr><td><b>Name:</b></td><td>" + user.getName() + "</td></tr>" +
                    "<tr><td><b>Email:</b></td><td>" + user.getEmail() + "</td></tr>" +
                    "<tr><td><b>Phone:</b></td><td>" + user.getPhone() + "</td></tr>" +
                    "<tr><td><b>Role:</b></td><td>" + user.getRole() + "</td></tr>" +
                    "<tr><td><b>Address:</b></td><td>" + user.getAddress() + "</td></tr>" +
                    "</table></html>";
            JOptionPane.showMessageDialog(this, details, "User Detailed Profile", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void toggleUserStatus() {
        int selected = userTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selected, 0);
        String role = (String) tableModel.getValueAt(selected, 4);
        boolean isActive = "Active".equals(tableModel.getValueAt(selected, 5));
        
        if ("ADMIN".equals(role)) {
            JOptionPane.showMessageDialog(this, "Cannot block an Admin account.");
            return;
        }
        
        if (userDAO.updateUserStatus(userId, !isActive)) {
            refreshTable();
            JOptionPane.showMessageDialog(this, "User status updated to: " + (!isActive ? "Active" : "Blocked"));
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update user status.");
        }
    }

    private void deleteUser() {
        int selected = userTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a user first.");
            return;
        }
        
        int userId = (int) tableModel.getValueAt(selected, 0);
        String role = (String) tableModel.getValueAt(selected, 4);
        
        if ("ADMIN".equals(role)) {
            JOptionPane.showMessageDialog(this, "Cannot delete an Admin account.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure? This will delete all user orders & carts.", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (userDAO.deleteUser(userId)) {
                refreshTable();
                JOptionPane.showMessageDialog(this, "User safely removed.");
            } else {
                JOptionPane.showMessageDialog(this, "Delete operation failed.");
            }
        }
    }
}
