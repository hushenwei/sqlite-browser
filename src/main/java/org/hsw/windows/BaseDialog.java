package org.hsw.windows;

import javax.swing.*;
import java.awt.*;

public abstract class BaseDialog extends JDialog {

    public BaseDialog() {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(400, 320));
        this.setLocationByPlatform(true);
        initializeComponents();
    }

    public abstract void initializeComponents();
}
