package Componentes;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class ModernScrollPane extends JScrollPane {

    public ModernScrollPane(Component view) {

        super(view);

        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder());
        setViewportBorder(BorderFactory.createEmptyBorder());

        getViewport().setOpaque(true);
        getViewport().setBackground(new Color(45, 45, 45));

        setHorizontalScrollBarPolicy(HORIZONTAL_SCROLLBAR_NEVER);

        JScrollBar vertical = getVerticalScrollBar();

        vertical.setPreferredSize(new Dimension(10, 0));
        vertical.setUI(new ModernScrollBarUI());
        vertical.setUnitIncrement(12);

        setBackground(new Color(45, 45, 45));
    }

    private static class ModernScrollBarUI extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {
            thumbColor = new Color(110, 110, 110);
            trackColor = new Color(45, 45, 45);
        }

        @Override
        protected JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private JButton createZeroButton() {

            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));

            return button;
        }

        @Override
        protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            // Limpia el fondo real
            g2.setColor(new Color(45, 45, 45));
            g2.fillRect(
                    trackBounds.x,
                    trackBounds.y,
                    trackBounds.width,
                    trackBounds.height
            );

            // Track moderno
            g2.setColor(trackColor);

            g2.fillRoundRect(
                    trackBounds.x + 2,
                    trackBounds.y,
                    trackBounds.width - 4,
                    trackBounds.height,
                    10,
                    10
            );

            g2.dispose();
        }

        @Override
        protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

            if (thumbBounds.isEmpty() || !scrollbar.isEnabled()) {
            }

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(isThumbRollover()
                    ? new Color(140, 140, 140)
                    : thumbColor);

            g2.fillRoundRect(
                    thumbBounds.x + 2,
                    thumbBounds.y,
                    thumbBounds.width - 4,
                    thumbBounds.height,
                    10,
                    10
            );

            g2.dispose();
        }
    }
}