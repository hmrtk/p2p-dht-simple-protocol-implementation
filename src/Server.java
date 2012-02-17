import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This code has been adapted from
 * http://download.oracle.com/javase/tutorial/networking/sockets/clientServer.html
 */

public class Server {
	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = null;

		try {
			/* Bluenose defaults to trying to use IPv6, which isn't working well right now and blocks the ServerSocket call.  This next line tells Java to us IPv4 like the rest of the world. */

			System.setProperty("java.net.preferIPv4Stack", "true");

			serverSocket = new ServerSocket(20112, 1); // no backlog
		} catch (IOException e) {
			System.out.println("Could not listen on port: " + 20112);
			System.exit(-1);
		}

		Socket clientSocket = null;

		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("Accept failed: " + 20112);
				System.exit(-1);
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			String userInput;

			userInput = in.readLine();
			if (userInput != null) {
				out.println(userInput);
			}
			out.close();
			in.close();
			clientSocket.close();
		}

		/* Java doesn't like unreachable code.  The line below should appear somewhere, but, since the infinite while loop never break, we'll comment it out to keep the compiler happy. */

		/*  serverSocket.close();  */
	}
}
