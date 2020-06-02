import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class Astar implements Algorithm{
	private HashMap<String, Node> closedList= new HashMap<String, Node>();
	private PriorityQueue<Node> openList= new PriorityQueue<Node>(new NodeCompA());
	private long time=0;
	@Override
	public void saveToFile(boolean toTime, Node g, int Num) {
		try 
		{
			if(g!=null) {
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
			else {
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

	@Override
	public String solve(Node root, boolean toTime, boolean toOpenList) {
		long start= System.currentTimeMillis();
		ArrayList<Node> mySons;
		HashMap<String, Node> openListHash= new HashMap<String, Node>();
		Node node;
		boolean found=false;
		openList.add(root);
		openListHash.put(root.getState().StringDesc(), root);
		while (!openList.isEmpty()) {
			if(toOpenList) {
				System.out.println("*********************************************************");
				for (Node curr : openList) {
					System.out.println(curr.getState().toString());
					System.out.println("            ----------------------");
				}
				System.out.println("*********************************************************");
			}
			node = openList.poll();
			if (node.isGoal()) {
				found=true;
				time=System.currentTimeMillis()-start;
				String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
				saveToFile(toTime,  node, openListHash.size()+closedList.size());
				return "A* result is\n"+ node.path().substring(0, node.path().length()-1)+"\n"+"num:"+(openListHash.size()+closedList.size())+"\ncost: "+node.getCost()+"\n"+timeTemp+" seconds";
			}
			closedList.put(node.getState().StringDesc(), node);
			openListHash.remove(node.getState().StringDesc());
			mySons = node.expendAll();
			for (Node son : mySons) {
				if (closedList.containsKey(son.getState().StringDesc())) continue;
				else {
					String sonDesc= son.getState().StringDesc();
					if(openListHash.containsKey(sonDesc) && !closedList.containsKey(sonDesc)) { //such node already exists
						Node prevIdenticalNode=openListHash.get(sonDesc);
						if(son.getCost() < prevIdenticalNode.getCost()){ //if the new way to this node is cheaper
							prevIdenticalNode=son;
						}
					}
					else {
						openList.add(son);
						openListHash.put(son.getState().StringDesc(), son);
					}
				}
			}
		}
		if(!found){
			String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
			saveToFile(toTime,  null, openListHash.size()+closedList.size());
			return "no path\n"+timeTemp+" second";
		}
		return " ";
	}
}
