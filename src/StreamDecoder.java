import java.util.Base64;

/**
 * helper class for decoding output stream messages.
 *
 * @author Marcin Bondaruk
 */
public abstract class StreamDecoder
{
    public static String decode(String serialized)
    {
        return new String(Base64.getDecoder().decode(serialized));
    }

    public static String[] deserialize(String encodedCommand)
    {
        String[] parts = encodedCommand.split(",");
        String[] decoded = new String[parts.length];

        for (int i = 0; i < parts.length; i++) {
            decoded[i] = StreamDecoder.decode(parts[i]);
        }

        return decoded;
    }
}