import Componentes.ModernScrollPane;
import Componentes.ModernTextField;
import Componentes.MultiSelectDropdown;
import Modelos.Coche;
import Modelos.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PantallaPrincipal extends JPanel {

    private Usuario usuario;

    private Consultas consultas = new Consultas();

    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    private PanelSuperiorPantallaPrincipal panelSuperior;

    private JPanel panelCochesDisponibles = new JPanel();
    private JPanel panelCochesPropios = new JPanel();

    private JPanel panelCoches_actual = panelCochesDisponibles;

    private JScrollPane scrollCoches;

    private JPanel panelLateral = new JPanel();

    private JPanel pestañas;
    private JLabel pestañaDisponibles;
    private JLabel pestañaPropios;

    private javax.swing.Timer timer;
    private JLabel labelTiempo;

    // Filtros
    private MultiSelectDropdown deplegable_marca_de_vehiculo;
    private MultiSelectDropdown deplegable_modelo_de_vehiculo;
    private MultiSelectDropdown desplegable_tipo_de_vehiculo;

    private JTextField campoAnioMin;
    private JTextField campoAnioMax;

    private JLabel errorAnioMin = new JLabel("Solo se permiten números");
    private JLabel errorAnioMax = new JLabel("Solo se permiten números");

    private JTextField campoKmMin;
    private JTextField campoKmMax;

    private JLabel errorKmMin = new JLabel("Solo se permiten números");
    private JLabel errorKmMax = new JLabel("Solo se permiten números");

    public PantallaPrincipal(CardLayout cardLayout, JPanel contenedorPrincipal, PantallaEditarPerfil pantallaEditarPerfil) {

        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;

        setLayout(new BorderLayout());

        // =========================
        // PANEL SUPERIOR
        // =========================
        pantallaEditarPerfil.setUsuario(usuario);
        panelSuperior = new PanelSuperiorPantallaPrincipal( this, cardLayout, contenedorPrincipal, pantallaEditarPerfil);

        // =========================
        // PANEL IZQUIERDO
        // =========================
        panelLateral.setLayout(new BoxLayout(panelLateral, BoxLayout.Y_AXIS));
        panelLateral.setBackground(new Color(35, 35, 35));
        panelLateral.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        Dimension tamañoCampos = new Dimension(160, 25);

        panelLateral.add(Box.createVerticalStrut(20));

        // =========================
        // MARCA VEHÍCULO
        // =========================
        JLabel marcaDeVehiculo = new JLabel("MARCA DE VEHÍCULO");
        marcaDeVehiculo.setForeground(Color.WHITE);
        marcaDeVehiculo.setFont(new Font("Arial", Font.BOLD, 12));
        marcaDeVehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelLateral.add(marcaDeVehiculo);
        panelLateral.add(Box.createVerticalStrut(10));

        String[] marcaVehiculos = consultas.obtenerVehiculosSegunFiltro("marca");

        deplegable_marca_de_vehiculo = new MultiSelectDropdown("Marcas de vehículos", marcaVehiculos);
        deplegable_marca_de_vehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);
        deplegable_marca_de_vehiculo.setMaximumSize(tamañoCampos);

        deplegable_marca_de_vehiculo.setOnChange(() -> {
            String[] modelos;
            if (panelCoches_actual == panelCochesDisponibles) {
                modelos = consultas.obtenerModeloSegunMarca(deplegable_marca_de_vehiculo.getSeleccionados());
            } else {
                modelos = consultas.obtenerModeloSegunMarca(deplegable_marca_de_vehiculo.getSeleccionados(), usuario.getId());
            }
            deplegable_modelo_de_vehiculo.actualizarOpciones(modelos);
            actualizarCoches();
        });

        panelLateral.add(deplegable_marca_de_vehiculo);
        panelLateral.add(Box.createVerticalStrut(20));

        // =========================
        // MODELO VEHÍCULO
        // =========================
        JLabel modeloDeVehiculo = new JLabel("MODELO DE VEHÍCULO");
        modeloDeVehiculo.setForeground(Color.WHITE);
        modeloDeVehiculo.setFont(new Font("Arial", Font.BOLD, 12));
        modeloDeVehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelLateral.add(modeloDeVehiculo);
        panelLateral.add(Box.createVerticalStrut(10));

        String[] modeloVehiculos = consultas.obtenerModeloSegunMarca(deplegable_marca_de_vehiculo.getSeleccionados());

        deplegable_modelo_de_vehiculo = new MultiSelectDropdown("Modelos de vehículos", modeloVehiculos);
        deplegable_modelo_de_vehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);
        deplegable_modelo_de_vehiculo.setMaximumSize(tamañoCampos);
        deplegable_modelo_de_vehiculo.setOnChange(() -> actualizarCoches());

        panelLateral.add(deplegable_modelo_de_vehiculo);
        panelLateral.add(Box.createVerticalStrut(20));

        // =========================
        // TIPOS VEHÍCULO
        // =========================
        JLabel tiposDeVehiculo = new JLabel("TIPOS DE VEHÍCULO");
        tiposDeVehiculo.setForeground(Color.WHITE);
        tiposDeVehiculo.setFont(new Font("Arial", Font.BOLD, 12));
        tiposDeVehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelLateral.add(tiposDeVehiculo);
        panelLateral.add(Box.createVerticalStrut(10));

        String[] tipos = consultas.obtenerVehiculosSegunFiltro("tipo_vehiculo");

        desplegable_tipo_de_vehiculo = new MultiSelectDropdown("Tipos de vehículo", tipos);
        desplegable_tipo_de_vehiculo.setAlignmentX(Component.LEFT_ALIGNMENT);
        desplegable_tipo_de_vehiculo.setMaximumSize(tamañoCampos);
        desplegable_tipo_de_vehiculo.setOnChange(() -> actualizarCoches());

        panelLateral.add(desplegable_tipo_de_vehiculo);
        panelLateral.add(Box.createVerticalStrut(20));

        // =========================
        // AÑO
        // =========================
        JLabel lblAnio = new JLabel("AÑO");
        lblAnio.setForeground(Color.WHITE);
        lblAnio.setFont(new Font("Arial", Font.BOLD, 12));
        lblAnio.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelLateral.add(lblAnio);
        panelLateral.add(Box.createVerticalStrut(10));

        // =========================
        // AÑO MÍNIMO
        // =========================
        JLabel lblcampoAnioMin = new JLabel("Año mín");
        lblcampoAnioMin.setForeground(Color.WHITE);
        lblcampoAnioMin.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoAnioMin = new ModernTextField();
        campoAnioMin.setMaximumSize(tamañoCampos);
        campoAnioMin.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorAnioMin.setForeground(Color.RED);
        errorAnioMin.setFont(new Font("Arial", Font.PLAIN, 11));
        errorAnioMin.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorAnioMin.setVisible(false);

        panelLateral.add(lblcampoAnioMin);
        panelLateral.add(Box.createVerticalStrut(5));
        panelLateral.add(campoAnioMin);
        panelLateral.add(errorAnioMin);
        panelLateral.add(Box.createVerticalStrut(10));

        // =========================
        // AÑO MÁXIMO
        // =========================
        JLabel lblcampoAnioMax = new JLabel("Año máx");
        lblcampoAnioMax.setForeground(Color.WHITE);
        lblcampoAnioMax.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoAnioMax = new ModernTextField();
        campoAnioMax.setMaximumSize(tamañoCampos);
        campoAnioMax.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorAnioMax.setForeground(Color.RED);
        errorAnioMax.setFont(new Font("Arial", Font.PLAIN, 11));
        errorAnioMax.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorAnioMax.setVisible(false);

        panelLateral.add(lblcampoAnioMax);
        panelLateral.add(Box.createVerticalStrut(5));
        panelLateral.add(campoAnioMax);
        panelLateral.add(errorAnioMax);

        // =========================
        // LISTENERS AÑOS
        // =========================
        campoAnioMin.addActionListener(e -> actualizarCoches());
        campoAnioMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { actualizarCoches(); }
        });

        campoAnioMax.addActionListener(e -> actualizarCoches());
        campoAnioMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { actualizarCoches(); }
        });

        panelLateral.add(Box.createVerticalStrut(20));

        // =========================
        // KM
        // =========================
        JLabel lblKm = new JLabel("KM");
        lblKm.setForeground(Color.WHITE);
        lblKm.setFont(new Font("Arial", Font.BOLD, 12));
        lblKm.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelLateral.add(lblKm);
        panelLateral.add(Box.createVerticalStrut(10));

        // =========================
        // KM MÍNIMO
        // =========================
        JLabel lblcampoKmMin = new JLabel("KM mín");
        lblcampoKmMin.setForeground(Color.WHITE);
        lblcampoKmMin.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoKmMin = new ModernTextField();
        campoKmMin.setMaximumSize(tamañoCampos);
        campoKmMin.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorKmMin.setForeground(Color.RED);
        errorKmMin.setFont(new Font("Arial", Font.PLAIN, 11));
        errorKmMin.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorKmMin.setVisible(false);

        panelLateral.add(lblcampoKmMin);
        panelLateral.add(Box.createVerticalStrut(5));
        panelLateral.add(campoKmMin);
        panelLateral.add(errorKmMin);
        panelLateral.add(Box.createVerticalStrut(10));

        // =========================
        // KM MÁXIMO
        // =========================
        JLabel lblcampoKmMax = new JLabel("KM máx");
        lblcampoKmMax.setForeground(Color.WHITE);
        lblcampoKmMax.setAlignmentX(Component.LEFT_ALIGNMENT);

        campoKmMax = new ModernTextField();
        campoKmMax.setMaximumSize(tamañoCampos);
        campoKmMax.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorKmMax.setForeground(Color.RED);
        errorKmMax.setFont(new Font("Arial", Font.PLAIN, 11));
        errorKmMax.setAlignmentX(Component.LEFT_ALIGNMENT);
        errorKmMax.setVisible(false);

        panelLateral.add(lblcampoKmMax);
        panelLateral.add(Box.createVerticalStrut(5));
        panelLateral.add(campoKmMax);
        panelLateral.add(errorKmMax);

        // =========================
        // LISTENERS KM
        // =========================
        campoKmMin.addActionListener(e -> actualizarCoches());
        campoKmMin.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { actualizarCoches(); }
        });

        campoKmMax.addActionListener(e -> actualizarCoches());
        campoKmMax.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent e) { actualizarCoches(); }
        });

        // =========================
        // PANEL CENTRAL
        // =========================
        panelCochesDisponibles.setLayout(new GridLayout(0, 3, 15, 15));
        panelCochesDisponibles.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelCochesDisponibles.setBackground(Color.GRAY);

        panelCochesPropios.setLayout(new GridLayout(0, 3, 15, 15));
        panelCochesPropios.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelCochesPropios.setBackground(Color.GRAY);

        // =========================
        // PESTAÑAS
        // =========================
        pestañas = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        pestañas.setBackground(new Color(20, 20, 20));

        pestañaDisponibles = crearPestaña("Coches disponibles", true);
        pestañaPropios = crearPestaña("Mis coches", false);

        activarPestaña(pestañaDisponibles, pestañaPropios);

        pestañas.add(pestañaDisponibles);
        pestañas.add(pestañaPropios);

        // =========================
        // SCROLL Y LAYOUT CENTRAL
        // =========================
        JScrollPane scrollLateral = new ModernScrollPane(panelLateral);
        scrollLateral.setPreferredSize(new Dimension(220, 0));

        scrollCoches = new ModernScrollPane(panelCoches_actual);

        JPanel centroConPestañas = new JPanel(new BorderLayout());
        centroConPestañas.add(pestañas, BorderLayout.NORTH);
        centroConPestañas.add(scrollCoches, BorderLayout.CENTER);

        // =========================
        // PANEL INFERIOR (TIMER)
        // =========================
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        panelInferior.setPreferredSize(new Dimension(0, 25));
        panelInferior.setBackground(new Color(30, 30, 30));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        panelInferior.add(Box.createHorizontalGlue());

        labelTiempo = new JLabel("00:00:00");
        labelTiempo.setForeground(Color.WHITE);
        labelTiempo.setFont(new Font("Arial", Font.PLAIN, 12));
        panelInferior.add(labelTiempo);

        // =========================
        // AÑADIR TODO
        // =========================
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollLateral, BorderLayout.WEST);
        add(centroConPestañas, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        reiniciarTimer();
        actualizarCoches();
    }

    public void reiniciarTimer() {
        if (timer != null) timer.stop();
        labelTiempo.setText("00:00:00");

        final long[] segundos = {0};

        timer = new javax.swing.Timer(1000, e -> {
            segundos[0]++;
            long horas   = segundos[0] / 3600;
            long minutos = (segundos[0] % 3600) / 60;
            long segs    = segundos[0] % 60;
            labelTiempo.setText(String.format("%02d:%02d:%02d", horas, minutos, segs));
        });

        timer.start();
    }

    private JLabel crearPestaña(String texto, boolean activa) {

        JLabel pestaña = new JLabel(texto);
        pestaña.setFont(new Font("Arial", Font.BOLD, 13));
        pestaña.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pestaña.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        pestaña.setOpaque(true);



        pestaña.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {

                boolean enDisponibles = panelCoches_actual == panelCochesDisponibles;
                boolean clickDisponibles = pestaña == pestañaDisponibles;

                if (!((enDisponibles && clickDisponibles) || (!enDisponibles && !clickDisponibles))) {
                    if (clickDisponibles) {
                        panelCoches_actual = panelCochesDisponibles;
                        activarPestaña(pestañaDisponibles, pestañaPropios);
                    } else {
                        panelCoches_actual = panelCochesPropios;
                        activarPestaña(pestañaPropios, pestañaDisponibles);
                    }

                    scrollCoches.setViewportView(panelCoches_actual);
                    actualizarFiltros();
                    actualizarCoches();
                }
            }
        });

        return pestaña;
    }

    private void activarPestaña(JLabel activa, JLabel inactiva) {
        activa.setForeground(Color.WHITE);
        activa.setBackground(Color.GRAY);

        inactiva.setForeground(new Color(130, 130, 130));
        inactiva.setBackground(new Color(20, 20, 20));
    }

    private void actualizarFiltros() {

        if (panelCoches_actual == panelCochesDisponibles) {
            deplegable_marca_de_vehiculo.actualizarOpciones(consultas.obtenerVehiculosSegunFiltro("marca"));
            deplegable_modelo_de_vehiculo.actualizarOpciones(consultas.obtenerModeloSegunMarca(deplegable_marca_de_vehiculo.getSeleccionados()));
            desplegable_tipo_de_vehiculo.actualizarOpciones(consultas.obtenerVehiculosSegunFiltro("tipo_vehiculo"));
        } else if (usuario != null) {
            deplegable_marca_de_vehiculo.actualizarOpciones(consultas.obtenerVehiculosSegunFiltro("marca", usuario.getId()));
            deplegable_modelo_de_vehiculo.actualizarOpciones(consultas.obtenerModeloSegunMarca(deplegable_marca_de_vehiculo.getSeleccionados(), usuario.getId()));
            desplegable_tipo_de_vehiculo.actualizarOpciones(consultas.obtenerVehiculosSegunFiltro("tipo_vehiculo", usuario.getId()));
        }
    }

    public void actualizarCoches() {

        // =========================
        // VALIDAR AÑO
        // =========================
        errorAnioMin.setText("Solo se permiten números");
        errorAnioMax.setText("Solo se permiten números");

        Integer anioMin = null;
        Integer anioMax = null;

        String textoAnioMin = campoAnioMin.getText().trim();
        if (!textoAnioMin.isEmpty()) {
            try {
                anioMin = Integer.parseInt(textoAnioMin);
                errorAnioMin.setVisible(false);
            } catch (NumberFormatException e) {
                errorAnioMin.setVisible(true);
                return;
            }
        } else {
            errorAnioMin.setVisible(false);
        }

        String textoAnioMax = campoAnioMax.getText().trim();
        if (!textoAnioMax.isEmpty()) {
            try {
                anioMax = Integer.parseInt(textoAnioMax);
                errorAnioMax.setVisible(false);
            } catch (NumberFormatException e) {
                errorAnioMax.setVisible(true);
                return;
            }
        } else {
            errorAnioMax.setVisible(false);
        }

        if (anioMin != null && anioMax != null && anioMin > anioMax) {
            errorAnioMin.setText("El año min tiene que ser < al max");
            errorAnioMin.setVisible(true);
            errorAnioMax.setText("El año min tiene que ser < al max");
            errorAnioMax.setVisible(true);
            return;
        }

        // =========================
        // VALIDAR KM
        // =========================
        errorKmMin.setText("Solo se permiten números");
        errorKmMax.setText("Solo se permiten números");

        Integer kmMin = null;
        Integer kmMax = null;

        String textoKmMin = campoKmMin.getText().trim();
        if (!textoKmMin.isEmpty()) {
            try {
                kmMin = Integer.parseInt(textoKmMin);
                errorKmMin.setVisible(false);
            } catch (NumberFormatException e) {
                errorKmMin.setVisible(true);
                return;
            }
        } else {
            errorKmMin.setVisible(false);
        }

        String textoKmMax = campoKmMax.getText().trim();
        if (!textoKmMax.isEmpty()) {
            try {
                kmMax = Integer.parseInt(textoKmMax);
                errorKmMax.setVisible(false);
            } catch (NumberFormatException e) {
                errorKmMax.setVisible(true);
                return;
            }
        } else {
            errorKmMax.setVisible(false);
        }

        if (kmMin != null && kmMax != null && kmMin > kmMax) {
            errorKmMin.setText("El KM min tiene que ser < al max");
            errorKmMin.setVisible(true);
            errorKmMax.setText("El KM min tiene que ser < al max");
            errorKmMax.setVisible(true);
            return;
        }

        // =========================
        // LIMPIAR PANEL CENTRAL
        // =========================
        panelCoches_actual.removeAll();
        panelCoches_actual.revalidate();
        panelCoches_actual.repaint();

        final Integer anioMinFinal = anioMin;
        final Integer anioMaxFinal = anioMax;
        final Integer kmMinFinal   = kmMin;
        final Integer kmMaxFinal   = kmMax;

        // =========================
        // CARGAR COCHES EN HILO
        // =========================
        new Thread(() -> {

            List<Coche> coches;

            if (panelCoches_actual == panelCochesDisponibles) {
                coches = consultas.obtenerVehiculos(
                        deplegable_marca_de_vehiculo.getSeleccionados(),
                        deplegable_modelo_de_vehiculo.getSeleccionados(),
                        desplegable_tipo_de_vehiculo.getSeleccionados(),
                        anioMinFinal,
                        anioMaxFinal,
                        kmMinFinal,
                        kmMaxFinal
                );
            } else {
                coches = consultas.obtenerVehiculos(
                        usuario.getId(),
                        deplegable_marca_de_vehiculo.getSeleccionados(),
                        deplegable_modelo_de_vehiculo.getSeleccionados(),
                        desplegable_tipo_de_vehiculo.getSeleccionados(),
                        anioMinFinal,
                        anioMaxFinal,
                        kmMinFinal,
                        kmMaxFinal
                );
            }

            SwingUtilities.invokeLater(() -> {
                for (Coche coche : coches) {
                    JPanel contenedor = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                    contenedor.setOpaque(false);
                    contenedor.add(crearTarjetaCoche(coche));
                    panelCoches_actual.add(contenedor);
                }
                panelCoches_actual.revalidate();
                panelCoches_actual.repaint();
            });

        }).start();
    }

    private JPanel crearTarjetaCoche(Coche coche) {

        return new TarjetaCoche(coche, this, panelCoches_actual, panelCochesDisponibles);

    }

    public void actualizarElementosPanelSuperior() {
        if (usuario != null && panelSuperior != null) {
            panelSuperior.cambiarBienvenida(usuario.getNombre());
            panelSuperior.actualizarSaldo(usuario.getDinero());
        }
    }

    public PanelSuperiorPantallaPrincipal getPanelSuperior() {
        return panelSuperior;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}