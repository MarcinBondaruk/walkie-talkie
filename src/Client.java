import java.io.*;
import java.net.*;

public final class Client
{
    public static void main(String[] args) throws IOException
    {
        ClientController clientController = new ClientController();
        StateContainer stateContainer = new StateContainer();
        new ClientFrame(clientController, stateContainer);
    }
}