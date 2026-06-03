# OOP Concept Mapping for Evaluators

This mapping matrix strictly highlights where mandatory SY Syllabus Concepts reflect inside the `/src` folder codebase:

1. **Class and Object**
   - Objects are initialized routinely throughout, natively utilizing constructors (e.g., `new LoginFrame()`, `new AuthService()`).
   
2. **Encapsulation**
   - Found across every class in `com.bookstore.model.*` (`Book`, `User`). All fields are strict `private`, modified only through public getters and setters.
   
3. **Inheritance**
   - `User` extends `Person`.
   - `Admin` extends `Person`.
   - `PhysicalBook` extends `Book`.
   - `LoginFrame` extends `BaseFrame` which extends `JFrame`.
   
4. **Polymorphism**
   - **Method Overriding**: `getRole()` in `User/Admin`, `getBookType()` in `PhysicalBook/EBook`.
   - **Method Overloading**: Common in base Java implementations, but implicit in multiple UI constructors.
   
5. **Abstraction**
   - `Person.java` and `Book.java` are strictly `abstract` classes to prevent direct instantiation of a generic unclassified Person/Book.
   
6. **Interface**
   - `Searchable.java` implemented by `BookDAO.java`.
   - `DiscountApplicable` implemented inside `BillingService.java`.
   
7. **Exception Handling**
   - Native usage: `try-catch` blocks inside all DAO SQL executions.
   - Custom exceptions: `InvalidLoginException`, `UserNotFoundException`, `OutOfStockException`, `DatabaseException`.
   
8. **File Handling**
   - Completely demonstrated inside `FileHandlerUtil.java` running java standard `java.io` stream writers to print Invoices.
   
9. **Collections Framework**
   - `List<CartItem>` utilizing `ArrayList` dynamically holding the user's cart in memory across `UserDashboardFrame`.
   
10. **Multithreading**
   - Demonstrated accurately in `MultithreadingUtil.java` managing background worker threads alongside `SwingUtilities.invokeLater()` to avoid GUI freezes on load arrays.

11. **JDBC Connectivity**
   - Found natively inside `DBConnection.java` utilizing `java.sql.DriverManager` and running queries universally across `com.bookstore.dao` package layers.
