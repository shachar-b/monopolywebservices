package src.ui.utils;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * @author liron ble
 * this is the util class taken from lirons lecture
 */
public class Utils {

    /**
     * public static ImageIcon getImageIcon (String name)
     * loads an image into an image icon 
     * @param name - the path of the image
     * @return - an ImageIcon with the given image
     */
    public static ImageIcon getImageIcon(String name) {
        Image image;
        try {
            image = Toolkit.getDefaultToolkit().createImage(name);
        } catch (SecurityException e) {
            throw new RuntimeException("Could not access resource " + name);
        }
        if (image == null) {
            return null;
        }
        ImageIcon result = new ImageIcon(image);
        return result;
    }
}
