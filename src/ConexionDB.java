import java.sql.*;

public class ConexionDB {

    private String url = "jdbc:mysql://localhost:33060/concesionario";
    private String user = "root";
    private String password = "admin";

    public boolean conectar() {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("CONEXIÓN OK");
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
            return false;
        }
    }
}