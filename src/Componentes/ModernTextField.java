package Componentes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ModernTextField extends JTextField {

    private int radio = 8;

    public ModernTextField() {
        setOpaque(false);
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE);
        setBackground(new Color(50, 50, 52));
        setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(new Color(50, 50, 52));
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radio, radio));

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Borde redondeado
        g2.setColor(new Color(70, 70, 75));
        g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, radio, radio));

        g2.dispose();
    }
}
