import java.util.*;
import java.net.*;
import java.io.*;

/**
 * Holds all online users and its socket's output streams references
 *
 * @author Marcin Bondaruk
 */
public final class OnlineList
{
    private HashMap<String, PrintWriter> onlineUsers;

    public OnlineList()
    {
        this.onlineUsers = new HashMap<>();
    }

    public synchronized void add(String username, PrintWriter socketOutput)
    {
        this.onlineUsers.put(username, socketOutput);
    }

    public synchronized PrintWriter getMessageByUser(String username)
    {
        return this.onlineUsers.get(username);
    }

    public synchronized void remove(String username)
    {
        this.onlineUsers.remove(username);
    }

    public synchronized boolean isOnline(String username)
    {
        return this.onlineUsers.containsKey(username);
    }

    public synchronized String whosOnline()
    {
        return String.join(",", this.onlineUsers.keySet());
    }
}