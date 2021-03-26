import java.util.Base64;

public class GetOnlineCommand extends Command
{
    private String username;

    public GetOnlineCommand() {
        super("GET_ONLINE");
    }

    public String serialize()
    {
        return "" +
            Base64.getEncoder().encodeToString(this.type.getBytes());
    }
}