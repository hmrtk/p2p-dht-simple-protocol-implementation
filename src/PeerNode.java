import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;


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
	private Hashtable<Integer, String> hashtable;
	
	public PeerNode(){
		this.hashtable = new Hashtable<Integer, String>();
	}
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

	/**
	 * @return the hashtable
	 */
	public Hashtable<Integer, String> getHashtable() {
		return hashtable;
	}

	/**
	 * @param hashtable the hashtable to set
	 */
	public void setHashtable(Hashtable<Integer, String> hashtable) {
		this.hashtable = hashtable;
	}

	public String toString(){
		return "Hostname: "+this.Hostname+" Port: "+this.Port+" ID: "+this.ID+" NextHostname: "+this.NextPeerHostName+" NextPort: "+
			this.NextPeerPort+" NextID: "+this.NextPeerID+" MaxID: "+this.MaxId+" isFirstPeer: "+this.firstPeer+" Hashtable: "+this.getHashtable().toString();
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
		if(peerID==null & Message.size()==0){
			return new Request(operation, version, numOfLines);
		}else if(peerID==null & Message.size()>0)
		{
			return new Request(operation, version, numOfLines, Message);
		}
		else
		{
			return new Request(operation, version, numOfLines, peerID, Message);
		}
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
					this.setID(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-H") ){
					this.setHostname(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-P")){
					this.setPort(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-M")){
					this.setMaxId(Integer.parseInt(ArrayCommand[counter+1]));
				}
				else if(strValue.trim().toUpperCase().equals("-R")){
					this.setRedirectHostName(ArrayCommand[counter+1]);
				}
				else if(strValue.trim().toUpperCase().equals("-S")){
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
	 * Protocol processes messages being received and sent in the applciation
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
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.PullQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.PullQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("ADD"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.AddQueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.AddQueryRequestProcess(this.genRequest(message)).toString();
		}
		else if(message.contains("QUERY"))
		{
			String firstWordOfMessage = message.toUpperCase().trim().split("\\s+")[0];
			if(firstWordOfMessage.equals(Settings.Version.toUpperCase()))
				return this.QueryResponseProcess(this.genResponse(message)).toString();
			else
				return this.QueryRequestProcess(this.genRequest(message)).toString();
			
		}
		else if(message.contains("info")){
			 return this.toString();
		}
		return "";
	}

	/**
	 * @param s
	 * @return
	 */
	public int getInt(String s){
		return Integer.parseInt(s);
	}
	
	/**
	 * Client connect to the redirect hostname and port number used mostly in the ID command
	 * @param message
	 */
	public void redirectClient(String message){
		try {
			this.client(this.getRedirectHostName(), getInt(this.getRedirectPort()), message);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 
	 * Client connect to the nexthostname and next port number set in the class
	 * @param message
	 */
	public void nextClient(String message){
		try {
			this.client(this.getNextPeerHostName(), getInt(this.getNextPeerPort()), message);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Processes an ID query response
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
			redirectClient(this.genRequest("ID", 0, this.getID()).toString());
			return this.genRequest("ID", 0, this.getID());
		case 200:
			redirectClient(this.genRequest("NEXT", 0, "0").toString());
			return this.genRequest("NEXT", 0, "0");
		default:
			redirectClient(this.genRequest("ID", 0, this.getID()).toString());
			return this.genRequest("ID", 0, this.getID());
		}
	}

	/**
	 * Processes an ID query request
	 * @param request
	 * @return
	 */
	public Response IDQueryRequestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		if(getInt(this.getID())<getInt(this.NextPeerID) && getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) > getInt(this.getNextPeerID()))
		{
			responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort());
			return new Response(Settings.Version, "ID", "1", "301", "redirect", responseMessage);
		}
		else if(getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) < getInt(this.getNextPeerID()) || 
				getInt(this.getID())>getInt(this.NextPeerID) && getInt(request.getPeerID()) > getInt(this.getID()))
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
		else
		{
			return new Response(Settings.Version, "ID", "0", "503", "UnknownCondition", responseMessage);
		}
	}
	
	/**
	 * Process Next query request
	 * @param request
	 * @return
	 */
	public Response NEXTQueryReuqestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		//check to see if either hostname or port doesnt exist, in either case there is an error
		if(this.getHostname().isEmpty()||this.getPort().isEmpty()){
			return new Response(Settings.Version, "NEXT", "0", "501", "NoNextExist", responseMessage);
		}
		responseMessage.add(this.getNextPeerHostName()+" "+this.getNextPeerPort()+" "+this.getNextPeerID());
		return new Response(Settings.Version, "NEXT", "1", "200", "ok", responseMessage);
	}
	
	/**
	 * Process Next query response
	 * @param response
	 * @return
	 */
	public Request NextQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			String[] msg = response.getMessage().get(0).trim().split("\\s+");
			this.setNextPeerHostName(msg[0]);
			this.setNextPeerPort(msg[1]);
			this.setNextPeerID(msg[2]);
			ArrayList<String> message = new ArrayList<String>();
			message.add(this.getHostname()+" "+this.getPort());
			redirectClient(new Request("PULL", Settings.Version, 1, this.getID(), message).toString());
			return new Request("PULL", Settings.Version, 1, this.getID(), message);
		case 501:
			redirectClient(new Request("NEXT", Settings.Version, 0).toString());
			return new Request("NEXT", Settings.Version, 0);
		}			
		return new Request();
	}
	
	/**
	 * Process Pull query request
	 * @param request
	 * @return
	 */
	public Response PullQueryRequestProcess(Request request){
		String[] msg = request.getMessage().get(0).trim().split("\\s+");
		this.setNextPeerHostName(msg[0]);
		this.setNextPeerPort(msg[1]);
		this.setNextPeerID(request.getPeerID());
		ArrayList<String> responseMessage = new ArrayList<String>();
		responseMessage.add("blah");
		return new Response(Settings.Version, "PULL", "1", "200", "ok", responseMessage);		
	}

	/**
	 * Process pull query response
	 * @param response
	 * @return
	 */
	public Request PullQueryResponseProcess(Response response){
		switch(Integer.parseInt(response.getResponseCode())){
		case 200:
			redirectClient(new Request("DONE", Settings.Version, 0).toString());
			return new Request("DONE", Settings.Version, 0);
		}
		return new Request();
	}
	
    /**
     * Get Ascii value
     * 
     * @param s
     * @return
     */
    public static int getAscii(String s){
    	int result = 0;
    	for (int i=0; i<s.length();i++)
    		result +=(int)s.charAt(i);
    	return result;
    }
    
    /**
     * Process Add query request
     * @param request
     * @return
     */
    public Response AddQueryRequestProcess(Request request){
    	ArrayList<String> responseMessage = new ArrayList<String>();
    	String msg = request.getMessage().get(0).trim();
    	int key = PeerNode.getAscii(msg) % this.getMaxId(); //get the Ascii value and convert it to the range of ids by modula maxId
    	Hashtable<Integer, String> hashtbl = this.getHashtable();
    	if(hashtbl.containsValue(msg)){
    		return new Response(Settings.Version, "ADD", "0", "202", "duplicate", responseMessage);
    	}
    	else if(getInt(this.getID()) <= key && key<getInt(this.getNextPeerID()) || 
    			getInt(this.getID()) <= key && getInt(this.getID()) >getInt(this.getNextPeerID()) ||
				getInt(this.getNextPeerID()) > key && getInt(this.getID()) >getInt(this.getNextPeerID()))
    	{
    		hashtbl.put(key, msg);
    		return new Response(Settings.Version, "ADD", "0", "200", "ok", responseMessage);
    	}
    	else{
    		responseMessage.add(msg);
    		nextClient(new Request("ADD", Settings.Version, 0,this.getID(),responseMessage).toString());
    		return new Response(Settings.Version, "ADD", "0", "400", "NotResponsible", responseMessage);
    	}
    	
    }
    
    /**
     * Process Add query response
     * @param response
     * @return
     */
    public Request AddQueryResponseProcess(Response response){
		return new Request("DONE", Settings.Version, 0);
    }

    /**
     * Process Query request
     * @param request
     * @return
     */
    public Response QueryRequestProcess(Request request){
    	ArrayList<String> responseMessage = new ArrayList<String>();
    	String msg = request.getMessage().get(0).trim();
    	int key = PeerNode.getAscii(msg) % this.getMaxId();
    	Hashtable<Integer, String> hashtbl = this.getHashtable();
    	if(hashtbl.containsValue(msg)){
    		return new Response(Settings.Version, "QUERY", "0", "200", "OK", responseMessage);
    	}
    	else if(getInt(this.getID()) <= key && key<getInt(this.getNextPeerID()) || 
    			getInt(this.getID()) <= key && getInt(this.getID()) >getInt(this.getNextPeerID()) ||
				getInt(this.getNextPeerID()) > key && getInt(this.getID()) >getInt(this.getNextPeerID()))
    	{
    		return new Response(Settings.Version, "ADD", "0", "201", "NotPresent", responseMessage);
    	}
    	else{
    		responseMessage.add(msg);
    		return new Response(Settings.Version, "ADD", "0", "400", "NotResponsible", responseMessage);
    	}
    	
    }
    
    /**
     * Process query response
     * @param response
     * @return
     */
    public Request QueryResponseProcess(Response response){
		return new Request("DONE", Settings.Version, 0);
    }
    
    /**
     * Client - open a socket to the host name and port provided and send the command
     * @param hostname
     * @param port
     * @param command
     * @throws IOException
     */
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

        out.println(command);
        if ((fromServer = in.readLine()) != null) {
	    	// process the message received from server in through the protocol method
	    	this.Protocol(fromServer);
            System.out.println(this.toString());
        }
        out.close();
        in.close();
        stdIn.close();
        kkSocket.close();
    }
    
    /**
     * This the first method called from peer for searching for next Peer if its not firt peer
     */
    public void run(){
    	//Generate Client request if and only if its not the first peer in
    	if(!isFirstPeer()){
        	// Starting the program by asking the other hostname and port that is set through the arguments using ID Query
        	String StartReuqest = this.genRequest("ID", 0, this.getID()).toString();
        	try {
        		this.client(this.getRedirectHostName(), Integer.parseInt(getRedirectPort()), StartReuqest);
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}    		
    	}
    }
}

