package com.phenix.swing;

import java.io.File;

/**
 * Action qui se déroule quand on a sélectionné ou définit le fichier/dossier
 * dans {@link com.phenix.swing.JChooser JChooser}.
 *
 * @see com.phenix.swing.JChooser
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
@FunctionalInterface
public interface Fichier {

    /**
     * Ce qui se passe quand on récupère le fichier/dossier sélectionné ou
     * définit.
     *
     * @param fichier Le fichier/dossier qu'on récupère (jamais {@code null}).
     */
    public void set(File fichier);
}
