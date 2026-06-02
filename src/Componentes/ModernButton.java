package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ModernButton extends JButton {

    private final int radio = 8;
    private boolean hovered = false;

    private static final Color COLOR_FONDO   = new Color(50, 50, 52);
    private static final Color COLOR_BORDE   = new Color(70, 70, 75);
    private static final Color COLOR_HOVER   = new Color(65, 65, 68);
    private static final Color COLOR_PRESSED = new Color(40, 40, 42);

    public ModernButton(String texto) {
        super(texto);
        init();
    }

    public ModernButton() {
        super();
        init();
    }

    private void init() {
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseEntered(java.awt.event.MouseEvent e) { hovered = true;  repaint(); }
            @Override public void mouseExited (java.awt.event.MouseEvent e) { hovered = false; repaint(); }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color fondo = getModel().isPressed() ? COLOR_PRESSED : (hovered ? COLOR_HOVER : COLOR_FONDO);
        g2.setColor(fondo);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radio, radio));

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(COLOR_BORDE);
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radio, radio));
        g2.dispose();
    }
}