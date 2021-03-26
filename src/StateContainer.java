import java.util.*;

public final class StateContainer
{
    private String currentUser;
    private Set<String> activeConversations;

    public StateContainer() {
        this.activeConversations = new HashSet<>();
    }

    public String currentUser()
    {
        return this.currentUser;
    }

    public void setCurrentUser(String currentUser)
    {
        this.currentUser = currentUser;
    }

    public boolean openConversation(String recipient)
    {
        if (!this.activeConversations.contains(recipient)) {
            this.activeConversations.add(recipient);
            return true;
        }

        return false;
    }

    public void closeConversation(String recipient)
    {
        this.activeConversations.remove(recipient);
    }
}