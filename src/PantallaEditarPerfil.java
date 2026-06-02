import Componentes.ModernTextField;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;

public class PantallaEditarPerfil extends JPanel {

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;
    private Usuario usuario;
    private PantallaPrincipal pantallaPrincipal;


    private Consultas consultas = new Consultas();

    private JLabel lblNombreAvatar;

    private JTextField campoNombre;
    private JLabel lblExitoNombre = new JLabel("");
    private JTextField campoEmail;
    private JTextField campoTelefono;
    private String nombre_us;
    private JPasswordField campoContrasenaActual;
    private JPasswordField campoContrasenaNueva;
    private JPasswordField campoContrasenaRepetir;

    public PantallaEditarPerfil(CardLayout cardLayout, JPanel contenedorPrincipal, PantallaPrincipal pantallaPrincipal) {

        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.pantallaPrincipal = pantallaPrincipal;

        setLayout(new BorderLayout());
        setBackground(new Color(20, 20, 20));

        // =========================
        // PANEL SUPERIOR
        // =========================
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
        panelSuperior.setBackground(new Color(30, 30, 30));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel btnVolver = new JLabel("← Volver");
        btnVolver.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnVolver.setForeground(new Color(100, 100, 255));
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(contenedorPrincipal, "pantallaPrincipal");
            }
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnVolver.setForeground(new Color(150, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnVolver.setForeground(new Color(100, 100, 255));
            }
        });

        JLabel lblTitulo = new JLabel("Mi perfil");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);

        panelSuperior.add(btnVolver);
        panelSuperior.add(Box.createHorizontalGlue());
        panelSuperior.add(lblTitulo);
        panelSuperior.add(Box.createHorizontalStrut(670));

        // =========================
        // PANEL CENTRAL (SCROLL)
        // =========================
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setBackground(new Color(20, 20, 20));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // =========================
        // AVATAR
        // =========================
        JPanel panelAvatar = new JPanel();
        panelAvatar.setOpaque(false);
        panelAvatar.setLayout(new BoxLayout(panelAvatar, BoxLayout.Y_AXIS));
        panelAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel circulo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                // ... (Mantén tu código inicial de inicialización)
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setClip(new java.awt.geom.Ellipse2D.Double(0, 0, getWidth(), getHeight()));

                String url = usuario != null ? usuario.getUrlImagen() : null;

                if (url != null && !url.isEmpty()) {
                    // --- SOLUCIÓN AQUÍ ---
                    // Cargamos la imagen directamente usando ImageIO para evitar el caché de ImageIcon
                    try {
                        java.io.File file = new java.io.File(url);
                        if (file.exists()) {
                            Image imgOriginal = javax.imageio.ImageIO.read(file);
                            Image img = imgOriginal.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
                            g2.drawImage(img, 0, 0, this);
                        } else {
                            // Si el archivo no existe por alguna razón, forzar el dibujo por defecto
                            url = null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        url = null; // Si falla la lectura, caerá en el "else" y pintará la inicial
                    }
                }

                // Esto se ejecuta si la url era nula o si falló la carga del archivo arriba
                if (url == null || url.isEmpty()) {
                    g2.setColor(new Color(60, 60, 70));
                    g2.fillOval(0, 0, getWidth(), getHeight());
                    g2.setClip(null);
                    g2.setColor(new Color(150, 150, 160));
                    g2.setFont(new Font("SansSerif", Font.BOLD, 36));
                    FontMetrics fm = g2.getFontMetrics();
                    String inicial = usuario != null ? String.valueOf(usuario.getNombre().charAt(0)).toUpperCase() : "?";
                    int x = (getWidth()  - fm.stringWidth(inicial)) / 2;
                    int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                    g2.drawString(inicial, x, y);
                }

                g2.dispose();
            }
        };
        circulo.setPreferredSize(new Dimension(90, 90));
        circulo.setMaximumSize(new Dimension(90, 90));
        circulo.setOpaque(false);
        circulo.setAlignmentX(Component.CENTER_ALIGNMENT);

// =========================
// BOTON ACTUALIZAR IMAGEN
// =========================
        JButton btnCambiarFoto = new JButton("✏ Cambiar foto");
        btnCambiarFoto.setFont(new Font("SansSerif", Font.PLAIN, 11));
        btnCambiarFoto.setForeground(new Color(100, 100, 255));
        btnCambiarFoto.setBackground(new Color(28, 28, 30));
        btnCambiarFoto.setBorderPainted(false);
        btnCambiarFoto.setFocusPainted(false);
        btnCambiarFoto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiarFoto.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnCambiarFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnCambiarFoto.setForeground(new Color(150, 150, 255));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnCambiarFoto.setForeground(new Color(100, 100, 255));
            }
        });

        btnCambiarFoto.addActionListener(e -> {

            // Abrir selector de archivos
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Selecciona una imagen de perfil");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes (jpg, png, jpeg)", "jpg", "png", "jpeg"
            ));

            int resultado = fileChooser.showOpenDialog(null);

            if (resultado == JFileChooser.APPROVE_OPTION) {

                java.io.File archivoSeleccionado = fileChooser.getSelectedFile();

                // Crear carpeta con el nombre del usuario
                String nombreCarpeta = "img/fotos_de_perfil/" + usuario.getNombre();
                java.io.File carpeta = new java.io.File(nombreCarpeta);

                if (!carpeta.exists()) {
                    carpeta.mkdirs();
                }

                // Copiar la imagen a la carpeta del usuario
                String extension = archivoSeleccionado.getName()
                        .substring(archivoSeleccionado.getName().lastIndexOf("."));
                java.io.File destino = new java.io.File(nombreCarpeta + "/foto_perfil" + extension);

                try {
                    java.nio.file.Files.copy(
                            archivoSeleccionado.toPath(),
                            destino.toPath(),
                            java.nio.file.StandardCopyOption.REPLACE_EXISTING
                    );

                    // Actualizar el usuario y la BD
                    String nuevaUrl = destino.getPath();
                    usuario.setUrlImagen(nuevaUrl);
                    consultas.actualizarUrlImagen(usuario.getId(), nuevaUrl);

                    // Refrescar el círculo
                    circulo.repaint();

                } catch (java.io.IOException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Error al guardar la imagen: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        lblNombreAvatar = new JLabel(usuario != null ? usuario.getNombre() : "Usuario");
        lblNombreAvatar.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblNombreAvatar.setForeground(Color.WHITE);
        lblNombreAvatar.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelAvatar.add(circulo);
        panelAvatar.add(Box.createVerticalStrut(6));
        panelAvatar.add(btnCambiarFoto);
        panelAvatar.add(Box.createVerticalStrut(6));
        panelAvatar.add(lblNombreAvatar);

        // =========================
        // FORMULARIO
        // =========================
        JPanel formulario = new JPanel();
        formulario.setLayout(new BoxLayout(formulario, BoxLayout.Y_AXIS));
        formulario.setBackground(new Color(28, 28, 30));
        formulario.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));
        formulario.setMaximumSize(new Dimension(500, Integer.MAX_VALUE));
        formulario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // =========================
        // SECCIÓN DATOS PERSONALES
        // =========================
        JLabel lblDatos = new JLabel("DATOS PERSONALES");
        lblDatos.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblDatos.setForeground(new Color(120, 120, 130));
        lblDatos.setAlignmentX(Component.LEFT_ALIGNMENT);

        formulario.add(lblDatos);
        formulario.add(Box.createVerticalStrut(12));

        campoNombre = crearCampo(formulario, "Nombre", usuario != null ? usuario.getNombre() : "");

        lblExitoNombre.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblExitoNombre.setForeground(new Color(52, 199, 89));
        lblExitoNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblExitoNombre.setVisible(false);

        formulario.add(Box.createVerticalStrut(4));
        formulario.add(lblExitoNombre);

        formulario.add(Box.createVerticalStrut(12));
        campoEmail = crearCampo(formulario, "Email", usuario != null ? usuario.getEmail() : "");

        campoEmail.setEditable(false);
        campoEmail.setBackground(new Color(88, 88, 89));
        campoEmail.setForeground(new Color(151, 149, 149));

        formulario.add(Box.createVerticalStrut(12));
        campoTelefono = crearCampo(formulario, "Dinero", usuario != null ? String.valueOf(usuario.getDinero()) : "");
        formulario.add(Box.createVerticalStrut(25));

        // =========================
        // SECCIÓN CONTRASEÑA
        // =========================
        JLabel lblSeguridad = new JLabel("SEGURIDAD");
        lblSeguridad.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblSeguridad.setForeground(new Color(120, 120, 130));
        lblSeguridad.setAlignmentX(Component.LEFT_ALIGNMENT);

        formulario.add(lblSeguridad);
        formulario.add(Box.createVerticalStrut(12));

        campoContrasenaActual = crearCampoPassword(formulario, "Contraseña actual");
        formulario.add(Box.createVerticalStrut(12));
        campoContrasenaNueva  = crearCampoPassword(formulario, "Nueva contraseña");
        formulario.add(Box.createVerticalStrut(12));
        campoContrasenaRepetir = crearCampoPassword(formulario, "Repetir nueva contraseña");
        formulario.add(Box.createVerticalStrut(25));

        // =========================
        // BOTÓN GUARDAR
        // =========================
        JButton btnGuardar = new JButton("Guardar cambios") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btnGuardar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setBackground(new Color(52, 199, 89));
        btnGuardar.setOpaque(false);
        btnGuardar.setContentAreaFilled(false);
        btnGuardar.setBorderPainted(false);
        btnGuardar.setFocusPainted(false);
        btnGuardar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGuardar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btnGuardar.setAlignmentX(Component.LEFT_ALIGNMENT);

        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btnGuardar.setBackground(new Color(40, 170, 70));
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                btnGuardar.setBackground(new Color(52, 199, 89));
            }
        });

        btnGuardar.addActionListener(e -> {
            guardarCambios();
        });

        formulario.add(btnGuardar);


        // =========================
        // ENSAMBLAR
        // =========================
        panelContenido.add(panelAvatar);
        panelContenido.add(Box.createVerticalStrut(30));
        panelContenido.add(formulario);

        JScrollPane scroll = new JScrollPane(panelContenido);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(new Color(20, 20, 20));
        scroll.getVerticalScrollBar().setUnitIncrement(12);

        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    // =========================
    // HELPERS
    // =========================
    private JTextField crearCampo(JPanel panel, String etiqueta, String valorInicial) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(170, 170, 175));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField campo = new ModernTextField();
        campo.setText(valorInicial);
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);

        return campo;
    }

    private JPasswordField crearCampoPassword(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lbl.setForeground(new Color(170, 170, 175));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPasswordField campo = new JPasswordField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setBackground(new Color(44, 44, 46));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(60, 60, 65)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(campo);

        return campo;
    }

    // =========================
    // LÓGICA
    // =========================
    private void guardarCambios() {

        String nombre_nuevo = campoNombre.getText().trim();


        if (!nombre_nuevo.isEmpty() && usuario != null && !nombre_nuevo.equals(usuario.getNombre())) {
            if (consultas.cambiarNombreDeUsuario(usuario.getId(), nombre_nuevo)){
                usuario.setNombre(nombre_nuevo);
                lblExitoNombre.setText("Nombre actualizado con éxito");
                lblExitoNombre.setForeground(new Color(52, 199, 89));
                lblExitoNombre.setVisible(true);
            }
        } else if (nombre_nuevo.isEmpty()){
            lblExitoNombre.setText("No se puede poner un nombre nulo");
            lblExitoNombre.setForeground(new Color(223, 33, 33));
            lblExitoNombre.setVisible(true);
        }else if (nombre_nuevo.equals(usuario.getNombre())){
            lblExitoNombre.setText("El nombre nuevo tiene que ser distinto al actual");
            lblExitoNombre.setForeground(new Color(223, 33, 33));
            lblExitoNombre.setVisible(true);
        }else {
            lblExitoNombre.setVisible(false);
        }

        if(usuario != null){
            pantallaPrincipal.actualizarElementosPanelSuperior();
        }

    }


    public void cargarUsuario(Usuario u) {
        this.usuario = u;
        campoNombre.setText(u != null ? u.getNombre() : "");
        campoEmail.setText(u != null ? u.getEmail() : "");
        campoTelefono.setText(u != null ? String.valueOf(u.getDinero()) : "");
        lblNombreAvatar.setText(u != null ? u.getNombre() : "Usuario");
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setPantallaPrincipal(PantallaPrincipal pantallaPrincipal) {
        this.pantallaPrincipal = pantallaPrincipal;
    }
}