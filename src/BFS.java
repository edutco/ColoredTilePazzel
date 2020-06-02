import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class BFS implements Algorithm{
	long time;
	long start;
	long end;
	public  void saveToFile( boolean toTime,  Node g , int Num)  {		
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

	public  String solve(Node root, boolean toTime, boolean openList) {
		Node goal=null;
		start= System.currentTimeMillis();
		int i=1;
		Queue<Node> Q = new LinkedList<Node>(); 
		Q.add(root);
		HashMap<String, Node> openListFollow= new HashMap<String, Node>();
		HashMap<String, Node> closeList= new HashMap<String, Node>();
		while(!Q.isEmpty()) {
			if(openList) {
				System.out.println("*********************************************************");
				for (Node node : Q) {
					System.out.println(node.getState().toString());
					System.out.println("            ----------------------");
				}
				System.out.println("*********************************************************");
			}
			Node current=Q.poll();
			openListFollow.put(current.getState().StringDesc(), current);
			Node l=current.exploreLeft();
			if(l!=null) {
				i++;
				if(!openListFollow.containsKey(l.getDesc()) 
						&& !closeList.containsKey(l.getState().StringDesc())) { //Totally new node
					if(l.isGoal()) {  
						goal=l;
						Q.add(l);
						end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, i);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+i+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(l);
				}
			}
			Node u=current.exploreUp();
			if(u!=null) {
				i++;
				if(!openListFollow.containsKey(u.getDesc()) 
						&& !closeList.containsKey(u.getState().StringDesc())) { //Totally new node
					if(u.isGoal()) {  
						goal=u;
						Q.add(u);
						end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, i);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+i+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(u);
				}
			}
			Node r=current.exploreRight();
			if(r!=null) {
				i++;
				if(!openListFollow.containsKey(r.getDesc()) 
						&& !closeList.containsKey(r.getState().StringDesc())) { //Totally new node
					if(r.isGoal()) {  
						goal=r;
						Q.add(r);
						end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, i);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+i+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(r);
				}
			}
			Node d=current.exploreDown();
			if(d!=null) {
				i++;
				if(!openListFollow.containsKey(d.getDesc()) 
						&& !closeList.containsKey(d.getState().StringDesc())) { //Totally new node
					if(d.isGoal()) {  
						goal=d;
						Q.add(d);
						end=System.currentTimeMillis();
						time=end-start;
						String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
						saveToFile(toTime, goal, i);
						return "BFS result is\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num:"+i+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
					}
					Q.add(d);
				}
			}

		}
		end=System.currentTimeMillis();
		time=end-start;
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		saveToFile(toTime, goal, i);
		return "no path\n"+timeTemp+" seconds";
	}
}
