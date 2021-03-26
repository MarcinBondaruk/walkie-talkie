import java.util.Base64;

public class ServerMessage extends Message
{
    private String message;

    public ServerMessage(String type, String message)
    {
        super(type);
        this.message = message;
    }

    public String message()
    {
        return this.message;
    }

    public String serialize()
    {
        return String.join(
            ",",
            Base64.getEncoder().encodeToString(this.type.getBytes()),
            Base64.getEncoder().encodeToString(this.message.getBytes())
        );
    }
}