import java.io.*;
import java.net.*;

public final class Client
{
    public static void main(String[] args) throws IOException
    {
        ServerCommunicator serverCommunicator = new ServerCommunicator();
        StateContainer stateContainer = new StateContainer();
        ClientController clientController = new ClientController(serverCommunicator);

        new ClientFrame(clientController, stateContainer);
    }
}