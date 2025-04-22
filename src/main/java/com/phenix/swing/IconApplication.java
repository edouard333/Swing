package com.phenix.swing;

import com.phenix.apios.OS;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class IconApplication {

    /**
     * On ne peut pas instancier cette classe.
     *
     * @throws Exception On ne peut pas instancier cette classe.
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
    public static void setIconTaskBar(@NotNull Class classe, @NotNull @NotBlank String chemin_icone) {
        if (OS.isMacOSX()) {
            final Toolkit defaultToolkit = Toolkit.getDefaultToolkit();
            Image image = defaultToolkit.getImage(classe.getClassLoader().getResource(chemin_icone));
            final Taskbar taskbar = Taskbar.getTaskbar();
            taskbar.setIconImage(image);
        }
    }
}
