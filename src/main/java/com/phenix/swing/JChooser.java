package com.phenix.swing;

import com.phenix.swing.util.Utils;
import com.sun.javafx.application.PlatformImpl;
import java.awt.FileDialog;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javax.swing.JFrame;

/**
 * Afficher une fenêtre (pour Windows ou macOS) qui permet de sélectionner ou
 * définir un/des fichier(s) ou dossier(s).
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public class JChooser {

    /**
     * Affiche une fenêtre permettant de choisir des dossiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param liste_dossier La liste des dossiers à définir.
     */
    public static void directories(JFrame parent, ListeFichier liste_dossier) {
        directories(parent, liste_dossier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir des dossiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param liste_dossier La liste des dossiers à définir.
     * @param dossier_initial Le dossier initial.
     */
    public static void directories(JFrame parent, ListeFichier liste_dossier, File dossier_initial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (Utils.getOS().equals(Utils.MACOSX)) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "true");

            FileDialog d = new FileDialog(frame);
            if (dossier_initial != null) {
                d.setDirectory(dossier_initial.getAbsolutePath());
            }
            if (dossier_initial != null) {
                d.setFile(dossier_initial.getName());
            }
            d.setMultipleMode(true);

            d.setVisible(true);

            File[] liste = d.getFiles();

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (liste != null && liste.length != 0) {
                liste_dossier.set(liste);
            }
        } // Pour Windows :
        else {
            try {
                PlatformImpl.startup(() -> {
                    parent.setEnabled(false);

                    DirectoryChooser d = new DirectoryChooser();
                    if (dossier_initial != null) {
                        d.setInitialDirectory(dossier_initial);
                    }
                    File selectedFile = d.showDialog(null);

                    if (selectedFile != null) {
                        liste_dossier.set(new File[]{selectedFile});
                    }

                    parent.setEnabled(true);
                    parent.requestFocus();
                });
            } catch (Exception exception) {
                System.out.println("Erreur : " + exception.getMessage());
            }
        }
    }

    /**
     * Affiche une fenêtre permettant de choisir un dossier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param dossier Le dossier à définir.
     */
    public static void directory(JFrame parent, Fichier dossier) {
        directory(parent, dossier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir un dossier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param dossier Le dossier à définir.
     * @param dossier_initial Le dossier initial.
     */
    public static void directory(JFrame parent, Fichier dossier, File dossier_initial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (Utils.getOS().equals(Utils.MACOSX)) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "true");

            FileDialog d = new FileDialog(frame);
            if (dossier_initial != null) {
                d.setDirectory(dossier_initial.getAbsolutePath());
            }
            d.setMultipleMode(false);

            d.setVisible(true);

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (d.getDirectory() != null && d.getFile() != null) {
                dossier.set(new File(d.getDirectory() + File.separator + d.getFile()));
                d.dispose();
            }
        } // Pour Windows :
        else {
            parent.setEnabled(false);

            try {
                PlatformImpl.startup(() -> {
                    DirectoryChooser d = new DirectoryChooser();
                    if (dossier_initial != null) {
                        d.setInitialDirectory(dossier_initial);
                    }
                    File selectedFile = d.showDialog(null);

                    if (selectedFile != null) {
                        dossier.set(selectedFile);
                    }
                });
            } catch (Exception exception) {
                System.out.println("Erreur : " + exception.getMessage());
            }

            parent.setEnabled(true);
            parent.requestFocus();
        }
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param fichier Le fichier à définir.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, Fichier fichier, int mode) {
        file(parent, fichier, mode, null, null, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param fichier Le fichier à définir.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param filtre Filtre sur base d'une ou plusieurs extensions de fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, Fichier fichier, int mode, ExtensionFilterGeneric filtre) {
        file(parent, fichier, mode, null, null, filtre);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param fichier Le fichier à définir.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param fichier_initial Fichier et/ou dossier initial.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(JFrame parent, Fichier fichier, int mode, File fichier_initial) {
        file(parent, fichier, mode, fichier_initial.isAbsolute() ? (fichier_initial.isFile() ? fichier_initial.getParentFile() : fichier_initial) : null, fichier_initial.isFile() ? fichier_initial.getName() : null, null);
    }

    public static void file(JFrame parent, Fichier fichier, int mode, File fichier_initial, ExtensionFilterGeneric filtre) throws IOException {
        file(parent, fichier, mode, fichier_initial.isAbsolute() ? (fichier_initial.isFile() ? fichier_initial.getParentFile() : fichier_initial) : null, fichier_initial.isFile() ? fichier_initial.getName() : null, filtre);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param fichier Le fichier à définir.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param dossier_initial Le dossier initial.
     * @param nom_fichier_initial Le nom initial du fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(JFrame parent, Fichier fichier, int mode, File dossier_initial, String nom_fichier_initial) {
        file(parent, fichier, mode, dossier_initial, nom_fichier_initial, null);
    }

    /**
     * @param fichier Le fichier à définir.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param dossier_initial Le dossier initial.
     * @param nom_fichier_initial Le nom initial du fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, Fichier fichier, int mode, File dossier_initial, String nom_fichier_initial, ExtensionFilterGeneric filtre) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (Utils.getOS().equals(Utils.MACOSX)) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "false");

            FileDialog d = new FileDialog(frame);
            if (dossier_initial != null) {
                d.setDirectory(dossier_initial.getAbsolutePath());
            }
            if (nom_fichier_initial != null) {
                d.setFile(nom_fichier_initial);
            }

            if (filtre != null) {
                d.setFilenameFilter((dir, name) -> {
                    return filtre.getExtensions().contains(name);
                });
            }

            d.setMultipleMode(false);
            d.setMode(mode);

            d.setVisible(true);

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (d.getDirectory() != null && d.getFile() != null) {
                fichier.set(new File(d.getDirectory() + File.separator + d.getFile()));
            }
        } else {
            PlatformImpl.startup(() -> {

                if (parent != null) {
                    parent.setEnabled(false);
                }

                FileChooser d = new FileChooser();
                if (dossier_initial != null) {
                    System.out.println("Fichier : '" + dossier_initial + "'");
                    d.setInitialDirectory(dossier_initial);
                }
                if (nom_fichier_initial != null) {
                    System.out.println("Fichier : '" + nom_fichier_initial + "'");
                    d.setInitialFileName(nom_fichier_initial);
                }

                if (filtre != null) {
                    ExtensionFilter filtre_tmp;

                    List<String> liste_extension = filtre.getExtensions();

                    for (int i = 0; i < liste_extension.size(); i++) {
                        liste_extension.set(i, "*" + liste_extension.get(i));
                    }

                    filtre_tmp = new ExtensionFilter(filtre.getDescription(), liste_extension);
                    d.getExtensionFilters().add(filtre_tmp);
                    d.setSelectedExtensionFilter(filtre_tmp);
                }

                File selectedFile;

                if (mode == FileDialog.LOAD) {
                    selectedFile = d.showOpenDialog(null);
                } else {
                    selectedFile = d.showSaveDialog(null);
                }

                if (selectedFile != null) {
                    fichier.set(selectedFile);
                }

                if (parent != null) {
                    parent.setEnabled(true);
                    parent.requestFocus();
                }
            });
        }
    }

    /**
     * Affiche une fenêtre permettant de choisir des fichiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param liste_fichier Les fichiers à définir.
     */
    public static void files(JFrame parent, ListeFichier liste_fichier) {
        files(parent, liste_fichier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir des fichiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param liste_fichier Les fichiers à définir.
     * @param dossier_initial Le dossier initial.
     */
    public static void files(JFrame parent, ListeFichier liste_fichier, File dossier_initial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (Utils.getOS().equals(Utils.MACOSX)) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "false");

            FileDialog d = new FileDialog(frame);
            if (dossier_initial != null) {
                d.setDirectory(dossier_initial.getAbsolutePath());
            }

            d.setMultipleMode(true);

            d.setVisible(true);

            File[] liste = d.getFiles();

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (liste != null && liste.length != 0) {
                liste_fichier.set(liste);
            }
        } else {
            PlatformImpl.startup(() -> {
                parent.setEnabled(false);

                FileChooser d = new FileChooser();
                if (dossier_initial != null) {
                    d.setInitialDirectory(dossier_initial);
                }

                List<File> selectedFile = d.showOpenMultipleDialog(null);

                if (selectedFile != null) {
                    liste_fichier.set((File[]) selectedFile.toArray());
                }

                parent.setEnabled(true);
                parent.requestFocus();
            });
        }
    }
}
