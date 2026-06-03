import Componentes.ModernButton;
import Componentes.ModernScrollPane;
import Modelos.Coche;
import Modelos.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class VentanaDetalleCocheDisponible extends JFrame {

    private static final Color BG_DEEP    = new Color(17, 17, 17);
    private static final Color BG_SURFACE = new Color(26, 26, 26);
    private static final Color BG_CARD    = new Color(30, 30, 30);
    private static final Color BORDER     = new Color(255, 255, 255, 20);
    private static final Color TEXT_PRI   = new Color(255, 255, 255);
    private static final Color TEXT_HINT  = new Color(255, 255, 255, 60);
    private static final Color ACCENT     = new Color(255, 255, 255);
    private static final Color DANGER_FG  = new Color(226, 75, 74);

    private Consultas consultas = new Consultas();

    public VentanaDetalleCocheDisponible(Coche coche, String url_img, PantallaPrincipal pantallaPrincipal) {

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(660, 620);
        setShape(new RoundRectangle2D.Double(0, 0, 660, 620, 20, 20));
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DEEP);
        root.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        // ── PANEL IMAGEN ──────────────────────────────────────────
        JPanel panelImagen = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(BG_SURFACE);
                g2.fillRect(0, 0, getWidth(), getHeight());
                Image img = (Image) getClientProperty("imagen");
                if (img != null) {
                    int aF  = (int) getClientProperty("anchoFinal");
                    int alF = (int) getClientProperty("altoFinal");
                    g2.drawImage(img, (getWidth() - aF) / 2, (getHeight() - alF) / 2, this);
                }
                g2.dispose();
            }
        };
        panelImagen.setPreferredSize(new Dimension(660, 240));
        panelImagen.setOpaque(false);

        if (url_img != null) {
            ImageIcon icono = new ImageIcon(url_img);
            Image orig = icono.getImage();
            int w = orig.getWidth(null), h = orig.getHeight(null);
            if (w > 0 && h > 0) {
                double escala = Math.min(660.0 / w, 240.0 / h);
                int wf = (int)(w * escala), hf = (int)(h * escala);
                Image scaled = orig.getScaledInstance(wf, hf, Image.SCALE_SMOOTH);
                SwingUtilities.invokeLater(() -> {
                    panelImagen.putClientProperty("imagen",     scaled);
                    panelImagen.putClientProperty("anchoFinal", wf);
                    panelImagen.putClientProperty("altoFinal",  hf);
                    panelImagen.repaint();
                });
            }
        }

        // Overlay: REF + botón cerrar
        JPanel overlayImagen = new JPanel(new BorderLayout());
        overlayImagen.setOpaque(false);
        overlayImagen.setBorder(BorderFactory.createEmptyBorder(14, 16, 14, 16));

        JLabel lblRef = new JLabel("REF #" + coche.getId());
        lblRef.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblRef.setForeground(TEXT_HINT);
        lblRef.setOpaque(true);
        lblRef.setBackground(new Color(0, 0, 0, 120));
        lblRef.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));

        overlayImagen.add(lblRef,          BorderLayout.WEST);
        overlayImagen.add(buildCloseButton(), BorderLayout.EAST);

        panelImagen.add(overlayImagen, BorderLayout.NORTH);
        panelImagen.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        // ── PANEL INFO ────────────────────────────────────────────
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(BG_DEEP);
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(22, 28, 26, 28));

        // Título + tarjeta precio
        JPanel rowTitulo = new JPanel(new BorderLayout());
        rowTitulo.setBackground(BG_DEEP);
        rowTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        rowTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel colTitulo = new JPanel();
        colTitulo.setLayout(new BoxLayout(colTitulo, BoxLayout.Y_AXIS));
        colTitulo.setBackground(BG_DEEP);

        JLabel lblNombre = new JLabel(coche.getMarca() + " " + coche.getModelo());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblNombre.setForeground(TEXT_PRI);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMeta = new JLabel(coche.getAnio() + "  ·  " +
                String.format("%,d km", coche.getKilometros()));
        lblMeta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblMeta.setForeground(TEXT_HINT);
        lblMeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        colTitulo.add(lblNombre);
        colTitulo.add(Box.createVerticalStrut(4));
        colTitulo.add(lblMeta);

        JPanel precioCard = buildStatCard(
                "Precio",
                String.format("%,.02f €", coche.getPrecio())
        );

        rowTitulo.add(colTitulo,  BorderLayout.WEST);
        rowTitulo.add(precioCard, BorderLayout.EAST);

        // Grid de 3 stats
        JPanel grid = new JPanel(new GridLayout(1, 3, 10, 0));
        grid.setBackground(BG_DEEP);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));
        grid.setAlignmentX(Component.LEFT_ALIGNMENT);
        grid.add(buildGridCard("Transmisión", String.valueOf(coche.getTransmision())));
        grid.add(buildGridCard("Potencia",    String.format("%,d cv", coche.getPotencia())));
        grid.add(buildGridCard("Color",       coche.getColor()));

        // Separador
        JSeparator sep = new JSeparator();
        sep.setForeground(BORDER);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Label error (oculto por defecto)
        JLabel lblError = new JLabel(" ");
        lblError.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblError.setForeground(DANGER_FG);
        lblError.setAlignmentX(Component.LEFT_ALIGNMENT);

        // COMBO FORMA DE PAGO
        JLabel lblFormaPago = new JLabel("FORMA DE PAGO");
        lblFormaPago.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblFormaPago.setForeground(TEXT_HINT);
        lblFormaPago.setAlignmentX(Component.LEFT_ALIGNMENT);

        String[] formasPago = {"Pago único", "Financiación 12 meses", "Financiación 24 meses"};
        JComboBox<String> comboPago = new JComboBox<>(formasPago);
        comboPago.setMaximumSize(new Dimension(280, 35));
        comboPago.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboPago.setBackground(new Color(30, 30, 30));
        comboPago.setForeground(Color.WHITE);
        comboPago.setFont(new Font("SansSerif", Font.PLAIN, 13));

        // Etiqueta que muestra el precio según la forma de pago
        JLabel lblPrecioFinal = new JLabel(String.format("Total: %,.02f €", coche.getPrecio()));
        lblPrecioFinal.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPrecioFinal.setForeground(new Color(52, 199, 89));
        lblPrecioFinal.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Al cambiar la selección, recalcula el precio mostrado
        comboPago.addActionListener(e -> {
            String seleccion = (String) comboPago.getSelectedItem();
            double interes;
            switch (seleccion) {
                case "Financiación 12 meses" -> interes = 0.05;
                case "Financiación 24 meses" -> interes = 0.10;
                default                      -> interes = 0.0;
            }
            double cantidadInteres = coche.getPrecio() * interes;
            double precioFinal     = coche.getPrecio() + cantidadInteres;

            if (interes == 0) {
                lblPrecioFinal.setText(String.format("%,.02f €", coche.getPrecio()));
            } else {
                lblPrecioFinal.setText(String.format("%,.02f € + %,.02f € = %,.02f €",
                        coche.getPrecio(), cantidadInteres, precioFinal));
            }
        });

        // Botón comprar
        JButton btnComprar = new ModernButton("Comprar coche");
        btnComprar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnComprar.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnComprar.setMaximumSize(new Dimension(200, 50));
        btnComprar.setPreferredSize(new Dimension(200, 50));
        btnComprar.addActionListener(e -> comprarCoche(pantallaPrincipal.getUsuario(), coche, pantallaPrincipal, lblError));

        panelInfo.add(rowTitulo);
        panelInfo.add(Box.createVerticalStrut(18));
        panelInfo.add(grid);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(sep);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(lblFormaPago);
        panelInfo.add(Box.createVerticalStrut(6));
        panelInfo.add(comboPago);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblPrecioFinal);
        panelInfo.add(Box.createVerticalStrut(18));
        panelInfo.add(btnComprar);
        panelInfo.add(Box.createVerticalStrut(8));
        panelInfo.add(lblError);

        // ── SCROLL + ENSAMBLADO ───────────────────────────────────
        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setBackground(BG_DEEP);
        contenido.add(panelImagen, BorderLayout.NORTH);
        contenido.add(panelInfo,   BorderLayout.CENTER);

        JScrollPane scroll = new ModernScrollPane(contenido);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_DEEP);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        root.add(scroll, BorderLayout.CENTER);
        setContentPane(root);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ── Lógica ────────────────────────────────────────────────────

    private void comprarCoche(Usuario usuario, Coche coche, PantallaPrincipal pantallaPrincipal, JLabel lblError) {
        if (usuario == null) {
            lblError.setText("Error: usuario no cargado.");

        }
        else if (usuario.getDinero() < coche.getPrecio()) {
            lblError.setText("No tienes suficiente dinero.");

        } else {

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    String.format("¿Comprar por %,.2f €?", coche.getPrecio()),
                    "Confirmar compra",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                consultas.comprarCoche(usuario, coche);
                pantallaPrincipal.actualizarCoches();
                pantallaPrincipal.getPanelSuperior().actualizarSaldo(usuario.getDinero());
                dispose();
            }
        }


    }

    // ── Helpers UI ────────────────────────────────────────────────

    private JButton buildCloseButton() {
        JButton btn = new JButton("✕");
        btn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        btn.setForeground(TEXT_HINT);
        btn.setBackground(new Color(0, 0, 0, 120));
        btn.setOpaque(true);
        btn.setBorderPainted(true);
        btn.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(30, 30));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(e -> dispose());
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { btn.setBackground(new Color(60, 60, 60, 180)); }
            @Override public void mouseExited(MouseEvent e)  { btn.setBackground(new Color(0, 0, 0, 120)); }
        });
        return btn;
    }

    private JPanel buildStatCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(TEXT_HINT);
        lbl.setAlignmentX(Component.RIGHT_ALIGNMENT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 17));
        val.setForeground(TEXT_PRI);
        val.setAlignmentX(Component.RIGHT_ALIGNMENT);

        card.add(lbl);
        card.add(Box.createVerticalStrut(2));
        card.add(val);
        return card;
    }

    private JPanel buildGridCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lbl.setForeground(TEXT_HINT);

        JLabel val = new JLabel(value);
        val.setFont(new Font("SansSerif", Font.BOLD, 15));
        val.setForeground(TEXT_PRI);

        card.add(lbl);
        card.add(Box.createVerticalStrut(4));
        card.add(val);
        return card;
    }

    private JButton buildAccentButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBackground(ACCENT);
        btn.setForeground(BG_DEEP);
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { btn.setBackground(new Color(230, 230, 230)); }
            @Override public void mouseExited(MouseEvent e)   { btn.setBackground(ACCENT); }
            @Override public void mousePressed(MouseEvent e)  { btn.setBackground(new Color(200, 200, 200)); }
            @Override public void mouseReleased(MouseEvent e) { btn.setBackground(ACCENT); }
        });
        return btn;
    }
}