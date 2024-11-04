package com.phenix.swing;

/**
 * Les valeurs que peuvent prendre un LookAndFeel.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public enum LookAndFeel {

    /**
     * LAF pour Windows.
     */
    WINDOWS("Windows"),
    /**
     * LAF pour macOS.
     */
    MACOSX("Mac OS X");

    /**
     * Valeur de Look And Feel.
     */
    private final String valeur;

    /**
     * Définit la valeur du Look And Feel.
     *
     * @param valeur La valeur.
     */
    private LookAndFeel(String valeur) {
        this.valeur = valeur;
    }

    /**
     * Retourne {@code true} si l'enum est égal au nom.
     *
     * @param nom javax.swing.UIManager.LookAndFeelInfo.getName()
     * @return {@code true} si l'enum est égal au nom.
     */
    public boolean equals(String nom) {
        return this.valeur.equals(nom);
    }

    /**
     * Retourne {@code true} si le nom du LookAndFeel est celui de Windows.
     *
     * @param nom javax.swing.UIManager.LookAndFeelInfo.getName()
     * @return {@code true} si c'est Windows.
     */
    public static boolean isWindows(String nom) {
        return WINDOWS.valeur.equals(nom);
    }

    /**
     * Retourne {@code true} si le nom du LookAndFeel est celui de macOS X.
     *
     * @param nom javax.swing.UIManager.LookAndFeelInfo.getName()
     * @return {@code true} si c'est macOS X.
     */
    public static boolean isMacOSX(String nom) {
        return MACOSX.valeur.equals(nom);
    }

    /**
     * Retourne la valeur du Look And Feel.
     *
     * @return La valeur.
     */
    @Override
    public String toString() {
        return this.valeur;
    }
}
