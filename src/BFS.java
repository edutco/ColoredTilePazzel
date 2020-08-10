import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
/**
 * this class represents the BFS algorithm used to find goal Node by breadth search
 * @author Edut Cohen
 *
 */
public class BFS implements Algorithm{
	long time=0;
	int nodeCounter=1;
 
	/**
	 * after finding goal state and path to it, save the result to file 
	 */
	public  void saveToFile( boolean toTime,  Node g , int Num)  {		

		try 
		{
			if(g!=null) { //a path was found
				PrintWriter pw = new PrintWriter(new File(outputfile));
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
				PrintWriter pw = new PrintWriter(new File(outputfile));
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
	 * find a path to goal state using BFS algorithm (with loop avoidance)
	 */
	public  String solve(Node root, boolean toTime, boolean openListPrint) {
		Node goal=null;
		long start= System.currentTimeMillis();
		if(!root.getState().colorSolvable()) {
			long end=System.currentTimeMillis();
			time=end-start;
			saveToFile(toTime, goal, 1);
			return "no path";
		}
		Queue<Node> Q = new LinkedList<Node>(); 
		HashMap<String, Node> openListHash= new HashMap<String, Node>();
		Q.add(root);
		openListHash.put(root.getDesc(), root);
		HashMap<String, Node> closeList= new HashMap<String, Node>();
		while(!Q.isEmpty()) {
			if(openListPrint) {
				System.out.println("*********************************************************");
				for (Node node : Q) {
					System.out.println(node.getState().toString());
					System.out.println("            ----------------------");
				}
				System.out.println("*********************************************************");
			}
			Node current=Q.poll(); //remove the first Node from open list queue and open list hash
			openListHash.remove(current.getDesc(), current);
			closeList.put(current.getState().StringDesc(), current); //add it to close list
			Node l=current.exploreLeft();
			if(l!=null) {
				nodeCounter++;
				if(!openListHash.containsKey(l.getDesc()) //if not a new Node ignore
						&& !closeList.containsKey(l.getState().StringDesc())) { //Totally new node
					if(l.isGoal()) {  //if goal save to file and end searching
						goal=l;
						Q.add(l);
						long end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, nodeCounter);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(l); //add new Node to open list- queue and hash
					openListHash.put(l.getDesc(), l);
				}
			}
			Node u=current.exploreUp();
			if(u!=null) {
				nodeCounter++;
				if(!openListHash.containsKey(u.getDesc())  //if not a new Node ignore
						&& !closeList.containsKey(u.getState().StringDesc())) { //Totally new node
					if(u.isGoal()) {  //if goal save to file and end searching
						goal=u;
						Q.add(u);
						long end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, nodeCounter);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(u); //add new Node to open list- queue and hash
					openListHash.put(u.getDesc(), u);

				}
			}
			Node r=current.exploreRight();
			if(r!=null) {
				nodeCounter++;
				if(!openListHash.containsKey(r.getDesc())  //if not a new Node ignore
						&& !closeList.containsKey(r.getState().StringDesc())) { //Totally new node
					if(r.isGoal()) {  //if goal save to file and end searching
						goal=r;
						Q.add(r);
						long end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, nodeCounter);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(r); //add new Node to open list- queue and hash
					openListHash.put(r.getDesc(), r);

				}
			}
			Node d=current.exploreDown();
			if(d!=null) {
				nodeCounter++;
				if(!openListHash.containsKey(d.getDesc())  //if not a new Node ignore
						&& !closeList.containsKey(d.getState().StringDesc())) { //Totally new node
					if(d.isGoal()) {  //if goal save to file and end searching
						goal=d;
						Q.add(d);
						long end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, nodeCounter);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+nodeCounter+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(d); //add new Node to open list- queue and hash
					openListHash.put(d.getDesc(), d);

				}
			}

		}
		//if a goal Node was not found
		long end=System.currentTimeMillis();
		time=end-start;
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		saveToFile(toTime, goal, nodeCounter);
		return "no path\n"+timeTemp+" seconds";
	}
}
