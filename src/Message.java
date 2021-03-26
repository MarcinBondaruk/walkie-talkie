public abstract class Message
{
    protected String type;

    public Message(String type)
    {
        this.type = type;
    }

    public String type()
    {
        return this.type;
    }
}