import java.awt.*;
import java.awt.event.*;

public class ErrorFrame extends Frame implements WindowListener
{
    public ErrorFrame(String errorMessage) {
        super("Error");
        Label errorLabel = new Label(errorMessage);
        add(errorLabel, "Center");

        addWindowListener(this);
        pack();
        setVisible(true);
        setSize(250, 150);
    }

    public void windowClosing(WindowEvent event)
    {
        dispose();
    }

    public void windowClosed(WindowEvent event)
    {}

    public void windowOpened(WindowEvent event)
    {}

    public void windowIconified(WindowEvent event)
    {}

    public void windowDeiconified(WindowEvent event)
    {}

    public void windowActivated(WindowEvent event)
    {}

    public void windowDeactivated(WindowEvent event)
    {}
}