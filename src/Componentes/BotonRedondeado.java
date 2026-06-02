package Componentes;

import javax.swing.*;
import java.awt.*;

public class BotonRedondeado extends JButton {

    private int radio;

    public BotonRedondeado(String texto, int radio) {

        super(texto);

        this.radio = radio;

        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

        g2.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());

        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

        g2.dispose();
    }
}

