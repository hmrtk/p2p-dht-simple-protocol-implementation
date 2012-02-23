import java.net.*;
import java.io.*;

public class peer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        // creating an instance of peernode object
        PeerNode peer = new PeerNode();
        peer.ProcessFileInputArgs(args);
        
        try {
            serverSocket = new ServerSocket(Integer.parseInt(peer.getPort()));
        } catch (IOException e) {
            System.err.println("Could not listen on port: "+Integer.parseInt(peer.getPort()));
            System.exit(-1);
        }
        //passing the object to the ServerThread object
        while (listening)
        	new ServerThread(serverSocket.accept(), peer).start();

        serverSocket.close();
    }
}