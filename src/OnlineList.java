import java.util.*;
import java.net.*;

public final class OnlineList
{
    private HashMap<String, Socket> onlineUsers;

    public OnlineList()
    {
        this.onlineUsers = new HashMap<>();
    }

    public void add(String username, Socket socket)
    {
        this.onlineUsers.put(username, socket);
    }

    public void remove(String username)
    {
        this.onlineUsers.remove(username);
    }

    public boolean isOnline(String username)
    {
        return this.onlineUsers.containsKey(username);
    }

    public String whosOnline()
    {
        return String.join(",", this.onlineUsers.keySet());
    }
}