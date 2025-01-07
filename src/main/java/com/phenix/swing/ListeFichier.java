package com.phenix.swing;

import java.io.File;

/**
 * Action qui se déroule quand on a sélectionné les fichiers/dossiers dans
 * {@link JChooser}.
 *
 * @see com.phenix.swing.JChooser
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
@FunctionalInterface
public interface ListeFichier {

    /**
     * Ce qui se passe quand on récupère les fichiers/dossiers sélectionnés.
     *
     * @param liste_fichier Les fichiers/dossiers qu'on récupère (jamais
     * {@code null}).
     */
    public void set(File[] liste_fichier);
}
