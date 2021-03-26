import java.io.*;
import java.net.*;

public final class Server
{
    public static void main(String[] args) throws IOException
    {
        int port = 1990;
        OnlineList onlineList = new OnlineList();
        ServerSocket serverSocket = null;
        System.out.println("server started");

        /**
        *  Boostrap server
        *  initialize clients list
        *  initialize message queue
        *  initialize worker
        */

        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Awaiting connections...");
        } catch(IOException e) {
            System.out.println("Error occured while opening the socket: " + e.getMessage());
        }

        while(true) {
            (new ClientSocketThread(serverSocket.accept(), onlineList)).start();
        }
    }
}