/**
 * Class: Hash
 * @author Hamidreza Tavakoli
 * Last revision: Feb 17, 2012 4:10:05 PM
 */
public class Hash {

	private String Hashkey; //Represents the string 
	private int Value;	//Represents the asscii value for the Hashkey
	/**
	 * Constructor for Hash
	 */
	public Hash() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the hashkey
	 */
	public String getHashkey() {
		return Hashkey;
	}
	/**
	 * @param hashkey the hashkey to set
	 */
	public void setHashkey(String hashkey) {
		Hashkey = hashkey;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return Value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		Value = value;
	}
	

}
