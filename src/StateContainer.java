import java.util.*;
import java.awt.*;

/**
 * Holds Client state.
 * Holds information such as logged user and open conversations along with its window references
 *
 * @author Marcin Bondaruk
 */
public final class StateContainer
{
    private String currentUser = "";
    private HashMap<String, ConversationFrame> activeConversations;

    public StateContainer() {
        this.activeConversations = new HashMap<>();
    }

    public String currentUser()
    {
        return this.currentUser;
    }

    public synchronized void setCurrentUser(String currentUser)
    {
        this.currentUser = currentUser;
    }

    public synchronized boolean isBusy(String recipient)
    {
        return this.activeConversations.containsKey(recipient);
    }

    public synchronized void openConversation(String recipient, ConversationFrame ref)
    {
        this.activeConversations.put(recipient, ref);
    }

    public synchronized ConversationFrame getReference(String recipient)
    {
        return this.activeConversations.get(recipient);
    }

    public synchronized void closeConversation(String recipient)
    {
        this.activeConversations.remove(recipient);
    }
}