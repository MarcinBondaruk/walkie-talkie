import java.util.Base64;

public abstract class CommandDecoder
{
    public static String decode(String serialized)
    {
        return new String(Base64.getDecoder().decode(serialized));
    }

    public static String[] deserialize(String encodedCommand)
    {;
        String[] parts = encodedCommand.split(",");
        String[] decoded = new String[parts.length];

        for (int i = 0; i < parts.length; i++) {
            decoded[i] = CommandDecoder.decode(parts[i]);
        }

        return decoded;
    }
}