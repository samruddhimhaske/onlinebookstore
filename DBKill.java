import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBKill {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String pass = "Samu@1606";

        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW PROCESSLIST");
            while(rs.next()) {
                String id = rs.getString("Id");
                String info = rs.getString("Info");
                String state = rs.getString("State");
                if (info != null && info.toLowerCase().contains("insert")) {
                    System.out.println("Killing query: " + id + " | " + info);
                    Statement k = conn.createStatement();
                    k.executeUpdate("KILL " + id);
                }
            }
            conn.close();
            System.out.println("Done.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
