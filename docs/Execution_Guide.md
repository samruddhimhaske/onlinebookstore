# How to Run Offline Book Store

Follow these steps exactly to run the Java application locally on your machine.

## Prerequisites
1. **Java JDK**: Ensure JDK 8 or later is installed (`java -version`).
2. **MySQL / MySQL Workbench**: Installed and running locally.
3. **IDE**: VS Code (with Java Extension Pack) or IntelliJ IDEA / Eclipse.

## Step 1: Database Setup
1. Open **MySQL Workbench**.
2. Connect to your local MySQL instance (usually `root` user).
3. Open the file `database/schema.sql` located in your project directory and execute the entire script. This creates the database `online_book_store` and all perfectly normalized tables.
4. Open the file `database/data.sql` and run the script. This populates your database with realistic books, an admin account, and a user account.
5. In your project codebase, go to `src/com/bookstore/db/DBConnection.java` and make sure the `USER` and `PASSWORD` static constants match your local MySQL credentials.

## Step 2: Adding JDBC Connector
**For VS Code:**
1. Download `mysql-connector-j-8.x.x.jar` from the internet.
2. In the "Java Projects" explorer tab on the bottom left of VS Code, find "Referenced Libraries".
3. Click the `+` icon and select the downloaded jar file.

**For Eclipse / IntelliJ:**
- Add the `mysql-connector-j-8.x.x.jar` file to your Project Structure's Libraries / Build Path.

## Step 3: Run the Application
1. Navigate to `src/com/bookstore/main/MainApplication.java`.
2. Right-click and choose **Run Java**.
3. You will see the beautiful multi-threaded Splash screen loading, followed by the Login page.

* **Admin Login**: `admin@bookstore.com` / `admin123`
* **User Login**: `student@mail.com` / `student123`
