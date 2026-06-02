package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class ModernCheckBox extends JCheckBox {

    private final int radio = 6;

    private static final Color COLOR_FONDO   = new Color(50, 50, 52);
    private static final Color COLOR_HOVER   = new Color(65, 65, 68);
    private static final Color COLOR_BORDE   = new Color(70, 70, 75);
    private static final Color COLOR_CHECK   = new Color(100, 149, 237); // azul suave

    public ModernCheckBox(String texto) {
        super(texto);
        init();
    }

    private void init() {
        setOpaque(false);
        setBackground(COLOR_FONDO);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.PLAIN, 12));
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(BorderFactory.createEmptyBorder(2, 6, 2, 6));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo de la fila
        Color fondo = getModel().isRollover() ? COLOR_HOVER : COLOR_FONDO;
        g2.setColor(fondo);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radio, radio));

        // Caja del checkbox (16x16, centrada verticalmente)
        int cajaSize = 14;
        int cajaX = 6;
        int cajaY = (getHeight() - cajaSize) / 2;

        // Fondo de la caja
        g2.setColor(COLOR_FONDO);
        g2.fill(new RoundRectangle2D.Double(cajaX, cajaY, cajaSize, cajaSize, 4, 4));

        // Borde de la caja
        g2.setColor(isSelected() ? COLOR_CHECK : COLOR_BORDE);
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Double(cajaX, cajaY, cajaSize, cajaSize, 4, 4));

        // Tick si está seleccionado
        if (isSelected()) {
            g2.setColor(COLOR_CHECK);
            g2.setStroke(new BasicStroke(2f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            int mx = cajaX + cajaSize / 2;
            int my = cajaY + cajaSize / 2;
            g2.drawLine(cajaX + 3, my,     mx - 1,     cajaY + cajaSize - 3);
            g2.drawLine(mx - 1,   cajaY + cajaSize - 3, cajaX + cajaSize - 2, cajaY + 3);
        }

        g2.dispose();

        // Desplazar el texto para que no solape la caja pintada
        setIconTextGap(cajaSize + 6);
        setIcon(new Icon() {
            public void paintIcon(Component c, Graphics g3, int x, int y) {}
            public int getIconWidth()  { return cajaSize + 4; }
            public int getIconHeight() { return cajaSize; }
        });

        super.paintComponent(g);
    }
}