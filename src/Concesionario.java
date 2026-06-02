import javax.swing.*;
import java.awt.*;

public class Concesionario extends JFrame {

    public Concesionario(){

        setTitle("Concesionario");
        setSize(1440,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // LOGO

        ImageIcon icono = new ImageIcon("img/img_logo.png");

        setIconImage(icono.getImage());

        // CARDLAYOUT

        CardLayout cardLayout = new CardLayout();

        JPanel contenedor = new JPanel(cardLayout);


        // PANTALLAS

        PantallaEditarPerfil pantallaEditarPerfil = new PantallaEditarPerfil(cardLayout,contenedor, null);

        PantallaPrincipal pantallaPrincipal = new PantallaPrincipal(cardLayout, contenedor, pantallaEditarPerfil);

        pantallaEditarPerfil.setPantallaPrincipal(pantallaPrincipal);

        PantallaLogin login = new PantallaLogin(cardLayout, contenedor,pantallaPrincipal);

        PantallaRegistro registro = new PantallaRegistro(cardLayout, contenedor, pantallaPrincipal);



        // AÑADIR PANTALLAS

        contenedor.add(login, "login");
        contenedor.add(registro, "registro");
        contenedor.add(pantallaPrincipal, "pantallaPrincipal");
        contenedor.add(pantallaEditarPerfil, "pantallaEditarPerfil");

        add(contenedor);

        setLocationRelativeTo(null);
        setVisible(true);
    }
}