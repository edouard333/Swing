/*
 * Licence Studio l'Equipe.
 */
package com.phenix.swing;

import java.awt.Toolkit;
import java.net.URL;
import javax.swing.JFrame;

/**
 * Fenêtre indiquant un message.
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class FenetreMessage extends JFrame {

    /**
     * Crée la fenêtre.
     *
     * @param parent Fenêtre parent.
     * @param message Le message à afficher.
     */
    public FenetreMessage(JFrame parent, String message) {
        this(parent, message, null);
    }

    /**
     * Crée la fenêtre.
     *
     * @param parent Fenêtre parent.
     * @param message Le message à afficher.
     * @param url URL de l'image à utiliser.
     */
    public FenetreMessage(JFrame parent, String message, URL url) {
        initComponents();

        this.L_message.setText(message);

        // Ajoute une icone à l'application.
        if (url != null) {
            setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        }

        JPositionMultiScreen.setLocation(this, parent);
    }

    /**
     * Modifie le message.
     *
     * @param message Le message.
     */
    public void setMessage(String message) {
        this.L_message.setText(message);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        L_message = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Information");

        L_message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(L_message, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel L_message;
    // End of variables declaration//GEN-END:variables
}