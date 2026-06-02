package Componentes;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class MultiSelectDropdown extends ModernButton {

    private final Set<String> seleccionados = new TreeSet<>();
    private final List<JCheckBox> checkBoxes = new ArrayList<>();

    private final JPopupMenu popup = new JPopupMenu();
    private final JPanel panelChecks = new JPanel();
    private final ModernScrollPane scrollPane = new ModernScrollPane(panelChecks);

    private Runnable onChange;

    public MultiSelectDropdown(String titulo, String[] opciones) {

        super(titulo + " ▾");

        setMaximumSize(new Dimension(180, 30));
        setBackground(new Color(70, 70, 70));
        setForeground(Color.WHITE);
        setFocusPainted(false);

        panelChecks.setLayout(new BoxLayout(panelChecks, BoxLayout.Y_AXIS));
        panelChecks.setBackground(new Color(60, 60, 60));

        JCheckBox cbTodos = new ModernCheckBox("Todos");
        cbTodos.setMaximumSize(new Dimension(180, 30));
        cbTodos.setBackground(new Color(60, 60, 60));
        cbTodos.setForeground(Color.WHITE);
        cbTodos.setHorizontalAlignment(SwingConstants.LEFT);
        cbTodos.setSelected(true);

        checkBoxes.add(cbTodos); // ← primero añadir a la lista

        for (String opcion : opciones) {
            JCheckBox cb = new ModernCheckBox(opcion);
            cb.setMaximumSize(new Dimension(180, 30));
            cb.setBackground(new Color(60, 60, 60));
            cb.setForeground(Color.WHITE);
            cb.setHorizontalAlignment(SwingConstants.LEFT);
            cb.addActionListener(e -> actualizarSeleccion());
            checkBoxes.add(cb);
            panelChecks.add(cb);
        }

        // listener DESPUÉS de tener todos los checkboxes en la lista
        cbTodos.addActionListener(e -> {
            if (cbTodos.isSelected()) {
                for (JCheckBox cb : checkBoxes) cb.setSelected(false);
                cbTodos.setSelected(true);
                seleccionados.clear();
            }
            actualizarSeleccion();
        });

        panelChecks.add(cbTodos, 0); // ← añadir al panel en primera posición

        popup.setBorder(BorderFactory.createEmptyBorder());
        popup.add(scrollPane);

        addActionListener(e -> mostrarPopup());
    }

    public void setOnChange(Runnable onChange) {
        this.onChange = onChange;
    }

    public void actualizarOpciones(String[] nuevasOpciones) {

        checkBoxes.clear();
        seleccionados.clear();
        panelChecks.removeAll();

        JCheckBox cbTodos = new ModernCheckBox("Todos");
        cbTodos.setMaximumSize(new Dimension(180, 30));
        cbTodos.setBackground(new Color(60, 60, 60));
        cbTodos.setForeground(Color.WHITE);
        cbTodos.setHorizontalAlignment(SwingConstants.LEFT);
        cbTodos.setSelected(true);

        checkBoxes.add(cbTodos); // ← primero añadir a la lista

        for (String opcion : nuevasOpciones) {
            JCheckBox cb = new ModernCheckBox(opcion);
            cb.setMaximumSize(new Dimension(180, 30));
            cb.setBackground(new Color(60, 60, 60));
            cb.setForeground(Color.WHITE);
            cb.setHorizontalAlignment(SwingConstants.LEFT);
            cb.addActionListener(e -> actualizarSeleccion());
            checkBoxes.add(cb);
            panelChecks.add(cb);
        }

        // listener DESPUÉS de tener todos los checkboxes en la lista
        cbTodos.addActionListener(e -> {
            if (cbTodos.isSelected()) {
                for (JCheckBox cb : checkBoxes) cb.setSelected(false);
                cbTodos.setSelected(true);
                seleccionados.clear();
            }
            actualizarSeleccion();
        });

        panelChecks.add(cbTodos, 0); // ← añadir al panel en primera posición

        panelChecks.revalidate();
        panelChecks.repaint();
    }

    private void mostrarPopup() {

        int ancho = getWidth();
        int alto = Math.min((3 * 30) + 10, 200);

        panelChecks.setPreferredSize(new Dimension(ancho, checkBoxes.size() * 30));
        popup.setPopupSize(ancho, alto);

        popup.show(this, 0, getHeight());
    }

    private void actualizarSeleccion() {

        JCheckBox cbTodos = checkBoxes.get(0);

        boolean algnoMarcado = checkBoxes.stream().skip(1).anyMatch(JCheckBox::isSelected);

        if (algnoMarcado) {
            cbTodos.setSelected(false);
        }

        if (!algnoMarcado) {
            cbTodos.setSelected(true);
        }

        seleccionados.clear();

        if (!cbTodos.isSelected()) {
            for (JCheckBox cb : checkBoxes) {
                if (cb.isSelected()) {
                    seleccionados.add(cb.getText());
                }
            }
        }

        if (onChange != null) onChange.run();
    }

    public Set<String> getSeleccionados() {
        return seleccionados;
    }
}