import java.io.*;
import java.net.*;

/**
 * Separates server Websocket communication from action logic.
 *
 * @author Marcin Bondaruk
 */
public final class ServerController
{
    private OnlineList onlineList;

    public ServerController(OnlineList onlineList)
    {
        this.onlineList = onlineList;
    }

    public void registerUser(String[] data, PrintWriter outputStream)
    {
        this.onlineList.add(data[1], outputStream);
        ServerMessage msg = new ServerMessage("REGISTERED", data[1]);
        outputStream.println(msg.serialize());
        System.out.println("User joined " + data[1]);
    }

    public void disconnect(String[] data, PrintWriter outputStream)
    {
        this.onlineList.remove(data[1]);
        System.out.println("User logged out: " + data[1]);
    }

    public void getOnlineList(PrintWriter outputStream)
    {
        ServerMessage msg = new ServerMessage("ONLINE_LIST", this.onlineList.whosOnline());
        outputStream.println(msg.serialize());
    }

    public void deliverMessage(String[] data, PrintWriter outputStream)
    {
        PrintWriter recipientOutputStream = this.onlineList.getMessageByUser(data[2]);
        ServerMessage msg = new ServerMessage(
            "MESSAGE_FROM",
            String.join(
                ",",
                data[1],
                data[2],
                data[3],
                data[4]
            )
        );

        recipientOutputStream.println(msg.serialize());
        ServerMessage ack = new ServerMessage("DELIVERED_" + data[4], "Ok");
        outputStream.println(ack.serialize());
    }
}