import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.omg.CORBA.VersionSpecHelper;


/**
 * Class: Peer
 * @author Hamidreza Tavakoli
 * Last revision: Feb 17, 2012 3:56:44 PM
 */
public class PeerNode {

	private String NextPeerID;
	private String ID;
	private String Port;
	private String Hostname;
	private String NextPeerHostName;
	private String NextPeerPort;
	private String RedirectHostName;
	private String RedirectPort;
	private ArrayList<StringHash> StringHashArraylist;
	private int MaxId;
	private boolean firstPeer = false;
	
	
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the port
	 */
	public String getPort() {
		return Port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(String port) {
		Port = port;
	}


	/**
	 * @return the maxId
	 */
	public int getMaxId() {
		return MaxId;
	}

	/**
	 * @param maxId the maxId to set
	 */
	public void setMaxId(int maxId) {
		MaxId = maxId;
	}

	/**
	 * @return the stringHashArraylist
	 */
	public ArrayList<StringHash> getStringHashArraylist() {
		return StringHashArraylist;
	}

	/**
	 * @param stringHashArraylist the stringHashArraylist to set
	 */
	public void setStringHashArraylist(ArrayList<StringHash> stringHashArraylist) {
		StringHashArraylist = stringHashArraylist;
	}
	/**
	 * @return the nextPeerID
	 */
	public String getNextPeerID() {
		return NextPeerID;
	}

	/**
	 * @param nextPeerID the nextPeerID to set
	 */
	public void setNextPeerID(String nextPeerID) {
		NextPeerID = nextPeerID;
	}

	/**
	 * @return the firstPeer
	 */
	public boolean isFirstPeer() {
		return firstPeer;
	}

	/**
	 * @param firstPeer the firstPeer to set
	 */
	public void setFirstPeer(boolean firstPeer) {
		this.firstPeer = firstPeer;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return Hostname;
	}

	/**
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		Hostname = hostname;
	}
	/**
	 * @return the NextPeerHostName
	 */
	public String getNextPeerHostName() {
		return NextPeerHostName;
	}

	/**
	 * @param NextPeerHostName the NextPeerHostName to set
	 */
	public void setNextPeerHostName(String NextPeerHostName) {
		this.NextPeerHostName = NextPeerHostName;
	}

	/**
	 * @return the NextPeerPort
	 */
	public String getNextPeerPort() {
		return NextPeerPort;
	}

	/**
	 * @param NextPeerPort the NextPeerPort to set
	 */
	public void setNextPeerPort(String NextPeerPort) {
		this.NextPeerPort = NextPeerPort;
	}
	
	/**
	 * @return the redirectHostName
	 */
	public String getRedirectHostName() {
		return RedirectHostName;
	}

	/**
	 * @param redirectHostName the redirectHostName to set
	 */
	public void setRedirectHostName(String redirectHostName) {
		RedirectHostName = redirectHostName;
	}

	/**
	 * @return the redirectPort
	 */
	public String getRedirectPort() {
		return RedirectPort;
	}

	/**
	 * @param redirectPort the redirectPort to set
	 */
	public void setRedirectPort(String redirectPort) {
		RedirectPort = redirectPort;
	}

	public String toString(){
		return "Hostname: "+this.Hostname+" Port: "+this.Port+" ID: "+this.ID+" NextHostname: "+this.NextPeerHostName+" NextPort: "+
			this.NextPeerPort+" NextID: "+this.NextPeerID+" MaxID: "+this.MaxId+" isFirstPeer: "+this.firstPeer;
	}

	/**
	 * Generates a Request Object
	 * @param operation
	 * @param numOfLines
	 * @param peerID
	 * @return
	 */
	public Request genRequest(String operation, int numOfLines, String peerID){
		if(peerID==null){
			return new Request(operation, Settings.Version, numOfLines);
		}
		return new Request(operation, Settings.Version, numOfLines, peerID);
	}
	public Request genRequest(String message){
		String[] ArrayMessage = message.trim().split("CRLF");
		String version = null;
		String operation = null;
		int numOfLines = 0;
		String peerID = null;
		ArrayList<String> Message = new ArrayList<String>();
		for(String strValue : ArrayMessage){
			if(strValue.toUpperCase().contains(Settings.Version.toUpperCase())){
				String[] wordsInLine = strValue.trim().split("\\s+");
				operation = wordsInLine[0];
				version = wordsInLine[1];
				numOfLines = Integer.parseInt(wordsInLine[2]);
				if(wordsInLine.length==4){
					peerID = wordsInLine[3];	
				}
			}
			else{
				Message.add(strValue);
			}
		}
		if(peerID==null){
			return new Request(operation, version, numOfLines);
		}
		return new Request(operation, version, numOfLines, peerID, Message);
	}	
	/**
	 * Generates a Response object
	 * @param operation
	 * @param numOfLines
	 * @param responseCode
	 * @param responseCodestr
	 * @param message
	 * @return
	 */
	public Response genResponse(String message){
		
		String[] ArrayMessage = message.trim().split("CRLF");
		String version = null;
		String operation = null;
		String numOfLines = null;
		String responseCode = null;
		String responseCodestr = null;
		ArrayList<String> responseMessage = new ArrayList<String>();
		for(String strValue : ArrayMessage){
			if(strValue.contains(Settings.Version)){
				String[] wordsInLine = strValue.trim().split("\\s+");
				version = wordsInLine[0];
				operation = wordsInLine[1];
				numOfLines = wordsInLine[2];
				responseCode = wordsInLine[3];
				responseCodestr = wordsInLine[4];
			}
			else{
				responseMessage.add(strValue);
			}
		}
		
		return new Response(version, operation, numOfLines, responseCode, responseCodestr, responseMessage);
	}	
	/**
	 * Processes the command sent from Main
	 * @param command
	 */
	public void ProcessFileInputArgs(String[] ArrayCommand)
	{
		int counter = 0;
		for(String strValue : ArrayCommand)
		{
			if(ArrayCommand.length>counter+1){
				
				if(strValue.trim().toUpperCase().equals("-I")){
//					System.out.println("-i "+ ArrayCommand[counter+1]);
					this.setID(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-H") ){
//					System.out.println("-h "+ ArrayCommand[counter+1]); 
					this.setHostname(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-P")){
//					System.out.println("-p "+ ArrayCommand[counter+1]); 
					this.setPort(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-M")){
//					System.out.println("-m "+ ArrayCommand[counter+1]); 
					this.setMaxId(Integer.parseInt(ArrayCommand[counter+1]));
				}
				else if(strValue.trim().toUpperCase().equals("-R")){
//					System.out.println("-r "+ ArrayCommand[counter+1]); 
					this.setRedirectHostName(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-S")){
//					System.out.println("-s "+ ArrayCommand[counter+1]); 
					this.setRedirectPort(ArrayCommand[counter+1]);
				}
			}
			else if(strValue.trim().toUpperCase().equals("-F"))
			{
				this.setFirstPeer(true);
			}
			counter ++;
		}
		if(this.firstPeer && !this.Hostname.equals(null) && !this.Port.equals(null)){
			this.setNextPeerHostName(this.Hostname);
			this.setNextPeerPort(this.Port);
			this.NextPeerID = this.ID;
		}
	}
	
	/**
	 * 
	 */
	public void SendMessage(){
		if(this.isFirstPeer()){
			
		}
		else{
			System.out.println( this.genRequest("ID", 0, this.getID()));
		}
	}

	/**
	 * @param message
	 */
	public String Protocol(String message){
		if(message.contains("ID"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.IDQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.IDQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("NEXT"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.NextQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.NEXTQueryReuqestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("PULL"))
		{
			
		}
		else if(message.contains("ADD"))
		{
			
		}
		else if(message.contains("DELETE"))
		{
			
		}
		else if(message.contains("QUERY"))
		{
			
		}
		else if(message.contains("DONE")){
			
		}
		else if(message.contains("info")){
			 return this.toString();
		}
		return "";
//		System.out.println(this.genResponse(message));
//		System.out.println(this.IDQueryResponseProcess(this.genResponse(message)));
	}

	/**
	 * @param s
	 * @return
	 */
	public int getInt(String s){
		return Integer.parseInt(s);
	}
	
	/**
	 * @param response
	 * @return
	 */
	public Request IDQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 301:
			//interpret first element of the arraylist containing the message to get the information for next hostname and port number
			String[] msg = response.getMessage().get(0).trim().split("\\s+"); 
			this.setRedirectHostName(msg[0]);
			this.setRedirectPort(msg[1]);
			return this.genRequest("ID", 0, this.getID());
		case 200:
			return this.genRequest("NEXT", 0, "0");
		default:
			return this.genRequest("ID", 0, this.getID());
		}
	}

	/**
	 * @param request
	 * @return
	 */
	public Response IDQueryRequestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		if(getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) > getInt(this.getNextPeerID()))
		{
			responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort());
			return new Response(Settings.Version, "ID", "1", "301", "redirect", responseMessage);
		}
		else if(getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) < getInt(this.getNextPeerID()))
		{
			return new Response(Settings.Version, "ID", "0", "200", "ok", responseMessage);
		}
		else if(this.getID() == this.NextPeerID)
		{
			if(getInt(this.getID())>getInt(request.getPeerID())){
				return new Response(Settings.Version, "ID", "0", "200", "ok", responseMessage);
			}
			else
			{
				//This condition requires more coding time
				return NEXTQueryReuqestProcess(request);
			}
		}
		else if(getInt(request.getPeerID()) == getInt(this.getID())){
			return new Response(Settings.Version, "ID", "0", "400", "peerexist", responseMessage);
		}
		else if(!request.getVersion().trim().equals(Settings.Version.trim())){
			return new Response(Settings.Version, "ID", "0", "401", "versionError", responseMessage);
		}
		return new Response(Settings.Version, "ID", "0", "401", "versionError", responseMessage);
	}
	
	/**
	 * @param request
	 * @return
	 */
	public Response NEXTQueryReuqestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		//check to see if either hostname or port doesnt exist, in either case there is an error
		if(this.getHostname().isEmpty()||this.getPort().isEmpty()){
			return new Response(Settings.Version, "NEXT", "0", "501", "NoNextExist", responseMessage);
		}
		responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort()+" "+this.getID());
		return new Response(Settings.Version, "NEXT", "1", "200", "ok", responseMessage);
	}
	
	/**
	 * @param response
	 * @return
	 */
	public Request NextQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			String[] msg = response.getMessage().get(0).trim().split("\\s+");
			this.setNextPeerHostName(msg[0]);
			this.setNextPeerID(msg[1]);
			this.setNextPeerID(msg[2]);
			ArrayList<String> message = new ArrayList<String>();
			message.add(this.getHostname()+" "+this.getPort());
			return new Request("PULL", Settings.Version, 1, this.getID(), message);
		case 501:
			return new Request("NEXT", Settings.Version, 0);
		}			
		return new Request();
	}
	
	public Response PullQueryRequestProcess(Request request){
		String[] msg = request.getMessage().get(0).trim().split("\\s+");
		this.setNextPeerHostName(msg[0]);
		this.setNextPeerPort(msg[1]);
		this.setNextPeerID(request.getPeerID());
		ArrayList<String> responseMessage = new ArrayList<String>();
		//responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort()+" "+this.getID());
		return new Response(Settings.Version, "NEXT", "1", "200", "ok", responseMessage);		
	}

	public Request PullQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			return new Request("DONE", Settings.Version, 0);
		}
		return new Request();
	}
	
    public void client(String hostname, int port, String command) throws IOException {

        Socket kkSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
			System.setProperty("java.net.preferIPv4Stack", "true");
            kkSocket = new Socket(hostname, port);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: taranis.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: taranis.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String fromServer;
        String fromUser;

//        while (true) {
//		    if ((fromUser = stdIn.readLine()) != null) {
//	                System.out.println(fromUser);
//	                out.println(fromUser);
//		    }
//		    if((fromServer = in.readLine()) != null){
//	        	System.out.println(fromServer);
//	            if (fromServer.equals("Bye."))
//	                break;
//		    }
//        }
        
        out.println(command);
        while ((fromServer = in.readLine()) != null) {
		    if((fromServer = in.readLine()) != null){
		    	// process the message received from server in through the protocol method
		    	String clientMsg = this.Protocol(fromServer);
		    	if(!clientMsg.trim().isEmpty())
		    		//pass the message to the server for processing
		    		out.println(clientMsg);
	            if (fromServer.equals("Bye."))
	                break;
		    }
        }
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
    
    public void run(){
    	//Generate Client request if and only if its not the first peer in
    	if(!isFirstPeer()){
        	// Starting the program by asking the other hostname and port that is set through the arguments using ID Query
        	String StartReuqest = this.genRequest("ID", 0, this.getID()).toString();
        	try {
    			this.client(this.getRedirectHostName(), Integer.parseInt(getRedirectPort()), StartReuqest);
    		} catch (NumberFormatException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}    		
    	}
    }
	public static void main(String[] args) 
	{
		PeerNode tmp = new PeerNode();
		tmp.ProcessFileInputArgs(args);
		//tmp.SendMessage();
		System.out.println(tmp.Protocol("3171_a3/1.0 ID 1 301 redirectCRLF reddwarf.cs.dal.ca 3000 CRLF"));
//		System.out.println(tmp.getNextPeerHostName());
//		System.out.println(tmp.getNextPeerPort());
//		tmp.setNextPeerID("29");
//
//		tmp.setID("29");
//		System.out.println(tmp.genRequest("ID 3171_A3/1.0 0 29CRLF"));
//		tmp.IDQueryRequestProcess(tmp.genRequest("ID 3171_A3/1.0 0 29CRLF"));
	}

}

