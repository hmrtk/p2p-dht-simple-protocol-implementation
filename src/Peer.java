import java.util.ArrayList;


/**
 * Class: Peer
 * @author Hamidreza Tavakoli
 * Last revision: Feb 17, 2012 3:56:44 PM
 */
public class Peer {

	private Peer NextPeer;
	private int ID;
	private int Port;
	private String Hostname;
	private ArrayList<Hash> Hashtable;
	private int MaxId;
	
	
	/**
	 * Constructor for Peer
	 */
	public Peer(){
		
	}

	/**
	 * @return the nextPeer
	 */
	public Peer getNextPeer() {
		return NextPeer;
	}

	/**
	 * @param nextPeer the nextPeer to set
	 */
	public void setNextPeer(Peer nextPeer) {
		NextPeer = nextPeer;
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
	 * @param hostname the hostname to set
	 */
	public void setHostname(String hostname) {
		Hostname = hostname;
	}

	/**
	 * @return the hashtable
	 */
	public ArrayList<Hash> getHashtable() {
		return Hashtable;
	}

	/**
	 * @param hashtable the hashtable to set
	 */
	public void setHashtable(ArrayList<Hash> hashtable) {
		Hashtable = hashtable;
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
	
	
}

