import java.net.*;
import java.io.*;

public class peer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        boolean listening = true;
        // creating an instance of peernode object
        PeerNode peer = new PeerNode();
        if(args.length>0)
        {
            peer.ProcessFileInputArgs(args);
            peer.run();
            
            try {
                serverSocket = new ServerSocket(Integer.parseInt(peer.getPort()));
    			System.out.println("Server "+peer.getID()+" Started!");
            } catch (IOException e) {
                System.err.println("Could not listen on port: "+Integer.parseInt(peer.getPort()));
                System.exit(-1);
            }
            //passing the object to the ServerThread object
            while (listening)
            	new ServerThread(serverSocket.accept(), peer).start();

            serverSocket.close();
        }
        else
        {
        	System.out.println("This program requires switches in the command line. i.e java peer -i 10 -h ubuntu -p 2113 -m 32 -r ubuntu -s 2112");
        }

    }
}