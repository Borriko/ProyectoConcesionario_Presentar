import Componentes.ModernTextField;
import Modelos.Coche;
import Modelos.Usuario;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class VentanaVender extends JFrame {

    private static final Color BG_DEEP    = new Color(17, 17, 17);
    private static final Color BG_SURFACE = new Color(26, 26, 26);
    private static final Color BG_CARD    = new Color(30, 30, 30);
    private static final Color BORDER     = new Color(255, 255, 255, 20);

    private static final Color TEXT_PRI   = new Color(255, 255, 255);
    private static final Color TEXT_SEC   = new Color(255, 255, 255, 120);
    private static final Color TEXT_HINT  = new Color(255, 255, 255, 70);

    private static final Color ACCENT     = new Color(255, 255, 255);

    private static final Color DANGER_BG  = new Color(226, 75, 74, 40);
    private static final Color DANGER_FG  = new Color(226, 75, 74);

    private final ModernTextField txtPrecio;
    private final JPanel errorPanel;

    private final Consultas consultas = new Consultas();

    private VentanaDetalleCochePropio ventanaDetalleCochePropio;

    public VentanaVender(Coche coche, PantallaPrincipal pantallaPrincipal, VentanaDetalleCochePropio ventanaDetalleCochePropio) {

        this.ventanaDetalleCochePropio = ventanaDetalleCochePropio;

        setTitle("Vender coche");
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(420, 560);
        setShape(new RoundRectangle2D.Double(0, 0, 420, 560, 26, 26));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DEEP);
        root.setBorder(BorderFactory.createLineBorder(BORDER, 1));

        // =========================================================
        // HEADER
        // =========================================================

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(BG_DEEP);
        header.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        JPanel closeContainer = new JPanel(new BorderLayout());
        closeContainer.setOpaque(false);

        JButton btnClose = new JButton("✕");
        btnClose.setFont(new Font("SansSerif", Font.PLAIN, 12));
        btnClose.setForeground(TEXT_HINT);
        btnClose.setBackground(BG_SURFACE);
        btnClose.setOpaque(true);
        btnClose.setBorderPainted(false);
        btnClose.setFocusPainted(false);
        btnClose.setPreferredSize(new Dimension(36, 36));
        btnClose.setMinimumSize(new Dimension(36, 36));
        btnClose.setMaximumSize(new Dimension(36, 36));
        btnClose.setMargin(new Insets(0, 0, 0, 0));

        btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnClose.addActionListener(e -> dispose());

        closeContainer.add(btnClose, BorderLayout.EAST);

        JPanel iconBox = new JPanel();
        iconBox.setPreferredSize(new Dimension(60, 60));
        iconBox.setMaximumSize(new Dimension(60, 60));
        iconBox.setBackground(BG_SURFACE);
        iconBox.setBorder(BorderFactory.createLineBorder(BORDER, 1));
        iconBox.setLayout(new GridBagLayout());

        JLabel icon = new JLabel("🚗");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 26));
        iconBox.add(icon);

        iconBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblSub = new JLabel("PUBLICAR VEHÍCULO");
        lblSub.setFont(new Font("SansSerif", Font.BOLD, 10));
        lblSub.setForeground(TEXT_HINT);
        lblSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel("Poner en venta");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(TEXT_PRI);
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(closeContainer);
        header.add(Box.createVerticalStrut(10));
        header.add(iconBox);
        header.add(Box.createVerticalStrut(14));
        header.add(lblSub);
        header.add(Box.createVerticalStrut(4));
        header.add(lblTitle);

        // =========================================================
        // BODY
        // =========================================================

        JPanel body = new JPanel();
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));
        body.setBackground(BG_DEEP);
        body.setBorder(BorderFactory.createEmptyBorder(24, 30, 30, 30));

        JPanel carCard = buildInfoCard(
                coche.getMarca() + " " + coche.getModelo() + " · " + coche.getAnio(),
                "El precio será visible para los compradores"
        );

        JLabel lblPrecio = new JLabel("PRECIO DE VENTA");
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblPrecio.setForeground(TEXT_HINT);
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtPrecio = new ModernTextField();
        txtPrecio.setMaximumSize(new Dimension(280, 58));
        txtPrecio.setPreferredSize(new Dimension(280, 58));
        txtPrecio.setHorizontalAlignment(JTextField.CENTER);
        txtPrecio.setFont(new Font("SansSerif", Font.BOLD, 22));
        txtPrecio.setBackground(BG_SURFACE);
        txtPrecio.setForeground(TEXT_PRI);
        txtPrecio.setCaretColor(TEXT_PRI);
        txtPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);


        errorPanel = buildErrorPanel("Introduce un precio válido");
        errorPanel.setVisible(false);

        JButton btnConfirmar = buildAccentButton("✓ Confirmar venta");
        btnConfirmar.addActionListener(e ->
                handleConfirmar(pantallaPrincipal.getUsuario(), coche, pantallaPrincipal)
        );

        JLabel lblNota = new JLabel("Esta acción no se puede deshacer");
        lblNota.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblNota.setForeground(TEXT_HINT);
        lblNota.setAlignmentX(Component.CENTER_ALIGNMENT);


        body.add(carCard);

        body.add(Box.createVerticalStrut(30));

        body.add(lblPrecio);

        body.add(Box.createVerticalStrut(12));

        body.add(txtPrecio);



        body.add(Box.createVerticalStrut(18));

        body.add(errorPanel);

        body.add(Box.createVerticalStrut(14));

        body.add(btnConfirmar);

        body.add(Box.createVerticalStrut(12));

        body.add(lblNota);

        root.add(header, BorderLayout.NORTH);
        root.add(body, BorderLayout.CENTER);

        setContentPane(root);
        setVisible(true);
    }

    // =============================================================
    // LÓGICA
    // =============================================================

    private void handleConfirmar(Usuario usuario,
                                 Coche coche,
                                 PantallaPrincipal pantalla) {

        try {

            double precio = Double.parseDouble(txtPrecio.getText().trim());

            if (precio <= 0) {
                throw new NumberFormatException();
            }

            errorPanel.setVisible(false);

            coche.setPrecio(precio);

            consultas.venderCoche(usuario, coche);

            pantalla.actualizarCoches();

            pantalla.getPanelSuperior()
                    .actualizarSaldo(usuario.getDinero());

            showSuccess(precio);

        } catch (NumberFormatException ex) {

            errorPanel.setVisible(true);

            txtPrecio.setBorder(
                    BorderFactory.createLineBorder(DANGER_FG, 1)
            );
        }
    }

    private void showSuccess(double precio) {

        JOptionPane.showMessageDialog(
                this,
                String.format("Coche vendido por %.2f €", precio),
                "Venta completada",
                JOptionPane.INFORMATION_MESSAGE
        );

        dispose();
        ventanaDetalleCochePropio.dispose();

    }


    // =============================================================
    // COMPONENTES
    // =============================================================

    private JPanel buildInfoCard(String title, String subtitle) {

        JPanel card = new JPanel();

        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        card.setBackground(BG_CARD);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        card.setMaximumSize(new Dimension(320, 90));

        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lTitle = new JLabel(title);

        lTitle.setFont(new Font("SansSerif", Font.BOLD, 15));

        lTitle.setForeground(TEXT_PRI);

        lTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lSub = new JLabel(subtitle);

        lSub.setFont(new Font("SansSerif", Font.PLAIN, 12));

        lSub.setForeground(TEXT_HINT);

        lSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(lTitle);

        card.add(Box.createVerticalStrut(6));

        card.add(lSub);

        return card;
    }



    private JPanel buildErrorPanel(String msg) {

        JPanel panel = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 8, 6)
        );

        panel.setBackground(DANGER_BG);

        panel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 75, 74, 80),
                        1
                )
        );

        panel.setMaximumSize(new Dimension(320, 38));

        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lbl = new JLabel("⚠ " + msg);

        lbl.setFont(new Font("SansSerif", Font.PLAIN, 12));

        lbl.setForeground(DANGER_FG);

        panel.add(lbl);

        return panel;
    }

    private JButton buildAccentButton(String text) {

        JButton btn = new JButton(text);

        btn.setFont(new Font("SansSerif", Font.BOLD, 14));

        btn.setBackground(ACCENT);

        btn.setForeground(BG_DEEP);

        btn.setOpaque(true);

        btn.setBorderPainted(false);

        btn.setFocusPainted(false);

        btn.setCursor(
                Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)
        );

        btn.setMaximumSize(new Dimension(280, 50));

        btn.setPreferredSize(new Dimension(280, 50));

        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        btn.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(230, 230, 230));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ACCENT);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                btn.setBackground(new Color(200, 200, 200));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                btn.setBackground(ACCENT);
            }
        });

        return btn;
    }

    public double getPrecio() {

        try {
            return Double.parseDouble(txtPrecio.getText());
        }
        catch (Exception e) {
            return 0;
        }
    }
}