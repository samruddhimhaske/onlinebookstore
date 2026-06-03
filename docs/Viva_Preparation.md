# Exam & Viva Preparation Guide

**Examiner:** "Why did you use Java for this project?"
**Answer:** "Java provides excellent Object-Oriented capabilities, ensuring our code is reusable through Inheritance and secure through Encapsulation. Plus, Java Swing allows building desktop applications natively without external libraries, and JDBC makes connecting to MySQL seamless."

---

**Examiner:** "Where have you used **Multithreading** in your project?"
**Answer:** "I implemented multithreading in our `SplashScreen` using `MultithreadingUtil.java`. When the application fires up, a separate worker thread simulates background loading (sleeping and updating) while the Java Event Dispatch Thread (EDT) updates the GUI progress bar sequentially. This prevents the UI from freezing."

---

**Examiner:** "Explain **Polymorphism** as used in your Book Store."
**Answer:** "We have an abstract `Book` class, from which `PhysicalBook` and `EBook` inherit. When I display inventory in the UserDashboard, I call the overridden `getBookType()` on a list of `Book` objects. Depending on exactly what object it is dynamically during runtime, it displays either 'PHYSICAL' or 'EBOOK' without me needing to write `if-else` loops checking types."

---

**Examiner:** "How are you handling the database connections securely?"
**Answer:** "I am using the **Singleton Design Pattern** in my `DBConnection.java` class. It ensures that the large overhead of opening a MySQL Connection happens only once. Subsequent database requests (DAO classes) reuse that exact same connection."

---

**Examiner:** "What happens if MySQL is turned off or crashes?"
**Answer:** "Our application catches SQL exceptions cleanly. Instead of the app crashing, the `DBConnection` throws a Custom Runtime Exception called `DatabaseException`, which securely halts the operation and can show an elegant Error Dialog to the user alerting them that the Database is offline."

---

**Examiner:** "Where have you used **File Handling**?"
**Answer:** "When a user successfully creates an order, we don't just insert it into the database. I wrote `FileHandlerUtil.java` which takes the Order details, creates a physical text file locally on the hard-drive (using `BufferedWriter` and `FileWriter`), formatting it explicitly into a printable receipt layout."

---

**Examiner:** "Did you use any **Interfaces**?"
**Answer:** "Yes, we implement the `Searchable` interface in our `BookDAO` class to force standard search functionality (By Title, by Author). Also in the `BillingService`, a `DiscountApplicable` interface mandates an implementation of the `applyDiscount` logic."
