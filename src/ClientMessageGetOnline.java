import java.util.Base64;

public final class ClientMessageGetOnline extends ClientMessage
{
    public ClientMessageGetOnline()
    {
        super("GET_ONLINE");
    }

    public String serialize()
    {
        return Base64.getEncoder().encodeToString(this.type.getBytes());
    }
}