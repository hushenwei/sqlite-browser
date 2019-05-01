package org.hsw;

import org.apache.log4j.Logger;
import org.hsw.windows.MainWindow;

/**
 * Hello world!
 */
public class App {
    private static Logger logger = Logger.getLogger(App.class);
    public static void main(String[] args) {
        initialize();
        startApplication();
    }
    private static void initialize() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            logger.fatal("Initialize sqlite jdbc failed!", e);
            System.exit(1);
        }
    }

    private static void startApplication() {
        MainWindow window = new MainWindow();
        window.setVisible(true);
    }

}
