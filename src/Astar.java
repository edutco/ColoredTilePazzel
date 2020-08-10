import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
/**
* this class represents the A* algorithm used to find the cheapest goal Node
* using a priority queue
* @author Edut Cohen
*
*/
public class Astar implements Algorithm{
	
	int nodeCounter=1;
	private long time=0;
	/**
	 * after finding goal state and path to it, save the result to file 
	 */
	@Override
	public void saveToFile(boolean toTime, Node g, int Num) {
		try 
		{
			if(g!=null) { //a path was found
				PrintWriter pw = new PrintWriter(new File("output.txt"));
				StringBuilder sb = new StringBuilder();
				sb=sb.append(g.path().substring(0, g.path().length()-1));
				sb=sb.append("\nNum: "+Num);
				sb=sb.append("\nCost: "+g.getCost());
				if(toTime) {
					String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
					sb=sb.append("\n"+timeTemp+" seconds");
				}
				pw.write(sb.toString());
				pw.close();
			}
			else { //no path was found
				PrintWriter pw = new PrintWriter(new File("output.txt"));
				StringBuilder sb = new StringBuilder();
				sb=sb.append("no path");
				sb=sb.append("\nNum: "+Num);
				if(toTime) {
					String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
					sb=sb.append("\n"+timeTemp+" seconds");
				}
				pw.write(sb.toString());
				pw.close();
			}

		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * find the cheapest path to goal state using A* algorithm
	 */
	@Override
	public String solve(Node root, boolean toTime, boolean openListPrint) {
		long start= System.currentTimeMillis();
		if(!root.getState().colorSolvable()) {
			long end=System.currentTimeMillis();
			time=end-start;
			saveToFile(toTime, null, nodeCounter);
			return "no path";
		}
		HashMap<String, Node> closedList= new HashMap<String, Node>();
		PriorityQueue<Node> openList= new PriorityQueue<Node>(new NodeComp());
		HashMap<String, Node> openListHash= new HashMap<String, Node>();
		root.setKey(nodeCounter);
		ArrayList<Node> mySons;
		Node node;
		boolean found=false;
		openList.add(root); //add initial state to open list- queue and hash
		openListHash.put(root.getState().StringDesc(), root);
		while (!openList.isEmpty()) {
			if(openListPrint) {
				System.out.println("*********************************************************");
				for (Node curr : openList) {
					System.out.println(curr.getState().toString());
					System.out.println("            ----------------------");
				}
				System.out.println("*********************************************************");
			}
			node = openList.poll(); //remove cheapest Node from open list- queue and hash
			openListHash.remove(node.getDesc());
			if (node.isGoal()) { //if is goal save result and return.
				found=true;
				time=System.currentTimeMillis()-start;
				String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
				saveToFile(toTime,  node, nodeCounter);
				return "A* result is\n"+ node.path().substring(0, node.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+node.getCost()+"\n"+timeTemp+" seconds";
			}
			//add the current Node to close list after removing it from open list 
			closedList.put(node.getState().StringDesc(), node);
			mySons = node.expendAll();
			for (Node son : mySons) {
				son.setKey(++nodeCounter); //key is used in priority queue sort
				String sonDesc= son.getState().StringDesc();
				if(!openListHash.containsKey(sonDesc) && !closedList.containsKey(sonDesc)) { //new Node
					openList.add(son); //add state to open list- queue and hash
					openListHash.put(sonDesc, son);
				}
				else {
					if(openListHash.containsKey(sonDesc) && openListHash.get(sonDesc).getCost() > son.getCost()) { 
						//if identical state is in the open list and current Node is cheaper, replace these Nodes 
						Node prevIdenticalNode=openListHash.get(sonDesc);
						openList.remove(prevIdenticalNode);
						openList.add(son);
						openListHash.remove(sonDesc);
						openListHash.put(sonDesc, son);
					}
				}
			}
		}
		if(!found){ //no goal Node was found
			String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
			saveToFile(toTime,  null, nodeCounter);
			return "no path\n"+timeTemp+" second";
		}
		return " ";
	}
}
