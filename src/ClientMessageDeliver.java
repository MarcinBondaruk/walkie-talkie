import java.util.Base64;

public final class ClientMessageDeliver extends ClientMessage
{
    private String from;
    private String to;
    private String contents;
    private String timestamp;

    public ClientMessageDeliver(
        String from,
        String to,
        String contents,
        String timestamp
    ) {
        super("DELIVER");
        this.from = from;
        this.to = to;
        this.contents = contents;
        this.timestamp = timestamp;
    }

    public String serialize()
    {
        return String.join(
            ",",
            Base64.getEncoder().encodeToString(this.type.getBytes()),
            Base64.getEncoder().encodeToString(this.from.getBytes()),
            Base64.getEncoder().encodeToString(this.to.getBytes()),
            Base64.getEncoder().encodeToString(this.contents.getBytes()),
            Base64.getEncoder().encodeToString(this.timestamp.getBytes())
        );
    }
}