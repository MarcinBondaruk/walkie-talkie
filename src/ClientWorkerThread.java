import java.io.*;

public final class ClientWorkerThread extends Thread
{
    private ServerCommunicator serverCommunicator;
    private MessageHeap messageHeap;

    public ClientWorkerThread(
        ServerCommunicator serverCommunicator,
        MessageHeap messageHeap
    ) {
        this.serverCommunicator = serverCommunicator;
        this.messageHeap = messageHeap;
    }

    public void run()
    {
        while(true) {
            if (this.serverCommunicator.isConnected()) {
                String serializedMsg = this.serverCommunicator.readInputStream();
                String[] msg = StreamDecoder.deserialize(serializedMsg);
                this.messageHeap.add(msg[0], new ServerMessage(msg[0], msg));
            }
        }
    }
}