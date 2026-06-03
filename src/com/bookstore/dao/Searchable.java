package com.bookstore.dao;

import java.util.List;

/**
 * Demonstrates the Interface constraint.
 */
public interface Searchable<T> {
    List<T> searchByTitle(String title);
    List<T> searchByAuthor(String author);
    List<T> searchByCategory(String category);
}
