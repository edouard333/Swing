package com.phenix.swing.util;

/**
 * Outils utiles dans le projet.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class Utils {

    /**
     * Nom de l'OS Windows.
     */
    public static final String WINDOWS = "win";

    /**
     * Nom de l'OS Mac.
     */
    public static final String MACOSX = "Mac OS X";

    /**
     * On ne peut pas instancier la classe.
     */
    private Utils() {
    }

    /**
     * Retourne l'OS sur lequel on est.
     *
     * @return Nom de l'OS.
     */
    public static String getOS() {
        return System.getProperty("os.name");
    }
}
