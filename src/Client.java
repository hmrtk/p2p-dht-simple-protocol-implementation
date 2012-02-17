import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This code has been adapted from:
 * http://download.oracle.com/javase/tutorial/networking/sockets/readingWriting.html
 */
public class Client {
	public static void main(String args[]) throws IOException {
		Socket echoSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
                        /* Bluenose defaults to trying to use IPv6, which isn't
working well right now and blocks the Socket call.  This next line tells J
ava to us IPv4 like the rest of the world. */

			System.setProperty("java.net.preferIPv4Stack", "true");

			echoSocket = new Socket("bluenose.cs.dal.ca", 20112);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
		} catch (IOException e) {
			System.err.println("Couldn't open socket for the connection.");
			System.exit(1);
		}

		out.println("Hello from Java");
		System.out.println("echo: " + in.readLine());

		out.close();
		in.close();
		echoSocket.close();
	}
}
