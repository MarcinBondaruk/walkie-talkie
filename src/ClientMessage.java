public abstract class ClientMessage extends Message
{
    protected String type;

    public ClientMessage(String type)
    {
        super(type);
    }

    public abstract String serialize();
}