import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * this class represents the DFID algorithm used to find goal Node by limited iterative DFS
 * @author Edut Cohen
 *
 */
public class DFID implements Algorithm{

	long time=0;
	boolean isCutOff;
	int nodeCounter=1;


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
			else {  //no path was found
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
	 * solve function is responsible to call limited DFS with an increasing limit every iteration until limited DFS finds goal Node
	 */
	@Override
	public String solve(Node root, boolean toTime, boolean openListPrint) {
		long start=System.currentTimeMillis();
		if(!root.getState().colorSolvable()) {
			long end=System.currentTimeMillis();
			time=end-start;
			saveToFile(toTime, null , 1);
			return "no path";
		}
		for (int l=1 ; l < Integer.MAX_VALUE; l++) { 
			if(openListPrint)
				System.out.println("Iteration nodeCounterber "+l);
			HashMap<String, Node> H= new HashMap<String, Node>();
			Object[] result=this.limitDFS(root, l, H, openListPrint);
			if(!((String) result[0]).equalsIgnoreCase("cutOff")) {	
				Node goal=(Node) result[1];
				time=System.currentTimeMillis()-start;
				saveToFile(toTime,goal , nodeCounter);
				String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
				return "DFID result is:\n"+ goal.path().substring(0, goal.path().length()-1)+"\n"+"num: "+nodeCounter+"\ncost: "+goal.getCost()+"\n"+timeTemp+" seconds";
			}
		}
		time=System.currentTimeMillis()-start;
		saveToFile(toTime,null , nodeCounter);
		String timeTemp=String.format("%.4f",(time *Math.pow(10, -3)));
		return "no path"+timeTemp;
	}

	/**
	 * recursive limited DFS searching goal node starting with n until depth of limit 
	 * 
	 * @param n current Node to start from
	 * @param limit
	 * @param h hash map
	 * @param openListPrint
	 * @return Object array- first location is a String- "fail" or "cutOff" or the path if goal Node was found
	 * second location is a pointer to goal Node, null if was not found yet
	 */
	private Object[] limitDFS(Node n, int limit, HashMap<String, Node> h, boolean openListPrint) {
		if(n.isGoal()) { //n is a goal- return to solve function
			Object[] ans=new Object[2];
			ans[0]=n.path(); //String of path
			ans[1]=n; //goal Node
			return ans;
		}
		if(limit==0) {
			Object[] ans = {"cutOff", null}; //did not find within the given limit
			return ans;
		}
		h.put(n.getState().StringDesc(), n);
		isCutOff=false;
		Node l=n.exploreLeft(); //call in recursive to left son
		if(l!=null) {
			nodeCounter++;
			if(!h.containsKey(l.getState().StringDesc())) {//new Node
				Object[] result=limitDFS(l, limit-1, h, openListPrint);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node u=n.exploreUp(); //call in recursive to up son
		if(u!=null) {
			nodeCounter++;
			if(!h.containsKey(u.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(u, limit-1, h, openListPrint);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node r=n.exploreRight(); //call in recursive to right son
		if(r!=null) {
			nodeCounter++;
			if(!h.containsKey(r.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(r, limit-1, h, openListPrint);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		Node d=n.exploreDown();  //call in recursive to down son
		if(d!=null) {
			nodeCounter++;
			if(!h.containsKey(d.getState().StringDesc())) { //new Node
				Object[] result=limitDFS(d, limit-1, h, openListPrint);
				if(((String) result[0]).equalsIgnoreCase("cutOff"))
					isCutOff=true;
				else {
					if (!((String) result[0]).equalsIgnoreCase("fail"))
						return result;
				}
			}
		}
		if(openListPrint && !h.isEmpty()) { //prints openlist
			System.out.println("*********************************************************");
			for (Node node : h.values()) {
				System.out.println(node.getState().toString());
				System.out.println("            ----------------------");
			}
			System.out.println("*********************************************************");
		}
		h.remove(n.getState().StringDesc());
		if(isCutOff==true) { //one of my sons was cut off during search
			Object[] ans= {"cutOff", null}; //could not find goal because of limit
			return ans;
		}
		else {
			Object[] ans= {"fail", null}; //could not find goal, not because of limit- fail result
			return ans;
		}
	}

}
