public class User
{
    protected String username;
    protected String ip;

    public User(String username, String ip)
    {
        this.username = username;
        this.ip = ip;
    }

    public String username()
    {
        return this.username;
    }

    public String ip()
    {
        return this.ip;
    }
}