import java.io.*;
import java.util.Arrays;

/**
 * Constantly reads socket output stream. If read message is a message delivery,
 * delivers it ad-hoc, discards message if conversations is not open.
 * Puts all other messages on message heap.
 *
 * @author Marcin Bondaruk
 */
public final class ClientWorkerThread extends Thread
{
    private ServerCommunicator serverCommunicator;
    private MessageHeap messageHeap;
    private StateContainer stateContainer;

    public ClientWorkerThread(
        ServerCommunicator serverCommunicator,
        MessageHeap messageHeap,
        StateContainer stateContainer
    ) {
        this.serverCommunicator = serverCommunicator;
        this.messageHeap = messageHeap;
        this.stateContainer = stateContainer;
    }

    public void run()
    {
        while(!interrupted()) {
            if (this.serverCommunicator.isConnected()) {
                String serializedMsg = this.serverCommunicator.readInputStream();
                if (serializedMsg != null && !serializedMsg.isEmpty()) {
                    String[] msg = StreamDecoder.deserialize(serializedMsg);
                    if ("MESSAGE_FROM".equals(msg[0])) {
                        this.deliverMessage(msg);

                    } else {
                        this.messageHeap.add(msg[0], new ServerMessage(msg[0], msg[1]));
                    }
                }
            }
        }
    }

    private void deliverMessage(String[] msg)
    {
        String[] msgContents = msg[1].split(",");
        if (this.stateContainer.isBusy(msgContents[0])) {
            ConversationFrame convFrame = this.stateContainer.getReference(msgContents[0]);
            convFrame.postMessage(msgContents[0], msgContents[2]);
        }
    }
}