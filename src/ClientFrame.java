import java.awt.*;
import java.awt.event.*;

/**
 * Main client frame. Base for all others.
 *
 * @author Marcin Bondaruk
 */
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

        /**
         * determines which button was clicked and invokes proper action
         */
        public void actionPerformed(ActionEvent event)
        {
            switch (event.getActionCommand()) {
                case "CONN_INIT":
                    this.handleConnectionAction(
                        this.ipTF.getText(),
                        this.portTF.getText(),
                        this.usernameTF.getText()
                    );
                    break;
                case "DISCONNECT":
                    this.handleDisconnectAction();
                    break;
                default:
                    break;
            }
        }

        /**
         * validates GUI inputs. Proceedes with connection and user registration or raises an Error.
         */
        private void handleConnectionAction(String ip, String port, String username)
        {
            if (port.length() < 1) {
                new ErrorFrame("Port field cannot be empty or negative!");
            } else if (ip.length() < 7) {
                new ErrorFrame("Invalid or empty ip address!");
            } else if (username.length() < 1) {
                new ErrorFrame("Username must not be empty!");
            } else {
                boolean connResult = this.cfref.getController()
                .initConnection(ip, port);

                if (connResult) {
                    boolean registResult = this.cfref.getController().registerUser(username);

                    if (registResult) {
                        this.disableConnectionPanel();
                        this.cfref.getStateContainer().setCurrentUser(username);

                        this.onlineListFrame = new OnlineListFrame(this.cfref);
                    } else {
                        new ErrorFrame("Username is taken.");
                    }
                } else {
                    new ErrorFrame("Something went wrong.");
                }
            }
        }

        private void disableConnectionPanel()
        {
            this.ipTF.setEditable(false);
            this.portTF.setEditable(false);
            this.usernameTF.setEditable(false);
            this.connectBtn.setEnabled(false);
            this.disconnectBtn.setEnabled(true);
        }

        private void enableConnectionPanel()
        {
            this.ipTF.setEditable(true);
            this.portTF.setEditable(true);
            this.usernameTF.setEditable(true);
            this.connectBtn.setEnabled(true);
            this.disconnectBtn.setEnabled(false);
        }

        private void handleDisconnectAction()
        {
            this.cfref.getController().disconnect(this.cfref.getStateContainer().currentUser());
            this.onlineListFrame.dispose();
            this.enableConnectionPanel();
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
        if (!this.getStateContainer().currentUser().isEmpty()) {
            this.clientController.disconnect(this.getStateContainer().currentUser());
        }

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