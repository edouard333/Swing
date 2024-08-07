/*
 * Licence Studio l'Equipe.
 */
package com.phenix.swing.accessibility;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JCheckBox;
import javax.swing.JLabel;

/**
 * Evènement souris pour un {@link javax.swing.JLabel JLabel}.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class MouseEventLabelFor implements MouseListener {

    /**
     * Evènement quand on clique.
     *
     * @param evt L'évènement.
     */
    @Override
    public void mouseClicked(MouseEvent evt) {
        Component composant = ((JLabel) evt.getSource()).getLabelFor();
        if (composant != null) {
            if (composant.getClass().getName().equals("javax.swing.JCheckBox")) {
                JCheckBox checkbox = ((JCheckBox) composant);
                checkbox.setSelected(!checkbox.isSelected());
            }
            composant.requestFocus();
        }
    }

    @Override
    public void mouseEntered(MouseEvent evt) {
    }

    @Override
    public void mouseExited(MouseEvent evt) {
    }

    @Override
    public void mousePressed(MouseEvent evt) {
    }

    @Override
    public void mouseReleased(MouseEvent evt) {
    }
}
