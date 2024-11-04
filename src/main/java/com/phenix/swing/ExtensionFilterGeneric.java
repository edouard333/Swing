package com.phenix.swing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filtre des extensions de fichiers pour
 * {@link com.phenix.swing.JChooser JChooser}.
 *
 * @see com.phenix.swing.JChooser
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class ExtensionFilterGeneric {

    /**
     * Description affiché dans la fenêtre pour les extension de fichiers.
     */
    private final String description;

    /**
     * La liste des extensions lié au filtre.
     */
    private final List<String> extensions;

    /**
     * Construit un filtre sur des extensions.
     *
     * @param description Description affiché pour les extensions.
     * @param extensions Liste des extensions (exemple : "<em>.txt</em>").
     */
    public ExtensionFilterGeneric(String description, List<String> extensions) {
        this.description = description;
        this.extensions = extensions;
    }

    /**
     * Construit un filtre sur des extensions.
     *
     * @param description Description affiché pour les extensions.
     * @param extension L'extension à filtrer (exemple : "<em>.txt</em>").
     */
    public ExtensionFilterGeneric(String description, String extension) {
        this.description = description;
        this.extensions = new ArrayList<String>();
        this.extensions.add(extension);
    }

    /**
     * Construit un filtre sur des extensions.
     *
     * @param description Description affiché pour les extensions.
     * @param extensions Liste des extensions (exemple : "<em>.txt</em>").
     */
    public ExtensionFilterGeneric(String description, String[] extensions) {
        this.description = description;
        this.extensions = Arrays.asList(extensions);
    }

    /**
     * Reoturne la description affichée pour la fenêtre des extensions.
     *
     * @return Description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retourne les extensions liés au filtre.
     *
     * @return Liste des extensions.
     */
    public List<String> getExtensions() {
        return this.extensions;
    }
}
