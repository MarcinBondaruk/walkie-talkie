public abstract class ClientMessage extends Message
{
    public ClientMessage(String type)
    {
        super(type);
    }

    public abstract String serialize();
}