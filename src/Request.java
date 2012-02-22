import java.util.ArrayList;

/**
 * 
 */

/**
 * Class: Request
 * @author Hamidreza Tavakoli
 * Last revision: Feb 21, 2012 10:41:16 PM
 */
public class Request {
	private String operation;
	private String version;
	private int numOfLines;
	private String peerID;
	private ArrayList<String> message;
	
	public Request() {
		
	}
	public Request(String operation, String version, int numOfLines) {
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.message = new ArrayList<String>();
	}	
	public Request(String operation, String version, int numOfLines, String peerID){
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.peerID = peerID;
		this.message = new ArrayList<String>();
	}
	public Request(String operation, String version, int numOfLines, String peerID, ArrayList<String> responseMessage){
		this.operation = operation;
		this.version = version;
		this.numOfLines = numOfLines;
		this.peerID = peerID;
		this.message = responseMessage;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the numOfLines
	 */
	public int getNumOfLines() {
		return numOfLines;
	}

	/**
	 * @param numOfLines the numOfLines to set
	 */
	public void setNumOfLines(int numOfLines) {
		this.numOfLines = numOfLines;
	}

	/**
	 * @return the peerID
	 */
	public String getPeerID() {
		return peerID;
	}

	/**
	 * @param peerID the peerID to set
	 */
	public void setPeerID(String peerID) {
		this.peerID = peerID;
	}

	public String toString()
	{
		if(message.size()>0)
		{
			String msgOutput = "";
			for(String str : message){
				msgOutput +=str+"CRLF"; 
			}
			return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+" "+this.getPeerID()+"CRLF"
					+msgOutput;
		}
		else if(this.getPeerID()==null)
		{
			return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+"CRLF";
		}
		return this.getOperation()+" "+this.getVersion()+" "+this.getNumOfLines()+" "+this.getPeerID()+"CRLF";
	}
}
