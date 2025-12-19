package com.phenix.swing;

import com.phenix.swing.exception.SwingException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JFrame;

/**
 *
 * @author <a href="mailto:edouard128@hotmail.com">Edouard Jeanjean</a>
 */
public final class JPositionMultiScreen {

    /**
     * On ne peut pas instancier cette classe.
     *
     * @throws Exception On ne peut pas instancier cette classe.
     */
    private JPositionMultiScreen() throws Exception {
        throw new Exception("Cette classe ne peut pas être instanciée.");
    }

    /**
     * Retourne le dossier où se trouve le fichier de préférence UI.
     *
     * @param nomApplication Nom de l'application.
     * @return
     */
    private static File getFichierPreferences(@NotNull @NotBlank String nomApplication) {
        return new File(System.getProperty("user.home") + File.separator + "preferences-" + nomApplication + ".prop");
    }

    /**
     * Sauvegarde les informations de positions.
     *
     * @param frame La fenêtre.
     * @param nomApplication Nom de l'application.
     *
     * @throws SwingException Erreur avec l'écriture du fichier properties.
     */
    public static void savePreferences(@NotNull JFrame frame, @NotNull @NotBlank String nomApplication) throws SwingException {
        try {
            File file = getFichierPreferences(nomApplication);
            Properties p = new Properties();
            // restore the frame from 'full screen' first!
            frame.setExtendedState(Frame.NORMAL);
            Rectangle r = frame.getBounds();

            p.setProperty("x", "" + (int) r.getX());
            p.setProperty("y", "" + (int) r.getY());
            p.setProperty("w", "" + (int) r.getWidth());
            p.setProperty("h", "" + (int) r.getHeight());
            p.setProperty("s", frame.getGraphicsConfiguration().getDevice().getIDstring());

            BufferedWriter br = new BufferedWriter(new FileWriter(file));
            p.store(br, "Properties of the user frame");
        } catch (IOException exception) {
            throw new SwingException(exception.getMessage(), exception);
        }
    }

    /**
     * Tente de charger des préférences UI et si n'y arrive pas, alors centre la
     * fenêtre sur le bon écran.<br>
     * Ajoute au
     * {@link java.awt.event.WindowAdapter#windowClosing(java.awt.event.WindowEvent) windowClosing(WindowEvent)}
     * la sauvegarde des préférences.<br>
     * S'il y a d'autres manières dont se ferme la fenêtre : ajouter la fonction
     * {@link #savePreferences(JFrame, String)}
     *
     * @param frame La fenêtre.
     * @param nomApplication Nom de l'application.
     *
     * @throws SwingException Erreur dans le chargement des préférences.
     */
    public static void loadPreferencesOrCenterScreen(@NotNull JFrame frame, @NotNull @NotBlank String nomApplication) throws SwingException {
        // Ajoute le comportement pour la fermeture de la fenêtre : sauver les préférences UI.
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evt) {
                try {
                    JPositionMultiScreen.savePreferences(frame, nomApplication);
                } // Comme on est sur une implémentation de fonction, on peut pas throw l'erreur.
                catch (SwingException exception) {
                    exception.printStackTrace();
                }
            }
        });

        // Si des préférences existes, on les utilise.
        if (preferencesExist(nomApplication)) {
            loadPreferences(frame, nomApplication);
        } else {
            setLocation(frame, null);
        }
    }

    /**
     *
     * @param frame La fenêtre.
     * @param nomApplication Nom de l'application.
     *
     * @throws SwingException Erreur dans le chargement du fichier properties.
     */
    public static void loadPreferences(@NotNull Window frame, @NotNull @NotBlank String nomApplication) throws SwingException {
        try {
            File file = getFichierPreferences(nomApplication);
            Properties p = new Properties();
            BufferedReader br = new BufferedReader(new FileReader(file));
            p.load(br);

            int x = Integer.parseInt(p.getProperty("x"));
            int y = Integer.parseInt(p.getProperty("y"));
            int w = Integer.parseInt(p.getProperty("w"));
            int h = Integer.parseInt(p.getProperty("h"));

            Rectangle r = new Rectangle(x, y, w, h);

            frame.setBounds(r);
        } catch (IOException exception) {
            throw new SwingException(exception.getMessage(), exception);
        }
    }

    /**
     * Retourne {@code true} si le fichier properties existe pour l'application.
     *
     * @param nomApplication Nom de l'application.
     * @return {@code true} si le fichier existe.
     */
    public static boolean preferencesExist(@NotNull @NotBlank String nomApplication) {
        File optionsFile = getFichierPreferences(nomApplication);

        // Si des préférences existes, on les utilise.
        return optionsFile.exists();
    }

    /**
     * Indique où doit se trouve la fenêtre au centre d'où se trouve la souris.
     *
     * @param frame La fenêtre.
     */
    public static void setLocation(@NotNull Window frame) {
        setLocation(frame, null);
    }

    /**
     * Indique où doit se trouve la fenêtre au centre d'où se trouve la souris
     * ou parent si renseigné.
     *
     * @param frame La fenêtre.
     * @param parent La fenêtre parent.
     */
    public static void setLocation(@NotNull Window frame, @Null Window parent) {
        // Obtenir la position actuelle de la souris.
        Point mouseLocation = parent == null ? MouseInfo.getPointerInfo().getLocation() : parent.getLocation();

        // Obtenir les écrans disponibles.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();

        // Déterminer l'écran contenant la position de la souris.
        GraphicsDevice currentScreen = null;

        for (GraphicsDevice screen : screens) {
            GraphicsConfiguration gc = screen.getDefaultConfiguration();
            Rectangle bounds = gc.getBounds();
            if (bounds.contains(mouseLocation)) {
                currentScreen = screen;
                break;
            }
        }

        // Si aucun écran ne contient la position de la souris, utiliser l'écran principal.
        if (currentScreen == null) {
            currentScreen = ge.getDefaultScreenDevice();
        }

        // Obtenir les dimensions de l'écran sélectionné.
        GraphicsConfiguration gc = currentScreen.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();

        // Calculer les coordonnées pour centrer la fenêtre sur l'écran sélectionné.
        int x = bounds.x + (bounds.width - frame.getWidth()) / 2;
        int y = bounds.y + (bounds.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}
