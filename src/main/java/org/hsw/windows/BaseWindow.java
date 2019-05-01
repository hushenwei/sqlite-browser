package org.hsw.windows;

import javax.swing.*;
import java.awt.*;

public abstract class BaseWindow extends JFrame {

    public BaseWindow() throws HeadlessException {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(1024, 800));
        this.setLocationByPlatform(true);
        initializeComponents();
    }

    public abstract void initializeComponents();
}
