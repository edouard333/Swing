package com.phenix.swing;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Gère le look des applications.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class LookAndFeelManager {

    /**
     * LAF pour Windows.
     */
    private static final String LAF_WINDOWS = "Windows";

    /**
     * LAF pour MacOS.
     */
    private static final String LAF_MAC_OS_X = "Mac OS X";

    /**
     * Définit le style de l'application avec celui de l'OS.
     *
     * @param afficher_erreur_fenetre Si on affiche une erreur dans une fenêtre.
     */
    public static void setByOS(boolean afficher_erreur_fenetre) {
        try {
            // On parcourt chaque LAF disponible et dès qu'on a Windows ou MacOS on l'utilise :
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Si on est sur Windows, on sélectionne cet aspect.
                if (LAF_WINDOWS.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }

                // Si c'est un Mac, on sélectionne cet aspect.
                if (LAF_MAC_OS_X.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exception) {
            System.out.println(exception.getMessage());

            if (afficher_erreur_fenetre) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur de LAF", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
