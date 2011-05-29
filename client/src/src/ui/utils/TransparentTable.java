package src.ui.utils;

import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 * public class TransparentTable extends JTable 
 * this is a table with a transparent background which default module 
 * is a non user editable  module 
 * @author Omer Shenhar and Shachar Butnaro based on a solution found online 
 *
 */
public class TransparentTable extends JTable {

    private static final long serialVersionUID = 1L;

    /**
     * public TransparentTable()
     * constructor for  a TransparentTable
     * makes a table with a blank model and transparent background
     */
    public TransparentTable() {
        super();
        resetModel();
        setOpaque(false);
    }

    /**
     * public void resetModel()
     * resets the table to a blank non user editable DefaultTableModel
     * 
     */
    public void resetModel() {
        setModel(new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            /* (non-Javadoc)
             * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
             */
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        });
    }

    /* (non-Javadoc)
     * @see javax.swing.JTable#prepareRenderer(javax.swing.table.TableCellRenderer, int, int)
     */
    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);
        // We want renderer component to be transparent
        // so background image is visible
        if (c instanceof JComponent) {
            ((JComponent) c).setOpaque(false);
        }
        return c;
    }
}
