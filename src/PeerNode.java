import java.util.ArrayList;


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
	private String OtherPeerHostName;
	private String OtherPeerPort;
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
	 * @return the otherPeerHostName
	 */
	public String getOtherPeerHostName() {
		return OtherPeerHostName;
	}

	/**
	 * @param otherPeerHostName the otherPeerHostName to set
	 */
	public void setOtherPeerHostName(String otherPeerHostName) {
		OtherPeerHostName = otherPeerHostName;
	}

	/**
	 * @return the otherPeerPort
	 */
	public String getOtherPeerPort() {
		return OtherPeerPort;
	}

	/**
	 * @param otherPeerPort the otherPeerPort to set
	 */
	public void setOtherPeerPort(String otherPeerPort) {
		OtherPeerPort = otherPeerPort;
	}
	
	public String toString(){
		return "Hostname: "+this.Hostname+" Port: "+this.Port+" ID: "+this.ID+" NextHostname: "+this.OtherPeerHostName+" NextPort: "+
			this.OtherPeerPort+" NextID: "+this.NextPeerID+" MaxID: "+this.MaxId+" isFirstPeer: "+this.firstPeer;
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
		ArrayList<String> responseMessage = new ArrayList<String>();
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
				responseMessage.add(strValue);
			}
		}
		if(peerID==null){
			return new Request(operation, version, numOfLines);
		}
		return new Request(operation, version, numOfLines, peerID);
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
				
				if(strValue.toUpperCase().equals("-I")){
//					System.out.println("-i "+ ArrayCommand[counter+1]);
					this.setID(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-H") ){
//					System.out.println("-h "+ ArrayCommand[counter+1]); 
					this.setHostname(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-P")){
//					System.out.println("-p "+ ArrayCommand[counter+1]); 
					this.setPort(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-M")){
//					System.out.println("-m "+ ArrayCommand[counter+1]); 
					this.setMaxId(Integer.parseInt(ArrayCommand[counter+1]));
				}
				else if(strValue.toUpperCase().equals("-R")){
//					System.out.println("-r "+ ArrayCommand[counter+1]); 
					this.setOtherPeerHostName(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-S")){
//					System.out.println("-s "+ ArrayCommand[counter+1]); 
					this.setOtherPeerPort(ArrayCommand[counter+1]);
				}
			}
			else if(strValue.toUpperCase().equals("-I"))
			{
				this.setFirstPeer(true);
			}
			counter ++;
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
			{
				return this.IDQueryResponseProcess(this.genResponse(message)).toString();
			}
			else
			{
				return this.IDQueryRequestProcess(this.genRequest(message)).toString();
			}	
		}
		else if(message.contains("NEXT"))
		{
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
			this.setOtherPeerHostName(msg[0]);
			this.setOtherPeerPort(msg[1]);
			return this.genRequest("ID", 0, this.getID());
		case 200:
			return this.genRequest("NEXT", 0, null);
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
		if(getInt(request.getPeerID()) > getInt(this.getID()) && getInt(request.getPeerID()) < getInt(this.getNextPeerID()))
		{
			responseMessage.add(this.getOtherPeerHostName()+" "+this.getOtherPeerPort());
			return new Response(Settings.Version, "ID", "1", "301", "redirect", responseMessage);
		}
		else{
			return new Response(Settings.Version, "ID", "0", "200", "ok", responseMessage);
		}
	}
	
	/**
	 * @param request
	 * @return
	 */
	public Response NEXTQueryReuqestProcess(Request request){
		ArrayList<String> responseMessage = new ArrayList<String>();
		responseMessage.add(this.getOtherPeerHostName()+" "+this.getOtherPeerPort()+" "+this.getID());
		return new Response(Settings.Version, "NEXT", "1", "200", "ok", responseMessage);
	}
	
	
	public static void main(String[] args) 
	{
		PeerNode tmp = new PeerNode();
		tmp.ProcessFileInputArgs(args);
		//tmp.SendMessage();
		System.out.println(tmp.Protocol("3171_a3/1.0 ID 1 301 redirectCRLF reddwarf.cs.dal.ca 3000 CRLF"));
//		System.out.println(tmp.getOtherPeerHostName());
//		System.out.println(tmp.getOtherPeerPort());
//		tmp.setNextPeerID("29");
//
//		tmp.setID("29");
//		System.out.println(tmp.genRequest("ID 3171_A3/1.0 0 29CRLF"));
//		tmp.IDQueryRequestProcess(tmp.genRequest("ID 3171_A3/1.0 0 29CRLF"));
		
		
		
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("ID 3171_A3/1.0 0 29CRLF");
		commands.add("3171_a3/1.0 ID 1 301 redirectCRLF reddwarf.cs.dal.ca 3000CRLF");
		commands.add("ID 3171_a3/1.0 0 29CRLF");
		commands.add("3171_a3/1.0 ID 1 301 redirectCRLF hector.cs.dal.ca 1024CRLF");
		commands.add("ID 3171_a3/1.0 0 29CRLF");
		commands.add("3171_a3/1.0 ID 0 200 okCRLF");
		commands.add("NEXT 3171_a3/1.0 0CRLF");
		commands.add("3171_a3/1.0 NEXT 1 200 okCRLF bluenose.cs.dal.ca 2100 7CRLF");
		commands.add("PULL 3171_a3/1.0 1 29CRLF newbie.cs.dal.ca 2112CRLF");
		commands.add("3171_a3/1.0 PULL 2 200 okCRLF jumps over theCRLF lazy dogCRLF");
		commands.add("DONE 3171_a3/1.0 0CRLF");
		
	}

}

