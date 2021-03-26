import java.util.HashMap;

/**
 * Holds all server messages except of Deliery messages.
 *
 * @author Marcin Bondaruk
 */
public final class MessageHeap
{
    private HashMap<String, ServerMessage> messageHeap;

    public MessageHeap()
    {
        this.messageHeap = new HashMap<>();
    }

    synchronized public void add(String id, ServerMessage ServerMessage)
    {
        this.messageHeap.put(id, ServerMessage);
    }

    synchronized public boolean hasMessage(String id)
    {
        return this.messageHeap.containsKey(id);
    }

    synchronized public ServerMessage getMessageById(String id)
    {
        return this.messageHeap.get(id);
    }

    public synchronized void remove(String id)
    {
        this.messageHeap.remove(id);
    }
}