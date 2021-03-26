import java.net.*;
import java.io.*;

public final class ServerCommunicator
{
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private boolean isConnected;

    public ServerCommunicator()
    {
        this.isConnected = false;
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
        } catch(IOException e) {}

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
                this.socket.close();
            }
        } catch(IOException e) {}
    }

    public void send(ClientMessage message)
    {
        this.outputStream.println(message.serialize());
    }

    // private Response deserializeResponse(String serializedResponse)
    // {
    //     String[] deserialized = CommandDecoder.deserialize(serializedResponse);
    //     return new Response(deserialized[0], deserialized[1]);
    // }
}