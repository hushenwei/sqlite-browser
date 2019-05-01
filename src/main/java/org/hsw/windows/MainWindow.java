package org.hsw.windows;

import org.apache.log4j.Logger;
import org.hsw.model.BConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class MainWindow extends BaseWindow {
    private static Logger logger = Logger.getLogger(MainWindow.class);

    JMenuBar menuMainBar;
    JMenu fileMain;
    JMenuItem mnNewConnection;
    JList<BConnection> listConnection;
    JSplitPane splitMain;
    JTabbedPane tabMain;
    JFileChooser jfileChooser;

    @Override
    public void initializeComponents() {
        menuMainBar = new JMenuBar();

        fileMain = new JMenu("File");

        mnNewConnection = new JMenuItem("New Connection");
        mnNewConnection.addActionListener(this::onNewConnection);
        fileMain.add(mnNewConnection);

        menuMainBar.add(fileMain);
        this.add(menuMainBar, BorderLayout.NORTH);



        splitMain = new JSplitPane();

        listConnection = new JList<BConnection>();
        listConnection.setPreferredSize(new Dimension(200, 200));
        splitMain.setLeftComponent(listConnection);


        tabMain = new BTabbedPane();
        splitMain.setRightComponent(tabMain);
        this.add(splitMain, BorderLayout.CENTER);

    }

    public void onNewConnection(ActionEvent event) {
        jfileChooser = new JFileChooser();
        jfileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        jfileChooser.setMultiSelectionEnabled(false);
        int result = jfileChooser.showDialog(this, null);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = jfileChooser.getSelectedFile();
        String filePath = file.getAbsolutePath();
        BConnection bconn = new BConnection(filePath);
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        tabMain.addTab(fileName, new BSessionPane(bconn));
    }
}
