package com.bookstore.ui;

import com.bookstore.dao.CategoryDAO;
import com.bookstore.model.Category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoryManagementDialog extends JDialog {

    private CategoryDAO categoryDAO;
    private JTable categoryTable;
    private DefaultTableModel tableModel;

    public CategoryManagementDialog(JFrame parent) {
        super(parent, "Manage Categories", true);
        setSize(400, 400);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        categoryDAO = new CategoryDAO();

        String[] cols = {"ID", "Category Name"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        categoryTable = new JTable(tableModel);
        add(new JScrollPane(categoryTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Add Category");
        JButton btnUpdate = new JButton("Update Category");
        JButton btnDelete = new JButton("Delete Category");

        btnAdd.addActionListener(e -> addCategory());
        btnUpdate.addActionListener(e -> updateCategory());
        btnDelete.addActionListener(e -> deleteCategory());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Category> categories = categoryDAO.getAllCategories();
        for (Category c : categories) {
            tableModel.addRow(new Object[]{c.getCategoryId(), c.getName()});
        }
    }

    private void addCategory() {
        String name = JOptionPane.showInputDialog(this, "Enter New Category Name:");
        if (name != null && !name.trim().isEmpty()) {
            if (categoryDAO.addCategory(name.trim())) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to add category.");
            }
        }
    }

    private void updateCategory() {
        int selected = categoryTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a category to update.");
            return;
        }
        int id = (int) tableModel.getValueAt(selected, 0);
        String oldName = (String) tableModel.getValueAt(selected, 1);
        
        String newName = JOptionPane.showInputDialog(this, "Update Category Name:", oldName);
        if (newName != null && !newName.trim().isEmpty()) {
            if (categoryDAO.updateCategory(id, newName.trim())) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update category.");
            }
        }
    }

    private void deleteCategory() {
        int selected = categoryTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a category to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(selected, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure? This may affect books inside this category.", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (categoryDAO.deleteCategory(id)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed. Some books might rely on this category.");
            }
        }
    }
}
