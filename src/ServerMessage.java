public class ServerMessage extends Message
{
    private String type;
    private String[] message;

    public ServerMessage(String type, String[] message)
    {
        super(type);
        this.message = message;
    }

    public String[] message()
    {
        return this.message;
    }
}