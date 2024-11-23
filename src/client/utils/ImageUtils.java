package client.utils;

import javax.swing.*;
import java.awt.*;

public class ImageUtils {
    /**
     * Resizes an ImageIcon to the specified width and height.
     *
     * @param originalIcon  the image file.
     * @param width The desired width of the image.
     * @param height The desired height of the image.
     * @return A resized ImageIcon.
     */
    public static ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
