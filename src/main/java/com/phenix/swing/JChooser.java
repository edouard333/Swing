package com.phenix.swing;

import com.phenix.apios.OS;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.awt.FileDialog;
import java.awt.Window;
import java.io.File;
import java.util.List;
import javafx.application.Platform;
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
public final class JChooser {

    static {
        // On doit lancer le thread de JavaFX. Puis on fait {@code Platform.runLater(...); }
        Platform.startup(() -> {
        });
    }

    /**
     * On ne peut pas instancier cette classe.
     *
     * @throws Exception On ne peut pas instancier cette classe.
     */
    private JChooser() throws Exception {
        throw new Exception("Cette classe ne peut pas être instanciée.");
    }

    /**
     * Affiche une fenêtre permettant de choisir des dossiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param listeDossier La liste des dossiers choisis.
     */
    public static void directories(Window parent, @NotNull FileListSelected listeDossier) {
        directories(parent, listeDossier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir des dossiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param listeDossier La liste des dossiers choisis.
     * @param dossierInitial Le dossier initial.
     */
    public static void directories(Window parent, @NotNull FileListSelected listeDossier, @Null File dossierInitial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (OS.isMacOSX()) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "true");

            FileDialog d = new FileDialog(frame);

            if (dossierInitial != null) {
                d.setDirectory(dossierInitial.getAbsolutePath());
            }
            if (dossierInitial != null) {
                d.setFile(dossierInitial.getName());
            }

            d.setMultipleMode(true);

            d.setVisible(true);

            File[] liste = d.getFiles();

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (liste != null && liste.length != 0) {
                listeDossier.choose(liste);
            }
        } // Pour Windows :
        else {
            try {
                Platform.runLater(() -> {
                    // Désactive la fenêtre le temps de l'opération.
                    parent.setEnabled(false);

                    DirectoryChooser d = new DirectoryChooser();
                    if (dossierInitial != null) {
                        d.setInitialDirectory(dossierInitial);
                    }
                    File selectedFile = d.showDialog(null);

                    if (selectedFile != null) {
                        listeDossier.choose(new File[]{selectedFile});
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
     * @param parent Fenêtre parent.
     * @param dossier Le dossier choisi.
     */
    public static void directory(Window parent, @NotNull FileSelected dossier) {
        directory(parent, dossier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir un dossier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param dossier Le dossier choisi.
     * @param dossierInitial Le dossier initial.
     */
    public static void directory(Window parent, @NotNull FileSelected dossier, @Null File dossierInitial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (OS.isMacOSX()) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "true");

            FileDialog d = new FileDialog(frame);
            if (dossierInitial != null) {
                d.setDirectory(dossierInitial.getAbsolutePath());
            }
            d.setMultipleMode(false);

            d.setVisible(true);

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (d.getDirectory() != null && d.getFile() != null) {
                dossier.choose(new File(d.getDirectory() + File.separator + d.getFile()));
                d.dispose();
            }
        } // Pour Windows :
        else {
            try {
                Platform.runLater(() -> {
                    if (parent != null) {
                        parent.setEnabled(false);
                    }

                    DirectoryChooser d = new DirectoryChooser();
                    if (dossierInitial != null) {
                        d.setInitialDirectory(dossierInitial);
                    }
                    File selectedFile = d.showDialog(null);

                    if (selectedFile != null) {
                        dossier.choose(selectedFile);
                    }

                    if (parent != null) {
                        parent.setEnabled(true);
                        parent.requestFocus();
                    }
                });
            } catch (Exception exception) {
                System.out.println("Erreur : " + exception.getMessage());
            }
        }
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode) {
        file(parent, fichier, mode, null, null, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param filtre Filtre sur base d'une ou plusieurs extensions de fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode, @Null ExtensionFilterGeneric filtre) {
        file(parent, fichier, mode, null, null, filtre);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param fichierInitial Fichier et/ou dossier initial.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode, @NotNull File fichierInitial) {
        file(parent, fichier, mode, fichierInitial.isAbsolute() ? (fichierInitial.getName().contains(".") ? fichierInitial.getParentFile() : fichierInitial) : null, fichierInitial.getName().contains(".") ? fichierInitial.getName() : null, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param fichierInitial Fichier et/ou dossier initial.
     * @param filtre Filtrer des fichiers par exentions de fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode, @NotNull File fichierInitial, @Null ExtensionFilterGeneric filtre) {
        file(parent, fichier, mode, fichierInitial.isAbsolute() ? (fichierInitial.getName().contains(".") ? fichierInitial.getParentFile() : fichierInitial) : null, fichierInitial.getName().contains(".") ? fichierInitial.getName() : null, filtre);
    }

    /**
     * Affiche une fenêtre permettant de choisir un fichier (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param dossierInitial Le dossier initial.
     * @param nomFichierInitial Le nom initial du fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode, @Null File dossierInitial, @Null String nomFichierInitial) {
        file(parent, fichier, mode, dossierInitial, nomFichierInitial, null);
    }

    /**
     * @param parent Fenêtre parent.
     * @param fichier Le fichier choisi.
     * @param mode Le mode pour la fenêtre entre
     * {@link java.awt.FileDialog#LOAD FileDialog.LOAD} ou
     * {@link java.awt.FileDialog#SAVE FileDialog.SAVE}.
     * @param dossierInitial Le dossier initial.
     * @param nomFichierInitial Le nom initial du fichier.
     * @param filtre Filtrer des fichiers par exentions de fichier.
     * @see java.awt.FileDialog#getMode
     */
    public static void file(Window parent, @NotNull FileSelected fichier, int mode, @Null File dossierInitial, @Null String nomFichierInitial, @Null ExtensionFilterGeneric filtre) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (OS.isMacOSX()) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "false");

            FileDialog d = new FileDialog(frame);
            if (dossierInitial != null) {
                d.setDirectory(dossierInitial.getAbsolutePath());
            }
            if (nomFichierInitial != null) {
                d.setFile(nomFichierInitial);
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
                fichier.choose(new File(d.getDirectory() + File.separator + d.getFile()));
            }
        } else {
            Platform.runLater(() -> {
                if (parent != null) {
                    parent.setEnabled(false);
                }

                FileChooser d = new FileChooser();

                if (dossierInitial != null) {
                    d.setInitialDirectory(dossierInitial);
                }

                if (nomFichierInitial != null) {
                    d.setInitialFileName(nomFichierInitial);
                }

                if (filtre != null) {
                    ExtensionFilter filtreTmp;

                    List<String> listeExtension = filtre.getExtensions();

                    for (int i = 0; i < listeExtension.size(); i++) {
                        listeExtension.set(i, "*" + listeExtension.get(i));
                    }

                    filtreTmp = new ExtensionFilter(filtre.getDescription(), listeExtension);
                    d.getExtensionFilters().add(filtreTmp);
                    d.setSelectedExtensionFilter(filtreTmp);
                } else {
                    d.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Files", "*.*"));
                }

                File selectedFile;

                if (mode == FileDialog.LOAD) {
                    selectedFile = d.showOpenDialog(null);
                } else {
                    selectedFile = d.showSaveDialog(null);
                }

                if (selectedFile != null) {
                    fichier.choose(selectedFile);
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
     * @param parent Fenêtre parent.
     * @param listeFichier Les fichiers choisis.
     */
    public static void files(Window parent, @NotNull FileListSelected listeFichier) {
        files(parent, listeFichier, null);
    }

    /**
     * Affiche une fenêtre permettant de choisir des fichiers (Finder pour macOS
     * et Explorer pour Windows).
     *
     * @param parent Fenêtre parent.
     * @param listeFichier Les fichiers choisis.
     * @param dossierInitial Le dossier initial.
     */
    public static void files(Window parent, @NotNull FileListSelected listeFichier, @Null File dossierInitial) {
        // Crée une fenêtre qui permet de sauver son fichier avec l'interface Finder.
        if (OS.isMacOSX()) {
            JFrame frame = new JFrame();

            // Dans le cas d'un mac, on affiche la mise en page Mac.
            System.setProperty("apple.awt.fileDialogForDirectories", "false");

            FileDialog d = new FileDialog(frame);
            if (dossierInitial != null) {
                d.setDirectory(dossierInitial.getAbsolutePath());
            }

            d.setMultipleMode(true);

            d.setVisible(true);

            File[] liste = d.getFiles();

            // Quand on a indiqué l'endroit où sauver le fichier ou qu'on a fermé la fenêtre, on est ici dans le code.
            // On s'assure qu'un fichier a été choisi (et dossier).
            if (liste != null && liste.length != 0) {
                listeFichier.choose(liste);
            }
        } else {
            Platform.runLater(() -> {
                parent.setEnabled(false);

                FileChooser d = new FileChooser();
                if (dossierInitial != null) {
                    d.setInitialDirectory(dossierInitial);
                }

                List<File> selectedFile = d.showOpenMultipleDialog(null);

                if (selectedFile != null) {
                    listeFichier.choose((File[]) selectedFile.toArray());
                }

                parent.setEnabled(true);
                parent.requestFocus();
            });
        }
    }
}
