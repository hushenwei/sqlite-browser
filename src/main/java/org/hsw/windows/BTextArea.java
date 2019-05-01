package org.hsw.windows;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BTextArea extends JTextArea implements KeyListener {
    private UndoManager undoManager;

    public BTextArea() {
        undoManager = new UndoManager();
        this.getDocument().addUndoableEditListener(undoManager);
        this.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
            if (undoManager.canUndo()) {
                undoManager.undo();
            }
        }

        if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y) {
            if (undoManager.canRedo()) {
                undoManager.redo();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
