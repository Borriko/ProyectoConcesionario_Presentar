import Componentes.ModernButton;
import Componentes.ModernScrollPane;
import Modelos.Coche;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class VentanaDetalleCochePropio extends JFrame {

    private static final Color BG_DEEP    = new Color(17, 17, 17);
    private static final Color BG_SURFACE = new Color(26, 26, 26);
    private static final Color BG_CARD    = new Color(30, 30, 30);
    private static final Color BORDER     = new Color(255, 255, 255, 20);
    private static final Color TEXT_PRI   = new Color(255, 255, 255);
    private static final Color TEXT_SEC   = new Color(255, 255, 255, 100);
    private static final Color TEXT_HINT  = new Color(255, 255, 255, 60);
    private static final Color ACCENT     = new Color(255, 255, 255);

    private Consultas consultas = new Consultas();

    public VentanaDetalleCochePropio(Coche coche, String url_img, PantallaPrincipal pantallaPrincipal) {

        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(660, 520);
        setShape(new RoundRectangle2D.Double(0, 0, 660, 520, 20, 20));
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

        JButton btnClose = contruirBotonCerrar();

        overlayImagen.add(lblRef,   BorderLayout.WEST);
        overlayImagen.add(btnClose, BorderLayout.EAST);

        panelImagen.add(overlayImagen, BorderLayout.NORTH);
        panelImagen.setBorder(new MatteBorder(0, 0, 1, 0, BORDER));

        // ── PANEL INFO ────────────────────────────────────────────
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(BG_DEEP);
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(22, 28, 26, 28));

// =========================================================
// TÍTULO + PRECIO
// =========================================================

        JPanel rowTitulo = new JPanel(new BorderLayout());
        rowTitulo.setBackground(BG_DEEP);
        rowTitulo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        rowTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel colTitulo = new JPanel();
        colTitulo.setLayout(new BoxLayout(colTitulo, BoxLayout.Y_AXIS));
        colTitulo.setBackground(BG_DEEP);
        colTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblNombre = new JLabel(
                coche.getMarca() + " " + coche.getModelo()
        );

        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblNombre.setForeground(TEXT_PRI);
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lblMeta = new JLabel(
                coche.getAnio() + "  ·  " +
                        String.format("%,d km", coche.getKilometros())
        );

        lblMeta.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblMeta.setForeground(TEXT_HINT);
        lblMeta.setAlignmentX(Component.LEFT_ALIGNMENT);

        colTitulo.add(lblNombre);
        colTitulo.add(Box.createVerticalStrut(4));
        colTitulo.add(lblMeta);



        // Alineación izquierda completa
        rowTitulo.add(colTitulo, BorderLayout.WEST);

        // =========================================================
        // GRID STATS
        // =========================================================

        JPanel grid = new JPanel(new GridLayout(1, 3, 10, 0));

        grid.setBackground(BG_DEEP);

        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, 76));

        grid.setAlignmentX(Component.LEFT_ALIGNMENT);

        grid.add(contruirTarjetaGrid(
                "Trasmisión",
                String.valueOf(coche.getTransmision())
        ));

        grid.add(contruirTarjetaGrid(
                "Potencia ",
                String.format("%,d cv", coche.getPotencia())
        ));

        grid.add(contruirTarjetaGrid(
                "Color",
                coche.getColor()
        ));

        // =========================================================
        // SEPARADOR
        // =========================================================

        JSeparator sep = new JSeparator();

        sep.setForeground(BORDER);

        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        sep.setAlignmentX(Component.LEFT_ALIGNMENT);

        // =========================================================
        // BOTÓN
        // =========================================================

        JButton btnVender = new ModernButton("Vender coche");
        btnVender.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnVender.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnVender.setMaximumSize(new Dimension(200, 50));
        btnVender.setPreferredSize(new Dimension(200, 50));
        btnVender.addActionListener(
                e -> new VentanaVender(
                        coche,
                        pantallaPrincipal,
                        this
                )
        );

        // =========================================================
        // ENSAMBLADO
        // =========================================================

        panelInfo.add(rowTitulo);

        panelInfo.add(Box.createVerticalStrut(18));

        panelInfo.add(grid);

        panelInfo.add(Box.createVerticalStrut(20));

        panelInfo.add(sep);

        panelInfo.add(Box.createVerticalStrut(18));

        panelInfo.add(btnVender);

        // ── SCROLL + ENSAMBLADO ──
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

    // ── Helpers UI ──
    private JButton contruirBotonCerrar() {
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



    private JPanel contruirTarjetaGrid(String label, String value) {
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


}