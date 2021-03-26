import java.io.*;
import java.net.*;

public final class Client
{
    public static void main(String[] args) throws IOException
    {
        MessageHeap messageHeap = new MessageHeap();
        ServerCommunicator serverCommunicator = new ServerCommunicator();
        StateContainer stateContainer = new StateContainer();
        ClientController clientController = new ClientController(serverCommunicator, messageHeap);

        new ClientFrame(clientController, stateContainer);
        (new ClientWorkerThread(serverCommunicator, messageHeap)).start();
    }
}