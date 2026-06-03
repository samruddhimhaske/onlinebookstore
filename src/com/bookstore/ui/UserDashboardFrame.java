package com.bookstore.ui;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.OrderDAO;
import com.bookstore.dao.WishlistDAO;
import com.bookstore.model.Book;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.service.AuthService;
import com.bookstore.service.BillingService;
import com.bookstore.util.FileHandlerUtil;
import com.bookstore.util.MultithreadingUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class UserDashboardFrame extends BaseFrame {

    private BookDAO bookDAO;
    private BillingService billingService;
    private WishlistDAO wishlistDAO;
    private List<Book> allBooks;
    private List<CartItem> cart;
    
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public UserDashboardFrame() {
        super("User Dashboard - Online Book Store");
        bookDAO = new BookDAO();
        billingService = new BillingService();
        wishlistDAO = new WishlistDAO();
        cart = new ArrayList<>();
        
        setCenterScreen(1200, 750);
        setLayout(new BorderLayout());

        // Top Panel: Welcome, Search, and Actions
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(PRIMARY_COLOR);
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        String userName = AuthService.currentUser != null ? AuthService.currentUser.getName() : "User";
        JLabel welcomeLabel = new JLabel("<html><span style='color: white; font-size: 16px;'>Welcome back, </span>" +
                "<b style='color: #FF6E40; font-size: 20px;'>" + userName + "</b></html>");
        welcomeLabel.setFont(TITLE_FONT);
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel actionPanel = new JPanel(new FlowLayout());
        actionPanel.setOpaque(false);
        
        JButton profileBtn = createStyledButton("My Profile", new Color(142, 68, 173));
        JButton wishlistBtn = createStyledButton("Wishlist", new Color(211, 84, 0));
        JButton historyBtn = createStyledButton("My Orders", Color.DARK_GRAY);
        JButton myCartBtn = createStyledButton("My Cart (" + cart.size() + ")", SECONDARY_COLOR);
        JButton helpBtn = createStyledButton("Help ?", ACCENT_COLOR);
        JButton logoutBtn = createStyledButton("Logout", Color.RED);
        
        logoutBtn.addActionListener(e -> {
            new AuthService().logout();
            dispose();
            new LoginFrame().setVisible(true);
        });
        
        helpBtn.addActionListener(e -> new HelpFrame(this).setVisible(true));
        
        historyBtn.addActionListener(e -> {
            if (AuthService.currentUser != null) {
                new OrderHistoryDialog(this, AuthService.currentUser.getId()).setVisible(true);
            }
        });
        
        profileBtn.addActionListener(e -> {
            if (AuthService.currentUser != null) {
                new UserProfileDialog(this).setVisible(true);
                // Dynamically update banner if name changed
                welcomeLabel.setText("<html><span style='color: white; font-size: 16px;'>Welcome back, </span>" +
                "<b style='color: #FF6E40; font-size: 20px;'>" + AuthService.currentUser.getName() + "</b></html>");
            }
        });

        wishlistBtn.addActionListener(e -> showWishlist());

        actionPanel.add(profileBtn);
        actionPanel.add(wishlistBtn);
        actionPanel.add(historyBtn);
        actionPanel.add(myCartBtn);
        actionPanel.add(helpBtn);
        actionPanel.add(logoutBtn);
        topPanel.add(actionPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Combining Search bar and Table
        JPanel centerPanel = new JPanel(new BorderLayout());
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        searchPanel.setBackground(BACKGROUND_COLOR);
        JLabel searchLbl = new JLabel("<html><b>Search Books By:</b></html>");
        searchLbl.setFont(LABEL_FONT);
        searchLbl.setForeground(TEXT_COLOR);
        
        String[] searchOptions = {"Title", "Author", "Category"};
        JComboBox<String> searchDropdown = new JComboBox<>(searchOptions);
        searchDropdown.setFont(LABEL_FONT);
        
        JTextField searchField = createStyledTextField();
        searchField.setPreferredSize(new Dimension(300, 35));
        
        JButton searchBtn = createStyledButton("Search", PRIMARY_COLOR);
        JButton resetBtn = createStyledButton("Reset", Color.GRAY);
        
        searchPanel.add(searchLbl);
        searchPanel.add(searchDropdown);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);
        searchPanel.add(resetBtn);
        
        centerPanel.add(searchPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Title", "Author", "Price (₹)", "Stock", "Type"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        bookTable = new JTable(tableModel);
        bookTable.setRowHeight(35);
        bookTable.setFont(LABEL_FONT);
        bookTable.getTableHeader().setFont(BUTTON_FONT);
        bookTable.getTableHeader().setBackground(TEXT_COLOR);
        bookTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(bookTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Add to Cart & Actions
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBackground(BACKGROUND_COLOR);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        
        JButton viewDetailsBtn = createStyledButton("View Details", new Color(41, 128, 185));
        JButton addWishlistBtn = createStyledButton("Add to Wishlist", new Color(155, 89, 182));
        JButton addToCartBtn = createStyledButton("Add to Cart", ACCENT_COLOR);
        addToCartBtn.setPreferredSize(new Dimension(250, 45));
        
        bottomPanel.add(viewDetailsBtn);
        bottomPanel.add(addWishlistBtn);
        bottomPanel.add(addToCartBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // Component Listeners
        addToCartBtn.addActionListener(e -> addToCart(myCartBtn));
        viewDetailsBtn.addActionListener(e -> viewDetails());
        addWishlistBtn.addActionListener(e -> addToWishlist());
        
        myCartBtn.addActionListener(e -> {
            new CartManagementDialog(this, cart, billingService, this::processCheckout, () -> {
                myCartBtn.setText("My Cart (" + cart.size() + ")");
            }).setVisible(true);
        });
        
        searchBtn.addActionListener(e -> {
            String term = searchField.getText().trim();
            if (term.isEmpty()) return;
            String criteria = (String) searchDropdown.getSelectedItem();
            List<Book> results;
            if ("Title".equals(criteria)) results = bookDAO.searchByTitle(term);
            else if ("Author".equals(criteria)) results = bookDAO.searchByAuthor(term);
            else results = bookDAO.searchByCategory(term);
            updateTable(results);
        });
        
        resetBtn.addActionListener(e -> {
            searchField.setText("");
            loadBooksToTable();
        });

        // Load Initial Data
        JLabel loadingIndicator = new JLabel("<html><i>Loading library...</i></html>");
        bottomPanel.add(loadingIndicator);
        MultithreadingUtil.simulateProcessing(800, loadingIndicator, this::loadBooksToTable);
    }

    private void loadBooksToTable() {
        allBooks = bookDAO.getAllBooks();
        updateTable(allBooks);
    }

    private void updateTable(List<Book> books) {
        tableModel.setRowCount(0);
        for (Book b : books) {
            tableModel.addRow(new Object[]{
                b.getId(), b.getTitle(), b.getAuthor(), 
                b.getPrice(), b.getStock(), b.getBookType()
            });
        }
    }

    private Book getSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) return null;
        int bookId = (int) tableModel.getValueAt(selectedRow, 0);
        return allBooks.stream().filter(b -> b.getId() == bookId).findFirst().orElse(null);
    }

    private void viewDetails() {
        Book selectedBook = getSelectedBook();
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Please select a book first.");
            return;
        }
        new BookDetailsDialog(this, selectedBook).setVisible(true);
    }
    
    private void addToWishlist() {
        Book selectedBook = getSelectedBook();
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Please select a book to wishlist.");
            return;
        }
        if (AuthService.currentUser != null) {
            boolean added = wishlistDAO.addToWishlist(AuthService.currentUser.getId(), selectedBook.getId());
            if (added) {
                JOptionPane.showMessageDialog(this, "Added to Wishlist!");
            } else {
                JOptionPane.showMessageDialog(this, "Book is already in your wishlist.");
            }
        }
    }

    private void showWishlist() {
        if (AuthService.currentUser == null) return;
        List<Book> wishes = wishlistDAO.getWishlistByUser(AuthService.currentUser.getId());
        if (wishes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Your wishlist is empty!");
            return;
        }
        // Minimal Display logic
        StringBuilder sb = new StringBuilder("<html><h3>Your Wishlist</h3><ul>");
        for (Book b : wishes) {
            sb.append("<li><b>").append(b.getTitle()).append("</b> by ").append(b.getAuthor()).append("</li>");
        }
        sb.append("</ul></html>");
        JOptionPane.showMessageDialog(this, sb.toString(), "Wishlist", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addToCart(JButton cartButton) {
        Book selectedBook = getSelectedBook();
        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Please select an exciting book first!");
            return;
        }

        if (selectedBook.getStock() > 0) {
            boolean exists = false;
            for(CartItem item : cart) {
                if(item.getBook().getId() == selectedBook.getId()) {
                    item.setQuantity(item.getQuantity() + 1);
                    exists = true; break;
                }
            }
            if (!exists) {
                cart.add(new CartItem(0, 0, selectedBook, 1));
            }
            cartButton.setText("My Cart (" + cart.size() + ")");
            JOptionPane.showMessageDialog(this, "<html><b>" + selectedBook.getTitle() + "</b> added to cart!</html>");
        } else {
            JOptionPane.showMessageDialog(this, "Sorry, this book is currently out of stock!");
        }
    }

    private void processCheckout() {
        if (cart.isEmpty()) return;
        
        double subTotal = billingService.calculateSubTotal(cart);
        double tax = billingService.calculateTax(subTotal);
        double total = billingService.calculateFinalTotal(subTotal);

        Object[] options = {"Credit/Debit Card", "Cash on Delivery", "Cancel"};
        int confirm = JOptionPane.showOptionDialog(this,
                "Total Amount: ₹" + String.format("%.2f", total) + "\nSelect your preferred Payment Method:",
                "Complete Checkout",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (confirm == JOptionPane.YES_OPTION || confirm == JOptionPane.NO_OPTION) {
            String paymentMethod = (confirm == JOptionPane.NO_OPTION) ? "Cash On Delivery" : "Card";
            try {
                Order order = new OrderDAO().createOrder(AuthService.currentUser.getId(), total, tax, cart, paymentMethod);
                FileHandlerUtil.generateInvoice(order, cart, AuthService.currentUser.getName());
                cart.clear();
                JOptionPane.showMessageDialog(this, "<html><b style='color: green;'>Order placed securely!</b><br/>Invoice generated automatically.</html>");
                dispose();
                new UserDashboardFrame().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to place order: " + ex.getMessage());
            }
        }
    }
}
