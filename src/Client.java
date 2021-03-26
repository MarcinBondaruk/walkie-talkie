import java.io.*;
import java.net.*;

/**
 * Entry point for client application
 * Sets up shared resoruces such as MessageHeap and StateContainer
 *
 * @author Marcin Bondaruk
 */
public final class Client
{
    public static void main(String[] args) throws IOException
    {
        MessageHeap messageHeap = new MessageHeap();
        StateContainer stateContainer = new StateContainer();
        ServerCommunicator serverCommunicator = new ServerCommunicator(messageHeap, stateContainer);
        ClientController clientController = new ClientController(serverCommunicator, messageHeap);

        new ClientFrame(clientController, stateContainer);
    }
}