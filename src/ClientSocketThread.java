import java.io.*;
import java.net.*;

/**
 * Server side thread that handles clients messages and communication
 *
 * @author Marcin Bondaruk
 */
public final class ClientSocketThread extends Thread
{
    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private ServerController serverController;

    public ClientSocketThread(
        Socket socket,
        ServerController serverController
    ) throws IOException
    {
        this.socket = socket;
        this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);
        this.serverController = serverController;
    }

    public void run()
    {
        System.out.println("Accepted connection from: " + this.socket.getInetAddress());

        try {
            while(!interrupted()) {
                String encodedMessage = this.inputStream.readLine();

                if (encodedMessage != null && !encodedMessage.isEmpty()) {
                    this.interpretCommand(StreamDecoder.deserialize(encodedMessage));
                }
            }
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void interpretCommand(String[] deserializedCommand) throws IOException
    {
        switch(deserializedCommand[0]) {
            case "DELIVER":
                this.serverController.deliverMessage(deserializedCommand, this.outputStream);
                break;
            case "DISCONNECT":
                this.serverController.disconnect(deserializedCommand, this.outputStream);
                interrupt();
                break;
            case "GET_ONLINE":
                this.serverController.getOnlineList(this.outputStream);
                break;
            case "REGISTER":
                this.serverController.registerUser(deserializedCommand, this.outputStream);
                break;
            default:
                break;
        }
    }
}