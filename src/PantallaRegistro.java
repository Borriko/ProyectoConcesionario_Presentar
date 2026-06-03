import Componentes.BotonRedondeado;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class PantallaRegistro extends JPanel {


    private JTextField nombre_escribir;
    private JTextField email_escribir;
    private JPasswordField contraseña_escribir;

    private Consultas consultas = new Consultas();

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    private PantallaPrincipal pantallaPrincipal;

    private Image imagen = new ImageIcon("img/img_portada_notxt.png").getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        g2.dispose();
    }

    public PantallaRegistro(CardLayout cardLayout, JPanel contenedorPrincipal, PantallaPrincipal pantallaPrincipal) {

        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.pantallaPrincipal = pantallaPrincipal;

        // ================= PANEL PRINCIPAL =================

        setBackground(new Color(30, 30, 30));
        setLayout(new GridBagLayout());

        // ================= CARD REGISTRO =================

        JPanel contenedor_registro = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
            }
        };

        contenedor_registro.setOpaque(false);
        contenedor_registro.setPreferredSize(new Dimension(420, 570));
        contenedor_registro.setBackground(Color.WHITE);

        contenedor_registro.setLayout(new BoxLayout(contenedor_registro, BoxLayout.Y_AXIS));

        contenedor_registro.setBorder(
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        );

        // ================= TITULO =================

        JLabel titulo = new JLabel("Registrarse");

        titulo.setFont(new Font("Arial", Font.BOLD, 42));
        titulo.setForeground(Color.BLACK);

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_registro.add(titulo);

        contenedor_registro.add(Box.createVerticalStrut(50));

        // ================= NOMBRE =================

        JLabel nombre_label = new JLabel("Nombre *");

        nombre_label.setFont(new Font("Arial", Font.PLAIN, 13));
        nombre_label.setForeground(Color.GRAY);

        nombre_label.setAlignmentX(Component.LEFT_ALIGNMENT);

        nombre_escribir = new JTextField();

        nombre_escribir.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.BLACK
        ));

        nombre_escribir.setBackground(new Color(245, 245, 245));

        nombre_escribir.setMaximumSize(new Dimension(320, 20));

        nombre_escribir.setFont(new Font("Arial", Font.PLAIN, 16));

        nombre_escribir.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ERROR NOMBRE

        JLabel error_nombre = new JLabel("El nombre no puede estar vacío");

        error_nombre.setFont(new Font("Arial", Font.PLAIN, 12));

        error_nombre.setForeground(Color.RED);

        error_nombre.setVisible(false);

        error_nombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_registro.add(nombre_label);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(nombre_escribir);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(error_nombre);

        contenedor_registro.add(Box.createVerticalStrut(30));

        // ================= EMAIL =================

        JLabel email_label = new JLabel("Email *");

        email_label.setFont(new Font("Arial", Font.PLAIN, 13));
        email_label.setForeground(Color.GRAY);

        email_label.setAlignmentX(Component.LEFT_ALIGNMENT);

        email_escribir = new JTextField();

        email_escribir.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.BLACK
        ));

        email_escribir.setBackground(new Color(245, 245, 245));

        email_escribir.setMaximumSize(new Dimension(320, 20));

        email_escribir.setFont(new Font("Arial", Font.PLAIN, 16));

        email_escribir.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ERROR EMAIL

        JLabel error_email = new JLabel("Este email ya está registrado");

        error_email.setFont(new Font("Arial", Font.PLAIN, 12));

        error_email.setForeground(Color.RED);

        error_email.setVisible(false);

        error_email.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_registro.add(email_label);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(email_escribir);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(error_email);

        contenedor_registro.add(Box.createVerticalStrut(25));

        // ================= CONTRASEÑA =================

        JLabel contraseña_label = new JLabel("Contraseña *");

        contraseña_label.setFont(new Font("Arial", Font.PLAIN, 13));
        contraseña_label.setForeground(Color.GRAY);

        contraseña_label.setAlignmentX(Component.LEFT_ALIGNMENT);

        contraseña_escribir = new JPasswordField();

        contraseña_escribir.setBorder(BorderFactory.createMatteBorder(
                0, 0, 1, 0, Color.BLACK
        ));

        contraseña_escribir.setBackground(new Color(245, 245, 245));

        contraseña_escribir.setMaximumSize(new Dimension(320, 20));

        contraseña_escribir.setFont(new Font("Arial", Font.PLAIN, 16));

        contraseña_escribir.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ERROR CONTRASEÑA

        JLabel error_contraseña = new JLabel("La contraseña no puede estar vacía");

        error_contraseña.setFont(new Font("Arial", Font.PLAIN, 12));

        error_contraseña.setForeground(Color.RED);

        error_contraseña.setVisible(false);

        error_contraseña.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_registro.add(contraseña_label);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(contraseña_escribir);

        contenedor_registro.add(Box.createVerticalStrut(5));

        contenedor_registro.add(error_contraseña);

        contenedor_registro.add(Box.createVerticalStrut(25));



        // ================= BOTON REGISTRO =================

        JButton registro_boton = new BotonRedondeado("Registrarse", 10);

        registro_boton.setFocusPainted(false);

        registro_boton.setForeground(Color.WHITE);

        registro_boton.setBackground(Color.BLACK);

        registro_boton.setFont(new Font("Arial", Font.BOLD, 16));

        registro_boton.setBorder(BorderFactory.createEmptyBorder(9, 9, 9, 9));

        registro_boton.setMaximumSize(new Dimension(180, 40));

        registro_boton.setPreferredSize(new Dimension(180, 40));

        registro_boton.setAlignmentX(Component.LEFT_ALIGNMENT);

        registro_boton.addActionListener(e -> {

            String nombre = nombre_escribir.getText().trim();
            String email = email_escribir.getText().trim();
            String password = new String(contraseña_escribir.getPassword()).trim();

            boolean hayError = false;

            // VALIDAR CAMPOS VACÍOS

            if (nombre.isEmpty()) {
                error_nombre.setText("El nombre no puede estar vacío");
                error_nombre.setVisible(true);
                hayError = true;
            } else {
                error_nombre.setVisible(false);
            }

            if (email.isEmpty()) {
                error_email.setText("El email no puede estar vacío");
                error_email.setVisible(true);
                hayError = true;
            } else if (consultas.emailRepetido(email)) {
                error_email.setText("Este email ya está registrado");
                error_email.setVisible(true);
                hayError = true;
            } else {
                error_email.setVisible(false);
            }

            if (password.isEmpty()) {
                error_contraseña.setText("La contraseña no puede estar vacía");
                error_contraseña.setVisible(true);
                hayError = true;
            } else {
                error_contraseña.setVisible(false);
            }


            if (!hayError) {

                int respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Deseas confirmar los datos?",
                        "Confirmación",
                        JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    try {

                        consultas.crearNuevoUsuario(nombre, email, password);

                        nombre_escribir.setText("");
                        email_escribir.setText("");
                        contraseña_escribir.setText("");

                        pantallaPrincipal.setUsuario(consultas.obtenerUsuarioPorEmail(email));

                        pantallaPrincipal.actualizarElementosPanelSuperior();

                        cardLayout.show(contenedorPrincipal, "pantallaPrincipal");

                    } catch (SQLException e1) {

                        System.out.println("ERROR EN LA VENTANA DE CONFIRMACIÓN: " + e1.getMessage());

                    }
                }
            }


        });

        contenedor_registro.add(registro_boton);

        contenedor_registro.add(Box.createVerticalStrut(50));

        // ================= LOGIN =================

        JLabel texto_registro = new JLabel("¿Ya tienes una cuenta?");

        texto_registro.setFont(new Font("Arial", Font.PLAIN, 14));

        texto_registro.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_registro.add(texto_registro);

        contenedor_registro.add(Box.createVerticalStrut(10));

        // ================= INICIAR SESION =================

        JLabel iniciar_sesion = new JLabel("Iniciar sesión");

        iniciar_sesion.setFont(new Font("Arial", Font.BOLD, 12));

        iniciar_sesion.setCursor(new Cursor(Cursor.HAND_CURSOR));

        iniciar_sesion.setAlignmentX(Component.LEFT_ALIGNMENT);

        iniciar_sesion.addMouseListener(new java.awt.event.MouseAdapter() {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                cardLayout.show(contenedorPrincipal, "login");

            }

        });

        contenedor_registro.add(iniciar_sesion);

        // ================= ADD FINAL =================

        add(contenedor_registro);
    }


}