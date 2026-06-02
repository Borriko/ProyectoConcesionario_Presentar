import javax.swing.*;

public class Main {
    public static void main(String[] args) {


        ConexionDB conexionDB = new ConexionDB();


        if (conexionDB.conectar()){
            SwingUtilities.invokeLater(() -> new Concesionario());
        } else {
            System.out.println("Fallo de la DB");
        }

    }
}
