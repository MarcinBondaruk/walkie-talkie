import java.util.Base64;

public class Response
{
    private String result;
    private String message;

    public Response(String result, String message)
    {
        this.result = result;
        this.message = message;
    }

    public boolean result()
    {
        return this.result.equals("success") ? true : false;
    }

    public String message()
    {
        return this.message;
    }

    public String serialize()
    {
        return "" +
            Base64.getEncoder().encodeToString(this.result.getBytes()) +
            "," +
            Base64.getEncoder().encodeToString(this.message.getBytes());
    }
}