import java.io.*;

public final class ClientController
{
    private ServerCommunicator serverCommunicator;
    private MessageHeap MessageHeap;

    public ClientController(
        ServerCommunicator serverCommunicator,
        MessageHeap MessageHeap
    ) {
        this.serverCommunicator = serverCommunicator;
        this.MessageHeap = MessageHeap;
    }

    public boolean initConnection(String ip, String port)
    {
        return this.serverCommunicator.initConnection(ip, Integer.parseInt(port));
    }

    public boolean registerUser(String user) {
        try {
            ClientMessageRegister clientMsg = new ClientMessageRegister(user);
            this.serverCommunicator.send(clientMsg);
            ServerMessage serverMsg = this.await("REGISTERED");

            return true;
        } catch(MaximumAwaitTimeReached e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public String[] getOnlineUsersList() throws NoResponseException
    {
        try {
            ClientMessageGetOnline getOnlineMsg = new ClientMessageGetOnline();
            this.serverCommunicator.send(getOnlineMsg);
            ServerMessage serverMsg = this.await("ONLINE_LIST");
            return serverMsg.message();
        } catch (MaximumAwaitTimeReached e) {
            throw new NoResponseException();
        } catch (InterruptedException e) {
            throw new NoResponseException();
        }
    }

    public void disconnect(String user)
    {
        ClientMessageDisconnect disconnectMsg = new ClientMessageDisconnect(user);
        this.serverCommunicator.send(disconnectMsg);
        this.serverCommunicator.disconnect();
    }

    public boolean deliverMessage(String from, String to, String contents)
    {
        try {
            String timestamp = Long.toString(System.currentTimeMillis());
            ClientMessageDeliver deliverMsg = new ClientMessageDeliver(from, to, contents, timestamp);
            this.serverCommunicator.send(deliverMsg);
            ServerMessage serverMsg = this.await("DELIVERED_" + timestamp);

            return true;
        } catch (MaximumAwaitTimeReached e) {
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    private ServerMessage await(String type) throws MaximumAwaitTimeReached, InterruptedException
    {
        int tries = 7;
        int sleepTime = 25;
        boolean found = false;
        ServerMessage msg = null;

        while (!found && tries > 0) {
            if (this.MessageHeap.hasMessage(type)) {
                msg = this.MessageHeap.getMessageById(type);
                this.MessageHeap.remove(type);
                found = true;
            }

            tries--;
            Thread.sleep(sleepTime);
            sleepTime += sleepTime;
        }

        if (tries == 0) {
            throw new MaximumAwaitTimeReached();
        }

        return msg;
    }

    private class MaximumAwaitTimeReached extends Exception
    {
        public MaximumAwaitTimeReached()
        {
            super("Maximum await time reached.");
        }
    }
}