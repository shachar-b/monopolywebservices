package src.ui.utils;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * public class IconCellRenderer extends DefaultTableCellRenderer
 * a cell renderer which makes it possible to hold an ImagePanel in the table
 * @author Omer Shenhar and Shachar Butnaro
 *
 */
public class IconCellRenderer extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    /**
     * public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
     * makes it possible to hold an ImagePanel in the table
     * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (value instanceof ImagePanel) {
            ImagePanel imageCopy = ((ImagePanel) value).getCopy();
            imageCopy.setRetainAspectRatio(true);
            return imageCopy;
        }
        return label;
    }
}
