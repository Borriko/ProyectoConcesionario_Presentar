import Modelos.Coche;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;

public class TarjetaCoche extends JPanel {

    private Consultas consultas = new Consultas();

    public TarjetaCoche(Coche coche, PantallaPrincipal pantallaPrincipal, JPanel panelCoches_actual,JPanel panelCochesDisponibles) {

        int id = coche.getId();
        String marca = coche.getMarca();
        String modelo = coche.getModelo();
        int km = coche.getKilometros();
        double precio = coche.getPrecio();
        int anio   = coche.getAnio();

        // =========================
        // PANEL PRINCIPAL
        // =========================
        JPanel tarjeta = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
            }
        };

        tarjeta.setLayout(new BorderLayout());
        tarjeta.setBackground(new Color(24, 24, 26));
        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(6, 6, 10, 6));

        Dimension tamaño = new Dimension(320, 260);
        tarjeta.setPreferredSize(tamaño);
        tarjeta.setMaximumSize(tamaño);

        // =========================
        // IMAGEN
        // =========================
        int anchoObjetivo = 280;
        int altoObjetivo  = 150;

        JPanel panelImagen = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.fillRoundRect(0, 0, getWidth(), getHeight() + 30, 30, 30);

                Image img = (Image) getClientProperty("imagen");
                if (img != null) {
                    int aF  = (int) getClientProperty("anchoFinal");
                    int alF = (int) getClientProperty("altoFinal");
                    int x   = (getWidth()  - aF) / 2;
                    int y   = (getHeight() - alF) / 2;
                    g2.drawImage(img, x, y, this);
                }

                g2.dispose();
            }
        };

        panelImagen.setPreferredSize(new Dimension(anchoObjetivo, altoObjetivo));
        panelImagen.setOpaque(false);

        String url_imagen = consultas.obtenerURLFotosPorIdCoche(coche.getId());

        SwingUtilities.invokeLater(() -> tarjeta.putClientProperty("url_imagen", url_imagen));

        if (url_imagen != null) {
            ImageIcon icono          = new ImageIcon(url_imagen);
            Image imagenOriginal     = icono.getImage();

            int anchoOrig = imagenOriginal.getWidth(null);
            int altoOrig  = imagenOriginal.getHeight(null);

            if (anchoOrig > 0 && altoOrig > 0) {
                double escala = Math.min(
                        (double) anchoObjetivo / anchoOrig,
                        (double) altoObjetivo  / altoOrig
                );

                int anchoFinal = (int) (anchoOrig * escala);
                int altoFinal  = (int) (altoOrig  * escala);

                Image imagenEscalada = imagenOriginal.getScaledInstance(anchoFinal, altoFinal, Image.SCALE_SMOOTH);

                SwingUtilities.invokeLater(() -> {
                    panelImagen.putClientProperty("imagen",      imagenEscalada);
                    panelImagen.putClientProperty("anchoFinal",  anchoFinal);
                    panelImagen.putClientProperty("altoFinal",   altoFinal);
                    panelImagen.repaint();
                });
            }
        }

        // =========================
        // BADGE AÑO
        // =========================
        JLabel lblAnio = new JLabel("  " + anio + "  ");
        lblAnio.setOpaque(true);
        lblAnio.setBackground(new Color(0, 0, 0, 170));
        lblAnio.setForeground(Color.WHITE);
        lblAnio.setFont(new Font("SansSerif", Font.BOLD, 12));
        lblAnio.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));

        JPanel topOverlay = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topOverlay.setOpaque(false);
        topOverlay.add(lblAnio);

        panelImagen.add(topOverlay, BorderLayout.NORTH);

        // =========================
        // INFO
        // =========================

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(12, 14, 12, 14));

        JLabel lblTitulo = new JLabel(marca + " " + modelo);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblKm = new JLabel(String.format("%,d km", km));
        lblKm.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblKm.setForeground(new Color(170, 170, 175));

        JLabel lblPrecio = new JLabel(String.format("%.0f €", precio));
        lblPrecio.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblPrecio.setForeground(new Color(52, 199, 89));

        JLabel lblId = new JLabel("REF #" + id);
        lblId.setFont(new Font("SansSerif", Font.PLAIN, 11));
        lblId.setForeground(new Color(120, 120, 125));

        panelInfo.add(lblTitulo);
        panelInfo.add(Box.createVerticalStrut(6));
        panelInfo.add(lblKm);

        if (panelCoches_actual == panelCochesDisponibles){
            panelInfo.add(Box.createVerticalStrut(12));
            panelInfo.add(lblPrecio);
        }
        panelInfo.add(Box.createVerticalStrut(4));
        panelInfo.add(lblId);

        // =========================
        // LISTENERS
        // =========================
        tarjeta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tarjeta.setBackground(new Color(34, 34, 36));
                tarjeta.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tarjeta.repaint();
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tarjeta.setBackground(new Color(24, 24, 26));
                tarjeta.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                tarjeta.repaint();
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                String url = (String) tarjeta.getClientProperty("url_imagen");
                if (panelCoches_actual == panelCochesDisponibles){
                    new VentanaDetalleCocheDisponible(coche, url, pantallaPrincipal);
                } else {
                    new VentanaDetalleCochePropio(coche,url,pantallaPrincipal);
                }
            }
        });

        // =========================
        // AÑADIR TODO
        // =========================
        tarjeta.add(panelImagen, BorderLayout.NORTH);
        tarjeta.add(panelInfo,   BorderLayout.CENTER);

        setOpaque(false);
        setLayout(new BorderLayout());
        add(tarjeta);
    }
}