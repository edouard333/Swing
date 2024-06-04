package com.phenix.swing;

import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
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
public class JPositionMultiScreen {

    /**
     * Retourne le dossier où se trouve le fichier de préférence UI.
     *
     * @param nom_application Nom de l'application.
     * @return
     */
    private static File getFichierPreferences(String nom_application) {
        return new File(System.getProperty("user.home") + File.separator + "preferences-" + nom_application + ".prop");
    }

    /**
     *
     * @param frame La fenêtre.
     * @param nom_application Nom de l'application.
     *
     * @throws IOException
     */
    public static void savePreferences(JFrame frame, String nom_application) throws IOException {
        File file = getFichierPreferences(nom_application);
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
    }

    /**
     * Tente de charger des préférences UI et si n'y arrive pas, alors centre la
     * fenêtre sur le bon écran.
     *
     * @param frame La fenêtre.
     * @param nom_application Nom de l'application.
     */
    public static void loadPreferencesOrCenterScreen(JFrame frame, String nom_application) throws IOException {
        // Ajoute le comportement pour la fermeture de la fenêtre : sauver les préférences UI.
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                    JPositionMultiScreen.savePreferences(frame, nom_application);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
        });

        // Si des préférences existes, on les utilise.
        if (preferencesExist("UX")) {
            loadPreferences(frame, "UX");
        } else {
            setLocation(frame, null);
        }
    }

    /**
     *
     * @param frame La fenêtre.
     * @param nom_application Nom de l'application.
     *
     * @throws IOException
     */
    public static void loadPreferences(JFrame frame, String nom_application) throws IOException {
        File file = getFichierPreferences(nom_application);
        Properties p = new Properties();
        BufferedReader br = new BufferedReader(new FileReader(file));
        p.load(br);

        int x = Integer.parseInt(p.getProperty("x"));
        int y = Integer.parseInt(p.getProperty("y"));
        int w = Integer.parseInt(p.getProperty("w"));
        int h = Integer.parseInt(p.getProperty("h"));

        Rectangle r = new Rectangle(x, y, w, h);

        frame.setBounds(r);
    }

    /**
     *
     * @param nom_application Nom de l'application.
     * @return
     */
    public static boolean preferencesExist(String nom_application) {
        File optionsFile = getFichierPreferences(nom_application);

        // Si des préférences existes, on les utilise.
        return optionsFile.exists();
    }

    /**
     * Indique où doit se trouve la fenêtre au centre d'où se trouve la souris.
     *
     * @param frame La fenêtre.
     */
    public static void setLocation(JFrame frame) {
        setLocation(frame, null);
    }

    /**
     * Indique où doit se trouve la fenêtre au centre d'où se trouve la souris
     * ou parent si renseigné.
     *
     * @param frame La fenêtre.
     * @param parent La fenêtre parent.
     */
    public static void setLocation(JFrame frame, JFrame parent) {
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

        // Obtenir les dimensions de l'écran sélectionné
        GraphicsConfiguration gc = currentScreen.getDefaultConfiguration();
        Rectangle bounds = gc.getBounds();

        // Calculer les coordonnées pour centrer la fenêtre sur l'écran sélectionné.
        int x = bounds.x + (bounds.width - frame.getWidth()) / 2;
        int y = bounds.y + (bounds.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }
}
