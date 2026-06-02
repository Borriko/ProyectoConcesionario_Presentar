package Componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class JMenuItemPanelSuperiorPerfil extends JMenuItem {

    public JMenuItemPanelSuperiorPerfil(String texto, ActionListener actionListener){

        setText(texto);
        setBackground(new Color(40, 40, 40));
        setForeground(new Color(255, 255, 255));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        addActionListener(actionListener);



    }

}
