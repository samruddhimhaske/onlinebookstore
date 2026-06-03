package com.bookstore.ui;

import com.bookstore.model.Book;

import javax.swing.*;
import java.awt.*;

public class BookDetailsDialog extends JDialog {

    public BookDetailsDialog(JFrame parent, Book book) {
        super(parent, "Book Summary", true);
        setSize(400, 350);
        setLocationRelativeTo(parent);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(BaseFrame.BACKGROUND_COLOR);

        JLabel titleLbl = new JLabel("<html><h2 style='margin:0; color:" + getHex(BaseFrame.PRIMARY_COLOR) + "'>" + book.getTitle() + "</h2></html>");
        JLabel authorLbl = new JLabel("<html><i>by " + book.getAuthor() + "</i></html>");
        
        // Generating random rating stars based on a loose algorithm just for visually stunning UI
        int stars = Math.max(3, book.getId() % 5 + 1); 
        String ratingHtml = "<span style='color: #FFD700;'>";
        for (int i=0; i<5; i++) { ratingHtml += (i < stars) ? "★" : "☆"; }
        ratingHtml += "</span>";

        JLabel ratingLbl = new JLabel("<html><b>Rating:</b> " + ratingHtml + "</html>");
        
        String stockHtml = book.getStock() > 0 
                ? "<span style='color: green;'>In Stock (" + book.getStock() + " available)</span>"
                : "<span style='color: red;'>Out of Stock!</span>";
        JLabel stockLbl = new JLabel("<html><b>Status:</b> " + stockHtml + "</html>");
        
        JLabel priceLbl = new JLabel("<html><b style='color: " + getHex(BaseFrame.SECONDARY_COLOR) + "; font-size: 16px;'>₹" + book.getPrice() + "</b></html>");

        JTextArea descArea = new JTextArea(book.getDescription());
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(BaseFrame.BACKGROUND_COLOR);
        descArea.setFont(BaseFrame.LABEL_FONT);
        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setPreferredSize(new Dimension(300, 80));

        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(BaseFrame.TEXT_COLOR);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.addActionListener(e -> dispose());

        titleLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        ratingLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        stockLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(titleLbl);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(authorLbl);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(ratingLbl);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(stockLbl);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        mainPanel.add(priceLbl);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(descScroll);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(closeBtn);

        add(mainPanel);
    }

    private String getHex(Color color) {
        return "#" + Integer.toHexString(color.getRGB()).substring(2);
    }
}
