package src.ui.guiComponents.Squares;

import java.awt.BorderLayout;
import src.squares.Square;
import src.ui.utils.ImagePanel;

/**
 * public class ImageOnlySquarePanel extends SquarePanel
 * an SquarePanel which displays an image only
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class ImageOnlySquarePanel extends SquarePanel {

    private static final long serialVersionUID = 1L;

    /**
     * public ImageOnlySquarePanel(Square representedSquare, String path)
     * a constructor for an ImageOnlySquarePanel
     * @param representedSquare - a non null valid square which would be represented by the ImageOnlySquarePanel
     * @param path - a valid path for the image to be displayed
     */
    public ImageOnlySquarePanel(Square representedSquare, String path) {
        super(representedSquare);
        //JLabel imageLabel = new JLabel(imageIcon);
        ImagePanel imageLabel = new ImagePanel(path);
        this.add(imageLabel, BorderLayout.CENTER);
    }
}
