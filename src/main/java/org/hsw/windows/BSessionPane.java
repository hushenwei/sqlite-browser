package org.hsw.windows;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hsw.model.BConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Closeable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;

public class BSessionPane extends JSplitPane implements Closeable {
    private static Logger logger = Logger.getLogger(BSessionPane.class);

    JScrollPane scrollPane4TaInputSql;
    BTextArea taInputSql;

    JTabbedPane tabResult;

    JScrollPane scrollPane4TaResult;
    DefaultTableModel tmResult;
    BTextArea taResult;

    JScrollPane scrollPane4TableResult;
    BTable tblResult;
    BConnection conn;

    public BSessionPane(BConnection conn) {
        this.conn = conn;
        this.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setResizeWeight(0.5);

        taInputSql = new BTextArea();
        taInputSql.setTabSize(2);
        taInputSql.addKeyListener(registeronKeyAction());
        scrollPane4TaInputSql = new JScrollPane(taInputSql);
        scrollPane4TaInputSql.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane4TaInputSql.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.setTopComponent(scrollPane4TaInputSql);



        tabResult = new JTabbedPane();

        taResult = new BTextArea();
        taResult.setEditable(false);
        taResult.setTabSize(2);
        scrollPane4TaResult = new JScrollPane(taResult);
        scrollPane4TaResult.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane4TaResult.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabResult.addTab("Message", scrollPane4TaResult);

        tmResult = new DefaultTableModel();
        tblResult = new BTable(tmResult);
        tblResult.getTableHeader().setResizingAllowed(true);
        tblResult.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        scrollPane4TableResult = new JScrollPane(tblResult);
        scrollPane4TableResult.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane4TableResult.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        tabResult.addTab("Result Set", scrollPane4TableResult);
        this.setBottomComponent(tabResult);
    }

    public KeyListener registeronKeyAction() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && (e.isAltDown() || e.isControlDown())) {
                    executeSql();
                }
            }
        };
    }

    private void executeSql() {
        try {
            String text = taInputSql.getSelectedText();
            if (StringUtils.isBlank(text)) {
                text = taInputSql.getText();
            }

            ResultSet rs = conn.executeSql(text);
            tmResult = new DefaultTableModel();

            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount();
            for (int i = 0; i < count; i++) {
                tmResult.addColumn(metaData.getColumnName(i + 1));
            }

            while(rs.next()) {
                Object[] rowData = new Object[count];
                for (int i = 0; i < count; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                tmResult.addRow(rowData);
            }
            tblResult.setModel(tmResult);
            tblResult.fitTableColumns();
            tabResult.setSelectedIndex(tabResult.indexOfComponent(scrollPane4TableResult));
        } catch (SQLException e) {
            if (!(e instanceof SQLWarning)) {
                logger.error("Execution sql failed!", e);
            }
            String currentText = taResult.getText();
            if (StringUtils.isBlank(currentText)) {
                taResult.setText(e.getMessage());
            } else {
                taResult.setText(currentText + "\r\n" + e.getMessage());
            }
            tabResult.setSelectedIndex(tabResult.indexOfComponent(scrollPane4TaResult));
        }
    }

    @Override
    public void close() throws IOException {
        conn.close();
    }
}
