import java.net.*;
import java.io.*;

public final class ClientController
{
    private Socket socket;
    private PrintWriter outputStream;
    private BufferedReader inputStream;
    private boolean isConnected;

    public ClientController() {
        this.isConnected = false;
    }

    public boolean isConnected()
    {
        return this.isConnected;
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

    public Response sendMessage(Command command)
    {
        try {
            this.outputStream.println(command.serialize());
            return this.deserializeResponse(this.inputStream.readLine());
        } catch(IOException e) {
            return new Response("failed", e.getMessage());
        }
    }

    private Response deserializeResponse(String serializedResponse)
    {
        String[] deserialized = CommandDecoder.deserialize(serializedResponse);
        return new Response(deserialized[0], deserialized[1]);
    }
}