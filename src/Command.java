public abstract class Command
{
    protected String type;

    public Command(String type)
    {
        this.type = type;
    }

    public String type()
    {
        return this.type;
    }

    public abstract String serialize();
}