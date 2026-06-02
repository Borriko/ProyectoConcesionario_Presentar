import Componentes.BotonRedondeado;
import Componentes.JMenuItemPanelSuperiorPerfil;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelSuperiorPantallaPrincipal extends JPanel {

    private Usuario usuario;

    private JLabel bienvenida = new JLabel("");
    private JLabel lblSaldo = new JLabel("");


    public PanelSuperiorPantallaPrincipal(PantallaPrincipal pantallaPrincipal, CardLayout cardLayout, JPanel contenedor, PantallaEditarPerfil pantallaEditarPerfil) {


        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setPreferredSize(new Dimension(0, 60));
        setBackground(new Color(30, 30, 30));
        setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        bienvenida.setForeground(Color.WHITE);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        add(bienvenida);

        bienvenida.setForeground(Color.WHITE);
        bienvenida.setFont(new Font("Arial", Font.BOLD, 22));
        add(bienvenida);

        add(Box.createHorizontalGlue());

        lblSaldo.setForeground(new Color(52, 199, 89));
        lblSaldo.setFont(new Font("Arial", Font.BOLD, 15));
        add(lblSaldo);



        add(Box.createHorizontalStrut(20));


        // =========================
        // POPUP MENU
        // =========================
        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(40, 40, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
            }
        };

        popupMenu.setOpaque(false);
        popupMenu.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Hacer transparente el panel interior de Swing
        for (Component c : popupMenu.getComponents()) {
            if (c instanceof JPanel) {
                ((JPanel) c).setOpaque(false);
            }
        }

        popupMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent e) {
                SwingUtilities.invokeLater(() -> {
                    Window ventana = SwingUtilities.windowForComponent(popupMenu);
                    if (ventana != null && !(ventana instanceof Frame)) {
                        ventana.setBackground(new Color(0, 0, 0, 0));
                    }
                });
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent e) {}
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent e) {}
        });

        // =========================
        // ITEMS
        // =========================
        JMenuItemPanelSuperiorPerfil itemPerfil = new JMenuItemPanelSuperiorPerfil(
                "Perfil",
                e -> {
                    pantallaEditarPerfil.cargarUsuario(pantallaPrincipal.getUsuario());
                    cardLayout.show(contenedor, "pantallaEditarPerfil");
                });

        JMenuItemPanelSuperiorPerfil itemCerrarSesion = new JMenuItemPanelSuperiorPerfil(
                "Cerrar sesión",
                e -> {
                    pantallaPrincipal.setUsuario(null);
                    cardLayout.show(contenedor, "login");
                });

        JSeparator separador = new JSeparator();
        separador.setOpaque(false);
        separador.setForeground(new Color(70, 70, 70));
        separador.setBackground(new Color(0, 0, 0, 0));

        popupMenu.add(itemPerfil);
        popupMenu.add(separador);
        popupMenu.add(itemCerrarSesion);

        // =========================
        // BOTÓN USUARIO
        // =========================
        BotonRedondeado botonUsuario = new BotonRedondeado("Mi cuenta", 20);
        botonUsuario.setFocusPainted(false);
        botonUsuario.setForeground(Color.WHITE);
        botonUsuario.setBackground(new Color(60, 60, 60));
        botonUsuario.setFont(new Font("Arial", Font.BOLD, 13));
        botonUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botonUsuario.setBorder(BorderFactory.createEmptyBorder(6, 14, 6, 14));
        botonUsuario.setMaximumSize(new Dimension(130, 35));
        botonUsuario.setPreferredSize(new Dimension(130, 35));

        botonUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!popupMenu.isVisible()) {
                    popupMenu.show(botonUsuario, 0, botonUsuario.getHeight());
                }
            }
        });

        popupMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                Rectangle bounds = new Rectangle(0, 0, popupMenu.getWidth(), popupMenu.getHeight());
                if (!bounds.contains(e.getPoint())) {
                    popupMenu.setVisible(false);
                }
            }
        });

        add(botonUsuario);
    }

    public void cambiarBienvenida(String nombre) {
        bienvenida.setText("Bienvenido de nuevo, " + nombre + "!");
    }
    public void actualizarSaldo(double saldo) {
        lblSaldo.setText(String.format("Saldo: %,.2f €", saldo));
    }

}