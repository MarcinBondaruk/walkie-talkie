import java.awt.*;
import java.awt.event.*;

public class ClientFrame extends Frame implements WindowListener
{
    private class SetupPanel extends Panel implements ActionListener
    {
        private ClientFrame cfref;
        private OnlineListFrame onlineListFrame;
        private TextField ipTF;
        private TextField portTF;
        private TextField usernameTF;
        private Button connectBtn;
        private Button disconnectBtn;

        public SetupPanel(ClientFrame cfref)
        {
            this.cfref = cfref;

            setLayout(new GridLayout(6,1));
            Label ipLabel = new Label("Server IP:");
            Label portLabel = new Label("Server Port:");
            Label usernameLabel = new Label("Username:");

            ipTF = new TextField("localhost");
            ipTF.setPreferredSize(new Dimension(150, 25));

            portTF = new TextField("1990");
            portTF.setPreferredSize(new Dimension(150, 25));

            usernameTF = new TextField("bondarum");
            usernameTF.setPreferredSize(new Dimension(150, 25));

            this.connectBtn = new Button("Connect");
            this.connectBtn.setPreferredSize(new Dimension(150, 25));
            this.connectBtn.setActionCommand("CONN_INIT");
            this.connectBtn.addActionListener(this);

            this.disconnectBtn = new Button("Disconnect");
            this.disconnectBtn.setPreferredSize(new Dimension(150, 25));
            this.disconnectBtn.setActionCommand("DISCONNECT");
            this.disconnectBtn.addActionListener(this);
            this.disconnectBtn.setEnabled(false);

            add(ipLabel);
            add(this.ipTF);
            add(portLabel);
            add(this.portTF);
            add(usernameLabel);
            add(this.usernameTF);
            add(this.connectBtn);
            add(this.disconnectBtn);
        }

        public void actionPerformed(ActionEvent event)
        {
            switch (event.getActionCommand()) {
                case "CONN_INIT":
                    this.handleConnectionAction(
                        this.ipTF.getText(),
                        this.portTF.getText()
                    );
                    break;
                case "DISCONNECT":
                    this.handleDisconnectAction();
                    break;
                default:
                    break;
            }
        }

        private void handleConnectionAction(String ip, String port)
        {
            if (port.length() < 1) {
                new ErrorFrame("Port field cannot be empty or negative!");
            } else if (ip.length() < 7) {
                new ErrorFrame("Invalid or empty ip address!");
            } else {
                boolean result = this.cfref.getController()
                    .initConnection(ip, Integer.parseInt(port));

                if (result) {
                    Response response = this.cfref.getController()
                        .sendMessage(new RegisterCommand(this.usernameTF.getText()));

                    if (response.result()) {
                        this.ipTF.setEditable(false);
                        this.portTF.setEditable(false);
                        this.usernameTF.setEditable(false);
                        this.connectBtn.setEnabled(false);
                        this.disconnectBtn.setEnabled(true);
                        this.cfref.getStateContainer().setCurrentUser(this.usernameTF.getText());

                        this.onlineListFrame = new OnlineListFrame(this.cfref);
                    } else {
                        this.cfref.getController().disconnect();
                        new ErrorFrame(response.message());
                    }
                } else {
                    this.cfref.getController().disconnect();
                    new ErrorFrame("Something went wrong.");
                }
            }
        }

        private void handleDisconnectAction()
        {
            this.cfref.getController().sendMessage(new DisconnectCommand(this.cfref.getStateContainer().currentUser()));
            this.cfref.getController().disconnect();
            this.onlineListFrame.dispose();
            this.ipTF.setEditable(true);
            this.portTF.setEditable(true);
            this.usernameTF.setEditable(true);
            this.connectBtn.setEnabled(true);
            this.disconnectBtn.setEnabled(false);
        }
    }

    private ClientController clientController;
    private StateContainer stateContainer;

    public ClientFrame(ClientController clientController, StateContainer stateContainer)
    {
        super("Walkie-Talkie");
        this.clientController = clientController;
        this.stateContainer = stateContainer;

        Panel onlineListPanel = new SetupPanel(this);
        add(onlineListPanel);

        addWindowListener(this);
        pack();
        setVisible(true);
    }

    public ClientController getController()
    {
        return this.clientController;
    }

    public StateContainer getStateContainer()
    {
        return this.stateContainer;
    }

    public void windowClosing(WindowEvent event)
    {
        if (this.getController().isConnected()) {
            this.getController().sendMessage(new DisconnectCommand(this.getStateContainer().currentUser()));
        }

        this.clientController.disconnect();
        System.exit(0);
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