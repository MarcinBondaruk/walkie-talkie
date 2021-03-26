import java.io.*;
import java.net.*;

public final class ClientSocketThread extends Thread
{
    private Socket socket;
    private BufferedReader inputStream;
    private PrintWriter outputStream;
    private OnlineList onlineList;

    public ClientSocketThread(
        Socket socket,
        OnlineList onlineList
    ) throws IOException
    {
        this.socket = socket;
        this.inputStream = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outputStream = new PrintWriter(this.socket.getOutputStream(), true);
        this.onlineList = onlineList;
    }

    public void run()
    {
        System.out.println("Accepted connection from: " + this.socket.getInetAddress());

        try {
            while(!interrupted()) {
                String encodedCommand = this.inputStream.readLine();
                if (!encodedCommand.isEmpty()) {
                    this.interpretCommand(CommandDecoder.deserialize(encodedCommand));
                }
            }

            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
        } catch(IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void interpretCommand(String[] deserializedCommand) throws IOException
    {
        switch(deserializedCommand[0]) {
            case "REGISTER":
                this.registerNewUser(new RegisterCommand(deserializedCommand[1]));
                break;
            case "MESSAGE_TO":
                break;
            case "GET_ONLINE":
                this.sendOnlineList();
                break;
            case "DISCONNECT":
                this.disconnect(new DisconnectCommand(deserializedCommand[1]));
                break;
            default:
                break;
        }
    }

    private synchronized void registerNewUser(RegisterCommand command)
    {
        if (!this.onlineList.isOnline(command.username())) {
            this.onlineList.add(command.username(), this.socket);
            System.out.println("User joined: " + command.username());
        } else {
            this.outputStream.println(this.getSerializedResponse("failed", "USERNAME_IN_USE"));
        }
    }

    private void sendOnlineList() throws IOException
    {
        this.outputStream.println(this.getSerializedResponse("success", this.onlineList.whosOnline()));
    }

    private synchronized void disconnect(DisconnectCommand command)
    {
        this.onlineList.remove(command.username());
        this.outputStream.println(this.getSerializedResponse("success", command.username()));
        System.out.println("User logged out: " + command.username());
        interrupt();
    }

    private String getSerializedResponse(String result, String message)
    {
        return new Response(result, message).serialize();
    }
}