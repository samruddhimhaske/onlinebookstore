# PowerPoint Presentation Content Structure

## Slide 1: Title Slide
**Title:** Online Book Store  
**Subtitle:** A Java Swing & JDBC E-commerce Application  
**Presented by:** [Your Name / Group Names]  
**Class/Year:** SY Tech Project  

---

## Slide 2: Project Overview & Objectives
- **Goal:** To develop a fully functional desktop e-commerce platform for book enthusiasts.
- **Key Features:** User registration, dynamic cart calculation, admin inventory controls, and invoice generation.
- **Focus:** Demonstrating rigorous implementation of Core Java concepts like OOP, File I/O, and Multithreading.

---

## Slide 3: Technologies Used
- **Frontend GUI:** Java Swing & AWT (Custom designed, Flat-UI aesthetics, no external plugins).
- **Backend Logic:** Core Java (JDK 8+).
- **Database Ecosystem:** MySQL 8.x + MySQL Workbench.
- **Connectivity bridge:** Java Database Connectivity (JDBC).

---

## Slide 4: System Architecture (MVC Approach)
*(You can draw a simple block diagram here)*
- **Models:** Java Objects mapping directly to Database Schema (`User`, `Book`, `Order`).
- **DAO & Services:** Intermediary layer handling Business Logic (`AuthService`, `BillingService`) and SQL queries.
- **UI Views:** Swing-based `JFrames` presenting data to the user securely.

---

## Slide 5: Core Java Concepts Demonstrated
- **Inheritance & Polymorphism:** `PhysicalBook` and `EBook` extending the `Book` class.
- **Multithreading:** Employed in the `SplashScreen` to simulate parallel application loading.
- **File Handling:** Utilizing `BufferedWriter` to generate offline `.txt` invoices upon checkout.
- **Exception Handling:** Custom classes throwing `OutOfStockException`, `InvalidLoginException`.
- **Collections:** Storing cart items gracefully using `ArrayList`.

---

## Slide 6: Database Normalization
- Shows 3rd Normal Form Implementation.
- Display a screenshot of the ER Diagram.
- Highlights: Foreign Keys mapping `orders` to `users`, and `order_items` mapping to `orders` ensuring NO dirty data.

---

## Slide 7: Live Demonstration
- Brief explanation script during demo:
  1. *Run the multi-threaded splash screen.*
  2. *Demonstrate Admin login and dashboard viewing.*
  3. *Log out, log in as Student.*
  4. *Scroll through populated book table.*
  5. *Add books to cart, simulate checkout.*
  6. *Open project directory and display the newly generated Invoice text file.*

---

## Slide 8: Future Enhancements & Conclusion
- **Future:** Cloud database integration, Real payment portals, and an Email-notification service.
- **Conclusion:** Successfully built a scalable, isolated system simulating enterprise environments while enforcing clean code theories taught in class.
- **Thank You! / Any Questions?**
