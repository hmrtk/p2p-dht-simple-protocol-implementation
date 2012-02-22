import java.util.ArrayList;


/**
 * Class: Peer
 * @author Hamidreza Tavakoli
 * Last revision: Feb 17, 2012 3:56:44 PM
 */
public class peer {

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

	/**
	 * Processes the command sent from Main
	 * @param command
	 */
	public void ProcessCommand(String[] ArrayCommand)
	{
		int counter = 0;
		for(String strValue : ArrayCommand)
		{
			if(ArrayCommand.length>counter+1){
				
				if(strValue.toUpperCase().equals("-I")){
					System.out.println("-i "+ ArrayCommand[counter+1]);
					this.setID(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-H") ){
					System.out.println("-h "+ ArrayCommand[counter+1]); 
					this.setHostname(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-P")){
					System.out.println("-p "+ ArrayCommand[counter+1]); 
					this.setPort(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-M")){
					System.out.println("-m "+ ArrayCommand[counter+1]); 
					this.setMaxId(Integer.parseInt(ArrayCommand[counter+1]));
				}
				else if(strValue.toUpperCase().equals("-R")){
					System.out.println("-r "+ ArrayCommand[counter+1]); 
					this.setOtherPeerHostName(ArrayCommand[counter+1]);
				}
				else if(strValue.toUpperCase().equals("-S")){
					System.out.println("-s "+ ArrayCommand[counter+1]); 
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


	
	public static void main(String[] args) 
	{
		peer tmp = new peer();
		tmp.ProcessCommand(args);
	}

}

