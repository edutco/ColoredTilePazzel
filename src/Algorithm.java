/**
 * this interface is implemented by all algorithm classes 
 * @author Edut Cohen 
 *
 */
public interface Algorithm {
	static String outputfile="output.txt";
	/**
	 * save result to file
	 */
	public void saveToFile( boolean toTime,  Node g , int Num);		
	/**
	 * search goal Node starting with root-initial state
	 * @param root
	 */
	public String solve(Node root, boolean toTime, boolean openList);

}
