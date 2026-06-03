package com.bookstore.db;

import java.sql.Connection;
import java.sql.Statement;

public class DBPatcher {
    public static void main(String[] args) {
        System.out.println("Applying Database Patches...");
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {
             
            try {
                stmt.execute("ALTER TABLE orders ADD COLUMN expected_delivery_days INT DEFAULT 0");
                System.out.println("Added 'expected_delivery_days' column.");
            } catch (Exception e) {
                System.out.println("Column 'expected_delivery_days' might already exist. Ignoring.");
            }
            
            try {
                stmt.execute("ALTER TABLE orders ADD COLUMN payment_method VARCHAR(50) DEFAULT 'CARD'");
                System.out.println("Added 'payment_method' column.");
            } catch (Exception e) {
                System.out.println("Column 'payment_method' might already exist. Ignoring.");
            }
            
            System.out.println("Database Patch Complete!");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
