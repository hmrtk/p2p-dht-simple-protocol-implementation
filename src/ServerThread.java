/**
 * Class: ServerThread
 * @author Hamidreza Tavakoli
 * Last revision: Feb 22, 2012 3:53:50 PM
 */
/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket = null;
    private PeerNode peer;

    public ServerThread(Socket socket, PeerNode peer) {
		super("ServerThread");
		this.socket = socket;
		this.peer = peer;
    }
    public static String processInput(String s){
    	return s;
    }
    
    /**
     * Logs the input and output of the server
     * @param text
     */
    public static void log(String text){
    	try {
    		PrintStream out = new PrintStream(new FileOutputStream("server.p2plog", true)); 
			out.println(text);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void run() {

		try {
		    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		    BufferedReader in = new BufferedReader(
					    new InputStreamReader(
					    socket.getInputStream()));
	
		    String inputLine, outputLine;
	
		    log("----------------> peer "+peer.getID()+" server loging <----------------");
		    while ((inputLine = in.readLine()) != null) {	
				log("[Received -"+" PeerID: "+peer.getID()+" Portnum: "+peer.getPort()+"] "+inputLine);
				outputLine = this.peer.Protocol(inputLine).toString();
				log("[Sent -"+" PeerID: "+peer.getID()+" Portnum: "+peer.getPort()+"]     "+outputLine);
				out.println(outputLine);
				if (outputLine.equals("Bye"))
				    break;
				System.out.println(peer.toString());
		    }
		    out.close();
		    in.close();
		    socket.close();
	
		} catch (IOException e) {
		    e.printStackTrace();
		}
    }
}