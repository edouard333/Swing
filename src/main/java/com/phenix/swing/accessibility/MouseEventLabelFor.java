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
 * Evènement souris pour un {@link JLabel}.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class MouseEventLabelFor implements MouseListener {

    /**
     * Evènement quand on clique.
     *
     * @param event L'évènement.
     */
    @Override
    public void mouseClicked(MouseEvent event) {
        Component composant = ((JLabel) event.getSource()).getLabelFor();
        if (composant != null) {
            if (composant instanceof JCheckBox checkbox) {
                checkbox.setSelected(!checkbox.isSelected());
            }

            composant.requestFocus();
        }
    }

    @Override
    public void mouseEntered(MouseEvent event) {
    }

    @Override
    public void mouseExited(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
    }

    @Override
    public void mouseReleased(MouseEvent event) {
    }
}
