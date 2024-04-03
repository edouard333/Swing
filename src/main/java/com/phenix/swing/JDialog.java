package com.phenix.swing;

import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 * Fenêtre permettant de choisir un fichier et renvoie le nom du fichier (sans
 * l'extension et/ou le chemin) sélectionné.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 * @version 3.9.0
 */
public class JDialog extends JPanel {

    /**
     * Si on sélectionne un fichier ou un dossier ou encore les 2.
     */
    public static int selectionner_dossier = JFileChooser.FILES_ONLY;

    /**
     * Cette classe se base sur un {@code JFileChooser}.
     */
    private JFileChooser fc = new JFileChooser();

    /**
     * Le fichier.
     */
    private File fichier;

    /**
     * Les valeurs de 'JDialog'
     */
    private String valeur;

    /**
     * Nom du répertoire.
     */
    private String repertoire;

    /**
     * Montrer l'extension du fichier.
     */
    private boolean extension = true;

    /**
     * Montrer le chemin du fichier.
     */
    private boolean chemin = false;

    /**
     * Renverra une valeur plus tard.
     */
    public JDialog() {
    }

    /**
     * Renverra une valeur plus tard.
     *
     * @param repertoire Répertoire.
     */
    public JDialog(String repertoire) {
        this.repertoire = repertoire;
    }

    /**
     * Renverra une valeur plus tard.
     *
     * @param extension Extension.
     */
    public JDialog(boolean extension) {
        this.extension = extension;
    }

    /**
     * Renverra une valeur plus tard.
     *
     * @param repertoire Répertoire.
     * @param extension Extension.
     */
    public JDialog(String repertoire, boolean extension) {
        this.repertoire = repertoire;
        this.extension = extension;
    }

    /**
     * Renverra une valeur plus tard.
     *
     * @param repertoire Répertoire.
     * @param extension Extension.
     * @param chemin Chemin d'accès.
     */
    public JDialog(String repertoire, boolean extension, boolean chemin) {
        this.repertoire = repertoire;
        this.extension = extension;
        this.chemin = chemin;
    }

    /**
     * Définit le filtre à utiliser.
     *
     * @param filtre Filtre dans la sélection d'élément.
     */
    public void addChoosableFileFilter(FileFilter filtre) {
        fc.addChoosableFileFilter(filtre);
    }

    /**
     * Indique si l'extension du fichier doit être gardé ou non.
     *
     * @return {@code true} si on conserve l'extension de fichier.
     */
    public boolean Extension() {
        return extension;
    }

    /**
     * Renvoie la valeur.
     *
     * @return La valeur.
     */
    public String JDialog() {
        voidJDialog();

        return getValeur();
    }

    /**
     * Définit le répertoire actuel.
     *
     * @param repertoire Le répertoire.
     */
    public void setCurrentDirectory(File repertoire) {
        fc.setCurrentDirectory(repertoire);
    }

    /**
     *
     */
    private void voidJDialog() {

        // On met un try parce que parfois il y a des erreurs bizarres.
        try {
            if (fc.showDialog(JDialog.this, "OK") == JFileChooser.APPROVE_OPTION) {
                fichier = fc.getSelectedFile();

                valeur = getValeur();
            }

        } catch (HeadlessException exception) {
        }

    }

    /**
     * Renvoie de la valeur.
     *
     * @return La valeur.
     */
    public String getValeur() {
        if (chemin) {
            if (extension) {
                valeur = "" + fichier;
            } else {
                valeur = this.getExtension("" + fichier);
            }
        } else if (extension) {
            valeur = fichier.getName();
        } else {
            valeur = this.getExtension(fichier.getName());
        }

        return valeur;
    }

    // -- Il y a la même chose dans le fichier repertoire : --
    /**
     * Enlève l'extension.
     *
     * @param nom Nom du fichier.
     * @return Nom du fichier sans l'extension.
     */
    public String getExtension(String nom) {
        while (!"".equals(nom)) {

            if (".".equals(nom.substring(nom.length() - 1))) {
                return nom.substring(0, nom.length() - 1);
            }

            // On remet le mot 'nom' dans un String mais sans son dernier caractère.
            nom = nom.substring(0, nom.length() - 1);

        }

        return nom;
    }

    /**
     * Retourne le répertoire.
     *
     * @return Le répertoire.
     */
    public String getRepertoire() {
        return repertoire;
    }

    /**
     * Définit le répertoire.
     *
     * @param repertoire Le répertoire.
     */
    public void setRepertoire(String repertoire) {
        this.repertoire = repertoire;
    }

    /**
     * Définit le chemin.
     *
     * @param chemin Le chemin.
     */
    public void setChemin(boolean chemin) {
        this.chemin = chemin;
    }

    /**
     * Définit l'extension de fichier.
     *
     * @param extension L'extension.
     */
    public void setExtension(boolean extension) {
        this.extension = extension;
    }

    /**
     * Modifie le mode de sélection des fichiers.
     *
     * @param option Mode de sélection.
     */
    public void setFileSelectionMode(int option) {
        fc.setFileSelectionMode(option);
    }

    /**
     * Réinitialise la valeur.
     *
     * @param t Sert que pour faire la différence avec l'autre méthode.
     */
    public void valeur(boolean t) {
        this.valeur = null;
        this.fichier = null;
        fc = new JFileChooser();
    }

}

/**
 * Filtre des extensions.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
class Filtre extends FileFilter {

    /**
     * Description et extension acceptée par le filtre.
     */
    private String description;

    /**
     * Description et extension acceptée par le filtre.
     */
    private String extension;

    /**
     * Construit un filtre sur base d'une description et de l'extension acceptée.
     *
     * @param description Description.
     * @param extension Extension.
     */
    public Filtre(String description, String extension) {
        if (description == null || extension == null) {
            throw new NullPointerException("La description (ou extension) ne peut être null.");
        }
        this.description = description;
        this.extension = extension;
    }

    /**
     * Implémentation de FileFilter.
     *
     * @param file
     * @return
     */
    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String nomFichier = file.getName().toLowerCase();

        return nomFichier.endsWith(extension);
    }

    /**
     * Retourne la description.
     *
     * @return Description.
     */
    @Override
    public String getDescription() {
        return description;
    }
}
