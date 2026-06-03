package com.bookstore.ui;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.model.Book;
import com.bookstore.model.Category;
import com.bookstore.model.PhysicalBook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookManagementDialog extends JDialog {

    private BookDAO bookDAO;
    private CategoryDAO categoryDAO;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BookManagementDialog(JFrame parent) {
        super(parent, "Manage Books Inventory", true);
        setSize(800, 500);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        bookDAO = new BookDAO();
        categoryDAO = new CategoryDAO();

        String[] cols = {"ID", "Title", "Author", "Price", "Stock", "Type"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bookTable = new JTable(tableModel);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Add New Book");
        JButton btnUpdate = new JButton("Update Selected Book");
        JButton btnDelete = new JButton("Delete Book");

        btnAdd.addActionListener(e -> showBookForm(null));
        btnUpdate.addActionListener(e -> {
            int selected = bookTable.getSelectedRow();
            if (selected == -1) {
                JOptionPane.showMessageDialog(this, "Select a book to update.");
                return;
            }
            int id = (int) tableModel.getValueAt(selected, 0);
            Book bookToEdit = bookDAO.getAllBooksAdmin().stream().filter(b -> b.getId() == id).findFirst().orElse(null);
            if (bookToEdit != null) {
                showBookForm(bookToEdit);
            }
        });
        
        btnDelete.addActionListener(e -> deleteBook());

        btnPanel.add(btnAdd);
        btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        refreshTable();
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Book> books = bookDAO.getAllBooksAdmin();
        for (Book b : books) {
            tableModel.addRow(new Object[]{b.getId(), b.getTitle(), b.getAuthor(), b.getPrice(), b.getStock(), b.getBookType()});
        }
    }

    // Pass null for new Book, pass Book for updating existing
    private void showBookForm(Book existingBook) {
        JTextField fTitle = new JTextField();
        JTextField fAuthor = new JTextField();
        JTextField fPrice = new JTextField();
        JTextField fStock = new JTextField();
        JTextArea fDesc = new JTextArea(3, 20);
        
        JComboBox<Category> fCategory = new JComboBox<>();
        List<Category> cats = categoryDAO.getAllCategories();
        for (Category c : cats) { fCategory.addItem(c); }
        
        JComboBox<String> fType = new JComboBox<>(new String[]{"PHYSICAL", "EBOOK"});

        if (existingBook != null) {
            fTitle.setText(existingBook.getTitle());
            fAuthor.setText(existingBook.getAuthor());
            fPrice.setText(String.valueOf(existingBook.getPrice()));
            fStock.setText(String.valueOf(existingBook.getStock()));
            fDesc.setText(existingBook.getDescription());
            fType.setSelectedItem(existingBook.getBookType());
            // Preselect category
            for (int i = 0; i < fCategory.getItemCount(); i++) {
                if (fCategory.getItemAt(i).getCategoryId() == existingBook.getCategoryId()) {
                    fCategory.setSelectedIndex(i);
                    break;
                }
            }
            fType.setEnabled(false); // Can't change type easily once created
        }

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JLabel("Title:")); panel.add(fTitle);
        panel.add(new JLabel("Author:")); panel.add(fAuthor);
        panel.add(new JLabel("Category:")); panel.add(fCategory);
        panel.add(new JLabel("Price:")); panel.add(fPrice);
        panel.add(new JLabel("Stock Quantity:")); panel.add(fStock);
        panel.add(new JLabel("Type:")); panel.add(fType);
        panel.add(new JLabel("Description:")); panel.add(new JScrollPane(fDesc));

        int result = JOptionPane.showConfirmDialog(this, panel, existingBook == null ? "Add Book" : "Update Book", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                Category selectedCat = (Category) fCategory.getSelectedItem();
                double price = Double.parseDouble(fPrice.getText());
                int stock = Integer.parseInt(fStock.getText());
                String type = (String) fType.getSelectedItem();
                
                // Using PhysicalBook as a generic proxy for DAO passing
                Book bookEntity = new PhysicalBook(
                        existingBook == null ? 0 : existingBook.getId(), 
                        fTitle.getText(), fAuthor.getText(), 
                        selectedCat != null ? selectedCat.getCategoryId() : 1, 
                        price, stock, fDesc.getText(), 0.0);
                
                // Hacky override for type string since PhysicalBook forces "PHYSICAL" natively in getBookType
                // To maintain OOP abstraction purity, we will enforce type logic inside BookDAO directly off instanceof checking if we wanted to, 
                // but since BookDAO update doesn't touch TYPE, it's fine. For saving new books:
                Book finalBook = new Book(existingBook == null ? 0 : existingBook.getId(), 
                        fTitle.getText(), fAuthor.getText(), 
                        selectedCat != null ? selectedCat.getCategoryId() : 1, 
                        price, stock, fDesc.getText()) {
                    @Override
                    public String getBookType() {
                        return type; 
                    }
                };

                boolean success;
                if (existingBook == null) {
                    success = bookDAO.addBook(finalBook);
                } else {
                    success = bookDAO.updateBook(finalBook);
                }

                if (success) {
                    refreshTable();
                    JOptionPane.showMessageDialog(this, "Success!");
                } else {
                    JOptionPane.showMessageDialog(this, "Database update failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input formats. Price/Stock must be numbers.");
            }
        }
    }

    private void deleteBook() {
        int selected = bookTable.getSelectedRow();
        if (selected == -1) {
            JOptionPane.showMessageDialog(this, "Select a book to delete.");
            return;
        }
        int id = (int) tableModel.getValueAt(selected, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this book?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (bookDAO.deleteBook(id)) {
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete operation failed.");
            }
        }
    }
}
