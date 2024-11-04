package com.phenix.swing;

import java.lang.reflect.InvocationTargetException;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

/**
 * Fenêtre affichant une barre de progression.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 * @version 0.1.0
 */
public final class JProgress extends JFrame {

    /**
     * La barre de progression.
     */
    private JProgressBar progress;

    /**
     * Si on a commencé.
     */
    private boolean isStarted;

    /**
     * Valeur actuelle de la progression.
     */
    private int value;

    /**
     * Max.
     */
    private int max;

    /**
     * Si on utilise le rafraichissement thread.
     */
    private boolean thread;

    /**
     * Construit la fenêtre de progression.
     *
     * @param max La valeur maximale de la progression.
     * @param thread Si on doit rafraichir la fenêtre.
     */
    public JProgress(int max, boolean thread) {
        // Modifie le titre de la fenêtre.
        super("Progression");

        this.isStarted = false;
        this.value = 0;
        this.max = max;
        this.progress = new JProgressBar(0, max);
        this.progress.setValue(0);
        this.progress.setStringPainted(true);
        this.getContentPane().add(this.progress);

        // Centre la fenêtre.
        this.setLocationRelativeTo(null);

        this.pack();

        // Ne peut pas redimensionner.
        this.setResizable(false);

        // Affiche la fenêtre.
        this.setVisible(true);

        this.thread = thread;
    }

    /**
     * Augmente la progression de la barre.
     */
    public void add() {
        this.value++;

        // Si thread désactivé, on met à jour manuellement.
        if (!thread) {
            progress.setValue(value);

            try {
                SwingUtilities.invokeAndWait(() -> {
                    progress.setValue(value);
                });
            } catch (InterruptedException | InvocationTargetException exception) {
                exception.printStackTrace();
            }
        }
    }

    /**
     * Définit la valeur.
     *
     * @param value La valeur.
     */
    public void setValue(int value) {
        progress.setValue(value);
    }
}
