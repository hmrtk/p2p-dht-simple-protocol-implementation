/**
 * 
 */

/**
 * Class: Response
 * @author Hamidreza Tavakoli
 * Last revision: Feb 21, 2012 10:41:03 PM
 */
public class Response {
	private String version;
	private String operation;
	private int numOfLines;
	private String responseCode;
	private String responseCodeStr;
	private String[] message;

	public Response(){
		
	}
	
	public Response(String Version, String Operation, int NumOfLines, String ResponseCode, String ResponseCodeStr, String[] Message){
		this.version = Version;
		this.operation = Operation;
		this.numOfLines = NumOfLines;
		this.responseCode = ResponseCode;
		this.responseCodeStr = ResponseCodeStr;
		this.message = Message;
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
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseCodeStr
	 */
	public String getResponseCodeStr() {
		return responseCodeStr;
	}

	/**
	 * @param responseCodeStr the responseCodeStr to set
	 */
	public void setResponseCodeStr(String responseCodeStr) {
		this.responseCodeStr = responseCodeStr;
	}

	/**
	 * @return the message
	 */
	public String[] getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String[] message) {
		this.message = message;
	}

	public String toString()
	{
		if(message.length>0)
		{
			return "";
		}
		else{
			return this.getVersion()+" "+this.getOperation()+" "+this.getNumOfLines()+" "+this.getResponseCode()+" "+this.getResponseCodeStr()+"CRLF";
		} 
	}
}
