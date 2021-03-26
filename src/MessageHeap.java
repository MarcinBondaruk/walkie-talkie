import java.util.HashMap;

public final class MessageHeap
{
    private HashMap<String, ServerMessage> messageHeap;

    public MessageHeap()
    {
        this.messageHeap = new HashMap<>();
    }

    public void add(String id, ServerMessage ServerMessage)
    {
        this.messageHeap.put(id, ServerMessage);
    }

    public boolean hasMessage(String id)
    {
        return this.messageHeap.containsKey(id);
    }

    public ServerMessage getMessageById(String id)
    {
        return this.messageHeap.get(id);
    }

    public void remove(String id)
    {
        this.messageHeap.remove(id);
    }
}