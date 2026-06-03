package com.bookstore.util;

import com.bookstore.model.Order;
import com.bookstore.model.CartItem;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Demonstrates File Handling concept in Java.
 */
public class FileHandlerUtil {
    
    // Generates a text invoice and saves it to the local system
    public static void generateInvoice(Order order, List<CartItem> items, String userName) {
        String directoryPath = "invoices";
        File dir = new File(directoryPath);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        String filename = directoryPath + "/Invoice_" + order.getOrderId() + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write("=========================================\n");
            writer.write("           ONLINE BOOK STORE             \n");
            writer.write("=========================================\n");
            writer.write("Order ID: " + order.getOrderId() + "\n");
            writer.write("Date: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n");
            writer.write("Customer Name: " + userName + "\n");
            writer.write("-----------------------------------------\n");
            writer.write(String.format("%-25s %-10s %-10s\n", "Book Title", "Qty", "Price"));
            writer.write("-----------------------------------------\n");
            
            for (CartItem item : items) {
                // Shorten title just for alignment if it's too long
                String title = item.getBook().getTitle();
                if(title.length() > 22) title = title.substring(0, 20) + "..";
                
                writer.write(String.format("%-25s %-10d %-10.2f\n", 
                        title, item.getQuantity(), item.getBook().getPrice() * item.getQuantity()));
            }
            
            writer.write("-----------------------------------------\n");
            writer.write("Tax (GST 5%): Rs " + order.getTaxAmount() + "\n");
            writer.write("Total Amount Paid: Rs " + order.getTotalAmount() + "\n");
            writer.write("Status: " + order.getStatus() + "\n");
            writer.write("=========================================\n");
            writer.write("         THANK YOU FOR SHOPPING!         \n");
            
            System.out.println("Invoice successfully saved to " + filename);
        } catch (IOException e) {
            System.err.println("Failed to write invoice file: " + e.getMessage());
        }
    }
}
