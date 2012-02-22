import java.util.ArrayList;


/**
 * Class: Peer
 * @author Hamidreza Tavakoli
 * Last revision: Feb 17, 2012 3:56:44 PM
 */
public class Peer {

	private int NextPeerID;
	private int ID;
	private int Port;
	private String Hostname;
	private ArrayList<StringHash> StringHashArraylist;
	private int MaxId;
	
	
	/**
	 * Constructor for Peer
	 */
	public Peer(){
		
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return Port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		Port = port;
	}

	/**
	 * @return the hostname
	 */
	public String getHostname() {
		return Hostname;
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
	public int getNextPeerID() {
		return NextPeerID;
	}

	/**
	 * @param nextPeerID the nextPeerID to set
	 */
	public void setNextPeerID(int nextPeerID) {
		NextPeerID = nextPeerID;
	}
	/**
	 * Processes the command sent from Main
	 * @param command
	 */
	public void ProcessCommand(String command)
	{
		String[] ArrayCommand = command.split("\\s+");
		
		int counter = 0;
		for(String strValue : ArrayCommand)
		{
			if(ArrayCommand.length>counter+1){
				
				if(strValue.toUpperCase().equals("-I")){
					System.out.println("-i "+ ArrayCommand[counter+1]); 
				}
				else if(strValue.toUpperCase().equals("-H") ){
					System.out.println("-h "+ ArrayCommand[counter+1]); 
				}
				else if(strValue.toUpperCase().equals("-P")){
					System.out.println("-p "+ ArrayCommand[counter+1]); 
				}
				else if(strValue.toUpperCase().equals("-M")){
					System.out.println("-m "+ ArrayCommand[counter+1]); 
				}
			}
			counter ++;
		}
	}

	/**
	 * Request template
	 * 
	 * @param operation
	 * @param version
	 * @param numOfLines
	 * @param peerID
	 * @return String
	 */
	public String Request(String operation, String version, int numOfLines, int peerID)
	{
		return operation+" "+version+" "+numOfLines+" "+peerID+"CRLF";
	}
	
	/**
	 * Response Template 
	 * 
	 * @param version
	 * @param operation
	 * @param numOfLines
	 * @param responseCode
	 * @param responseCodeStr
	 * @param message
	 * @return string
	 */
	public String Response(String version, String operation, String numOfLines, int responseCode, String responseCodeStr, String[] message)
	{
		if(message.length>0)
		{
			return "";
		}
		else{
			return version+" "+operation+" "+numOfLines+" "+responseCode+" "+responseCodeStr+"CRLF";
		} 
	}
	
	
	
}

