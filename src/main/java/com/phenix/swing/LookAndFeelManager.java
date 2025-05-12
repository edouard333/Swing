package com.phenix.swing;

import com.phenix.apios.OS;
import jakarta.validation.constraints.Null;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Gère le look des applications.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class LookAndFeelManager {

    /**
     * On ne peut pas instancier cette classe.
     *
     * @throws Exception On ne peut pas instancier cette classe.
     */
    private LookAndFeelManager() throws Exception {
        throw new Exception("Cette classe ne peut pas être instanciée.");
    }

    /**
     * Définit le style de l'application avec celui de l'OS.
     *
     * @param afficherErreurFenetre Si on affiche une erreur dans une fenêtre.
     */
    public static void setByOS(boolean afficherErreurFenetre) {
        setByOS(afficherErreurFenetre, null);
    }

    /**
     * Définit le style de l'application avec celui de l'OS.
     *
     * @param afficherErreurFenetre Si on affiche une erreur dans une fenêtre.
     * @param titreApplication Titre de l'application, affiché pour macOS.
     */
    public static void setByOS(boolean afficherErreurFenetre, @Null String titreApplication) {
        if (OS.isMacOSX() && titreApplication != null) {
            // Définit le nom de l'application sur macOS.
            System.setProperty("apple.awt.application.name", titreApplication);
        }

        try {
            // On parcourt chaque LAF disponible et dès qu'on a Windows ou macOS on l'utilise :
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                // Si on est sur Windows, on sélectionne cet aspect.
                if (LookAndFeel.isWindows(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                } // Si c'est un Mac, on sélectionne cet aspect.
                else if (LookAndFeel.isMacOSX(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException exception) {
            System.out.println(exception.getMessage());

            if (afficherErreurFenetre) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur de LAF", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
