import Componentes.BotonRedondeado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PantallaLogin extends JPanel {

    private JTextField email_escribir;
    private JTextField contraseña_escribir;

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

    public PantallaLogin(CardLayout cardLayout, JPanel contenedorPrincipal, PantallaPrincipal pantallaPrincipal) {

        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.pantallaPrincipal = pantallaPrincipal;

        // ================= PANEL PRINCIPAL =================


        setBackground(new Color(30,30,30));
        setLayout(new GridBagLayout());

        // ================= CARD LOGIN =================

        JPanel contenedor_logeo = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
                g2.dispose();
            }
        };

        contenedor_logeo.setOpaque(false);

        contenedor_logeo.setPreferredSize(new Dimension(420, 500));
        contenedor_logeo.setBackground(Color.WHITE);

        contenedor_logeo.setLayout(new BoxLayout(contenedor_logeo, BoxLayout.Y_AXIS));



        contenedor_logeo.setBorder(
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        );

        // ================= TITULO =================

        JLabel titulo = new JLabel("Iniciar sesión");

        titulo.setFont(new Font("Arial", Font.BOLD, 42));

        titulo.setForeground(Color.BLACK);

        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_logeo.add(titulo);

        contenedor_logeo.add(Box.createVerticalStrut(50));

        // ================= EMAIL =================

        JLabel email_label = new JLabel("Email *");

        email_label.setFont(new Font("Arial", Font.PLAIN, 13));
        email_label.setForeground(Color.GRAY);

        email_label.setAlignmentX(Component.LEFT_ALIGNMENT);

        email_escribir = new JTextField();

        email_escribir.setBorder(BorderFactory.createMatteBorder(
                0,0,1,0,Color.BLACK
        ));

        email_escribir.setBackground(new Color(245,245,245));

        email_escribir.setMaximumSize(new Dimension(320, 30));

        email_escribir.setFont(new Font("Arial", Font.PLAIN, 16));

        email_escribir.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ERROR EMAIL

        JLabel error_email = new JLabel("El email no está registrado");

        error_email.setFont(new Font("Arial", Font.PLAIN, 12));

        error_email.setForeground(Color.RED);

        error_email.setVisible(false);

        error_email.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_logeo.add(email_label);

        contenedor_logeo.add(Box.createVerticalStrut(5));

        contenedor_logeo.add(email_escribir);

        contenedor_logeo.add(Box.createVerticalStrut(5));

        contenedor_logeo.add(error_email);

        contenedor_logeo.add(Box.createVerticalStrut(25));

        // ================= CONTRASEÑA =================

        JLabel contraseña_label = new JLabel("Contraseña *");

        contraseña_label.setFont(new Font("Arial", Font.PLAIN, 13));
        contraseña_label.setForeground(Color.GRAY);

        contraseña_label.setAlignmentX(Component.LEFT_ALIGNMENT);

        contraseña_escribir = new JPasswordField();

        contraseña_escribir.setBorder(BorderFactory.createMatteBorder(
                0,0,1,0,Color.BLACK
        ));

        contraseña_escribir.setBackground(new Color(245,245,245));

        contraseña_escribir.setMaximumSize(new Dimension(320, 30));

        contraseña_escribir.setFont(new Font("Arial", Font.PLAIN, 16));

        contraseña_escribir.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ERROR CONTRASEÑA

        JLabel error_contraseña = new JLabel("Contraseña incorrecta");

        error_contraseña.setFont(new Font("Arial", Font.PLAIN, 12));

        error_contraseña.setForeground(Color.RED);

        error_contraseña.setVisible(false);

        error_contraseña.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_logeo.add(contraseña_label);

        contenedor_logeo.add(Box.createVerticalStrut(5));

        contenedor_logeo.add(contraseña_escribir);

        contenedor_logeo.add(Box.createVerticalStrut(5));

        contenedor_logeo.add(error_contraseña);

        contenedor_logeo.add(Box.createVerticalStrut(25));



        // ================= BOTON LOGIN =================

        JButton iniciar_sesion_boton = new BotonRedondeado("Iniciar sesión", 10);

        iniciar_sesion_boton.setFocusPainted(false);

        iniciar_sesion_boton.setForeground(Color.WHITE);

        iniciar_sesion_boton.setBackground(Color.BLACK);

        iniciar_sesion_boton.setFont(new Font("Arial", Font.BOLD, 16));

        iniciar_sesion_boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        iniciar_sesion_boton.setBorder(BorderFactory.createEmptyBorder(4,4,4,4));

        iniciar_sesion_boton.setMaximumSize(new Dimension(180, 20));

        iniciar_sesion_boton.setPreferredSize(new Dimension(180, 40));

        iniciar_sesion_boton.setAlignmentX(Component.LEFT_ALIGNMENT);

        iniciar_sesion_boton.addActionListener(e -> {

            esValidoInicioSesion(error_email, error_contraseña);

        });

        contenedor_logeo.add(iniciar_sesion_boton);

        contenedor_logeo.add(Box.createVerticalStrut(45));

        // ================= REGISTRO =================

        JLabel texto_registro = new JLabel("¿No tienes una cuenta?");

        texto_registro.setFont(new Font("Arial", Font.PLAIN, 14));

        texto_registro.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor_logeo.add(texto_registro);

        contenedor_logeo.add(Box.createVerticalStrut(15));

        // ================= CREATE ACCOUNT =================

        JLabel registrarse = new JLabel("Lánzate!");

        registrarse.setFont(new Font("Arial", Font.BOLD, 12));

        registrarse.setCursor(new Cursor(Cursor.HAND_CURSOR));

        registrarse.setAlignmentX(Component.LEFT_ALIGNMENT);

        registrarse.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {

                cardLayout.show(contenedorPrincipal, "registro");

            }

        });

        contenedor_logeo.add(registrarse);


        // ================= ADD FINAL =================

        add(contenedor_logeo);
    }

    private void esValidoInicioSesion(JLabel error_email, JLabel error_contraseña){

        String email = email_escribir.getText().trim();

        String password = contraseña_escribir.getText().trim();

        boolean hayError = false;

        // VALIDAR CAMPOS VACÍOS

        if (email.isEmpty()) {

            error_email.setText("El email no puede estar vacío");

            error_email.setVisible(true);

            hayError = true;

        }

        if (password.isEmpty()) {

            error_contraseña.setText("La contraseña no puede estar vacía");

            error_contraseña.setVisible(true);

            hayError = true;

        }

        if (hayError) return;

        // VALIDAR EN BASE DE DATOS

        int suceso = consultas.buscarPorEmail(email, password);

        if (suceso == -1){

            error_email.setText("El email no está registrado");

            error_email.setVisible(true);

            error_contraseña.setVisible(false);

        } else if (suceso == 0){

            error_email.setVisible(false);

            error_contraseña.setText("Contraseña incorrecta");

            error_contraseña.setVisible(true);

        } else if (suceso == 1){

            error_email.setVisible(false);

            error_contraseña.setVisible(false);

            email_escribir.setText("");
            contraseña_escribir.setText("");

            pantallaPrincipal.setUsuario(consultas.obtenerUsuarioPorEmail(email));
            pantallaPrincipal.actualizarElementosPanelSuperior();
            cardLayout.show(contenedorPrincipal, "pantallaPrincipal");

        }
    }
}