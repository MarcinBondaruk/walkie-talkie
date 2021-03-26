import java.net.*;
import java.io.*;

/**
 * Client side class.
 * Serves as a wrapper for Websocket communication
 *
 * @author Marcin Bondaruk
 */
public final class ServerCommunicator
{
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private MessageHeap messageHeap;
    private ClientWorkerThread cwt;
    private StateContainer stateContainer;
    private boolean isConnected;

    public ServerCommunicator(MessageHeap messageHeap, StateContainer stateContainer)
    {
        this.isConnected = false;
        this.messageHeap = messageHeap;
        this.stateContainer = stateContainer;
    }

    public boolean isConnected()
    {
        return this.isConnected;
    }

    public String readInputStream()
    {
        String msg = "";
        try {
            msg = this.inputStream.readLine();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }

        return msg;
    }

    public boolean initConnection(String ip, int port)
    {
        try {
            if (!this.isConnected) {
                this.socket = new Socket(ip, port);
                outputStream = new PrintWriter(this.socket.getOutputStream(), true);
                inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.isConnected = true;

                MessageHeap messageHeap = new MessageHeap();
                this.cwt = new ClientWorkerThread(this, this.messageHeap, this.stateContainer);
                cwt.start();
            }
            return true;
        } catch(UnknownHostException e) {
            return false;
        } catch(IOException e) {
            return false;
        }
    }

    public void disconnect()
    {
        try {
            if (this.isConnected) {
                this.cwt.interrupt();
                this.socket.close();
            }
        } catch(IOException e) {}
    }

    public void send(ClientMessage message)
    {
        this.outputStream.println(message.serialize());
    }
}