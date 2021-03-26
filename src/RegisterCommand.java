import java.util.Base64;

public class RegisterCommand extends Command
{
    private String username;

    public RegisterCommand(String username) {
        super("REGISTER");
        this.username = username;
    }

    public String username()
    {
        return this.username;
    }

    public String serialize()
    {
        return "" +
            Base64.getEncoder().encodeToString(this.type.getBytes()) +
            "," +
            Base64.getEncoder().encodeToString(this.username.getBytes());
    }
}