package org.hsw.windows;

import javax.swing.*;
import java.awt.*;

public class BTabbedPane extends JTabbedPane {

    @Override
    public void addTab(String title, Component component) {
        super.addTab(title, component);
        initialize(title, null, component, null);
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        super.addTab(title, icon, component);
        initialize(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        initialize(title, icon, component, tip);
    }

    private void initialize(String title, Icon icon, Component component, String tip) {
        setTabComponentAt(indexOfComponent(component), new ButtonTabComponent(this));
    }
}
