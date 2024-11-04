package com.phenix.swing;

import com.phenix.apios.OS;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class IconApplication {

    /**
     * Pour empêcher d'instancier la classe.
     *
     * @throws Exception
     */
    private IconApplication() throws Exception {
        throw new Exception("Cette classe ne peut pas être instanciée.");
    }

    /**
     * Définit l'icone de la bar des tâches pour macOS.
     *
     * @param classe La classe qui lance le programme (Main.java).
     * @param chemin_icone
     */
    public static void setIconTaskBar(Class classe, String chemin_icone) {
        if (OS.isMacOSX()) {
            final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Image image = defaultToolkit.getImage(classe.getClassLoader().getResource(chemin_icone));
            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
    }
}
