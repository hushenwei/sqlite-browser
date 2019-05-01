package org.hsw.windows;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.util.Enumeration;
import java.util.Vector;

public class BTable extends JTable {

    public BTable() {
    }

    public BTable(TableModel dm) {
        super(dm);
    }

    public BTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
    }

    public BTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
    }

    public BTable(int numRows, int numColumns) {
        super(numRows, numColumns);
    }

    public BTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
    }

    public BTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    public void fitTableColumns() {
        JTableHeader header = this.getTableHeader();
        int rowCount = this.getRowCount();

        Enumeration columns = this.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) this.getTableHeader().getDefaultRenderer()
                    .getTableCellRendererComponent(this, column.getIdentifier(), false, false, -1, col)
                    .getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) this.getCellRenderer(row, col)
                        .getTableCellRendererComponent(this, this.getValueAt(row, col), false, false, row, col)
                        .getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width + this.getIntercellSpacing().width + 10);
            this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        }
    }
}
