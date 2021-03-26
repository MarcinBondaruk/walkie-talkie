import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class OnlineListFrame extends Frame implements ActionListener, WindowListener
{
    private ClientFrame cfref;
    private String[] online;
    private Button refreshBtn;
    private List onlineList;

    public OnlineListFrame(ClientFrame cfref) {
        super("Online List");
        this.cfref = cfref;
        this.onlineList = this.createOnlineList(this.online);
        this.refreshOnlineList();

        this.refreshBtn = this.createButton("Refresh", "REFRESH");

        add(this.onlineList, "Center");
        add(this.refreshBtn, "South");

        addWindowListener(this);
        pack();
        setVisible(true);
        setSize(250, 400);
    }

    private List createOnlineList(String[] onlineUsers)
    {
        List onlineList = new List(10, true);
        onlineList.setMultipleMode(false);
        onlineList.addActionListener(this);
        return onlineList;
    }

    private Button createButton(String name, String command)
    {
        Button btn = new Button(name);
        btn.setPreferredSize(new Dimension(40,40));
        btn.setActionCommand(command);
        btn.addActionListener(this);
        return btn;
    }

    public void actionPerformed(ActionEvent event)
    {
        String actionCmd = event.getActionCommand();
        switch (actionCmd) {
            case "REFRESH":
                this.refreshOnlineList();
                break;
            default:
                this.beginConversation(actionCmd);
                break;
        }
    }

    private void refreshOnlineList()
    {
        this.online = this.cfref.getController().sendMessage(new GetOnlineCommand()).message().split(",");
        Arrays.sort(this.online);
        this.onlineList.removeAll();

        for(String user : this.online) {
            onlineList.add(user);
        }
    }

    private void beginConversation(String recipientName)
    {
        if (this.cfref.getStateContainer().openConversation(recipientName)) {
            new ConversationFrame(this.cfref, recipientName);
        } else {
            new ErrorFrame("Conversation already in progress...");
        }
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