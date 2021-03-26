import java.util.Base64;

public final class ClientMessageRegister extends ClientMessage
{
    private String username;

    public ClientMessageRegister(String username)
    {
        super("REGISTER");
        this.username = username;
    }

    public String username()
    {
        return this.username;
    }

    public String serialize()
    {
        return String.join(
            ",",
            Base64.getEncoder().encodeToString(this.type.getBytes()),
            Base64.getEncoder().encodeToString(this.username.getBytes())
        );
    }
}