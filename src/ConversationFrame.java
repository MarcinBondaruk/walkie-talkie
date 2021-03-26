import java.awt.*;
import java.awt.event.*;

public class ConversationFrame extends Frame implements ActionListener, WindowListener
{
    private ClientFrame cfref;
    private Button sendButton;
    private TextArea conversationTextArea;
    private TextArea messageTextArea;
    private String recipient;

    public ConversationFrame(ClientFrame cfref, String recipient)
    {
        super(recipient);
        this.cfref = cfref;
        this.recipient = recipient;

        this.messageTextArea = this.createTextArea(7, 20);
        this.sendButton = this.createSendButton();

        Panel lower = new Panel();
        lower.setLayout(new BorderLayout());
        lower.add(this.messageTextArea, "Center");
        lower.add(sendButton, "South");
        add(lower, "South");

        this.conversationTextArea = this.createTextArea(15, 20);
        this.conversationTextArea.setEditable(false);
        add(this.conversationTextArea,"Center");

        addWindowListener(this);
        pack();
        setVisible(true);
        setSize(400,400);
    }

    private TextArea createTextArea(int rows, int cols)
    {
        TextArea ta = new TextArea(rows, cols);
        return ta;
    }

    private Button createSendButton()
    {
        Button btn = new Button("Send");
        btn.setPreferredSize(new Dimension(150, 25));
        btn.setActionCommand("SEND_MESSAGE");
        btn.addActionListener(this);
        return btn;
    }

    public void actionPerformed(ActionEvent action)
    {
        String message = this.messageTextArea.getText();
        this.conversationTextArea.append(this.formatMessage(this.recipient, message));
        this.messageTextArea.setText("");
    }

    private String formatMessage(String from, String message)
    {
        return "" + from + " wrote:\n" + message + "\n";
    }

    public void windowClosing(WindowEvent event)
    {
        this.cfref.getStateContainer().closeConversation(this.recipient);
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